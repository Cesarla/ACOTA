package org.weso.acota.core.business.enhancer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.tika.language.LanguageIdentifier;
import org.weso.acota.core.CoreConfiguration;
import org.weso.acota.core.business.enhancer.EnhancerAdapter;
import org.weso.acota.core.business.enhancer.analyzer.opennlp.OpenNLPAnalyzer;
import org.weso.acota.core.business.enhancer.analyzer.opennlp.SpanishOpenNLPAnalyzer;
import org.weso.acota.core.entity.ProviderTO;
import org.weso.acota.core.entity.TagTO;
import org.weso.acota.core.exceptions.AcotaConfigurationException;

import static org.weso.acota.core.utils.LanguageUtil.ISO_639_SPANISH;

/**
 * OpenNLPEnhancer is an {@link Enhancer} specialized in modifying
 * the weights, of the sets of{@link TagTO}s, depending on its morphosyntactic type.
 * 
 * @author César Luis Alvargonzález
 * @author Weena Jimenez
 */
public class OpenNLPEnhancer extends EnhancerAdapter implements Configurable {
	
	protected static final String[] nplTokensEs = new String[] { "SPC", "P", "DI",
		"PR", "CC", "PP", "CS", "DD", "DP", "RG", "AO", "SPS", "AQ", "W",
		"DN", "PD", "PI", "PN", "PT", "RN", "Y", "DT", "I", "DE" };
	
	protected static Logger logger;
	protected static Set<String> tokensEs;

	protected Set<String> noun;
	protected Set<String> verbs;
	protected Set<String> numbers;
	
	protected OpenNLPAnalyzer analyzer;
	
	protected CoreConfiguration configuration;
	
	/**
	 * Zero-argument default constructor
	 * @throws AcotaConfigurationException Any exception that occurs while initializing 
	 * a Configuration object
	 */
	public OpenNLPEnhancer() throws AcotaConfigurationException {
		super();
		OpenNLPEnhancer.logger = Logger.getLogger(OpenNLPEnhancer.class);
		OpenNLPEnhancer.provider = new ProviderTO("OpenNPL tagger");
		OpenNLPEnhancer.tokensEs = new HashSet<String>(Arrays.asList((nplTokensEs)));
		this.noun = new HashSet<String>();
		this.verbs = new HashSet<String>();
		this.numbers = new HashSet<String>();
		
		loadConfiguration(configuration);
	}
	
	@Override
	public void loadConfiguration(CoreConfiguration configuration) throws AcotaConfigurationException{
		if(configuration==null)
			configuration = new CoreConfiguration();
		this.configuration = configuration;
		this.analyzer = new SpanishOpenNLPAnalyzer(configuration);
	}
	
	@Override
	protected void preExecute() throws Exception {
		this.suggest = request.getSuggestions();
		this.tags = suggest.getTags();
		suggest.setResource(request.getResource());
		
		noun.clear();
		verbs.clear();
		numbers.clear();
	}
	
	@Override
	protected void execute() throws Exception {
		analyseLabelTerms();
		analyseDescriptionTerms();
	}

	@Override
	protected void postExecute() throws Exception {
		logger.debug("Add providers to request");
		this.request.getTargetProviders().add(provider);
		logger.debug("Add suggestons to request");
		this.request.setSuggestions(suggest);
	}

	/**
	 * Makes an Analysis of the label terms
	 * @throws IOException Any exception that occurs while reading OpenNLP's files
	 */
	protected void analyseLabelTerms() throws IOException {
		analysisOfTerms(request.getResource().getLabel());
	}

	/**
	 * Makes an Analysis of the descriptions terms
	 * @throws IOException Any exception that occurs while reading OpenNLP's files
	 */
	protected void analyseDescriptionTerms() throws IOException {
		analysisOfTerms(request.getResource().getDescription());
	}

	/**
	 * Makes an Analysis of a text's terms
	 * @param text Text to make the analysis
	 * @throws IOException Any exception that occurs while reading OpenNLP's files
	 */
	public void analysisOfTerms(String text) throws IOException {
		LanguageIdentifier ld = new LanguageIdentifier(text);

		if (ld.getLanguage().equals(ISO_639_SPANISH)) {
			String sentences[] = analyzer.sentDetect(suggest
					.getResource().getDescription());
			for (String sentence : sentences) {
				String[] textTokenized = analyzer.tokenize(sentence);
				processSetence(analyzer.tag(textTokenized), textTokenized);
			}
		}

		findAndChangeNoun();
		findAndChangeVerbs();
		findAndChangeNumbers();
	}

	/**
	 * Processes a set of terms and saves them depending on its morphosyntactic type
	 * @param tags OpenNLP Tags related to the Tokenized Text
	 * @param tokenizedText Tokenized Text
	 */
	protected void processSetence(String[] tags, String[] tokenizedText) {
		for (int y = 0; y < tokenizedText.length; y++) {
			if (tokensEs.contains(tags[y])) {
				findAndRemove(tokenizedText[y]);
			} else if (tags[y].startsWith("N")) {
				noun.add(tokenizedText[y].toLowerCase());
			} else if (tags[y].startsWith("V")) {
				verbs.add(tokenizedText[y].toLowerCase());
			} else if (tags[y].startsWith("Z")) {
				numbers.add(tokenizedText[y].toLowerCase());
			}
		}
	}

	/**
	 * Calculates the maximum value of the tags Map
	 * @return The maximum value of the tags Map
	 */
	protected double calculateMaxValue() {
		List<TagTO> list = new ArrayList<TagTO>(tags.values());
		Collections.sort(list);
		double value = 0d;
		if(list.size()>0)
			value = list.get(0).getValue();
		return value;
	}

	/**
	 * Removes a word from the tags Map
	 * @param label Label of the tag to remove
	 */
	protected void findAndRemove(String label) {
		logger.debug("Remove some tags");
		if (tags.containsKey(label.toLowerCase())) {
			tags.remove(label.toLowerCase());
		}
	}

	/**
	 * Modifies Nouns' weight. Adds the half of the maximum weight.
	 */
	protected void findAndChangeNoun() {
		double sum = calculateMaxValue() / 2;

		for (Entry<String, TagTO> label : tags.entrySet()) {
			if (noun.contains(label.getKey())) {
				label.getValue().addValue(sum);
			}
		}
	}

	/**
	 * Modifies Verbs' weight. Subtracts the half of the maximum weight.
	 */
	protected void findAndChangeVerbs() {
		double sum = calculateMaxValue() / 2;

		for (Entry<String, TagTO> label : tags.entrySet()) {
			if (verbs.contains(label.getKey())) {
				label.getValue().subValue(sum);
			}
		}
	}

	/**
	 * Modifies Numbers' weight. Subtracts the maximum weight.
	 */
	protected void findAndChangeNumbers() {
		double value = calculateMaxValue();
		for (Entry<String, TagTO> label : tags.entrySet()) {
			if (numbers.contains(label.getKey())) {
				label.getValue().subValue(value);
			}
		}
	}

}