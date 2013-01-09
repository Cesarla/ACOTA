package org.weso.acota.core.business.enhancer;

import org.apache.commons.configuration.ConfigurationException;
import org.weso.acota.core.Configuration;

/**
 * Configurable Interface, this interface allows configure the classes that
 * implements this interface.
 * 
 * @author César Luis Alvargonzález
 * 
 */
public interface Configurable {
	/**
	 * Loads the configuration into the class that implements this interface
	 * @param configuration
	 *            Acota-core's configuration class
	 * @throws ConfigurationException
	 *             Any exception that occurs while initializing a Configuration
	 *             object
	 */
	public void loadConfiguration(Configuration configuration)
			throws ConfigurationException;
}
