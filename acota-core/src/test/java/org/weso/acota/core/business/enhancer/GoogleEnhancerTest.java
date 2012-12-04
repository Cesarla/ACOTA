package org.weso.acota.core.business.enhancer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Before;
import org.junit.Test;
import org.weso.acota.core.business.enhancer.GoogleEnhancer;
import org.weso.acota.core.entity.RequestSuggestionTO;
import org.weso.acota.core.entity.ResourceTO;
import org.weso.acota.core.entity.SuggestionTO;
import org.weso.acota.core.entity.TagTO;

public class GoogleEnhancerTest {
protected GoogleEnhancer googleEnhancer;
	
	@Before
	public void setUp() throws ConfigurationException{
		this.googleEnhancer = new GoogleEnhancer();
	}
	
	@Test
	public void getSuggestEmpty(){
		assertEquals(new SuggestionTO(),googleEnhancer.getSuggest());
	}
	
	@Test
	public void getSuggestTest(){
		SuggestionTO s = new SuggestionTO(Collections.<String, Double> emptyMap(), Collections.<TagTO> emptySet(), new ResourceTO());
		googleEnhancer.suggest = s;
		assertEquals(s,googleEnhancer.getSuggest());
	}
	
	@Test
	public void setAdapterTest() throws ConfigurationException{
		GoogleEnhancer successor = new GoogleEnhancer();
		googleEnhancer.setSuccessor(successor);
		assertEquals(successor,googleEnhancer.successor);
	}
	
	@Test
	public void getProviderTest(){
		assertEquals(GoogleEnhancer.provider, googleEnhancer.getProvider());
	}

	@Test
	public void preExecuteEmptyRequestTest() throws Exception {
		ResourceTO resource = new ResourceTO();
		SuggestionTO suggestion = new SuggestionTO();

		RequestSuggestionTO request = mock(RequestSuggestionTO.class);
		when(request.getResource()).thenReturn(resource);
		when(request.getSuggestions()).thenReturn(suggestion);

		
		googleEnhancer.request = request;
		googleEnhancer.preExecute();
		
		assertEquals(resource, googleEnhancer.suggest.getResource());
	}
}
