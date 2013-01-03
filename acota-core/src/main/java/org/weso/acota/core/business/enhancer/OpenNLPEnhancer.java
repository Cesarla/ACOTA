package org.weso.acota.core.business.enhancer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import opennlp.tools.lang.spanish.PosTagger;
import opennlp.tools.lang.spanish.SentenceDetector;
import opennlp.tools.postag.POSTagger;
import opennlp.tools.tokenize.SimpleTokenizer;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Logger;
import org.apache.tika.language.LanguageIdentifier;
import org.weso.acota.core.Configuration;
import org.weso.acota.core.business.enhancer.EnhancerAdapter;
import org.weso.acota.core.entity.ProviderTO;
import org.weso.acota.core.entity.RequestSuggestionTO;
import org.weso.acota.core.entity.TagTO;

import static org.weso.acota.core.utils.LanguageUtil.ISO_639_SPANISH;

/**
 * 
 * @author César Luis Alvargonzález
 * @author Weena
 *
 */
public class OpenNLPEnhancer extends EnhancerAdapter implements Configurable {
	
	protected static final String[] nplTokensEs = new String[] { "SPC", "P", "DI",
		"PR", "CC", "PP", "CS", "DD", "DP", "RG", "AO", "SPS", "AQ", "W",
		"DN", "PD", "PI", "PN", "PT", "RN", "Y", "DT", "I", "DE" };
	
	protected static Logger logger;
	protected static Set<String> tokensEs;
	
	protected String esSentBin;
	protected String esPosBin;
	
	protected double maxWeight;

	protected Set<String> pronouns;
	protected Set<String> verbs;
	protected Set<String> numbers;
	
	protected Configuration configuration;
	
	public OpenNLPEnhancer() throws ConfigurationException {
		super();
		OpenNLPEnhancer.logger = Logger.getLogger(OpenNLPEnhancer.class);
		OpenNLPEnhancer.provider = new ProviderTO("OpenNPL tagger");
		loadConfiguration(configuration);
		
		this.pronouns = new HashSet<String>();
		this.verbs = new HashSet<String>();
		this.numbers = new HashSet<String>();
		tokensEs = new HashSet<String>(Arrays.asList((nplTokensEs)));
	}
	
	@Override
	public void loadConfiguration(Configuration configuration) throws ConfigurationException{
		if(configuration==null)
			configuration = new Configuration();
		this.configuration = configuration;
		this.esPosBin = configuration.getOpenNLPesPosBin();
		this.esSentBin = configuration.getOpenNLPesSentBin();
	}
	
	@Override
	protected void preExecute() throws Exception {
		this.suggest = request.getSuggestions();
		this.tags = suggest.getTags();
		suggest.setResource(request.getResource());
	}
	
	
	@Override
	protected void execute() throws Exception {
		analyseLabelTerms(request);
		analyseDescriptionTerms(request);
	}

	@Override
	protected void postExecute() throws Exception {
		logger.debug("Add providers to request");
		this.request.getTargetProviders().add(provider);
		logger.debug("Add suggestons to request");
		this.request.setSuggestions(suggest);
	}

	protected void analyseLabelTerms(RequestSuggestionTO request) throws IOException {
		analysisOfTerms(request.getResource().getLabel());
	}

	protected void analyseDescriptionTerms(RequestSuggestionTO request) throws IOException {
		analysisOfTerms(request.getResource().getDescription());
	}

	public void analysisOfTerms(String terms) throws IOException {
		LanguageIdentifier ld = new LanguageIdentifier(terms);

		if (ld.getLanguage().equals(ISO_639_SPANISH)) {
			SimpleTokenizer tokenizer = new SimpleTokenizer();
			SentenceDetector sentencesDetector = new SentenceDetector(esPosBin);
			
			POSTagger spost = new PosTagger(esSentBin);
			
			String sentences[] = sentencesDetector.sentDetect(suggest
					.getResource().getDescription());
			for (String sentence : sentences) {
				String[] textTokenized = tokenizer.tokenize(sentence);
				processSetence(spost.tag(textTokenized), textTokenized);
			}
		}

		findAndChangePronouns(pronouns);
		findAndChangeVerbs(verbs);
		findAndChangeNumbers(numbers);
	}

	protected void processSetence(String[] tags, String[] textTokenized) {
		for (int y = 0; y < textTokenized.length; y++) {
			if (tokensEs.contains(tags[y])) {
				findAndRemove(textTokenized[y]);
			} else if (tags[y].startsWith("N")) {
				pronouns.add(textTokenized[y].toLowerCase());
			} else if (tags[y].startsWith("V")) {
				verbs.add(textTokenized[y].toLowerCase());
			} else if (tags[y].startsWith("Z")) {
				numbers.add(textTokenized[y].toLowerCase());
			}
		}
	}

	protected void calculateMaxValue() {
		List<TagTO> list = new ArrayList<TagTO>(tags.values());
		Collections.sort(list);
		if(list.size()>0)
			maxWeight = list.get(0).getValue();
	}

	/**
	 * Remove a word from labels
	 * 
	 * @param word
	 */
	protected void findAndRemove(String word) {
		logger.debug("Remove some tags");
		if (tags.containsKey(word.toLowerCase())) {
			tags.remove(word.toLowerCase());
		}
	}

	/**
	 * Modify Pronouns
	 * 
	 * @param words
	 */
	protected void findAndChangePronouns(Set<String> words) {
		calculateMaxValue();
		double sum = maxWeight / 2;

		for (Entry<String, TagTO> label : tags.entrySet()) {
			if (words.contains(label.getKey())) {
				label.getValue().addValue(sum);
			}
		}
	}

	/**
	 * Modify Verbs
	 * 
	 * @param words
	 */

	protected void findAndChangeVerbs(Set<String> words) {
		calculateMaxValue();
		double sum = maxWeight / 2;

		for (Entry<String, TagTO> label : tags.entrySet()) {
			if (words.contains(label.getKey())) {
				label.getValue().subValue(sum);
			}
		}

	}

	/**
	 * Modify Numbers
	 * 
	 * @param words
	 */
	protected void findAndChangeNumbers(Set<String> words) {
		calculateMaxValue();

		for (Entry<String, TagTO> label : tags.entrySet()) {
			if (words.contains(label.getKey())) {
				label.getValue().subValue(maxWeight);
			}
		}

	}

}