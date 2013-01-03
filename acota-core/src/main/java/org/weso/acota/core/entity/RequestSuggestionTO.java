package org.weso.acota.core.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Jose María Álvarez
 */
public class RequestSuggestionTO {

	private ResourceTO resource;
	private SuggestionTO suggestions;
	private List<ProviderTO> targetProviders;

	/**
	 * Zero-argument default constructor.
	 */
	public RequestSuggestionTO() {
		super();
	}

	/**
	 * One-argument default constructor.
	 * @param resource
	 */
	public RequestSuggestionTO(ResourceTO resource) {
		super();
		this.resource = resource;
	}
	
	/**
	 * Three-argument default constructor.
	 * @param resource
	 * @param suggestions
	 * @param targetProviders
	 */
	public RequestSuggestionTO(ResourceTO resource, SuggestionTO suggestions,
			List<ProviderTO> targetProviders) {
		super();
		this.resource = resource;
		this.suggestions = suggestions;
		this.targetProviders = targetProviders;
	}
	
	public ResourceTO getResource() {
		if(resource==null)
			this.resource=new ResourceTO();
		return resource;
	}

	public void setResource(ResourceTO resource) {
		this.resource = resource;
	}

	public List<ProviderTO> getTargetProviders() {
		if (targetProviders == null) {
			this.targetProviders = new ArrayList<ProviderTO>();
		}
		return targetProviders;
	}

	public void setTargetProviders(List<ProviderTO> targetProviders) {
		this.targetProviders = targetProviders;
	}

	public void setSuggestions(SuggestionTO suggestions) {
		this.suggestions = suggestions;
	}

	public SuggestionTO getSuggestions() {
		if(suggestions==null)
			this.suggestions = new SuggestionTO();
		return suggestions;
	}
}
