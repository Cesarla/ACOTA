package org.weso.acota.core.business.enhancer;

import org.apache.commons.configuration.ConfigurationException;
import org.weso.acota.core.FeedbackConfiguration;

public interface FeedbackConfigurable {
	public void loadConfiguration(FeedbackConfiguration configuration) throws ConfigurationException;
}
