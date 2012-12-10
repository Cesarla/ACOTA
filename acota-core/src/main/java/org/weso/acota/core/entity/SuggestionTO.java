package org.weso.acota.core.entity;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * @author chema
 */
@XmlRootElement(name = "suggestionTO")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "suggestionTO", propOrder = { "tags", "labels", "resource" })
public class SuggestionTO {

	protected Map<String, Double> labels = new LinkedHashMap<String, Double>();
	protected Set<TagTO> tags = new HashSet<TagTO>();
	protected ResourceTO resource;

	public SuggestionTO() {
		super();
	}

	public SuggestionTO(Map<String, Double> labels, Set<TagTO> tags,
			ResourceTO resource) {
		super();
		this.labels = labels;
		this.tags = tags;
		this.resource = resource;
	}

	public Map<String, Double> getLabels() {
		return labels;
	}

	public void setLabels(Map<String, Double> labels) {
		this.labels = labels;
	}

	public Set<TagTO> getTags() {
		return tags;
	}

	public void setTags(Set<TagTO> tags) {
		this.tags = tags;
	}

	public ResourceTO getResource() {
		return resource;
	}

	public void setResource(ResourceTO resource) {
		this.resource = resource;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((labels == null) ? 0 : labels.hashCode());
		result = prime * result
				+ ((resource == null) ? 0 : resource.hashCode());
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
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
		SuggestionTO other = (SuggestionTO) obj;
		if (labels == null) {
			if (other.labels != null)
				return false;
		} else if (!labels.equals(other.labels))
			return false;
		else;
		if (resource == null) {
			if (other.resource != null)
				return false;
		} else if (!resource.equals(other.resource))
			return false;
		else;
		if (tags == null) {
			if (other.tags != null)
				return false;
		} else if (!tags.equals(other.tags))
			return false;
		else;
		return true;
	}

	

	
}
