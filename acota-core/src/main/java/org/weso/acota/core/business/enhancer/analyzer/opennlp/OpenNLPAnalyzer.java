package org.weso.acota.core.business.enhancer.analyzer.opennlp;

import org.weso.acota.core.business.enhancer.Configurable;

/**
 * Analyzer interface, a class that implements OpenNLPAnalyzer
 * is ready to perform natural language processing (NLP) operations
 * @author César Luis Alvargonzález
 */
public interface OpenNLPAnalyzer extends Configurable{
	/**
	 * Tags a text with Morphosyntactic Tags
	 * @param text The string to be tagged.
	 * @return Split The String[] with the individual tags
	 */
	String[] tag(String[] text);
	/**
	 * Splits a string into its atomic parts
	 * @param text The string to be tokenized.
	 * @return The String[] with the individual tokens as the array elements
	 */
	String[] tokenize(String text);
	/**
	 * Splits a string into sentences
	 * @param text The string to be extracted
	 * @return The String[] with the detected sentences
	 */
	String[] sentDetect(String text);
}
