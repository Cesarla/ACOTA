package org.weso.acota.core.business.enhancer;

import java.util.Map.Entry;

import org.apache.commons.configuration.ConfigurationException;
import org.weso.acota.core.FeedbackConfiguration;
import org.weso.acota.core.entity.ProviderTO;
import org.weso.acota.persistence.FeedbackDAO;
import org.weso.acota.persistence.factory.FactoryDAO;

public class SimpleRecommenderEnhancer extends EnhancerAdapter implements FeedbackConfigurable{

	protected FeedbackDAO feedbackDao;
	protected double relevance;

	protected FeedbackConfiguration configuration;

	public SimpleRecommenderEnhancer() throws ConfigurationException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		super();
		SimpleRecommenderEnhancer.provider = new ProviderTO("Simple Recommender Enhancer");
		loadConfiguration(configuration);
		this.feedbackDao = FactoryDAO.createFeedbackDAO();
	}
	
	@Override
	public void loadConfiguration(FeedbackConfiguration configuration) throws ConfigurationException{
		if(configuration==null)
			configuration = new FeedbackConfiguration();
		this.configuration = configuration;
		this.relevance = configuration.getSimpleRecommenderRelevance();
	}

	@Override
	protected void execute() throws Exception {
		for (Entry<String, Double> tag : labels.entrySet()) {
			tag.setValue(tag.getValue()
					+ feedbackDao.getFeedbacksByLabel(tag.getKey()).size()
					* relevance);
		}
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

}
