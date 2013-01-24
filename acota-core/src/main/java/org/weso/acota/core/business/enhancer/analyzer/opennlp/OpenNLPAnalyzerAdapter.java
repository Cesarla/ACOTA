package org.weso.acota.core.business.enhancer.analyzer.opennlp;

import opennlp.tools.lang.spanish.SentenceDetector;
import opennlp.tools.lang.spanish.Tokenizer;
import opennlp.tools.postag.POSTagger;

import org.weso.acota.core.CoreConfiguration;
import org.weso.acota.core.business.enhancer.analyzer.tokenizer.TokenizerAnalyzer;
import org.weso.acota.core.exceptions.AcotaConfigurationException;

/**
 * This class adapts the interface OpenNLPAnalyzer, implementing common
 * operations for derived classes
 * @author César Luis Alvargonzález
 */
public abstract class OpenNLPAnalyzerAdapter implements OpenNLPAnalyzer{

	protected SentenceDetector sentenceDetector;
	protected Tokenizer tokenizer;
	protected POSTagger posTagger;
	
	/**
	 * @see org.weso.acota.core.business.enhancer.Configurable#loadConfiguration(CoreConfiguration)
	 */
	@Override
	public abstract void loadConfiguration(CoreConfiguration configuration) throws AcotaConfigurationException;

	/**
	 * @see TokenizerAnalyzer#tag(java.lang.String[])
	 */
	@Override
	public String[] tag(String[] text) {
		return posTagger.tag(text);
	}

	/**
	 * @see TokenizerAnalyzer#tokenize(java.lang.String)
	 */
	@Override
	public String[] tokenize(String text) {
		return tokenizer.tokenize(text);
	}

	/**
	 * @see TokenizerAnalyzer#sentDetect(java.lang.String)
	 */
	@Override
	public String[] sentDetect(String text) {
		return sentenceDetector.sentDetect(text);
	}
	
}
