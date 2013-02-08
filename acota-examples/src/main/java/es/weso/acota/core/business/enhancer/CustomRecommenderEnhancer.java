package es.weso.acota.core.business.enhancer;

import java.util.Map.Entry;


import es.weso.acota.core.FeedbackConfiguration;
import es.weso.acota.core.business.enhancer.EnhancerAdapter;
import es.weso.acota.core.business.enhancer.FeedbackConfigurable;
import es.weso.acota.core.entity.ProviderTO;
import es.weso.acota.core.entity.TagTO;
import es.weso.acota.core.exceptions.AcotaConfigurationException;
import es.weso.acota.persistence.FeedbackDAO;
import es.weso.acota.persistence.factory.FactoryDAO;

/**
 * Naive recommender {@link Enhancer}, increases the weight of the most used labels
 * @author César Luis Alvargonzález
 *
 */
public class CustomRecommenderEnhancer extends EnhancerAdapter implements FeedbackConfigurable{

	protected FeedbackDAO feedbackDao;
	protected double relevance;

	protected FeedbackConfiguration configuration;

	/**
	 * Zero-argument default Exception
	 * @throws AcotaConfigurationException Any exception that occurs 
	 * while initializing Configuration object
	 * @throws InstantiationException Thrown when an application tries to instantiate
	 * an interface or an abstract class
	 * @throws IllegalAccessException An IllegalAccessException is thrown when an
	 * application tries to reflectively create an instance
	 * @throws ClassNotFoundException Thrown when an application tries to load in a
	 * class through its string name
	 */
	public CustomRecommenderEnhancer() throws AcotaConfigurationException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		super();
		CustomRecommenderEnhancer.provider = new ProviderTO("Simple Recommender Enhancer");
		loadConfiguration(configuration);
		this.feedbackDao = FactoryDAO.createFeedbackDAO();
	}
	
	@Override
	public void loadConfiguration(FeedbackConfiguration configuration) throws AcotaConfigurationException{
		if(configuration==null)
			configuration = new FeedbackConfiguration();
		this.configuration = configuration;
		this.relevance = configuration.getSimpleRecommenderRelevance();
	}

	@Override
	protected void execute() throws Exception {
		for (Entry<String, TagTO> tag : tags.entrySet()) {
			tag.getValue().addValue(feedbackDao.getFeedbacksByLabel(tag.getKey()).size()
					* relevance);
		}
	}

	@Override
	protected void preExecute() throws Exception {
		this.suggest = request.getSuggestions();
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
