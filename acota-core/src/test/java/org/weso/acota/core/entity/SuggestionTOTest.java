package org.weso.acota.core.entity;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.weso.acota.core.entity.ResourceTO;
import org.weso.acota.core.entity.SuggestionTO;
import org.weso.acota.core.entity.TagTO;

/**
 * 
 * @author César Luis Alvargonzález
 *
 */
public class SuggestionTOTest {

	protected SuggestionTO suggestionTO;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.suggestionTO = new SuggestionTO();
	}

	@Test
	public void emptyConstructorLabels() {
		assertEquals(Collections.EMPTY_MAP,
				suggestionTO.labels);
	}

	@Test
	public void emptyConstructorTags() {
		assertEquals(Collections.EMPTY_SET,
				suggestionTO.tags);
	}

	@Test
	public void ConstructorLabelsResource() {
		assertTrue(null == suggestionTO.resource);
	}
	
	@Test
	public void ConstructorLabelsTest() {
		this.suggestionTO = new SuggestionTO(
				null, null, new ResourceTO());
		assertTrue(null == suggestionTO.labels);
	}

	@Test
	public void ConstructorTagsTest() {
		this.suggestionTO = new SuggestionTO(
				null, null, new ResourceTO());
		assertTrue(null == suggestionTO.tags);
	}

	@Test
	public void ConstructorLabelsResourceTest() {
		ResourceTO resourceTO = new ResourceTO();
		this.suggestionTO = new SuggestionTO(
				null, null, resourceTO);
		assertEquals(resourceTO, suggestionTO.resource);
	}

	@Test
	public void getLabelsEmpty() {
		assertEquals(Collections.EMPTY_MAP, suggestionTO.getLabels());
	}

	@Test
	public void getLabelsTest() {
		ResourceTO resourceTO = new ResourceTO();
		this.suggestionTO = new SuggestionTO(
				null, null, resourceTO);
		assertEquals(null, suggestionTO.getLabels());
	}

	@Test
	public void getTagsEmpty() {
		assertEquals(Collections.EMPTY_SET, suggestionTO.getTags());
	}

	@Test
	public void getTagsTest() {
		ResourceTO resourceTO = new ResourceTO();
		this.suggestionTO = new SuggestionTO(
				null, null, resourceTO);
		assertEquals(null, suggestionTO.getTags());
	}
	
	@Test
	public void getResourceEmpty() {
		assertTrue(null == suggestionTO.getResource());
	}

	@Test
	public void getResourceTest() {
		ResourceTO resourceTO = new ResourceTO();
		this.suggestionTO = new SuggestionTO(
				null, null, resourceTO);
		assertEquals(resourceTO, suggestionTO.getResource());
	}

	@Test
	public void setLabelsEmpty() {
		suggestionTO.setLabels(Collections.<String, Double> emptyMap());
		assertTrue(Collections.<String, Double> emptyMap() == suggestionTO.labels);
	}

	@Test
	public void setLabelsTest() {
		suggestionTO.setLabels(null);
		assertEquals(null, suggestionTO.labels);
	}

	@Test
	public void setTagsEmpty() {
		suggestionTO.setTags(Collections.<TagTO> emptySet());
		assertTrue(Collections.<TagTO> emptySet() == suggestionTO.tags);
	}

	@Test
	public void setTagsTest() {
		suggestionTO.setTags(null);
		assertEquals(null, suggestionTO.tags);
	}
	
	@Test
	public void setResourceEmpty() {
		suggestionTO.setTags(null);
		assertTrue(null == suggestionTO.tags);
	}

	@Test
	public void setReourceTest() {
		ResourceTO resourceTO = new ResourceTO();
		suggestionTO.setResource(resourceTO);
		assertEquals(resourceTO, suggestionTO.resource);
	}


	@Test
	public void hashCodeEmpty() {
		assertTrue(29791 == suggestionTO.hashCode());
	}

	@Test
	public void hashCodeTest() {
		ResourceTO resourceTO = new ResourceTO();
		this.suggestionTO = new SuggestionTO(
				null, null, resourceTO);
		assertTrue(28658942 == suggestionTO.hashCode());
	}
	
	@Test
	public void equalsNull(){
		assertFalse(suggestionTO.equals(null));
	}
	
	@Test
	public void equalsSameInstance(){
		assertTrue(suggestionTO.equals(suggestionTO));
	}
	
	@Test
	public void equalsDifferentObject(){
		String aux = new String("aux");
		assertFalse(suggestionTO.equals(aux));
	}
	
	@Test
	public void equalsResourcesEquals(){
		SuggestionTO s1 = new SuggestionTO(Collections.<String, Double> emptyMap(),Collections.<TagTO> emptySet(),new ResourceTO());
		SuggestionTO s2 = new SuggestionTO(Collections.<String, Double> emptyMap(),Collections.<TagTO> emptySet(),new ResourceTO());
		
		assertTrue(s1.equals(s2));
	}
	
	@Test
	public void equalsResourcesNulls(){
		SuggestionTO s1 = new SuggestionTO(null,null,null);
		SuggestionTO s2 = new SuggestionTO(null,null,null);
		
		assertTrue(s1.equals(s2));
	}
	
	@Test
	public void equalsTags(){
		Set<TagTO> s = new HashSet<TagTO>();
		s.add(new TagTO());
		
		SuggestionTO s1 = new SuggestionTO(null,Collections.<TagTO> emptySet(),null);
		SuggestionTO s2 = new SuggestionTO(null,s,null);
		
		assertFalse(s1.equals(s2));
	}
	
	@Test
	public void equalsTags1(){
		SuggestionTO s1 = new SuggestionTO(null,null,null);
		SuggestionTO s2 = new SuggestionTO(null,Collections.<TagTO> emptySet(),null);
		
		assertFalse(s1.equals(s2));
	}
	
	@Test
	public void equalsLabels(){
		Map<String,Double> m = new HashMap<String,Double>();
		m.put("a",2.0);
		
		SuggestionTO s1 = new SuggestionTO(Collections.<String, Double> emptyMap(),null,null);
		SuggestionTO s2 = new SuggestionTO(m,null,null);
		
		assertFalse(s1.equals(s2));
	}
	
	@Test
	public void equalsLabels1(){
		SuggestionTO s1 = new SuggestionTO(null,null,null);
		SuggestionTO s2 = new SuggestionTO(Collections.<String, Double> emptyMap(),null,null);
		
		assertFalse(s1.equals(s2));
	}
	
	@Test
	public void equalsResources(){
		ResourceTO r = new ResourceTO();
		r.setLabel("a");
		
		SuggestionTO s1 = new SuggestionTO(null,null,new ResourceTO());
		SuggestionTO s2 = new SuggestionTO(null,null,r);
		
		assertFalse(s1.equals(s2));
	}
	
	@Test
	public void equalsResources1(){
		SuggestionTO s1 = new SuggestionTO(null,null,null);
		SuggestionTO s2 = new SuggestionTO(null,null,new ResourceTO());
		
		assertFalse(s1.equals(s2));
	}
	
}

