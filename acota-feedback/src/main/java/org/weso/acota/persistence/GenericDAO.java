package org.weso.acota.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface GenericDAO {
	
	public Connection openConnection() throws ClassNotFoundException, SQLException;
	
	public void closeConnection(Connection con) throws SQLException;

	public void closeStatement(PreparedStatement ps) throws SQLException;

	public void closeResult(ResultSet rs) throws SQLException;
}
