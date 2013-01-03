package org.weso.acota.core.business.enhancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.lucene.analysis.tokenattributes.CharTermAttributeImpl;
import org.junit.Before;
import org.junit.Test;
import org.weso.acota.core.business.enhancer.LuceneEnhancer;
import org.weso.acota.core.business.enhancer.lucene.analyzer.EnglishStopAnalyzer;
import org.weso.acota.core.business.enhancer.lucene.analyzer.SpanishStopAnalyzer;
import org.weso.acota.core.business.enhancer.lucene.analyzer.DefaultStopAnalyzer;
import org.weso.acota.core.entity.RequestSuggestionTO;
import org.weso.acota.core.entity.ResourceTO;
import org.weso.acota.core.entity.SuggestionTO;
import org.weso.acota.core.entity.TagTO;

public class LuceneEnhancerTest{

	private LuceneEnhancer luceneEnhancer;

	@Before
	public void startTest() throws ConfigurationException {
		this.luceneEnhancer = new LuceneEnhancer();
	}

	@Test
	public void getProviderTest(){
		assertEquals(LuceneEnhancer.provider, luceneEnhancer.getProvider());
	}
	
	@Test
	public void enhanceWithSuccessor()throws IOException, ConfigurationException{
		luceneEnhancer.setSuccessor(new LuceneEnhancer());
		ResourceTO resource = new ResourceTO();
		resource.setDescription("Esto es Español");
		resource.setLabel("español");
		SuggestionTO suggestion = new SuggestionTO();
		
		RequestSuggestionTO request = mock(RequestSuggestionTO.class);
		when(request.getResource()).thenReturn(resource);
		when(request.getSuggestions()).thenReturn(suggestion);
		
		luceneEnhancer.enhance(request);
		assertTrue(30 == suggestion.getTags().get("español").getValue());
	}
	
	@Test
	public void enhanceTest() throws IOException{
		ResourceTO resource = new ResourceTO();
		resource.setDescription("Un perro es un animal de compañía");
		resource.setLabel("animal de compañía");
		SuggestionTO suggestion = new SuggestionTO();
		
		RequestSuggestionTO request = mock(RequestSuggestionTO.class);
		when(request.getResource()).thenReturn(resource);
		when(request.getSuggestions()).thenReturn(suggestion);
		
		luceneEnhancer.enhance(request);
		assertTrue(15 == suggestion.getTags().get("animal").getValue());
	}
	
	@Test
	public void enhanceEspecialCase() throws IOException{
		ResourceTO resource = new ResourceTO();
		resource.setDescription("sdfsdf");
		resource.setLabel("sdfsdfl");
		SuggestionTO suggestion = new SuggestionTO();
		
		RequestSuggestionTO request = mock(RequestSuggestionTO.class);
		when(request.getResource()).thenReturn(resource);
		when(request.getSuggestions()).thenReturn(suggestion);
		
		luceneEnhancer.enhance(request);
		assertTrue(null == suggestion.getTags().get("español"));
	}
	
	
	@Test
	public void preExecuteEmptyRequestTest() throws Exception {
		ResourceTO resource = new ResourceTO();
		SuggestionTO suggestion = new SuggestionTO();

		RequestSuggestionTO request = mock(RequestSuggestionTO.class);
		when(request.getResource()).thenReturn(resource);
		when(request.getSuggestions()).thenReturn(suggestion);

		
		luceneEnhancer.request = request;
		luceneEnhancer.preExecute();
		
		assertEquals(resource, luceneEnhancer.suggest.getResource());
	}

	@Test
	public void loadAnalyzerEs() {
		assertTrue(SpanishStopAnalyzer.class.isInstance(
				luceneEnhancer.loadAnalyzer("Esto es Español")));
	}

	@Test
	public void loadAnalyzerEn() {
		assertTrue(EnglishStopAnalyzer.class.isInstance(
				luceneEnhancer.loadAnalyzer("This is English")));
	}

