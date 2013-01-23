/**
 * 
 */
package org.weso.acota.core.business.enhancer.analyzer.tokenizer;

import java.util.regex.Pattern;

import opennlp.tools.lang.spanish.SentenceDetector;
import opennlp.tools.lang.spanish.Tokenizer;
import opennlp.tools.postag.POSTagger;

import org.weso.acota.core.Configuration;
import org.weso.acota.core.exceptions.AcotaConfigurationException;

/**
 * This class adapts the interface TokenizerAnalyzer, implementing common
 * operations for derived classes
 * @author César Luis Alvargonzález
 *
 */
public abstract class TokenizerAnalyzerAdapter implements TokenizerAnalyzer{
	protected Pattern pattern;
	protected SentenceDetector sentenceDetector;
	protected Tokenizer tokenizer;
	protected POSTagger posTagger;
	
	/**
	 * @see org.weso.acota.core.business.enhancer.Configurable#loadConfiguration(Configuration)
	 */
	@Override
	public abstract void loadConfiguration(Configuration configuration) throws AcotaConfigurationException;

	/**
	 * @see TokenizerAnalyzer#match(java.lang.String)
	 */
	@Override
	public boolean match(String text) {
		return pattern.matcher(text).find();
	}
	
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
