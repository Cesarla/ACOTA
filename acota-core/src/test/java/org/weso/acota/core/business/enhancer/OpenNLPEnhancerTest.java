package org.weso.acota.core.business.enhancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Before;
import org.junit.Test;
import org.weso.acota.core.business.enhancer.OpenNLPEnhancer;
import org.weso.acota.core.entity.ProviderTO;
import org.weso.acota.core.entity.RequestSuggestionTO;
import org.weso.acota.core.entity.ResourceTO;
import org.weso.acota.core.entity.SuggestionTO;

public class OpenNLPEnhancerTest {
	
	protected OpenNLPEnhancer openNLPEnhancer;
	
	@Before
	public void setUp() throws ConfigurationException{
		this.openNLPEnhancer = new OpenNLPEnhancer();
	}
	
	@Test
	public void getProviderTest(){
		assertEquals(OpenNLPEnhancer.provider, openNLPEnhancer.getProvider());
	}
	
	@Test
	public void OpenNLPEConstructorPronouns(){
		assertEquals(Collections.EMPTY_SET, openNLPEnhancer.pronouns);
	}
	
	@Test
	public void OpenNLPEConstructorVerbs(){
		assertEquals(Collections.EMPTY_SET, openNLPEnhancer.verbs);
	}

	@Test
	public void OpenNLPEConstructorNumbers(){
		assertEquals(Collections.EMPTY_SET, openNLPEnhancer.numbers);
	}

	@Test
	public void OpenNLPEConstructorTokens(){
		assertTrue(OpenNLPEnhancer.tokensEs!=null);
	}
	
	@Test
	public void preExecuteEmptyRequestTest() throws Exception {
		ResourceTO resource = new ResourceTO();
		SuggestionTO suggestion = new SuggestionTO();

		RequestSuggestionTO request = mock(RequestSuggestionTO.class);
		when(request.getResource()).thenReturn(resource);
		when(request.getSuggestions()).thenReturn(suggestion);

		
		openNLPEnhancer.request = request;
		openNLPEnhancer.preExecute();
		
		assertEquals(resource, openNLPEnhancer.suggest.getResource());
	}
	
	@Test
	public void postExecuteTargetTermns() throws Exception{
		List<ProviderTO> providers = new LinkedList<ProviderTO>();
		
		RequestSuggestionTO request = mock(RequestSuggestionTO.class);
		when(request.getTargetProviders()).thenReturn(providers);
		
		openNLPEnhancer.request = request;
		
		openNLPEnhancer.postExecute();
		assertTrue(openNLPEnhancer.request.getTargetProviders().contains(OpenNLPEnhancer.provider)==true);
	}
	
	@Test
	public void postExecuteTargetProviders() throws Exception{
		SuggestionTO suggestion = new SuggestionTO();
		
		RequestSuggestionTO request = mock(RequestSuggestionTO.class);
		when(request.getSuggestions()).thenReturn(suggestion);
		
		openNLPEnhancer.request = request;
		
		openNLPEnhancer.postExecute();
		assertEquals(suggestion, openNLPEnhancer.request.getSuggestions());
	}
	
	@Test
	public void enhanceTest() throws IOException{
		Map<String, Double> labels = new HashMap<String, Double>();
		labels.put("prueba",4.0);
		
		ResourceTO resource = new ResourceTO();
		resource.setDescription("Esto es una prueba");
		resource.setLabel("roberto");
		
		SuggestionTO suggestion = new SuggestionTO();
		suggestion.setLabels(labels);
		
		RequestSuggestionTO request = mock(RequestSuggestionTO.class);
		when(request.getResource()).thenReturn(resource);
		when(request.getSuggestions()).thenReturn(suggestion);
		
		openNLPEnhancer.enhance(request);
		
		assertTrue(4 == suggestion.getLabels().get("prueba"));
	}
	
	@Test
	public void processSentenceEmpty(){
		Map<String,Double> labels = new HashMap<String, Double>();
		labels.put("eso", 2.0);
		labels.put("tu", 2.0);
		labels.put("comer", 2.0);
		labels.put("uno", 2.0);
		
		openNLPEnhancer.labels = labels;
		
		openNLPEnhancer.processSetence(new String[]{}, new String[]{});
		
		assertTrue(labels.get("eso") == 2.0);
		assertTrue(labels.get("tu") == 2.0);
		assertTrue(labels.get("comer") == 2.0);
		assertTrue(labels.get("eso") == 2.0);
	}
	
