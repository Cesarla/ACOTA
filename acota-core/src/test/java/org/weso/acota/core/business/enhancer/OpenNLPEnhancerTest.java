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

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Before;
import org.junit.Test;
import org.weso.acota.core.business.enhancer.OpenNLPEnhancer;
import org.weso.acota.core.entity.ProviderTO;
import org.weso.acota.core.entity.RequestSuggestionTO;
import org.weso.acota.core.entity.ResourceTO;
import org.weso.acota.core.entity.SuggestionTO;
import org.weso.acota.core.entity.TagTO;

public class OpenNLPEnhancerTest {
	
	protected OpenNLPEnhancer openNLPEnhancer;
	
	@Before
	public void setUp() throws ConfigurationException, IOException{
		this.openNLPEnhancer = new OpenNLPEnhancer();
	}
	
	@Test
	public void getProviderTest(){
		assertEquals(OpenNLPEnhancer.provider, openNLPEnhancer.getProvider());
	}
	
	@Test
	public void OpenNLPEConstructorPronouns(){
		assertEquals(Collections.EMPTY_SET, openNLPEnhancer.noun);
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
	public void enhanceTest() throws Exception{
		SuggestionTO suggest = initializeSuggest();
		
		Map<String, TagTO> tags = new HashMap<String, TagTO>();
		TagTO tag = new TagTO("prueba", LuceneEnhancer.provider,
				suggest.getResource());
		tag.setValue(4.0);
		tags.put(tag.getLabel(), tag);
		
		openNLPEnhancer.tags = tags;
		openNLPEnhancer.suggest = suggest;
		
		suggest.setTags(tags);
		
		ResourceTO resource = new ResourceTO();
		resource.setDescription("Esto es una prueba");
		resource.setLabel("roberto");
		
		
		RequestSuggestionTO request = mock(RequestSuggestionTO.class);
		when(request.getResource()).thenReturn(resource);
		when(request.getSuggestions()).thenReturn(suggest);
		
		openNLPEnhancer.enhance(request);
		
		assertEquals(6.0,suggest.getTags().get("prueba").getValue(),1e-15);
	}
	
	@Test
	public void processSentenceEmpty() throws Exception{
		SuggestionTO suggest = initializeSuggest();
		
		Map<String, TagTO> tags = new HashMap<String, TagTO>();
		TagTO tag1 = new TagTO("eso", LuceneEnhancer.provider,suggest.getResource());
		tag1.setValue(2.0);
		TagTO tag2 = new TagTO("tu", LuceneEnhancer.provider,suggest.getResource());
		tag2.setValue(2.0);
		TagTO tag3 = new TagTO("comer", LuceneEnhancer.provider,suggest.getResource());
		tag3.setValue(2.0);
		TagTO tag4 = new TagTO("uno", LuceneEnhancer.provider,suggest.getResource());
		tag4.setValue(2.0);
		
		tags.put(tag1.getLabel(), tag1);
		tags.put(tag2.getLabel(), tag2);
		tags.put(tag3.getLabel(), tag3);
		tags.put(tag4.getLabel(), tag4);
		
		openNLPEnhancer.tags = tags;
		openNLPEnhancer.suggest = suggest;
		
		suggest.setTags(tags);
		
		openNLPEnhancer.processSetence(new String[]{}, new String[]{});
		
		assertTrue(tags.get("eso").getValue() == 2.0);
		assertTrue(tags.get("tu").getValue() == 2.0);
		assertTrue(tags.get("comer").getValue() == 2.0);
		assertTrue(tags.get("eso").getValue() == 2.0);
	}
	
	@Test
	public void processSentenceDoNothin() throws Exception{
		SuggestionTO suggest = initializeSuggest();
		
		Map<String, TagTO> tags = new HashMap<String, TagTO>();
		TagTO tag = new TagTO("perro", LuceneEnhancer.provider,
				suggest.getResource());
		tag.setValue(2.0);
		tags.put(tag.getLabel(), tag);
		
		openNLPEnhancer.tags = tags;
		openNLPEnhancer.suggest = suggest;
		
		openNLPEnhancer.processSetence(new String[]{"T"}, new String[]{"perro"});
		
		assertTrue(openNLPEnhancer.tags.get("perro").getValue()==2);
	}
	
	@Test
	public void processSentenceRemoveAll() throws Exception{
		SuggestionTO suggest = initializeSuggest();
		
		Map<String, TagTO> tags = new HashMap<String, TagTO>();
		TagTO tag1 = new TagTO("eso", LuceneEnhancer.provider,suggest.getResource());
		tag1.setValue(2.0);
		TagTO tag2 = new TagTO("tu", LuceneEnhancer.provider,suggest.getResource());
		tag2.setValue(2.0);
		TagTO tag3 = new TagTO("comer", LuceneEnhancer.provider,suggest.getResource());
		tag3.setValue(2.0);
		TagTO tag4 = new TagTO("uno", LuceneEnhancer.provider,suggest.getResource());
		tag4.setValue(2.0);
		
		tags.put(tag1.getLabel(), tag1);
		tags.put(tag2.getLabel(), tag2);
		tags.put(tag3.getLabel(), tag3);
		tags.put(tag4.getLabel(), tag4);
		
		openNLPEnhancer.tags = tags;
		openNLPEnhancer.suggest = suggest;
		
		suggest.setTags(tags);
		
		openNLPEnhancer.processSetence(new String[]{"PN","N","V","Z"}, new String[]{"eso","tu","comer","uno"});
		
		assertTrue(openNLPEnhancer.tags.get("eso")==null);
		assertTrue(openNLPEnhancer.noun.contains("tu"));
		assertTrue(openNLPEnhancer.verbs.contains("comer"));
		assertTrue(openNLPEnhancer.numbers.contains("uno"));
	}
	
	@Test 
	public void calculateMaxValueEmptyTest(){
		Map<String, TagTO> tags = new HashMap<String, TagTO>();
		
		openNLPEnhancer.tags = tags;
		assertTrue(0 == openNLPEnhancer.calculateMaxValue());
	}
	
	@Test 
	public void findAndremoveNotMatch() throws Exception{
		SuggestionTO suggest = initializeSuggest();
		
		Map<String, TagTO> tags = new HashMap<String, TagTO>();
		TagTO tag = new TagTO("esos", LuceneEnhancer.provider,
				suggest.getResource());
		tag.setValue(1.0);
		tags.put(tag.getLabel(), tag);
		
		openNLPEnhancer.tags = tags;
		openNLPEnhancer.suggest = suggest;
		
		openNLPEnhancer.findAndRemove("eso");
		
		assertTrue(null == openNLPEnhancer.tags.get("eso"));
	}
	
	@Test 
	public void findAndremoveTest() throws Exception{
		SuggestionTO suggest = initializeSuggest();
		
		Map<String, TagTO> tags = new HashMap<String, TagTO>();
		TagTO tag = new TagTO("eso", LuceneEnhancer.provider,
				suggest.getResource());
		tag.setValue(1.0);
		tags.put(tag.getLabel(), tag);
		
		openNLPEnhancer.tags = tags;
		openNLPEnhancer.suggest = suggest;
		
		openNLPEnhancer.findAndRemove("eso");
		
		assertTrue(null == openNLPEnhancer.tags.get("eso"));
	}
	
	@Test
	public void findAndChangeNounsEmpty() throws Exception{
		SuggestionTO suggest = initializeSuggest();
		
		Map<String, TagTO> tags = new HashMap<String, TagTO>();
		TagTO tag = new TagTO("yo", LuceneEnhancer.provider,
				suggest.getResource());
		tag.setValue(1.0);
		tags.put(tag.getLabel(), tag);
		
		openNLPEnhancer.tags = tags;
		openNLPEnhancer.suggest = suggest;
		
		openNLPEnhancer.findAndChangeNoun();
		
		assertTrue(1 == openNLPEnhancer.tags.get("yo").getValue());
	}
	
	@Test
	public void findAndChangeNounsTest() throws Exception{
		SuggestionTO suggest = initializeSuggest();
		
		Map<String, TagTO> tags = new HashMap<String, TagTO>();
		TagTO tag = new TagTO("yo", LuceneEnhancer.provider,
				suggest.getResource());
		tag.setValue(1.0);
		tags.put(tag.getLabel(), tag);
		
		openNLPEnhancer.tags = tags;
		openNLPEnhancer.suggest = suggest;
		
		openNLPEnhancer.noun = new HashSet<String>();
		openNLPEnhancer.noun.add("yo");
		
		openNLPEnhancer.findAndChangeNoun();
		assertTrue(1.5 == openNLPEnhancer.tags.get("yo").getValue());
	}
	
	@Test
	public void findAndChangeVerbsEmpty() throws Exception{
		SuggestionTO suggest = initializeSuggest();
		
		Map<String, TagTO> tags = new HashMap<String, TagTO>();
		TagTO tag = new TagTO("comer", LuceneEnhancer.provider,
				suggest.getResource());
		tag.setValue(2.0);
		tags.put(tag.getLabel(), tag);
		
		openNLPEnhancer.tags = tags;
		openNLPEnhancer.suggest = suggest;
		
		openNLPEnhancer.findAndChangeVerbs();
		
		assertTrue(2 == openNLPEnhancer.tags.get("comer").getValue());
	}
	
	@Test
	public void findAndChangeVerbsTest() throws Exception{
		SuggestionTO suggest = initializeSuggest();
		
		Map<String, TagTO> tags = new HashMap<String, TagTO>();
		TagTO tag = new TagTO("comer", LuceneEnhancer.provider,
				suggest.getResource());
		tag.setValue(2.0);
		tags.put(tag.getLabel(), tag);
		
		openNLPEnhancer.tags = tags;
		openNLPEnhancer.suggest = suggest;
		
		openNLPEnhancer.verbs = new HashSet<String>();
		openNLPEnhancer.verbs.add("comer");
		
		openNLPEnhancer.findAndChangeVerbs();
		
		assertTrue(1 == openNLPEnhancer.tags.get("comer").getValue());
	}
	
	@Test
	public void findAndChangeNumbersEmpty() throws Exception{
		SuggestionTO suggest = initializeSuggest();
		
		Map<String, TagTO> tags = new HashMap<String, TagTO>();
		TagTO tag = new TagTO("uno", LuceneEnhancer.provider,
				suggest.getResource());
		tag.setValue(1.0);
		tags.put(tag.getLabel(), tag);
		
		openNLPEnhancer.tags = tags;
		openNLPEnhancer.suggest = suggest;
		
		openNLPEnhancer.findAndChangeNumbers();
		
		assertTrue(1 == openNLPEnhancer.tags.get("uno").getValue());
	}
	
	@Test
	public void findAndChangeNumbersTest() throws Exception{
		SuggestionTO suggest = initializeSuggest();
		
		Map<String, TagTO> tags = new HashMap<String, TagTO>();
		TagTO tag = new TagTO("uno", LuceneEnhancer.provider,
				suggest.getResource());
		tag.setValue(1.0);
		tags.put(tag.getLabel(), tag);
		
		openNLPEnhancer.tags = tags;
		openNLPEnhancer.suggest = suggest;
		
		openNLPEnhancer.numbers = new HashSet<String>();
		openNLPEnhancer.numbers.add("uno");
		
		openNLPEnhancer.findAndChangeNumbers();
		
		assertTrue(0 == openNLPEnhancer.tags.get("uno").getValue());
	}
	
	private SuggestionTO initializeSuggest() throws Exception {
		SuggestionTO suggest = new SuggestionTO();
		ResourceTO resource = new ResourceTO();
		RequestSuggestionTO request = mock(RequestSuggestionTO.class);
		when(request.getResource()).thenReturn(resource);
		when(request.getSuggestions()).thenReturn(suggest);
		openNLPEnhancer.request = request;
		openNLPEnhancer.preExecute();
		return openNLPEnhancer.suggest;
	}
	
}
