package org.weso.acota.core.business.enhancer;

import org.weso.acota.core.entity.RequestSuggestionTO;
import org.weso.acota.core.entity.SuggestionTO;

/**
 * Enhancer Interface, implements Chain of Responsibility design pattern
 * @author César Luis Alvargonzález
 *
 */
public interface Enhancer {

	/**
	 * @param successor The next Enhancer to be executed
	 */
	public void setSuccessor(Enhancer successor);
	
	/**
	 * Enhance the results using several techniques
	 * @param request Request object
	 * @return The results of the Enhancement
	 */
	public SuggestionTO enhance(RequestSuggestionTO request);
	
}