	@Test
	public void processSentenceDoNothin(){
		Map<String,Double> labels = new HashMap<String, Double>();
		labels.put("perro", 2.0);
		
		openNLPEnhancer.labels = labels;
		
		openNLPEnhancer.processSetence(new String[]{"T"}, new String[]{"perro"});
		
		assertTrue(openNLPEnhancer.labels.get("perro")==2);
	}
	
	@Test
	public void processSentenceRemoveAll(){
		Map<String,Double> labels = new HashMap<String, Double>();
		labels.put("eso", 2.0);
		labels.put("tu", 2.0);
		labels.put("comer", 2.0);
		labels.put("uno", 2.0);
		
		openNLPEnhancer.labels = labels;
		
		openNLPEnhancer.processSetence(new String[]{"PN","N","V","Z"}, new String[]{"eso","tu","comer","uno"});
		
		assertTrue(openNLPEnhancer.labels.get("eso")==null);
		assertTrue(openNLPEnhancer.pronouns.contains("tu"));
		assertTrue(openNLPEnhancer.verbs.contains("comer"));
		assertTrue(openNLPEnhancer.numbers.contains("uno"));
	}
	
	@Test 
	public void calculateMaxValueEmptyTest(){
		Map<String,Double> labels = new HashMap<String, Double>();
		
		openNLPEnhancer.labels = labels;
		openNLPEnhancer.calculateMaxValue();
		assertTrue(0 == openNLPEnhancer.maxWeight);
	}
	
	@Test 
	public void findAndremoveNotMatch(){
		Map<String,Double> labels = new HashMap<String, Double>();
		labels.put("esos", 1.0);
		
		openNLPEnhancer.labels = labels;
		
		openNLPEnhancer.findAndRemove("eso");
		
		assertTrue(null == openNLPEnhancer.labels.get("eso"));
	}
	
	@Test 
	public void findAndremoveTest(){
		Map<String,Double> labels = new HashMap<String, Double>();
		labels.put("eso", 1.0);
		
		openNLPEnhancer.labels = labels;
		
		openNLPEnhancer.findAndRemove("eso");
		
		assertTrue(null == openNLPEnhancer.labels.get("eso"));
	}
	
	@Test
	public void findAndChangePronounsEmpty(){
		Map<String,Double> labels = new HashMap<String, Double>();
		labels.put("yo", 1.0);
		
		openNLPEnhancer.labels = labels;
		
		Set<String> pronouns = new HashSet<String>();
		
		openNLPEnhancer.findAndChangePronouns(pronouns);
		
		assertTrue(1 == openNLPEnhancer.labels.get("yo"));
	}
	
	@Test
	public void findAndChangePronounsTest(){
		Map<String,Double> labels = new HashMap<String, Double>();
		labels.put("yo", 1.0);
		
		openNLPEnhancer.labels = labels;
		
		Set<String> pronouns = new HashSet<String>();
		pronouns.add("yo");
		
		openNLPEnhancer.findAndChangePronouns(pronouns);
		assertTrue(1.5 == openNLPEnhancer.labels.get("yo"));
	}
	
	@Test
	public void findAndChangeVerbsEmpty(){
		Map<String,Double> labels = new HashMap<String, Double>();
		labels.put("comer", 2.0);
		
		openNLPEnhancer.labels = labels;
		
		Set<String> words = new HashSet<String>();
		
		openNLPEnhancer.findAndChangeVerbs(words);
		
		assertTrue(2 == openNLPEnhancer.labels.get("comer"));
	}
	
	@Test
	public void findAndChangeVerbsTest(){
		Map<String,Double> labels = new HashMap<String, Double>();
		labels.put("comer", 2.0);
		
		openNLPEnhancer.labels = labels;
		
		Set<String> verbs = new HashSet<String>();
		verbs.add("comer");
		
		openNLPEnhancer.findAndChangeVerbs(verbs);
		
		assertTrue(1 == openNLPEnhancer.labels.get("comer"));
	}
	
	@Test
	public void findAndChangeNumbersEmpty(){
		Map<String,Double> labels = new HashMap<String, Double>();
		labels.put("uno", 1.0);
		
		openNLPEnhancer.labels = labels;
		
		Set<String> words = new HashSet<String>();
		
		openNLPEnhancer.findAndChangeNumbers(words);
		
		assertTrue(1 == openNLPEnhancer.labels.get("uno"));
	}
	
	@Test
	public void findAndChangeNumbersTest(){
		Map<String,Double> labels = new HashMap<String, Double>();
		labels.put("uno", 1.0);
		
		openNLPEnhancer.labels = labels;
		
		Set<String> words = new HashSet<String>();
		words.add("uno");
		
		openNLPEnhancer.findAndChangeNumbers(words);
		
		assertTrue(0 == openNLPEnhancer.labels.get("uno"));
	}
	
}
