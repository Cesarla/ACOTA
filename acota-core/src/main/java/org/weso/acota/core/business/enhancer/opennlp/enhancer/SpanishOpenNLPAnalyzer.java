package org.weso.acota.core.business.enhancer.opennlp.enhancer;

import javax.security.auth.login.Configuration;

import org.apache.commons.configuration.ConfigurationException;

import opennlp.tools.lang.spanish.SentenceDetector;
import opennlp.tools.postag.POSTagger;
import opennlp.tools.tokenize.SimpleTokenizer;

/**
 * @author César Luis Alvargonzález
 *
 */
public class SpanishOpenNLPAnalyzer implements OpenNLPAnalyzer{

	
	protected SentenceDetector sentenceDetector;
	protected SimpleTokenizer tokenizer;
	protected POSTagger posTagger;
	
	private SpanishOpenNLPAnalyzer(Configuration configuration){
	}
	
	/* (non-Javadoc)
	 * @see org.weso.acota.core.business.enhancer.Configurable#loadConfiguration(org.weso.acota.core.Configuration)
	 */
	@Override
	public void loadConfiguration(
			org.weso.acota.core.Configuration configuration)
			throws ConfigurationException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String[] tag(String[] text) {
		return posTagger.tag(text);
	}

	@Override
	public String[] tokenizer(String text) {
		return tokenizer.tokenize(text);
	}

	@Override
	public String[] sentDetect(String text) {
		return sentDetect(text);
	}


}
