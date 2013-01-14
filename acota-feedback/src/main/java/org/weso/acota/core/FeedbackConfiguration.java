package org.weso.acota.core;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.weso.acota.core.entity.persistence.tables.DocumentTable;
import org.weso.acota.core.entity.persistence.tables.FeedbackTable;
import org.weso.acota.core.entity.persistence.tables.LabelTable;

/**
 * The main task of this class is to load the feedback configuration properties of ACOTA,
 * this properties could be set programmatically or by a configuration file called
 * acota.properties
 * 
 * @author César Luis Alvargonzález
 *
 */
public class FeedbackConfiguration extends Configuration {

	protected static final String INTERNAL_ACOTA_PERSISTENCE_PROPERTIES_PATH = "/resources/inner.acota.persistence.properties";
	
	protected String documentDAOClass;
	protected String feedbackDAOClass;
	protected String labelDAOClass;
	
	protected String databaseUrl;
	protected String databaseName;
	protected String databaseUser;
	protected String databasePassword;

	protected DocumentTable documentTuple;
	protected FeedbackTable feedbackTuple;
	protected LabelTable labelTuple;

	protected double simpleRecommenderRelevance;

	protected double labelRecommenderRelevance;
	protected int labelRecomenderNumRecommendations;

	protected static Logger logger;

	/**
	 * Zero-argument default constructor.
	 * @throws ConfigurationException Any exception that occurs while initializing 
	 * a Configuration object
	 */
	public FeedbackConfiguration() throws ConfigurationException {
		super();

		Configuration.logger = Logger.getLogger(FeedbackConfiguration.class);

		config.addConfiguration(new PropertiesConfiguration(this.getClass()
				.getResource(INTERNAL_ACOTA_PERSISTENCE_PROPERTIES_PATH)));

		loadDAOClasses();
		loadDatabaseConfig();
		loadDocumentConfig();
		loadFeedbackConfig();
		loadLabelConfig();

		loadLabelRecommenderConfig();
		loadSimpleRecommenderSimple();
	}

	/**
	 * Loads the DAO Classes properties
	 */
	private void loadDAOClasses(){
		this.documentDAOClass = config.getString("database.dao.impl.documentDAO");
		this.feedbackDAOClass = config.getString("database.dao.impl.feedbackDAO");
		this.labelDAOClass = config.getString("database.dao.impl.labelDAO");
	}
	
	/**
	 * Loads the Database Configuration properties
	 */
	private void loadDatabaseConfig() {
		this.databaseUrl = config.getString("database.url");
		this.databaseName = config.getString("database.name");
		this.databaseUser = config.getString("database.user");
		this.databasePassword = config.getString("database.password");
	}

	/**
	 * Loads the Document Table properties
	 */
	private void loadDocumentConfig() {
		this.documentTuple = new DocumentTable();
		documentTuple.setName(config.getString("database.document"));
		documentTuple.setIdAttribute(config.getString("database.document.id"));
		documentTuple.setNameAttribute(config.getString("database.document.name"));
	}

	/**
	 * Loads the Feedback Table properties
	 */
	private void loadFeedbackConfig() {
		this.feedbackTuple = new FeedbackTable();
		feedbackTuple.setName(config.getString("database.feedback"));
		feedbackTuple.setIdAttribute(config.getString("database.feedback.id"));
		feedbackTuple.setUserIdAttribute(config
				.getString("database.feedback.userId"));
		feedbackTuple.setDocumentIdAttribute(config
				.getString("database.feedback.document"));
		feedbackTuple.setLabelIdAttribute(config
				.getString("database.feedback.label"));
		feedbackTuple.setPreferenceAttribute(config
				.getString("database.feedback.preference"));
		feedbackTuple.setTimestampAttribute(config
				.getString("database.feedback.timestamp"));
	}

	/**
	 * Loads the Label Table properties
	 */
	private void loadLabelConfig() {
		this.labelTuple = new LabelTable();
		labelTuple.setName(config.getString("database.label"));
		labelTuple.setIdAttribute(config.getString("database.label.id"));
		labelTuple.setNameAttribute(config.getString("database.label.name"));
	}

	/**
	 * Loads the LabelRecommenderEnhancer properties
	 */
	private void loadLabelRecommenderConfig() {
		this.labelRecommenderRelevance = config
				.getDouble("enhancer.recommender.label.relevance");
		this.labelRecomenderNumRecommendations = config
				.getInt("enhancer.recommender.label.recommendations");
	}
	
	/**
	 * Loads the SimpleRecommenderEnhancer properties
	 */
	private void loadSimpleRecommenderSimple() {
		this.simpleRecommenderRelevance = config
				.getDouble("enhancer.recommender.simple.relevance");
	}

	public String getDocumentDAOClass() {
		return documentDAOClass;
	}

	public void setDocumentDAOClass(String documentDAOClass) {
		this.documentDAOClass = documentDAOClass;
	}

	public String getFeedbackDAOClass() {
		return feedbackDAOClass;
	}

	public void setFeedbackDAOClass(String feedbackDAOClass) {
		this.feedbackDAOClass = feedbackDAOClass;
	}

	public String getLabelDAOClass() {
		return labelDAOClass;
	}

	public void setLabelDAOClass(String labelDAOClass) {
		this.labelDAOClass = labelDAOClass;
	}

	public String getDatabaseUrl() {
		return databaseUrl;
	}

	public void setDatabaseUrl(String databaseUrl) {
		this.databaseUrl = databaseUrl;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getDatabaseUser() {
		return databaseUser;
	}

	public void setDatabaseUser(String databaseUser) {
		this.databaseUser = databaseUser;
	}

	public String getDatabasePassword() {
		return databasePassword;
	}

	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}

	public DocumentTable getDocumentTuple() {
		return documentTuple;
	}

	public void setDocumentTuple(DocumentTable documentTuple) {
		this.documentTuple = documentTuple;
	}

	public FeedbackTable getFeedbackTuple() {
		return feedbackTuple;
	}

	public void setFeedbackTuple(FeedbackTable feedbackTuple) {
		this.feedbackTuple = feedbackTuple;
	}

	public LabelTable getLabelTuple() {
		return labelTuple;
	}

	public void setLabelTuple(LabelTable labelTuple) {
		this.labelTuple = labelTuple;
	}

	public double getSimpleRecommenderRelevance() {
		return simpleRecommenderRelevance;
	}

	public void setSimpleRecommenderRelevance(double simpleRecommenderRelevance) {
		this.simpleRecommenderRelevance = simpleRecommenderRelevance;
	}

	public double getLabelRecommenderRelevance() {
		return labelRecommenderRelevance;
	}

	public void setLabelRecommenderRelevance(double labelRecommenderRelevance) {
		this.labelRecommenderRelevance = labelRecommenderRelevance;
	}

	public int getLabelRecomenderNumRecommendations() {
		return labelRecomenderNumRecommendations;
	}

	public void setLabelRecomenderNumRecommendations(
			int labelRecomenderNumRecommendations) {
		this.labelRecomenderNumRecommendations = labelRecomenderNumRecommendations;
	}
	
	

}