	@Test
	public void loadAnalyzerOther() {
		assertTrue(DefaultStopAnalyzer.class.isInstance(
				luceneEnhancer.loadAnalyzer("Das ist Deutsche")));
	}
	
	
	@Test
	public void extractDescriptionTermsEmptyLabels() throws Exception{
		Map<String, TagTO> tags = new HashMap<String, TagTO>();
		
		SuggestionTO suggest = initializeSuggest();
		suggest.setTags(tags);
		luceneEnhancer.suggest = suggest;
		luceneEnhancer.tags = tags;
		
		ResourceTO resource = new ResourceTO();
		resource.setDescription("Esto es Español");
		
		luceneEnhancer.request = new RequestSuggestionTO(resource);
		luceneEnhancer.extractDescriptionTerms();
		
		assertTrue(5 == suggest.getTags().get("español").getValue());
	}
	
	@Test
	public void extractDescriptionSpecialCase() throws Exception{
		SuggestionTO suggest = initializeSuggest();
		
		Map<String, TagTO> tags = new HashMap<String, TagTO>();
		
		TagTO tag = new TagTO("español", LuceneEnhancer.provider,
				suggest.getResource());
		tag.setValue(1.0);
		tags.put(tag.getLabel(),tag);
		
		suggest.setTags(tags);
		luceneEnhancer.suggest = suggest;
		luceneEnhancer.tags = tags;
		
		ResourceTO resource = new ResourceTO();
		resource.setDescription("asdfokl");
		
		luceneEnhancer.request = new RequestSuggestionTO(resource);
		luceneEnhancer.extractDescriptionTerms();
		
		assertTrue(1 == suggest.getTags().get("español").getValue());
	}
	
	@Test
	public void extracDescriptionTermsTest() throws Exception{
		SuggestionTO suggest = initializeSuggest();
		
		Map<String, TagTO> tags = new HashMap<String, TagTO>();
		
		TagTO tag = new TagTO("español", LuceneEnhancer.provider,
				suggest.getResource());
		tag.setValue(1.0);
		tags.put(tag.getLabel(),tag);
		
		suggest.setTags(tags);
		luceneEnhancer.suggest = suggest;
		luceneEnhancer.tags = tags;
		
		ResourceTO resource = new ResourceTO();
		resource.setDescription("Esto es Español");
		
		luceneEnhancer.request = new RequestSuggestionTO(resource);
		luceneEnhancer.extractDescriptionTerms();
		
		assertTrue(6 == suggest.getTags().get("español").getValue());
	}
	
	@Test
	public void extractLabelTermsEmptyLabels() throws Exception{
		Map<String, TagTO> tags = new HashMap<String, TagTO>();
		
		SuggestionTO suggest = initializeSuggest();
		suggest.setTags(tags);
		luceneEnhancer.suggest = suggest;
		luceneEnhancer.tags = tags;
		
		ResourceTO resource = new ResourceTO();
		resource.setLabel("Esto es Español");
		
		luceneEnhancer.request = new RequestSuggestionTO(resource);
		
		luceneEnhancer.extractLabelTerms();
		
		assertTrue(10 == suggest.getTags().get("español").getValue());
	}
	
	@Test
	public void extractLabelSpecialCase() throws Exception{
		SuggestionTO suggest = initializeSuggest();
		
		Map<String, TagTO> tags = new HashMap<String, TagTO>();
		
		TagTO tag = new TagTO("español", LuceneEnhancer.provider,
				suggest.getResource());
		tag.setValue(1.0);
		tags.put(tag.getLabel(),tag);
		
		suggest.setTags(tags);
		luceneEnhancer.suggest = suggest;
		luceneEnhancer.tags = tags;
		
		ResourceTO resource = new ResourceTO();
		resource.setLabel("asdfokl");
		
		luceneEnhancer.request = new RequestSuggestionTO(resource);
		
		luceneEnhancer.extractLabelTerms();
		
		assertTrue(1 == suggest.getTags().get("español").getValue());
	}

