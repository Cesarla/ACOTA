package org.weso.acota.core.business.enhancer;

import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Logger;
import org.weso.acota.core.entity.ProviderTO;
import org.weso.acota.core.entity.RequestSuggestionTO;
import org.weso.acota.core.entity.SuggestionTO;
import org.weso.acota.core.entity.TagTO;
import org.weso.acota.core.exceptions.AcotaModelException;

/**
 * Abstract class that implements Enhacer interface, it implements
 * the chain-of-responsibility design pattern
 * @author César Luis Alvargonzález
 *
 */
public abstract class EnhancerAdapter implements Enhancer {

	protected static Logger logger;
	
	protected RequestSuggestionTO request;
	protected SuggestionTO suggest;
	protected Enhancer successor;
	
	protected Map<String, TagTO> tags;
	
	protected static ProviderTO provider;
	
	/**
	 * Zero-argument default constructor.
	 *
	 * @throws ConfigurationException Any exception that occurs while initializing a Configuration
	 *             object
	 */
	public EnhancerAdapter() throws ConfigurationException{
		EnhancerAdapter.logger = Logger.getLogger(EnhancerAdapter.class);
	}
	
	/**
	 * @return The current provider
	 */
	public ProviderTO getProvider() {
		return provider;
	}
	
	@Override
	public SuggestionTO enhance(RequestSuggestionTO request) {
		try{
			this.request = request;
			preExecute();
			execute();
			postExecute();
			if(successor != null){
				return successor.enhance(this.request);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception enhancing request.", e);
			throw new AcotaModelException("Exception enhancing request.", e);
		}
		return suggest;
	}

	@Override
	public void setSuccessor(Enhancer successor) {
		logger.debug("Set succesor of class");
		this.successor = successor;
	}	
	
	/**
	 * Returns the current SuggestionTO, if the SuggestionTO does not exist,
	 * it creates a new one and returns it.
	 * @return Current SuggestionTO
	 */
	public SuggestionTO getSuggest(){
		if(this.suggest==null){
			logger.debug("New instance singleton of suggestions");
			return (suggest = new SuggestionTO());
		}else{
			logger.debug("Get instance singleton of suggestions");
			return this.suggest;
		}
	}
	
	/**
	 * Executes the main task of the Enhancer
	 * @throws Exception
	 */
	protected abstract void execute() throws Exception;
	
	/**
	 * Executes previous tasks to get the Enhancer ready to execute its main task.
	 * @throws Exception
	 */
	protected abstract void preExecute() throws Exception;
	
	/**
	 * Cleans the house after the Enhancer execution
	 * @throws Exception
	 */
	protected abstract void postExecute() throws Exception;
	
	/**
	 * Adds some weight to a specific label
	 * @param tags Label name
	 * @param weight Label weight
	 */
	protected void fillSuggestions(TagTO tag, double weight) {
		TagTO current = tags.get(tag.getLabel());
		if(current==null)
			current = tag;
		current.addValue(weight);
		tags.put(current.getLabel(), current);
	}

}
