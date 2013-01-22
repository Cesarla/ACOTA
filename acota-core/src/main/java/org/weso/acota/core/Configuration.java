package org.weso.acota.core;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

/**
 * The main task of this class is to load the core configuration properties of
 * ACOTA, this properties could be set programmatically or by a configuration
 * file called acota.properties
 * 
 * @author César Luis Alvargonzález
 */
public class Configuration {

	protected static final String INTERNAL_ACOTA_PROPERTIES_PATH = "/resources/inner.acota.properties";

	protected static Logger LOGGER;
	protected static CompositeConfiguration CONFIG;
	protected String googleEncoding;

	protected Double googleRelevance;
	protected String googleUrl;
	protected Double luceneLabelRelevance;

	protected Double luceneTermRelevance;
	protected String openNLPenPosBin;
	protected String openNLPenSentBin;
	protected String openNLPenTokBin;

	protected String openNLPesPosBin;
	protected String openNLPesSentBin;
	protected String openNLPesTokBin;

	protected String tokenizerEnPattern;
	protected String tokenizerEnTokens[];

	protected String tokenizerEsPattern;
	protected String tokenizerEsTokens[];
	protected Integer tokenizerK;

	protected Double tokenizerLabelRelevance;
	protected Double tokenizerTermRelevance;

	protected String wordnetEnDict;

	protected Double wordnetRelevance;

	/**
	 * Zero-argument default constructor.
	 * 
	 * @throws ConfigurationException Any exception that 
	 * occurs while initializing a Configuration object
	 */
	public Configuration() throws ConfigurationException {
		Configuration.LOGGER = Logger.getLogger(Configuration.class);

		if(CONFIG==null){
			Configuration.CONFIG = new CompositeConfiguration();
			loadsConfiguration();
		}
		
		loadLuceneEnhancerConfig();
		loadTokenizerEnhancerConfig();
		loadOpenNLPEnhancerConfig();
		loadWordnetEnhancerConfig();
		loadGoogleEnhancerConfig();
	}

	public String getGoogleEncoding() {
		return googleEncoding;
	}

	public Double getGoogleRelevance() {
		return googleRelevance;
	}

	public String getGoogleUrl() {
		return googleUrl;
	}

	public Double getLuceneLabelRelevance() {
		return luceneLabelRelevance;
	}

	public Double getLuceneTermRelevance() {
		return luceneTermRelevance;
	}

	public String getOpenNLPenPosBin() {
		return openNLPenPosBin;
	}

	public String getOpenNLPenSentBin() {
		return openNLPenSentBin;
	}

	public String getOpenNLPenTokBin() {
		return openNLPenTokBin;
	}

	public String getOpenNLPesPosBin() {
		return openNLPesPosBin;
	}

	public String getOpenNLPesSentBin() {
		return openNLPesSentBin;
	}

	public String getOpenNLPesTokBin() {
		return openNLPesTokBin;
	}

	public String getTokenizerEnPattern() {
		return tokenizerEnPattern;
	}

	public String[] getTokenizerEnTokens() {
		return tokenizerEnTokens;
	}

	public String getTokenizerEsPattern() {
		return tokenizerEsPattern;
	}

	public String[] getTokenizerEsTokens() {
		return tokenizerEsTokens;
	}

	public Integer getTokenizerK() {
		return tokenizerK;
	}

	public Double getTokenizerLabelRelevance() {
		return tokenizerLabelRelevance;
	}

	public Double getTokenizerTermRelevance() {
		return tokenizerTermRelevance;
	}

	public String getWordnetEnDict() {
		return wordnetEnDict;
	}

	public Double getWordnetRelevance() {
		return wordnetRelevance;
	}

	/**
	 * Loads an Array from a String
	 * 
	 * @param planeArray  String to transform
	 * @return Array generated
	 */
	private String[] loadArray(String planeArray) {
		return planeArray.split("[\\s,;]+");
	}

	/**
	 * Loads {@linked org.weso.acota.core.business.enhancer.GoogleEnhancer}'s
	 * Configuration
	 */
	private void loadGoogleEnhancerConfig() {
		this.setGoogleUrl(CONFIG.getString("google.url"));
		this.setGoogleEncoding(CONFIG.getString("google.encoding"));
		this.setGoogleRelevance(CONFIG.getDouble("google.relevance"));
	}

	/**
	 * Loads {@linked org.weso.acota.core.business.enhancer.LuceneEnhancer}'s
	 * Configuration
	 */
	private void loadLuceneEnhancerConfig() {
		this.setLuceneTermRelevance(CONFIG
				.getDouble("lucene.term.relevance"));
		this.setLuceneLabelRelevance(CONFIG
				.getDouble("lucene.label.relevance"));
	}

