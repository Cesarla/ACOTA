package org.weso.acota.core.business.enhancer;

import org.apache.commons.configuration.ConfigurationException;
import org.weso.acota.core.Configuration;

public interface Configurable {
	public void loadConfiguration(Configuration configuration) throws ConfigurationException;
}
