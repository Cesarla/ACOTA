package org.weso.acota.core.entity;

/**
 * 
 * @author Jose María Álvarez
 */
public class TagTO implements Comparable<TagTO>{

	private String label;
	private ProviderTO provider;
	private ResourceTO tagged;
	private double value;

	/**
	 * Zero-argument default constructor.
	 */
	public TagTO() {
		super();
		this.value = 0;
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
		this.value = 0;
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
	
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public void addValue(double value){
		this.value+=value;
	}
	
	public void subValue(double value){
		this.value-=value;
	}
	
	@Override
	public int compareTo(TagTO tag) {
		if(value>tag.getValue())
			return 1;
		else if(value<tag.getValue())
			return -1;
		else
			return 0;
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
				+ tagged + ", value=" + value + "]";
	}
	
}
