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
 * operations for English text
 * @author César Luis Alvargonzález
 */
public class EnglishOpenNLPAnalyzer extends OpenNLPAnalyzerAdapter implements OpenNLPAnalyzer{
	
	/**
	 * Default Constructor
	 * @param configuration Acota's Configuration Object
	 * @throws AcotaConfigurationException An exception that occurs 
	 * while installing and configuration Acota
	 */
	public EnglishOpenNLPAnalyzer(CoreConfiguration configuration) throws AcotaConfigurationException {
		loadConfiguration(configuration);
	}
	
	/**
	 * @see org.weso.acota.core.business.enhancer.Configurable#loadConfiguration(CoreConfiguration)
	 */
	@Override
	public void loadConfiguration(CoreConfiguration configuration) throws AcotaConfigurationException {
		try{
			this.sentenceDetector = new SentenceDetector(configuration.getOpenNlpEnSentBin());
			this.posTagger = new PosTagger(configuration.getOpenNlpEnPosBin());
			this.tokenizer = new Tokenizer(configuration.getOpenNlpEnTokBin());
			this.tokens = new HashSet<String>(configuration.getOpenNlpEnTokens());
			this.nouns = new HashSet<String>(configuration.getOpenNlpEnNouns());
			this.verbs = new HashSet<String>(configuration.getOpenNlpEnVerbs());
			this.numbers = new HashSet<String>(configuration.getOpenNlpEnNumbers());
		} catch (IOException e) {
			throw new AcotaConfigurationException(e);
		}
	}

}
