package org.weso.acota.core.entity.tuples;

public class DocumentTuple {
	protected String name;
	protected String idField;
	protected String nameField;

	public DocumentTuple() {
		super();
	}

	public DocumentTuple(String name, String idField, String nameField) {
		super();
		this.name = name;
		this.idField = idField;
		this.nameField = nameField;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdField() {
		return idField;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}

	public String getNameField() {
		return nameField;
	}

	public void setNameField(String nameField) {
		this.nameField = nameField;
	}

	@Override
	public String toString() {
		return "DocumentTable [name=" + name + ", idField=" + idField
				+ ", nameField=" + nameField + "]";
	}

}
