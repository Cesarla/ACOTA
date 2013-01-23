package org.weso.acota.core.business.enhancer.analyzer.tokenizer;

import java.io.IOException;
import java.util.regex.Pattern;

import opennlp.tools.lang.spanish.PosTagger;
import opennlp.tools.lang.spanish.SentenceDetector;
import opennlp.tools.lang.spanish.Tokenizer;

import org.weso.acota.core.Configuration;
import org.weso.acota.core.exceptions.AcotaConfigurationException;


/**
 * Analyzer specialized in perform English auxiliary operations such as NLP (Natural 
 * Language Processing) operations for Spanish or perform regular expression matching.
 * @author César Luis Alvargonzález
 */
public class EnglishTokenizerAnalyzer extends TokenizerAnalyzerAdapter implements TokenizerAnalyzer{
	
	/**
	 * Default Constructor
	 * @param configuration Acota's Configuration Object
	 * @throws AcotaConfigurationException An exception that occurs 
	 * while installing and configuration Acota
	 */
	public EnglishTokenizerAnalyzer(Configuration configuration)
			throws AcotaConfigurationException {
		loadConfiguration(configuration);
	}
	
	/**
	 * @see org.weso.acota.core.business.enhancer.Configurable#loadConfiguration(Configuration)
	 */
	@Override
	public void loadConfiguration(Configuration configuration) throws AcotaConfigurationException {
		try{
			this.pattern = Pattern.compile(configuration.getTokenizerEnPattern());
			this.sentenceDetector = new SentenceDetector(configuration.getOpenNLPenSentBin());
			this.posTagger = new PosTagger(configuration.getOpenNLPenPosBin());
			this.tokenizer = new Tokenizer(configuration.getOpenNLPenTokBin());
		} catch (IOException e) {
			throw new AcotaConfigurationException(e);
		}
	}

}