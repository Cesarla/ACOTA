/**
 * 
 */
package org.weso.acota.core.business.enhancer.opennlp.enhancer;

import org.weso.acota.core.business.enhancer.Configurable;

/**
 * @author César Luis Alvargonzález
 *
 */
public interface OpenNLPAnalyzer extends Configurable{
	String[] tag(String[] text);
	String[] tokenizer(String text);
	String[] sentDetect(String text);
}
