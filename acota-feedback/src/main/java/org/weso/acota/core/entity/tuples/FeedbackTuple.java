package org.weso.acota.core.entity.tuples;

public class FeedbackTuple {
	
	protected String name;
	protected String idField;
	protected String userIdField;
	protected String documentIdField;
	protected String labelIdField;
	protected String preferenceField;
	protected String timestampField;

	public FeedbackTuple() {
		super();
	}

	public FeedbackTuple(String name, String idField, String userIdField,
			String documentIdField, String labelIdField,
			String preferenceField, String timestampField) {
		super();
		this.name = name;
		this.idField = idField;
		this.userIdField = userIdField;
		this.documentIdField = documentIdField;
		this.labelIdField = labelIdField;
		this.preferenceField = preferenceField;
		this.timestampField = timestampField;
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

	public String getUserIdField() {
		return userIdField;
	}

	public void setUserIdField(String userIdField) {
		this.userIdField = userIdField;
	}

	public String getDocumentIdField() {
		return documentIdField;
	}

	public void setDocumentIdField(String documentIdField) {
		this.documentIdField = documentIdField;
	}

	public String getLabelIdField() {
		return labelIdField;
	}

	public void setLabelIdField(String labelIdField) {
		this.labelIdField = labelIdField;
	}

	public String getPreferenceField() {
		return preferenceField;
	}

	public void setPreferenceField(String preferenceField) {
		this.preferenceField = preferenceField;
	}

	public String getTimestampField() {
		return timestampField;
	}

	public void setTimestampField(String timestampField) {
		this.timestampField = timestampField;
	}

	@Override
	public String toString() {
		return "FeedbackTable [name=" + name + ", idField=" + idField
				+ ", userIdField=" + userIdField + ", documentIdField="
				+ documentIdField + ", labelIdField=" + labelIdField
				+ ", preferenceField=" + preferenceField + ", timestampField="
				+ timestampField + "]";
	}
	
}
