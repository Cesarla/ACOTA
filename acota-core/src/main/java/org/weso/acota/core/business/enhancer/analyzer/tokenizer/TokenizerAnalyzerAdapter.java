package org.weso.acota.core.business.enhancer.analyzer.tokenizer;

import java.util.Set;
import java.util.regex.Pattern;


import opennlp.tools.postag.POSTagger;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.tokenize.Tokenizer;

import org.weso.acota.core.CoreConfiguration;
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
	protected Set<String> tokens;
	
	/**
	 * @see org.weso.acota.core.business.enhancer.Configurable#loadConfiguration(CoreConfiguration)
	 */
	@Override
	public abstract void loadConfiguration(CoreConfiguration configuration) throws AcotaConfigurationException;
	
	/**
	 * @see TokenizerAnalyzer#match(java.lang.String)
	 */
	@Override
	public boolean match(String text) {
		return pattern.matcher(text).find();
	}
	
	/**
	 * @see TokenizerAnalyzer#containsTag(String)
	 */
	@Override
	public boolean containsTag(String tag) {
		return tokens.contains(tag);
	};
	
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