	@Test
	public void extractLabelTermsTest() throws Exception{
		SuggestionTO suggest = initializeSuggest();
		
		Map<String, TagTO> tags = new HashMap<String, TagTO>();
		
		TagTO tag = new TagTO("español", LuceneEnhancer.provider,
				suggest.getResource());
		tag.setValue(1.0);
		tags.put(tag.getLabel(),tag);
		

		suggest.setTags(tags);
		luceneEnhancer.suggest = suggest;
		luceneEnhancer.tags = tags;
		
		ResourceTO resource = new ResourceTO();
		resource.setLabel("Esto es Español");
		
		luceneEnhancer.request = new RequestSuggestionTO(resource);
		
		luceneEnhancer.extractLabelTerms();
		
		assertTrue(11 == suggest.getTags().get("español").getValue());
	}
	
	
	@Test
	public void extractTermnsEmtpyLabels() throws Exception{
		SuggestionTO suggest = initializeSuggest();
		Map<String, TagTO> tags = new HashMap<String, TagTO>();
		
		suggest.setTags(tags);
		luceneEnhancer.suggest = suggest;
		luceneEnhancer.tags = tags;
		
		luceneEnhancer.extractTerms("label", "Esto es Español", 10);
		assertTrue(10 == suggest.getTags().get("español").getValue());
	}
	
	@Test
	public void extractTermnsTest() throws Exception{
		SuggestionTO suggest = initializeSuggest();
		
		Map<String, TagTO> tags = new HashMap<String, TagTO>();
		TagTO tag = new TagTO("español", LuceneEnhancer.provider,
				suggest.getResource());
		tag.setValue(3.0);
		tags.put(tag.getLabel(), tag);
		
		luceneEnhancer.suggest.setTags(tags);
		luceneEnhancer.tags = tags;
		
		luceneEnhancer.extractTerms("label", "Esto es Español", 10);
		assertTrue(13 == suggest.getTags().get("español").getValue());
	}
	
	@Test
	public void fillSuggestiosTest() throws Exception{
		SuggestionTO suggest = initializeSuggest();
		
		Map<String, TagTO> tags = new HashMap<String, TagTO>();
		TagTO tag = new TagTO("foo", LuceneEnhancer.provider,
				suggest.getResource());
		tag.setValue(1);
		tags.put(tag.getLabel(), tag);
		
		luceneEnhancer.suggest = suggest;
		luceneEnhancer.tags = tags;
		
		tag = new TagTO("foo", LuceneEnhancer.provider,
				suggest.getResource());
		
		luceneEnhancer.fillSuggestions(tag,1);
		
		assertTrue(2 == tags.get("foo").getValue());
	}
	
	@Test
	public void fillSuggestionsEmptyLabels() throws Exception{
		Map<String, TagTO> tags = new HashMap<String, TagTO>();
		
		SuggestionTO suggest = initializeSuggest();

		luceneEnhancer.suggest = suggest;
		luceneEnhancer.tags = tags;
		
		TagTO tag = new TagTO("foo", LuceneEnhancer.provider,
				suggest.getResource());
		
		luceneEnhancer.fillSuggestions(tag,1);
		
		assertTrue(1 == tags.get("foo").getValue());
	}

	@Test
	public void addTagTest() throws Exception {
		CharTermAttributeImpl attribute = new CharTermAttributeImpl();
		attribute.append("foo");
		SuggestionTO suggest = initializeSuggest();
		TagTO tag = new TagTO("foo", LuceneEnhancer.provider,
				suggest.getResource());

		assertEquals(tag, luceneEnhancer.createTag(attribute));
	}
	
	private SuggestionTO initializeSuggest() throws Exception {
		SuggestionTO suggest = new SuggestionTO();
		ResourceTO resource = new ResourceTO();
		RequestSuggestionTO request = mock(RequestSuggestionTO.class);
		when(request.getResource()).thenReturn(resource);
		when(request.getSuggestions()).thenReturn(suggest);
		luceneEnhancer.request = request;
		luceneEnhancer.preExecute();
		return luceneEnhancer.suggest;
	}
}
