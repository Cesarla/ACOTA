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

import opennlp.maxent.MaxentModel;
import opennlp.tools.lang.spanish.PosTagger;
import opennlp.tools.lang.spanish.SentenceDetector;
import opennlp.tools.postag.POSTagger;
import opennlp.tools.tokenize.SimpleTokenizer;

import opennlp.tools.lang.spanish.Tokenizer;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.StringUtils;
import org.weso.acota.core.Configuration;
import org.weso.acota.core.entity.ProviderTO;
import org.weso.acota.core.entity.TagTO;

/**
 * TokenizerEnhancer is an {@link Enhancer} specialized in tokenizing, removing stop-words and
 * cleaning the input data, producing a set of k-word {@link TagTO}s, k is configurable and
 * supplied by {@link Configuration}.
 * 
 * @author César Luis Alvargonzález
 */
public class TokenizerEnhancer extends EnhancerAdapter implements Configurable {

	protected static final String[] nplTokensEs= new String[] { "NC", "NP", "VA", "VIP", "VM", "VS",
		"W", "Z", "ZM", "ZP", "AO", "AQ" };
	
	protected static final String DESCIPTION = "description";
	protected static final String LABEL = "label";

	protected double tokenizerRelevanceLabel;
	protected double tokenizerRelevanceTerm;
	protected String esSentBin;
	protected String esPosBin;

	protected int k;
	
	protected Map<StringArrayWrapper, Double> auxiliar;
	protected Set<String> tokens;

	protected Pattern pattern;
	
	protected SentenceDetector esSentenceDetector;
	protected Tokenizer tokenizer;
	protected POSTagger esPOSTagger;

	protected Configuration configuration;

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

	/**
	 * Zero-argument default constructor
	 * @throws ConfigurationException Any exception that occurs while initializing 
	 * a Configuration object
	 * @throws IOException If there is some issue reading OpenNLP's files
	 */
	public TokenizerEnhancer() throws ConfigurationException, IOException {
		super();
		
		this.tokens = new HashSet<String>(Arrays.asList(nplTokensEs));
		
		this.auxiliar = new HashMap<StringArrayWrapper, Double>();
		this.tokenizer = new Tokenizer("/etc/acota/open_nlp/es/SpanishTok.bin");
		this.pattern = Pattern.compile("[^A-Za-z\\d]");
		
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
		this.tokenizerRelevanceLabel = configuration.getTokenizerLabelRelevance();
		this.tokenizerRelevanceTerm = configuration.getTokenizerTermRelevance();
		this.k = configuration.getTokenizerK();
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
	}

	@Override
	protected void postExecute() throws Exception {
		logger.debug("Add providers to request");
		this.request.getTargetProviders().add(provider);
		logger.debug("Add suggestons to request");
		this.request.setSuggestions(suggest);
	}

	/**
	 * Extracts Label terms
	 * @throws IOException Signals that an I/O exception of some sort has occurred
	 */
	protected void extractLabelTerms() throws IOException {
		extractTerms(LABEL, request.getResource().getLabel(),
				tokenizerRelevanceLabel);
	}
	
	/**
	 * Extracts Description terms
	 * @throws IOException Signals that an I/O exception of some sort has occurred
	 */
	protected void extractDescriptionTerms() throws IOException {
		extractTerms(DESCIPTION, request.getResource().getDescription(),
				tokenizerRelevanceTerm);
	}

	/**
	 * Tokenizes and removes stop-words (Spanish and English) from the supplied text
	 * @param title FieldName (description, label)
	 * @param text Text to extract the terms
	 * @param relevance Weight which is incremented each matched term
	 * @throws IOException Signals that an I/O exception of some sort has occurred
	 */
	protected void extractTerms(String title, String text, double relevance)
			throws IOException {
		String[] sentences = esSentenceDetector.sentDetect(text);
		auxiliar.clear();
		for (String sentence : sentences) {
			System.out.println(sentence);
			loadChunks(tokenizer.tokenize(sentence), relevance);
		}
		analysisOfTerms(relevance);

	}

	/**
	 * Generates the k-words saving them into a auxiliary map
	 * @param tokenizedText Tokenized Text
	 * @param relevance Weight which is incremented each matched term
	 */
	protected void loadChunks(String[] tokenizedText, double relevance) {
		for (int i = 1; i <= k; i++) {
			for (int j = 0; j + i <= tokenizedText.length; j++) {
				addString(
						cleanChunks(Arrays.copyOfRange(tokenizedText, j, i + j - 1)),
						relevance);
			}
		}
	}

	/**
	 * Trims any word of the edges that not fits with:
	 * 	<ul>
	 * 		<li>Characters</li>
	 * 		<li>Numbers</li>
	 * 		<li>Length (of the word) bigger than 2</li>
	 * 	</ul>
	 * 
	 * @param chunk
	 * @return 
	 */
	protected String[] cleanChunks(String[] chunk) {
		List<String> auxList = new LinkedList<String>();
		for (int i = 0; i < chunk.length; i++) {
			if (!pattern.matcher(chunk[i]).find()) {
				if (!((i == 0 || i == chunk.length - 1) && chunk[i].length() <= 2))
					auxList.add(chunk[i]);
			}
		}
		String[] finalChunk = new String[auxList.size()];
		return auxList.toArray(finalChunk);

	}

	/**
	 * Adds or Increases (If currently exists) the label to the auxiliary labels map 
	 * @param label Label's name
	 * @param relevance Weight which is incremented each matched term
	 */
	protected void addString(String[] label, double relevance) {
		StringArrayWrapper stringArrayWraper = new StringArrayWrapper(label);
		Double value = auxiliar.get(stringArrayWraper);
		if (value == null)
			value = 0d;
		value += relevance;
		auxiliar.put(stringArrayWraper, value);
	}

	/**
	 * Process the auxiliary 
	 * @param relevance Weight which is incremented each matched term
	 * @throws IOException Signals that an I/O exception of some sort has occurred
	 */
	protected void analysisOfTerms(double relevance) throws IOException {
		for (Entry<StringArrayWrapper, Double> value : auxiliar.entrySet()) {
			processSetence(esPOSTagger.tag(value.getKey().data),
					value.getKey().data, relevance);
		}
	}

	/**
	 * Trims the labels removing non meaningful words from the edges of the sentence
	 * @param tags OpenNLP Tags related to the Tokenized Text
	 * @param tokenizedText Tokenized Text
	 * @param relevance Weight which is incremented each matched term
	 */
	protected void processSetence(String[] tags, String[] tokenizedText,
			double relevance) {
		int min = calculateMin(tags);
		int max = calculateMax(tags);
		if (min <= max && min >= 0 && max >= 0) {

			TagTO tag = new TagTO(StringUtils.join(
					Arrays.copyOfRange(tokenizedText, min, max + 1), " "),
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

}
