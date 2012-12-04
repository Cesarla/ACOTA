package org.weso.acota.persistence;

import java.sql.SQLException;
import java.util.Set;

import org.weso.acota.core.entity.Feedback;

public interface FeedbackDAO extends GenericDAO{
	
	public void saveFeedback(Feedback feedback) throws SQLException, ClassNotFoundException;
	
	public Set<Feedback> getAllFeedbacks() throws SQLException, ClassNotFoundException;
	
	public Set<Feedback> getFeedbacksByUserId(int userId) throws SQLException, ClassNotFoundException;
	
	public Set<Feedback> getFeedbacksByLabel(String label) throws SQLException, ClassNotFoundException;
	
	public Set<Feedback> getFeedbacksByDocument(String document) throws SQLException, ClassNotFoundException;
	
}
