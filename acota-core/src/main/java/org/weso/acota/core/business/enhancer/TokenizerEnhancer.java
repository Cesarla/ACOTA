package org.weso.acota.core.business.enhancer;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import opennlp.tools.lang.spanish.PosTagger;
import opennlp.tools.lang.spanish.SentenceDetector;
import opennlp.tools.postag.POSTagger;
import opennlp.tools.tokenize.SimpleTokenizer;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.StringUtils;
import org.weso.acota.core.Configuration;
import org.weso.acota.core.entity.ProviderTO;
import org.weso.acota.core.entity.TagTO;

/**
 * 
 * @author César Luis Alvargonzález
 *
 */
public class TokenizerEnhancer extends EnhancerAdapter implements Configurable {

	protected static final String[] nplTokensEs= new String[] { "NC", "NP", "VA", "VIP", "VM", "VS",
		"W", "Z", "ZM", "ZP", "AO", "AQ" };
	
	protected static final String DESCIPTION = "description";
	protected static final String LABEL = "label";

	protected double luceneRelevanceLabel;
	protected double luceneRelevanceTerm;
	protected String esSentBin;
	protected String esPosBin;

	protected int k = 5;
	
	protected Map<StringArrayWrapper, Double> auxiliar;
	protected Set<String> tokens;

	protected Pattern pattern;
	
	protected SentenceDetector esSentenceDetector;
	protected SimpleTokenizer tokenizer;
	protected POSTagger esPOSTagger;

	protected Configuration configuration;

	/**
	 * Zero-argument default constructor
	 * @throws ConfigurationException Any exception that occurs while initializing 
	 * a Configuration object
	 * @throws IOException If there is some issue reading OpenNLP's files
	 */
	public TokenizerEnhancer() throws ConfigurationException, IOException {
		super();
		
		this.auxiliar = new HashMap<StringArrayWrapper, Double>();
		this.tokenizer = new SimpleTokenizer();
		this.pattern = Pattern.compile("[^A-Za-z\\d]");
		this.tokens = new HashSet<String>(Arrays.asList(nplTokensEs));
		
		loadConfiguration(configuration);
		
		this.esSentenceDetector = new SentenceDetector(esSentBin);
		this.esPOSTagger = new PosTagger(esPosBin);
		
		LuceneEnhancer.provider = new ProviderTO("Tokenizer Analizer");
	}

	@Override
	public void loadConfiguration(Configuration configuration)
			throws ConfigurationException {
		if (configuration == null)
			configuration = new Configuration();
		this.configuration = configuration;
		this.luceneRelevanceLabel = configuration.getLuceneLabelRelevance();
		this.luceneRelevanceTerm = configuration.getLuceneTermRelevance();
		this.esPosBin = configuration.getOpenNLPesPosBin();
		this.esSentBin = configuration.getOpenNLPesSentBin();
	}

	@Override
	protected void execute() throws Exception {
		extractLabelTerms();
		extractDescriptionTerms();
	}

	@Override
	protected void preExecute() throws Exception {
		this.suggest = request.getSuggestions();
		this.tags = suggest.getTags();
		suggest.setResource(request.getResource());
		
		auxiliar.clear();
	}

	@Override
	protected void postExecute() throws Exception {
		logger.debug("Add providers to request");
		this.request.getTargetProviders().add(provider);
		logger.debug("Add suggestons to request");
		this.request.setSuggestions(suggest);
	}

	protected void extractDescriptionTerms() throws IOException {
		extractTerms(DESCIPTION, request.getResource().getDescription(),
				luceneRelevanceTerm);
	}

	protected void extractLabelTerms() throws IOException {
		extractTerms(LABEL, request.getResource().getLabel(),
				luceneRelevanceLabel);
	}

	protected void extractTerms(String title, String text, double relevance)
			throws IOException {
		String[] sentences = esSentenceDetector.sentDetect(text);
		for (String sentence : sentences) {
			loadChunks(tokenizer.tokenize(sentence), relevance);
		}
		analysisOfTerms(relevance);

	}

	protected void loadChunks(String[] chunks, double relevance) {
		for (int i = 1; i <= k; i++) {
			for (int j = 0; j + i <= chunks.length; j++) {
				addString(
						cleanChunks(Arrays.copyOfRange(chunks, j, i + j - 1)),
						relevance);
			}
		}
	}

	protected StringArrayWrapper cleanChunks(String[] chunk) {
		List<String> auxList = new LinkedList<String>();
		for (int i = 0; i < chunk.length; i++) {
			if (!pattern.matcher(chunk[i]).find()) {
				if (!((i == 0 || i == chunk.length - 1) && chunk[i].length() <= 2))
					auxList.add(chunk[i]);
			}
		}
		String[] finalChunk = new String[auxList.size()];
		return new StringArrayWrapper(auxList.toArray(finalChunk));

	}

	protected void addString(StringArrayWrapper phrase, double relevance) {
		Double value = auxiliar.get(phrase);
		if (value == null)
			value = 0d;
		value += relevance;
		auxiliar.put(phrase, value);
	}

	public void analysisOfTerms(double relevance) throws IOException {
		for (Entry<StringArrayWrapper, Double> value : auxiliar.entrySet()) {
			processSetence(esPOSTagger.tag(value.getKey().data),
					value.getKey().data, relevance);
		}
	}

	protected void processSetence(String[] tags, String[] textTokenized,
			double relevance) {
		int min = calculateMin(tags);
		int max = calculateMax(tags);
		if (min <= max && min >= 0 && max >= 0) {

			TagTO tag = new TagTO(StringUtils.join(
					Arrays.copyOfRange(textTokenized, min, max + 1), " "),
					provider, request.getResource());
			fillSuggestions(tag, relevance);
		}
	}

	/**
	 * Calculates the bigger valid index of the array
	 * @param tags Array of OpenNLP tags
	 * @return The bigger valid index of the array
	 * @return -1 If there is no valid tags
	 */
	protected int calculateMax(String[] tags) {
		for (int i = tags.length - 1; i >= 0; i--) {
			if (tokens.contains(tags[i]))
				return i;
		}
		return -1;
	}

	/**
	 * Calculates the lower valid index of the array
	 * @param tags Array of OpenNLP tags
	 * @return The lower valid index of the array
	 * @return -1 If there is no valid tags
	 */
	protected int calculateMin(String[] tags) {
		for (int i = 0; i < tags.length; i++) {
			if (tokens.contains(tags[i]))
				return i;
		}
		return -1;
	}

	/**
	 * Inner Class StringArrayWrapper is a wrap of a String[], so
	 * it could be used as key in Maps or similar
	 * data structures.
	 * @author César Luis Alvargonzález
	 *
	 */
	public final class StringArrayWrapper {
		protected final String[] data;

		/**
		 * One-argument default constructor.
		 * @param data String[] to store
		 */
		public StringArrayWrapper(String[] data) {
			if (data == null) {
				throw new NullPointerException();
			}
			this.data = data;
		}

		@Override
		public boolean equals(Object other) {
			if (!(other instanceof StringArrayWrapper)) {
				return false;
			}
			return Arrays.equals(data, ((StringArrayWrapper) other).data);
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(data);
		}

		@Override
		public String toString() {
			return Arrays.toString(data);
		}

	}

}
