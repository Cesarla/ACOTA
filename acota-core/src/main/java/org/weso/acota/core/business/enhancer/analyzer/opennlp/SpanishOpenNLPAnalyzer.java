package org.weso.acota.core.business.enhancer.analyzer.opennlp;

import java.io.IOException;
import java.util.HashSet;

import opennlp.tools.lang.spanish.PosTagger;
import opennlp.tools.lang.spanish.SentenceDetector;
import opennlp.tools.lang.spanish.Tokenizer;

import org.weso.acota.core.CoreConfiguration;
import org.weso.acota.core.exceptions.AcotaConfigurationException;

/**
 * Analyzer specialized in perform NLP (Natural Language Processing)
 * operations for Spanish text
 * @author César Luis Alvargonzález
 */
public class SpanishOpenNLPAnalyzer extends OpenNLPAnalyzerAdapter implements
		OpenNLPAnalyzer {

	/**
	 * Default Constructor
	 * @param configuration Acota's Configuration Object
	 * @throws AcotaConfigurationException An exception that occurs 
	 * while installing and configuration Acota
	 */
	public SpanishOpenNLPAnalyzer(CoreConfiguration configuration)
			throws AcotaConfigurationException {
		loadConfiguration(configuration);
	}

	/**
	 * @see org.weso.acota.core.business.enhancer.Configurable#loadConfiguration(CoreConfiguration)
	 */
	@Override
	public void loadConfiguration(CoreConfiguration configuration)
			throws AcotaConfigurationException {
		try {
			this.sentenceDetector = new SentenceDetector(
					configuration.getOpenNlpEsSentBin());
			this.posTagger = new PosTagger(configuration.getOpenNlpEsPosBin());
			this.tokenizer = new Tokenizer(configuration.getOpenNlpEsTokBin());
			this.tokens = new HashSet<String>(configuration.getOpenNlpEsTokens());
			this.nouns = new HashSet<String>(configuration.getOpenNlpEsNouns());
			this.verbs = new HashSet<String>(configuration.getOpenNlpEsVerbs());
			this.numbers = new HashSet<String>(configuration.getOpenNlpEsNumbers());
		} catch (IOException e) {
			throw new AcotaConfigurationException(e);
		}
	}

}
