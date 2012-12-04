package org.weso.acota.persistence.factory;

import org.apache.commons.configuration.ConfigurationException;
import org.weso.acota.core.FeedbackConfiguration;
import org.weso.acota.persistence.DocumentDAO;
import org.weso.acota.persistence.FeedbackDAO;
import org.weso.acota.persistence.LabelDAO;

public class FactoryDAO {

	private static FactoryDAO FACTORY_DAO;
	private static FeedbackConfiguration configuration;

	private FactoryDAO() throws ConfigurationException {
		FactoryDAO.configuration = new FeedbackConfiguration();
	}

	private static void instanciateClass() throws ConfigurationException {
		if (FACTORY_DAO == null) {
			FactoryDAO.FACTORY_DAO = new FactoryDAO();
		}
	}

	public static DocumentDAO createDocumentDAO()
			throws ClassNotFoundException, ConfigurationException,
			InstantiationException, IllegalAccessException {
		instanciateClass();
		return (DocumentDAO) Class.forName(configuration.getDocumentDAOClass())
				.newInstance();
	}

	public static FeedbackDAO createFeedbackDAO()
			throws ConfigurationException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		instanciateClass();
		return (FeedbackDAO) Class.forName(configuration.getFeedbackDAOClass())
				.newInstance();
	}

	public static LabelDAO createLabelDAO() throws ConfigurationException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		instanciateClass();
		return (LabelDAO) Class.forName(configuration.getLabelDAOClass())
				.newInstance();
	}
}
