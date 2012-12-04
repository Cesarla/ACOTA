package org.weso.acota.persistence.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.configuration.ConfigurationException;
import org.weso.acota.core.entity.Feedback;
import org.weso.acota.core.entity.tuples.DocumentTuple;
import org.weso.acota.core.entity.tuples.FeedbackTuple;
import org.weso.acota.core.entity.tuples.LabelTuple;
import org.weso.acota.persistence.FeedbackDAO;

public class FeedbackMysqlDAO extends GenericMysqlDAO implements FeedbackDAO {
	
	protected String documentTableName;
	protected String documentIdField;
	protected String documentNameField;

	protected String feedbackTableName;
	protected String feedbackIdField;
	protected String feedbackUserIdField;
	protected String feedbackDocumentIdField;
	protected String feedbackLabelIdField;
	protected String feedbackPreferenceField;
	protected String feedbackTimestampField;

	protected String labelTableName;
	protected String labelIdField;
	protected String labelNameField;

	public FeedbackMysqlDAO() throws ConfigurationException {
		super();
		DocumentTuple document = configuration.getDocumentTuple();
		this.documentTableName = document.getName();
		this.documentIdField = document.getIdField();
		this.documentNameField = document.getNameField();

		FeedbackTuple feedback = configuration.getFeedbackTuple();
		this.feedbackTableName = feedback.getName();
		this.feedbackIdField = feedback.getIdField();
		this.feedbackUserIdField = feedback.getUserIdField();
		this.feedbackDocumentIdField = feedback.getDocumentIdField();
		this.feedbackLabelIdField = feedback.getLabelIdField();
		this.feedbackPreferenceField = feedback.getPreferenceField();
		this.feedbackTimestampField = feedback.getTimestampField();

		LabelTuple label = configuration.getLabelTuple();
		this.labelTableName = label.getName();
		this.labelIdField = label.getIdField();
		this.labelNameField = label.getNameField();
	}

	@Override
	public void saveFeedback(Feedback feedback) throws ClassNotFoundException,
			SQLException {
		PreparedStatement ps = null;
		Connection con = null;

		try {
			con = openConnection();

			StringBuilder query = new StringBuilder("insert into ")
					.append(feedbackTableName).append(" (")
					.append(feedbackUserIdField).append(", ")
					.append(feedbackDocumentIdField).append(", ")
					.append(feedbackLabelIdField).append(") values(?,?,?)");

			ps = con.prepareStatement(query.toString());
			ps.setInt(1, feedback.getUserId());
			ps.setInt(2, feedback.getDocument().hashCode());
			ps.setInt(3, feedback.getLabel().hashCode());

			ps.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			closeStatement(ps);
			closeConnection(con);

		}
	}

	@Override
	public Set<Feedback> getAllFeedbacks() throws SQLException,
			ClassNotFoundException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		Feedback feedback = null;

