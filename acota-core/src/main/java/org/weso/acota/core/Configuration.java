package org.weso.acota.core;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

public class Configuration {

	protected String googleUrl;
	protected String googleEncoding;
	protected Double googleRelevance;

	protected Double luceneLabelRelevance;
	protected Double luceneTermRelevance;

	protected String openNLPesPosBin;
	protected String openNLPesSentBin;
	
	protected static Logger logger;

	public Configuration() throws ConfigurationException {
		Configuration.logger =  Logger.getLogger(Configuration.class);
		
		CompositeConfiguration config = new CompositeConfiguration();
		
		try {
			config.addConfiguration(new PropertiesConfiguration("acota.properties"));
		} catch (Exception e) {
			logger.warn("acota.properties not found, Using default values.");
		}
		
		config.addConfiguration(new PropertiesConfiguration(this.getClass()
				.getResource("/resources/inner.acota.properties")));
		
		
		
		this.googleUrl = config.getString("google.url");
		this.googleEncoding = config.getString("google.encoding");
		this.googleRelevance = config.getDouble("google.relevance");

		this.luceneTermRelevance = config.getDouble("lucene.term.relevance");
		this.luceneLabelRelevance = config.getDouble("lucene.label.relevance");

		this.openNLPesPosBin = config.getString("opennlp.es.pos");
		this.openNLPesSentBin = config.getString("opennlp.es.sent");
	}

	public String getGoogleUrl() {
		return googleUrl;
	}

	public void setGoogleUrl(String googleUrl) {
		this.googleUrl = googleUrl;
	}

	public String getGoogleEncoding() {
		return googleEncoding;
	}

	public void setGoogleEncoding(String googleEncoding) {
		this.googleEncoding = googleEncoding;
	}

	public Double getGoogleRelevance() {
		return googleRelevance;
	}

	public void setGoogleRelevance(Double googleRelevance) {
		this.googleRelevance = googleRelevance;
	}

	public Double getLuceneLabelRelevance() {
		return luceneLabelRelevance;
	}

	public void setLuceneLabelRelevance(Double luceneLabelRelevance) {
		this.luceneLabelRelevance = luceneLabelRelevance;
	}

	public Double getLuceneTermRelevance() {
		return luceneTermRelevance;
	}

	public void setLuceneTermRelevance(Double luceneTermRelevance) {
		this.luceneTermRelevance = luceneTermRelevance;
	}

	public String getOpenNLPesSentBin() {
		return openNLPesSentBin;
	}

	public void setOpenNLPesSentBin(String openNLPesSentBin) {
		this.openNLPesSentBin = openNLPesSentBin;
	}

	public String getOpenNLPesPosBin() {
		return openNLPesPosBin;
	}

	public void setOpenNLPesPosBin(String openNLPesPosBin) {
		this.openNLPesPosBin = openNLPesPosBin;
	}

}
