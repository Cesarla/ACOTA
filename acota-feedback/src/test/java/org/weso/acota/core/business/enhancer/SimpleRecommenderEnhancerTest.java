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

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Before;
import org.junit.Test;
import org.weso.acota.core.entity.Feedback;
import org.weso.acota.core.entity.RequestSuggestionTO;
import org.weso.acota.core.entity.ResourceTO;
import org.weso.acota.core.entity.SuggestionTO;
import org.weso.acota.core.entity.TagTO;
import org.weso.acota.persistence.mysql.FeedbackMysqlDAO;

public class SimpleRecommenderEnhancerTest {

	protected SimpleRecommenderEnhancer simpleRecommenderEnhancer;

	@Before
	public void startUp() throws ConfigurationException, SQLException,
			ClassNotFoundException, InstantiationException, IllegalAccessException {
		this.simpleRecommenderEnhancer = new SimpleRecommenderEnhancer();

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
				Collections.<String, Double> emptyMap(),
				Collections.<TagTO> emptySet(), new ResourceTO());
		simpleRecommenderEnhancer.suggest = s;
		assertEquals(s, simpleRecommenderEnhancer.getSuggest());
	}

	@Test
	public void getProviderTest() {
		assertEquals(SimpleRecommenderEnhancer.provider,
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
	public void executeTest(){
		Map<String, Double> labels = new HashMap<String, Double>();
		labels.put("researcher",4.0);
		
		ResourceTO resource = new ResourceTO();
		resource.setDescription("Esto es Español");
		resource.setLabel("español");
		
		SuggestionTO suggestion = new SuggestionTO();
		suggestion.setLabels(labels);
		
		RequestSuggestionTO request = mock(RequestSuggestionTO.class);
		when(request.getResource()).thenReturn(resource);
		when(request.getSuggestions()).thenReturn(suggestion);
		
		simpleRecommenderEnhancer.enhance(request);
		assertTrue(9 == suggestion.getLabels().get("researcher"));
	}
}
