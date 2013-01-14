package org.weso.acota.persistence.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.configuration.ConfigurationException;
import org.weso.acota.core.FeedbackConfiguration;
import org.weso.acota.persistence.GenericDAO;

/**
 * Concrete implementation of LabelDAO for the DBMS MySQL 5.x
 * @see GenericDAO
 * @author César Luis Alvargonzález
 *
 */
public abstract class GenericMysqlDAO implements GenericDAO {

	protected FeedbackConfiguration configuration;
	protected String url;

	/**
	 * Zero-argument default constructor
	 * @throws ConfigurationException Any exception that occurs while initializing 
	 * a Configuration object
	 */
	public GenericMysqlDAO() throws ConfigurationException {
		super();
		this.configuration = new FeedbackConfiguration();
		this.url = new StringBuilder("jdbc:mysql://")
				.append(configuration.getDatabaseUrl()).append("/")
				.append(configuration.getDatabaseName()).toString();
	}

	@Override
	public Connection openConnection() throws ClassNotFoundException,
			SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection(url,
				configuration.getDatabaseUser(),
				configuration.getDatabasePassword());
	}

	@Override
	public void closeConnection(Connection con) throws SQLException {
		if (con != null)
			con.close();
	}

	@Override
	public void closeStatement(PreparedStatement ps) throws SQLException {
		if (ps != null)
			ps.close();
	}

	@Override
	public void closeResult(ResultSet rs) throws SQLException {
		if (rs != null)
			rs.close();
	}
}
