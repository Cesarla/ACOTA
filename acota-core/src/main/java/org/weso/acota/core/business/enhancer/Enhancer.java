package org.weso.acota.core.business.enhancer;

import org.weso.acota.core.entity.RequestSuggestionTO;
import org.weso.acota.core.entity.SuggestionTO;

/**
 * 
 * @author César Luis Alvargonzález
 *
 */
public interface Enhancer {

	/**
	 * @param successor The next Enhancer to be executed
	 */
	public void setSuccessor(Enhancer successor);
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public SuggestionTO enhance(RequestSuggestionTO request);
	
}