	/**
	 * Loads {@linked org.weso.acota.core.business.enhancer.OpenNLPEnhancer}'s
	 * Configuration
	 */
	private void loadOpenNLPEnhancerConfig() {
		this.setOpenNLPesPosBin(CONFIG.getString("opennlp.es.pos"));
		this.setOpenNLPesSentBin(CONFIG.getString("opennlp.es.sent"));
		this.setOpenNLPesTokBin(CONFIG.getString("opennlp.es.tok"));
		this.setOpenNLPenPosBin(CONFIG.getString("opennlp.en.pos"));
		this.setOpenNLPenSentBin(CONFIG.getString("opennlp.en.sent"));
		this.setOpenNLPenTokBin(CONFIG.getString("opennlp.en.tok"));
	}

	/**
	 * Loads {@linked org.weso.acota.core.business.enhancer.TokenizerEnhancer}'s
	 * Configuration
	 */
	private void loadTokenizerEnhancerConfig() {
		this.setTokenizerK(CONFIG.getInteger("tokenizer.k", 1));
		this.setTokenizerLabelRelevance (CONFIG
				.getDouble("tokenizer.label.relevance"));
		this.setTokenizerTermRelevance(CONFIG
				.getDouble("tokenizer.term.relevance"));
		this.setTokenizerEnPattern(CONFIG.getString("tokenizer.en.pattern"));
		this.setTokenizerEnTokens(loadArray(CONFIG
				.getString("tokenizer.en.tokens")));
		this.setTokenizerEsPattern(CONFIG.getString("tokenizer.es.pattern"));
		this.setTokenizerEsTokens(loadArray(CONFIG
				.getString("tokenizer.es.tokens")));
	}

	/**
	 * Loads {@linked org.weso.acota.core.business.enhancer.WordnetEnhancer}'s
	 * Configuration
	 */
	private void loadWordnetEnhancerConfig() {
		this.setWordnetEnDict(CONFIG.getString("wordnet.en.dict"));
		this.setWordnetRelevance(CONFIG.getDouble("wordnet.relevance"));
	}

	/**
	 * Loads Acota's configuration properties files
	 * 
	 * @throws ConfigurationException Any exception that occurs 
	 * while initializing a Configuration object
	 */
	private void loadsConfiguration() throws ConfigurationException {
		try {
			CONFIG.addConfiguration(new PropertiesConfiguration(
					"acota.properties"));
		} catch (Exception e) {
			LOGGER.warn("acota.properties not found, Using default values.");
		}

		CONFIG.addConfiguration(new PropertiesConfiguration(this.getClass()
				.getResource(INTERNAL_ACOTA_PROPERTIES_PATH)));
	}

	public void setGoogleEncoding(String googleEncoding) {
		this.googleEncoding = googleEncoding;
	}

	public void setGoogleRelevance(Double googleRelevance) {
		this.googleRelevance = googleRelevance;
	}

	public void setGoogleUrl(String googleUrl) {
		this.googleUrl = googleUrl;
	}

	public void setLuceneLabelRelevance(Double luceneLabelRelevance) {
		this.luceneLabelRelevance = luceneLabelRelevance;
	}

	public void setLuceneTermRelevance(Double luceneTermRelevance) {
		this.luceneTermRelevance = luceneTermRelevance;
	}

	public void setOpenNLPenPosBin(String openNLPenPosBin) {
		this.openNLPenPosBin = openNLPenPosBin;
	}

	public void setOpenNLPenSentBin(String openNLPenSentBin) {
		this.openNLPenSentBin = openNLPenSentBin;
	}

	public void setOpenNLPenTokBin(String openNLPenTokBin) {
		this.openNLPenTokBin = openNLPenTokBin;
	}

	public void setOpenNLPesPosBin(String openNLPesPosBin) {
		this.openNLPesPosBin = openNLPesPosBin;
	}

	public void setOpenNLPesSentBin(String openNLPesSentBin) {
		this.openNLPesSentBin = openNLPesSentBin;
	}

	public void setOpenNLPesTokBin(String openNLPesTokBin) {
		this.openNLPesTokBin = openNLPesTokBin;
	}

	public void setTokenizerEnPattern(String tokenizerEnPattern) {
		this.tokenizerEnPattern = tokenizerEnPattern;
	}

	public void setTokenizerEnTokens(String[] tokenizerEnTokens) {
		this.tokenizerEnTokens = tokenizerEnTokens;
	}

	public void setTokenizerEsPattern(String tokenizerEsPattern) {
		this.tokenizerEsPattern = tokenizerEsPattern;
	}

	public void setTokenizerEsTokens(String[] tokenizerEsTokens) {
		this.tokenizerEsTokens = tokenizerEsTokens;
	}

	public void setTokenizerK(Integer tokenizerK) {
		this.tokenizerK = tokenizerK;
	}

	public void setTokenizerLabelRelevance(Double tokenizerLabelRelevance) {
		this.tokenizerLabelRelevance = tokenizerLabelRelevance;
	}

	public void setTokenizerTermRelevance(Double tokenizerTermRelevance) {
		this.tokenizerTermRelevance = tokenizerTermRelevance;
	}

	public void setWordnetEnDict(String wordnetEnDict) {
		this.wordnetEnDict = wordnetEnDict;
	}

	public void setWordnetRelevance(Double wordnetRelevance) {
		this.wordnetRelevance = wordnetRelevance;
	}

}