package org.weso.acota.core.business.enhancer.analyzer.opennlp;

import java.util.Set;

import opennlp.tools.lang.spanish.SentenceDetector;
import opennlp.tools.lang.spanish.Tokenizer;
import opennlp.tools.postag.POSTagger;

import org.weso.acota.core.CoreConfiguration;
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
	
	protected Set<String> tokens;
	protected Set<String> nouns;
	protected Set<String> verbs;
	protected Set<String> numbers;
	
	/**
	 * @see org.weso.acota.core.business.enhancer.Configurable#loadConfiguration(CoreConfiguration)
	 */
	@Override
	public abstract void loadConfiguration(CoreConfiguration configuration) throws AcotaConfigurationException;

	/**
	 * @see OpenNLPAnalyzer#tag(java.lang.String[])
	 */
	@Override
	public String[] tag(String[] text) {
		return posTagger.tag(text);
	}

	/**
	 * @see OpenNLPAnalyzer#tokenize(java.lang.String)
	 */
	@Override
	public String[] tokenize(String text) {
		return tokenizer.tokenize(text);
	}

	/**
	 * @see OpenNLPAnalyzer#sentDetect(java.lang.String)
	 */
	@Override
	public String[] sentDetect(String text) {
		return sentenceDetector.sentDetect(text);
	}
	
	/**
	 * @see OpenNLPAnalyzer#isDispenasble(java.lang.String)
	 */
	@Override
	public boolean isDispenasble(String tag) {
		return tokens.contains(tag);
	}

	/**
	 * @see OpenNLPAnalyzer#isNoun(java.lang.String)
	 */
	@Override
	public boolean isNoun(String tag) {
		return nouns.contains(tag);
	}

	/**
	 * @see OpenNLPAnalyzer#isVerb(java.lang.String)
	 */
	@Override
	public boolean isVerb(String tag) {
		return verbs.contains(tag);
	}
	
	/**
	 * @see OpenNLPAnalyzer#isNumber(java.lang.String)
	 */
	@Override
	public boolean isNumber(String tag) {
		return numbers.contains(tag);
	}
	
}
