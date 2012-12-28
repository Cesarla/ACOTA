package org.weso.acota.core.entity;

/**
 * 
 * @author Jose María Álvarez
 */
public class TagTO {

	private String label;
	private ProviderTO provider;
	private ResourceTO tagged;
	private String taggingDate;
	private String uri;
	private String id;

	/**
	 * Zero-argument default constructor.
	 */
	public TagTO() {
		super();
	}

	/**
	 * 
	 * @param label
	 * @param provider
	 * @param tagged
	 */
	public TagTO(String label, ProviderTO provider, ResourceTO tagged) {
		super();
		this.label = label;
		this.provider = provider;
		this.tagged = tagged;
	}

	public TagTO(String label, ProviderTO provider) {
		this.label = label;
		this.provider = provider;
	}

	public ProviderTO getProvider() {
		return provider;
	}

	public void setProvider(ProviderTO provider) {
		this.provider = provider;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public ResourceTO getTagged() {
		return tagged;
	}

	public void setTagged(ResourceTO tagged) {
		this.tagged = tagged;
	}

	public String getTaggingDate() {
		return taggingDate;
	}

	public void setTaggingDate(String taggingDate) {
		this.taggingDate = taggingDate;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result
				+ ((provider == null) ? 0 : provider.hashCode());
		result = prime * result + ((tagged == null) ? 0 : tagged.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TagTO other = (TagTO) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (provider == null) {
			if (other.provider != null)
				return false;
		} else if (!provider.equals(other.provider))
			return false;
		if (tagged == null) {
			if (other.tagged != null)
				return false;
		} else if (!tagged.equals(other.tagged))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "TagTO [label=" + label + ", provider=" + provider + ", tagged="
				+ tagged + ", taggingDate=" + taggingDate + ", uri=" + uri
				+ ", id=" + id + "]";
	}

}
