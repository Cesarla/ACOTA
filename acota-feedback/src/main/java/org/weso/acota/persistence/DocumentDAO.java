package org.weso.acota.persistence;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

public interface DocumentDAO extends GenericDAO{
	
	public void saveDocument(Integer id, String label) throws SQLException,
	ClassNotFoundException;
	
	public String getDocumentById(Integer id) throws SQLException,
			ClassNotFoundException;

	public String getDocumentByHash(Integer hash) throws SQLException,
			ClassNotFoundException;

	public Set<String> getDocumentsByIds(Collection<Integer> hashses)
			throws SQLException, ClassNotFoundException;
	
	public Set<String> getDocumentsByHashes(Collection<Integer> hashses)
			throws SQLException, ClassNotFoundException;
}
