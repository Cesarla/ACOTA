package org.weso.acota.persistence.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.configuration.ConfigurationException;
import org.weso.acota.core.entity.tuples.DocumentTuple;
import org.weso.acota.persistence.DocumentDAO;

public class DocumentMysqlDAO extends GenericMysqlDAO implements DocumentDAO {
	protected String tableName;
	protected String idField;
	protected String nameField;

	public DocumentMysqlDAO() throws ConfigurationException {
		super();
		DocumentTuple label = configuration.getDocumentTuple();
		this.tableName = label.getName();
		this.idField = label.getIdField();
		this.nameField = label.getNameField();
	}

	@Override
	public void saveDocument(Integer id, String document) throws SQLException,
			ClassNotFoundException {
		PreparedStatement ps = null;
		Connection con = null;

		try {
			con = openConnection();

			StringBuilder query = new StringBuilder("insert into ")
					.append(tableName).append(" (").append(idField)
					.append(", ").append(nameField).append(") values(?,?)");

			ps = con.prepareStatement(query.toString());
			ps.setInt(1, id);
			ps.setString(2, document);

			ps.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			closeStatement(ps);
			closeConnection(con);

		}
	}

	@Override
	public String getDocumentById(Integer id) throws SQLException,
			ClassNotFoundException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		String labels = null;

		try {
			con = openConnection();

			StringBuilder query = new StringBuilder("select * from ")
					.append(tableName).append(" where ").append(idField)
					.append("=?");

			ps = con.prepareStatement(query.toString());
			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				labels = rs.getString(nameField);
			}

		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			closeResult(rs);
			closeStatement(ps);
			closeConnection(con);

		}
		return labels;
	}

	@Override
	public String getDocumentByHash(Integer hash) throws SQLException,
			ClassNotFoundException {
		return getDocumentById(hash);
	}

	@Override
	public Set<String> getDocumentsByIds(Collection<Integer> ids)
			throws SQLException, ClassNotFoundException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		Set<String> documents = new HashSet<String>();
		List<Integer> hashesList = new LinkedList<Integer>(ids);
		if (!ids.isEmpty()) {
			try {
				con = openConnection();

				StringBuilder query = new StringBuilder("select * from ")
						.append(tableName).append(" where ");

				for (int i = 0; i < ids.size(); i++) {
					query.append(idField).append("=?");
					if (i + 1 < ids.size()) {
						query.append(" or ");
					}
				}

				ps = con.prepareStatement(query.toString());

				for (int i = 0; i < hashesList.size(); i++) {
					ps.setInt(i + 1, hashesList.get(i));
				}

				rs = ps.executeQuery();

				while (rs.next()) {
					documents.add(rs.getString(nameField));
				}

			} catch (ClassNotFoundException e) {
				throw e;
			} finally {
				closeResult(rs);
				closeStatement(ps);
				closeConnection(con);
			}
		}
		return documents;
	}

	@Override
	public Set<String> getDocumentsByHashes(Collection<Integer> hashes)
			throws SQLException, ClassNotFoundException {
		return getDocumentsByIds(hashes);
	}
}
