package org.weso.acota.core.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Jose María Álvarez
 */
public class RequestSuggestionTO {

	private ResourceTO resource;
	private SuggestionTO suggestions = new SuggestionTO();
	private List<ProviderTO> targetProviders = new ArrayList<ProviderTO>();
	private int max = 10;

	/**
	 * Zero-argument default constructor.
	 */
	public RequestSuggestionTO() {
		super();
	}

	/**
	 * 
	 * @param resource
	 */
	public RequestSuggestionTO(ResourceTO resource) {
		super();
		this.resource = resource;
	}
	
	/**
	 * 
	 * @param resource
	 * @param suggestions
	 * @param targetProviders
	 * @param max
	 */
	public RequestSuggestionTO(ResourceTO resource, SuggestionTO suggestions,
			List<ProviderTO> targetProviders, int max) {
		super();
		this.resource = resource;
		this.suggestions = suggestions;
		this.targetProviders = targetProviders;
		this.max = max;
	}
	
	public ResourceTO getResource() {
		return resource;
	}

	public void setResource(ResourceTO resource) {
		this.resource = resource;
	}

	public List<ProviderTO> getTargetProviders() {
		if (this.targetProviders == null) {
			this.targetProviders = new ArrayList<ProviderTO>();
		}
		return targetProviders;
	}

	public void setTargetProviders(List<ProviderTO> targetProviders) {
		this.targetProviders = targetProviders;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public void setSuggestions(SuggestionTO suggestions) {
		this.suggestions = suggestions;
	}

	public SuggestionTO getSuggestions() {
		return suggestions;
	}
}
