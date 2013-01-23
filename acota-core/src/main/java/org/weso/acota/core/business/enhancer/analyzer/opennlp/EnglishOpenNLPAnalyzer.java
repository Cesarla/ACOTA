package org.weso.acota.core.business.enhancer.analyzer.opennlp;

import java.io.IOException;

import opennlp.tools.lang.spanish.PosTagger;
import opennlp.tools.lang.spanish.SentenceDetector;
import opennlp.tools.lang.spanish.Tokenizer;

import org.weso.acota.core.Configuration;
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
	public EnglishOpenNLPAnalyzer(Configuration configuration) throws AcotaConfigurationException {
		loadConfiguration(configuration);
	}
	
	/**
	 * @see org.weso.acota.core.business.enhancer.Configurable#loadConfiguration(Configuration)
	 */
	@Override
	public void loadConfiguration(Configuration configuration) throws AcotaConfigurationException {
		try{
			this.sentenceDetector = new SentenceDetector(configuration.getOpenNLPenSentBin());
			this.posTagger = new PosTagger(configuration.getOpenNLPenPosBin());
			this.tokenizer = new Tokenizer(configuration.getOpenNLPenTokBin());
		} catch (IOException e) {
			throw new AcotaConfigurationException(e);
		}
	}

}
