package org.weso.acota.core.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "providerTO")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "providerTO", propOrder = { "provider", "uri" })
public class ProviderTO {

	protected String provider;
	protected String uri;

	public ProviderTO() {
		super();
	}

	public ProviderTO(String provider) {
		super();
		this.provider = provider;
	}

	public ProviderTO(String provider, String uri) {
		super();
		this.provider = provider;
		this.uri = uri;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((provider == null) ? 0 : provider.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
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
		ProviderTO other = (ProviderTO) obj;
		if (provider == null) {
			if (other.provider != null)
				return false;
		} else if (!provider.equals(other.provider))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProviderTO [provider=" + provider + ", uri=" + uri + "]";
	}
	
}
