package org.weso.acota.core.business.enhancer.analyzer.tokenizer;

import java.io.IOException;
import java.util.regex.Pattern;

import opennlp.tools.lang.spanish.PosTagger;
import opennlp.tools.lang.spanish.SentenceDetector;
import opennlp.tools.lang.spanish.Tokenizer;

import org.weso.acota.core.Configuration;
import org.weso.acota.core.exceptions.AcotaConfigurationException;


/**
 * Analyzer specialized in perform Spanish auxiliary operations such as NLP (Natural 
 * Language Processing) operations for Spanish or perform regular expression matching.
 * @author César Luis Alvargonzález
 */
public class SpanishTokenizerAnalyzer extends TokenizerAnalyzerAdapter implements TokenizerAnalyzer{
	
	/**
	 * Default Constructor
	 * @param configuration Acota's Configuration Object
	 * @throws AcotaConfigurationException An exception that occurs 
	 * while installing and configuration Acota
	 */
	public SpanishTokenizerAnalyzer(Configuration configuration)
			throws AcotaConfigurationException {
		loadConfiguration(configuration);
	}
	
	/**
	 * @see org.weso.acota.core.business.enhancer.Configurable#loadConfiguration(Configuration)
	 */
	@Override
	public void loadConfiguration(Configuration configuration) throws AcotaConfigurationException {
		try{
			this.pattern = Pattern.compile(configuration.getTokenizerEsPattern());
			this.sentenceDetector = new SentenceDetector(configuration.getOpenNLPesSentBin());
			this.posTagger = new PosTagger(configuration.getOpenNLPesPosBin());
			this.tokenizer = new Tokenizer(configuration.getOpenNLPesTokBin());
		} catch (IOException e) {
			throw new AcotaConfigurationException(e);
		}
	}
}