package org.weso.acota.core.business.enhancer;

import org.weso.acota.core.entity.RequestSuggestionTO;
import org.weso.acota.core.entity.SuggestionTO;

/**
 * 
 * @author César Luis Alvargonzález
 *
 */
public interface Enhancer {

	public void setSuccessor(Enhancer successor);
	
	public SuggestionTO enhance(RequestSuggestionTO request);
	
}
