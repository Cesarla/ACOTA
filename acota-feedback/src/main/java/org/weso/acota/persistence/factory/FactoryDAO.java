package org.weso.acota.persistence.factory;

import org.apache.commons.configuration.ConfigurationException;
import org.weso.acota.core.FeedbackConfiguration;
import org.weso.acota.persistence.DocumentDAO;
import org.weso.acota.persistence.FeedbackDAO;
import org.weso.acota.persistence.LabelDAO;

/**
 * FactoryDAO is a class that creates DAO Objects, the implementation
 * of this objects is defined within the {@link FeedbackConfiguration} class 
 * @author César Luis Alvargonzález
 */
public class FactoryDAO {

	private static FactoryDAO FACTORY_DAO;
	private static FeedbackConfiguration configuration;

	/**
	 * Zero-Argument Constructor
	 * @throws ConfigurationException ConfigurationException Any exception that 
	 * occurs while initializing a Configuration object
	 */
	private FactoryDAO() throws ConfigurationException {
		FactoryDAO.configuration = new FeedbackConfiguration();
	}

	/**
	 * Instantiate FactoryDAO (Singleton Design Pattern)
	 * @throws ConfigurationException ConfigurationException Any exception that 
	 * occurs while initializing a Configuration object
	 */
	private static void instanciateClass() throws ConfigurationException {
		if (FACTORY_DAO == null) {
			FactoryDAO.FACTORY_DAO = new FactoryDAO();
		}
	}

	/**
	 * Creates a DocumentDAO with the implementation defined in the configuration
	 * @see DocumentDAO
	 * @return DocumentDAO with the implementation defined in the configuration
	 * @throws ConfigurationException ConfigurationException Any exception that 
	 * occurs while initializing a Configuration object
	 * @throws InstantiationException Thrown when an application tries to instantiate
	 * an interface or an abstract class
	 * @throws IllegalAccessException An IllegalAccessException is thrown when an
	 * application tries to reflectively create an instance
	 * @throws ClassNotFoundException Thrown when an application tries to load in a
	 * class through its string name
	 */
	public static DocumentDAO createDocumentDAO()
			throws ClassNotFoundException, ConfigurationException,
			InstantiationException, IllegalAccessException {
		instanciateClass();
		return (DocumentDAO) Class.forName(configuration.getDocumentDAOClass())
				.newInstance();
	}

	/**
	 * Creates a FeedbackDAO with the implementation defined in the configuration
	 * @see FeedbackDAO
	 * @return DocumentDAO with the implementation defined in the configuration
	 * @throws ConfigurationException ConfigurationException Any exception that 
	 * occurs while initializing a Configuration object
	 * @throws InstantiationException Thrown when an application tries to instantiate
	 * an interface or an abstract class
	 * @throws IllegalAccessException An IllegalAccessException is thrown when an
	 * application tries to reflectively create an instance
	 * @throws ClassNotFoundException Thrown when an application tries to load in a
	 * class through its string name
	 */
	public static FeedbackDAO createFeedbackDAO()
			throws ConfigurationException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		instanciateClass();
		return (FeedbackDAO) Class.forName(configuration.getFeedbackDAOClass())
				.newInstance();
	}

	/**
	 * Creates a LabelDAO with the implementation defined in the configuration
	 * @see LabelDAO LabelDAO with the implementation defined in the configuration
	 * @return LabelDAO with the implementation defined in the configuration
	 * @throws ConfigurationException ConfigurationException Any exception that 
	 * occurs while initializing a Configuration object
	 * @throws InstantiationException Thrown when an application tries to instantiate
	 * an interface or an abstract class
	 * @throws IllegalAccessException An IllegalAccessException is thrown when an
	 * application tries to reflectively create an instance
	 * @throws ClassNotFoundException Thrown when an application tries to load in a
	 * class through its string name
	 */
	public static LabelDAO createLabelDAO() throws ConfigurationException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		instanciateClass();
		return (LabelDAO) Class.forName(configuration.getLabelDAOClass())
				.newInstance();
	}
}
