package org.weso.acota.core.business.enhancer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.weso.acota.core.FeedbackConfiguration;
import org.weso.acota.core.entity.ProviderTO;
import org.weso.acota.core.entity.tuples.FeedbackTuple;
import org.weso.acota.persistence.LabelDAO;
import org.weso.acota.persistence.factory.FactoryDAO;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class LabelRecommenderEnhancer extends EnhancerAdapter implements FeedbackConfigurable{

	protected LabelDAO labelDao;
	
	protected int numRecommendations;
	protected double relevance;

	protected FeedbackConfiguration configuration;

	public LabelRecommenderEnhancer() throws ConfigurationException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		super();
		LabelRecommenderEnhancer.provider = new ProviderTO(
				"Label Recommender Enhancer");
		loadConfiguration(configuration);
		this.labelDao = FactoryDAO.createLabelDAO();
	}
	
	@Override
	public void loadConfiguration(FeedbackConfiguration configuration) throws ConfigurationException{
		if(configuration==null)
			configuration = new FeedbackConfiguration();
		this.configuration = configuration;
		this.relevance = configuration.getLabelRecommenderRelevance();
		this.numRecommendations = configuration.getLabelRecomenderNumRecommendations();
	}

	@Override
	protected void execute() throws Exception {
		recommendLabel();
	}

	@Override
	protected void preExecute() throws Exception {
		this.suggest = request.getSuggestions();
		this.labels = suggest.getLabels();
		this.tags = suggest.getTags();
		suggest.setResource(request.getResource());
	}

	@Override
	protected void postExecute() throws Exception {
		logger.debug("Add providers to request");
		this.request.getTargetProviders().add(getProvider());
		logger.debug("Add suggestons to request");
		this.request.setSuggestions(suggest);
	}

	public ItemBasedRecommender loadRecommender() throws IOException {

		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setServerName(configuration.getDatabaseUrl());
		dataSource.setUser(configuration.getDatabaseUser());
		dataSource.setPassword(configuration.getDatabaseUser());
		dataSource.setDatabaseName(configuration.getDatabaseName());

		FeedbackTuple feedback = configuration.getFeedbackTuple();

		JDBCDataModel dataModel = new MySQLJDBCDataModel(dataSource,
				feedback.getName(), feedback.getDocumentIdField(),
				feedback.getLabelIdField(), feedback.getPreferenceField(),
				feedback.getTimestampField());

		ItemSimilarity itemSimilarity = new LogLikelihoodSimilarity(dataModel);
		return new GenericItemBasedRecommender(dataModel, itemSimilarity);
	}

	protected void recommendLabel() throws IOException, TasteException,
			SQLException, ClassNotFoundException {
		ItemBasedRecommender recommender = loadRecommender();
		List<RecommendedItem> items = null;
		for (Entry<String, Double> label : labels.entrySet()) {
			try {
				items = recommender.mostSimilarItems(label.getKey().hashCode(),
						numRecommendations);
				handleRecommendLabels(items);
			} catch (TasteException e) {
				//Drain, It is essentially necessary, mahout throws an
				//exception when you try to recommend an item that does
				//not exists
			}
		}
	}

	protected void handleRecommendLabels(List<RecommendedItem> items)
			throws SQLException, ClassNotFoundException {
		Collection<Integer> hashes = getHashCollection(items);
		Set<String> recommendedLabel = labelDao.getLabelsByHashes(hashes);
		for (String label : recommendedLabel) {
			handleRecommendLabel(label);
		}
	}

	protected Collection<Integer> getHashCollection(
			Collection<RecommendedItem> items) {
		Set<Integer> hashes = new HashSet<Integer>();
		for (RecommendedItem item : items) {
			hashes.add((int) item.getItemID());
		}
		return hashes;
	}

	protected void handleRecommendLabel(String label) {
		Double value = labels.get(label);
		if (value == null) {
			value = 0d;
		}
		labels.put(label, value + relevance);
	}

}
