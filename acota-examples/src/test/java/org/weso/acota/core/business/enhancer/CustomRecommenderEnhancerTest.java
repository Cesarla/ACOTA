package org.weso.acota.core.business.enhancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.weso.acota.core.business.enhancer.CustomRecommenderEnhancer;
import org.weso.acota.core.entity.persistence.Feedback;
import org.weso.acota.core.entity.RequestSuggestionTO;
import org.weso.acota.core.entity.ResourceTO;
import org.weso.acota.core.entity.SuggestionTO;
import org.weso.acota.core.entity.TagTO;
import org.weso.acota.core.exceptions.AcotaConfigurationException;
import org.weso.acota.persistence.mysql.FeedbackMysqlDAO;

public class CustomRecommenderEnhancerTest {

	protected CustomRecommenderEnhancer simpleRecommenderEnhancer;

	@Before
	public void startUp() throws AcotaConfigurationException, SQLException,
			ClassNotFoundException, InstantiationException, IllegalAccessException {
		this.simpleRecommenderEnhancer = new CustomRecommenderEnhancer();

		Set<Feedback> feedbacks = new HashSet<Feedback>();
		feedbacks.add(new Feedback(1, 1, "researcher",
				"http://www.example.com", new Date()));

		FeedbackMysqlDAO feedbackDao = mock(FeedbackMysqlDAO.class);
		when(feedbackDao.getFeedbacksByLabel("researcher")).thenReturn(feedbacks);

		simpleRecommenderEnhancer.feedbackDao = feedbackDao;
	}

	@Test
	public void getSuggestEmpty() {
		assertEquals(new SuggestionTO(), simpleRecommenderEnhancer.getSuggest());
	}

	@Test
	public void getSuggestTest() {
		SuggestionTO s = new SuggestionTO(
				Collections.<String, TagTO> emptyMap(), new ResourceTO());
		simpleRecommenderEnhancer.suggest = s;
		assertEquals(s, simpleRecommenderEnhancer.getSuggest());
	}

	@Test
	public void getProviderTest() {
		assertEquals(CustomRecommenderEnhancer.provider,
				simpleRecommenderEnhancer.getProvider());
	}

	@Test
	public void preExecuteEmptyRequestTest() throws Exception {
		ResourceTO resource = new ResourceTO();
		SuggestionTO suggestion = new SuggestionTO();

		RequestSuggestionTO request = mock(RequestSuggestionTO.class);
		when(request.getResource()).thenReturn(resource);
		when(request.getSuggestions()).thenReturn(suggestion);

		simpleRecommenderEnhancer.request = request;
		simpleRecommenderEnhancer.preExecute();

		assertEquals(resource, simpleRecommenderEnhancer.suggest.getResource());
	}
	
	@Test
	public void executeTest() throws Exception{
		SuggestionTO suggest = initializeSuggest();
		
		Map<String, TagTO> tags = new HashMap<String, TagTO>();
		TagTO tag = new TagTO("researcher", CustomRecommenderEnhancer.provider,
				suggest.getResource());
		tag.setValue(4.0);
		tags.put(tag.getLabel(), tag);
		
		simpleRecommenderEnhancer.tags = tags;
		simpleRecommenderEnhancer.suggest = suggest;
		
		ResourceTO resource = new ResourceTO();
		resource.setDescription("Esto es Español");
		resource.setLabel("español");
		
		suggest.setTags(tags);
		
		RequestSuggestionTO request = mock(RequestSuggestionTO.class);
		when(request.getResource()).thenReturn(resource);
		when(request.getSuggestions()).thenReturn(suggest);
		
		simpleRecommenderEnhancer.enhance(request);
		assertTrue(9 == suggest.getTags().get("researcher").getValue());
	}
	
	private SuggestionTO initializeSuggest() throws Exception {
		SuggestionTO suggest = new SuggestionTO();
		ResourceTO resource = new ResourceTO();
		RequestSuggestionTO request = mock(RequestSuggestionTO.class);
		when(request.getResource()).thenReturn(resource);
		when(request.getSuggestions()).thenReturn(suggest);
		simpleRecommenderEnhancer.request = request;
		simpleRecommenderEnhancer.preExecute();
		return simpleRecommenderEnhancer.suggest;
	}
}
