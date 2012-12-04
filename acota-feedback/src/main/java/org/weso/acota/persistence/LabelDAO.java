package org.weso.acota.persistence;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

public interface LabelDAO extends GenericDAO {

	public void saveLabel(Integer id, String label) throws SQLException,
	ClassNotFoundException;
	
	public String getLabelById(Integer id) throws SQLException,
			ClassNotFoundException;

	public String getLabelByHash(Integer hash) throws SQLException,
			ClassNotFoundException;

	public Set<String> getLabelsByIds(Collection<Integer> hashses)
			throws SQLException, ClassNotFoundException;
	
	public Set<String> getLabelsByHashes(Collection<Integer> hashses)
			throws SQLException, ClassNotFoundException;

}