		Set<Feedback> feedbacks = new HashSet<Feedback>();
		try {
			con = openConnection();

			StringBuilder query = new StringBuilder("select f.")
					.append(feedbackIdField).append(", f.")
					.append(feedbackUserIdField).append(", d.")
					.append(documentNameField).append(", l.")
					.append(labelNameField).append(",f.")
					.append(feedbackTimestampField).append(" from ")
					.append(documentTableName).append(" as d,")
					.append(feedbackTableName).append(" as f,")
					.append(labelTableName).append(" as l where f.")
					.append(feedbackDocumentIdField).append(" = d.")
					.append(documentIdField).append(" and f.")
					.append(feedbackLabelIdField).append(" = l.")
					.append(labelIdField);

			ps = con.prepareStatement(query.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				feedback = new Feedback();
				feedback.setId(rs.getInt(feedbackIdField));
				feedback.setUserId(rs.getInt(feedbackUserIdField));
				feedback.setDocument(rs.getString("d." + documentNameField));
				feedback.setLabel(rs.getString("l." + labelNameField));
				feedback.setDate(rs.getTimestamp(feedbackTimestampField));
				feedbacks.add(feedback);
			}

		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			closeResult(rs);
			closeStatement(ps);
			closeConnection(con);

		}
		return feedbacks;
	}

	@Override
	public Set<Feedback> getFeedbacksByUserId(int userId) throws SQLException,
			ClassNotFoundException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		Feedback feedback = null;

		Set<Feedback> feedbacks = new HashSet<Feedback>();
		try {
			con = openConnection();

			StringBuilder query = new StringBuilder("select f.")
					.append(feedbackIdField).append(", f.")
					.append(feedbackUserIdField).append(", d.")
					.append(documentNameField).append(", l.")
					.append(labelNameField).append(",f.")
					.append(feedbackTimestampField).append(" from ")
					.append(documentTableName).append(" as d,")
					.append(feedbackTableName).append(" as f,")
					.append(labelTableName).append(" as l where f.")
					.append(feedbackUserIdField).append(" = ? and f.")
					.append(feedbackDocumentIdField).append(" = d.")
					.append(documentIdField).append(" and f.")
					.append(feedbackLabelIdField).append(" = l.")
					.append(labelIdField);

			ps = con.prepareStatement(query.toString());
			ps.setInt(1, userId);
			rs = ps.executeQuery();

			while (rs.next()) {
				feedback = new Feedback();
				feedback = new Feedback();
				feedback.setId(rs.getInt(feedbackIdField));
				feedback.setUserId(rs.getInt(feedbackUserIdField));
				feedback.setDocument(rs.getString("d." + documentNameField));
				feedback.setLabel(rs.getString("l." + labelNameField));
				feedback.setDate(rs.getTimestamp(feedbackTimestampField));
				feedbacks.add(feedback);
			}

		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			closeResult(rs);
			closeStatement(ps);
			closeConnection(con);

		}
		return feedbacks;
	}

	@Override
	public Set<Feedback> getFeedbacksByLabel(String label) throws SQLException,
			ClassNotFoundException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		Feedback feedback = null;

		Set<Feedback> feedbacks = new HashSet<Feedback>();
		try {
			con = openConnection();

			StringBuilder query = new StringBuilder("select f.")
					.append(feedbackIdField).append(", f.")
					.append(feedbackUserIdField).append(", d.")
					.append(documentNameField).append(", l.")
					.append(labelNameField).append(",f.")
					.append(feedbackTimestampField).append(" from ")
					.append(documentTableName).append(" as d,")
					.append(feedbackTableName).append(" as f,")
					.append(labelTableName).append(" as l where l.")
					.append(labelNameField).append(" = ? and f.")
					.append(feedbackDocumentIdField).append(" = d.")
					.append(documentIdField).append(" and f.")
					.append(feedbackLabelIdField).append(" = l.")
					.append(labelIdField);

			ps = con.prepareStatement(query.toString());
			ps.setString(1, label);
			rs = ps.executeQuery();

			while (rs.next()) {
				feedback = new Feedback();
				feedback = new Feedback();
				feedback.setId(rs.getInt(feedbackIdField));
				feedback.setUserId(rs.getInt(feedbackUserIdField));
				feedback.setDocument(rs.getString("d." + documentNameField));
				feedback.setLabel(rs.getString("l." + labelNameField));
				feedback.setDate(rs.getTimestamp(feedbackTimestampField));
				feedbacks.add(feedback);
			}

		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			closeResult(rs);
			closeStatement(ps);
			closeConnection(con);

		}
		return feedbacks;
	}

	@Override
	public Set<Feedback> getFeedbacksByDocument(String document)
			throws SQLException, ClassNotFoundException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		Feedback feedback = null;

		Set<Feedback> feedbacks = new HashSet<Feedback>();
		try {
			con = openConnection();

			StringBuilder query = new StringBuilder("select f.")
					.append(feedbackIdField).append(", f.")
					.append(feedbackUserIdField).append(", d.")
					.append(documentNameField).append(", l.")
					.append(labelNameField).append(",f.")
					.append(feedbackTimestampField).append(" from ")
					.append(documentTableName).append(" as d,")
					.append(feedbackTableName).append(" as f,")
					.append(labelTableName).append(" as l where d.")
					.append(documentNameField).append(" = ? and f.")
					.append(feedbackDocumentIdField).append(" = d.")
					.append(documentIdField).append(" and f.")
					.append(feedbackLabelIdField).append(" = l.")
					.append(labelIdField);

			ps = con.prepareStatement(query.toString());
			ps.setString(1, document);
			rs = ps.executeQuery();

			while (rs.next()) {
				feedback = new Feedback();
				feedback = new Feedback();
				feedback.setId(rs.getInt(feedbackIdField));
				feedback.setUserId(rs.getInt(feedbackUserIdField));
				feedback.setDocument(rs.getString("d." + documentNameField));
				feedback.setLabel(rs.getString("l." + labelNameField));
				feedback.setDate(rs.getTimestamp(feedbackTimestampField));
				feedbacks.add(feedback);
			}

		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			closeResult(rs);
			closeStatement(ps);
			closeConnection(con);

		}
		return feedbacks;
	}

}
