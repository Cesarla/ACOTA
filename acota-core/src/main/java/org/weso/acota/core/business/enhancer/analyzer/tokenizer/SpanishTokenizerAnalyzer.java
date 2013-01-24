package org.weso.acota.core.business.enhancer.analyzer.tokenizer;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Pattern;

import opennlp.tools.lang.spanish.PosTagger;
import opennlp.tools.lang.spanish.SentenceDetector;
import opennlp.tools.lang.spanish.Tokenizer;

import org.weso.acota.core.CoreConfiguration;
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
	public SpanishTokenizerAnalyzer(CoreConfiguration configuration)
			throws AcotaConfigurationException {
		loadConfiguration(configuration);
	}
	
	/**
	 * @see org.weso.acota.core.business.enhancer.Configurable#loadConfiguration(CoreConfiguration)
	 */
	@Override
	public void loadConfiguration(CoreConfiguration configuration) throws AcotaConfigurationException {
		try{
			this.pattern = Pattern.compile(configuration.getTokenizerEsPattern());
			this.sentenceDetector = new SentenceDetector(configuration.getOpenNlpEsSentBin());
			this.posTagger = new PosTagger(configuration.getOpenNlpEsPosBin());
			this.tokenizer = new Tokenizer(configuration.getOpenNlpEsTokBin());
			this.tokens = new HashSet<String>(Arrays.asList(configuration.getTokenizerEsTokens()));
		} catch (IOException e) {
			throw new AcotaConfigurationException(e);
		}
	}
}