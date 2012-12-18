package org.weso.acota.core.business.enhancer;

import java.util.Map;
import java.util.Set;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Logger;
import org.weso.acota.core.entity.ProviderTO;
import org.weso.acota.core.entity.RequestSuggestionTO;
import org.weso.acota.core.entity.SuggestionTO;
import org.weso.acota.core.entity.TagTO;
import org.weso.acota.core.exceptions.AcotaModelException;

public abstract class EnhancerAdapter implements Enhancer {

	protected static Logger logger;
	
	protected RequestSuggestionTO request;
	protected SuggestionTO suggest;
	protected Enhancer successor;
	
	protected Set<TagTO> tags;
	protected Map<String, Double> labels;
	
	protected static ProviderTO provider;
	
	public EnhancerAdapter() throws ConfigurationException{
		EnhancerAdapter.logger = Logger.getLogger(EnhancerAdapter.class);
	}
	
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
	
	public SuggestionTO getSuggest(){
		if(this.suggest==null){
			logger.debug("New instance singleton of suggestions");
			return (suggest = new SuggestionTO());
		}else{
			logger.debug("Get instance singleton of suggestions");
			return this.suggest;
		}
	}
	
	protected abstract void execute() throws Exception;
	protected abstract void preExecute() throws Exception;
	protected abstract void postExecute() throws Exception;
	
	protected void fillSuggestions(String label, double relevance) {
		Double value = labels.get(label);
		
		if(value==null){
			value = new Double(0);
		}
		
		labels.put(label, value + relevance);
	}

}
