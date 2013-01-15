package org.weso.acota.core;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

/**
 * The main task of this class is to load the core configuration properties of ACOTA,
 * this properties could be set programmatically or by a configuration file called
 * acota.properties
 * 
 * @author César Luis Alvargonzález
 */
public class Configuration {

	protected static final String INTERNAL_ACOTA_PROPERTIES_PATH = "/resources/inner.acota.properties";
	
	protected String googleUrl;
	protected String googleEncoding;
	protected Double googleRelevance;

	protected Integer tokenizerK;
	protected Double tokenizerTermRelevance;
	protected Double tokenizerLabelRelevance;
	
	protected Double luceneLabelRelevance;
	protected Double luceneTermRelevance;

	protected String openNLPesPosBin;
	protected String openNLPesSentBin;
	
	protected String wordnetEnDict;
	protected Double wordnetRelevance;
	
	protected static Logger logger;
	
	protected CompositeConfiguration config;

	/**
	 * Zero-argument default constructor.
	 * @throws ConfigurationException Any exception that occurs while initializing 
	 * a Configuration object
	 */
	public Configuration() throws ConfigurationException {
		Configuration.logger =  Logger.getLogger(Configuration.class);
		
		this.config = new CompositeConfiguration();
		
		loadsConfiguration();
		
		loadLuceneEnhancerConfig();
		loadTokenizerEnhancerConfig();
		loadOpenNLPEnhancerConfig();
		loadWordnetEnhancerConfig();
		loadGoogleEnhancerConfig();
	}

	/**
	 * Loads Acota's configuration properties files
	 * @throws ConfigurationException Any exception that occurs while initializing 
	 * a Configuration object
	 */
	private void loadsConfiguration() throws ConfigurationException {
		try {
			config.addConfiguration(new PropertiesConfiguration("acota.properties"));
		} catch (Exception e) {
			logger.warn("acota.properties not found, Using default values.");
		}
		
		config.addConfiguration(new PropertiesConfiguration(this.getClass()
				.getResource(INTERNAL_ACOTA_PROPERTIES_PATH)));
	}
	
	/**
	 * Loads {@linked org.weso.acota.core.business.enhancer.TokenizerEnhancer}'s Configuration
	 */
	private void loadTokenizerEnhancerConfig(){
		this.tokenizerK = config.getInteger("tokenizer.k", 1);
		this.tokenizerLabelRelevance = config.getDouble("tokenizer.label.relevance");
		this.tokenizerTermRelevance = config.getDouble("tokenizer.term.relevance");
	}

	/**
	 * Loads {@linked org.weso.acota.core.business.enhancer.LuceneEnhancer}'s Configuration
	 */
	private void loadLuceneEnhancerConfig() {
		this.luceneTermRelevance = config.getDouble("lucene.term.relevance");
		this.luceneLabelRelevance = config.getDouble("lucene.label.relevance");
	}
	
	/**
	 * Loads {@linked org.weso.acota.core.business.enhancer.OpenNLPEnhancer}'s Configuration
	 */
	private void loadOpenNLPEnhancerConfig() {
		this.openNLPesPosBin = config.getString("opennlp.es.pos");
		this.openNLPesSentBin = config.getString("opennlp.es.sent");
	}
	
	/**
	 * Loads {@linked org.weso.acota.core.business.enhancer.WordnetEnhancer}'s Configuration
	 */
	private void loadWordnetEnhancerConfig() {
		this.wordnetEnDict = config.getString("wordnet.en.dict");
		this.wordnetRelevance = config.getDouble("wordnet.relevance");
	}

	/**
	 * Loads {@linked org.weso.acota.core.business.enhancer.GoogleEnhancer}'s Configuration
	 */
	private void loadGoogleEnhancerConfig() {
		this.googleUrl = config.getString("google.url");
		this.googleEncoding = config.getString("google.encoding");
		this.googleRelevance = config.getDouble("google.relevance");
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

	public String getWordnetEnDict() {
		return wordnetEnDict;
	}

	public void setWordnetEnDict(String wordnetEnDict) {
		this.wordnetEnDict = wordnetEnDict;
	}

	public Double getWordnetRelevance() {
		return wordnetRelevance;
	}

	public void setWordnetRelevance(Double wordnetRelevance) {
		this.wordnetRelevance = wordnetRelevance;
	}

	public Integer getTokenizerK() {
		return tokenizerK;
	}

	public void setTokenizerK(Integer tokenizerK) {
		this.tokenizerK = tokenizerK;
	}
	
	public Double getTokenizerTermRelevance() {
		return tokenizerTermRelevance;
	}

	public void setTokenizerTermRelevance(Double tokenizerTermRelevance) {
		this.tokenizerTermRelevance = tokenizerTermRelevance;
	}
	

	public Double getTokenizerLabelRelevance() {
		return tokenizerLabelRelevance;
	}

	public void setTokenizerLabelRelevance(Double tokenizerLabelRelevance) {
		this.tokenizerLabelRelevance = tokenizerLabelRelevance;
	}
	
}