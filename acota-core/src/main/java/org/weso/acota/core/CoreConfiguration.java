package org.weso.acota.core;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.weso.acota.core.exceptions.AcotaConfigurationException;

/**
 * The main task of this class is to load the core configuration properties of
 * ACOTA, this properties could be set programmatically or by a configuration
 * file called acota.properties
 * 
 * @author César Luis Alvargonzález
 */
public class CoreConfiguration implements Configuration {

	protected static final String INTERNAL_ACOTA_PROPERTIES_PATH = "/resources/inner.acota.properties";

	protected static Logger LOGGER;
	protected static CompositeConfiguration CONFIG;
	protected String googleEncoding;

	protected Double googleRelevance;
	protected String googleUrl;
	protected Double luceneLabelRelevance;

	protected Double luceneTermRelevance;
	protected String openNlpEnPosBin;
	protected String openNlpEnSentBin;
	protected String openNlpEnTokBin;

	protected String openNlpEsPosBin;
	protected String openNlpEsSentBin;
	protected String openNlpEsTokBin;

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
	 * @throws AcotaConfigurationException
	 *             Any exception that occurs while initializing a Acota's
	 *             Configuration object
	 */
	public CoreConfiguration() throws AcotaConfigurationException {
		CoreConfiguration.LOGGER = Logger.getLogger(CoreConfiguration.class);

		if (CONFIG == null) {
			loadsConfiguration();
		}

		loadLuceneEnhancerConfig();
		loadTokenizerEnhancerConfig();
		loadOpenNlpEnhancerConfig();
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

	public String getOpenNlpEnPosBin() {
		return openNlpEnPosBin;
	}

	public String getOpenNlpEnSentBin() {
		return openNlpEnSentBin;
	}

	public String getOpenNlpEnTokBin() {
		return openNlpEnTokBin;
	}

	public String getOpenNlpEsPosBin() {
		return openNlpEsPosBin;
	}

	public String getOpenNlpEsSentBin() {
		return openNlpEsSentBin;
	}

	public String getOpenNlpEsTokBin() {
		return openNlpEsTokBin;
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

	public void setOpenNlpEnPosBin(String openNlpEnPosBin) {
		this.openNlpEnPosBin = openNlpEnPosBin;
	}

	public void setOpenNlpEnSentBin(String openNlpEnSentBin) {
		this.openNlpEnSentBin = openNlpEnSentBin;
	}

	public void setOpenNlpEnTokBin(String openNlpEnTokBin) {
		this.openNlpEnTokBin = openNlpEnTokBin;
	}

	public void setOpenNlpEsPosBin(String openNlpEsPosBin) {
		this.openNlpEsPosBin = openNlpEsPosBin;
	}

	public void setOpenNlpEsSentBin(String openNlpEsSentBin) {
		this.openNlpEsSentBin = openNlpEsSentBin;
	}

	public void setOpenNlpEsTokBin(String openNlpEsTokBin) {
		this.openNlpEsTokBin = openNlpEsTokBin;
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

	/**
	 * @see org.weso.acota.core.Configuration#loadsConfiguration()
	 */
	public void loadsConfiguration() throws AcotaConfigurationException {
		CoreConfiguration.CONFIG = new CompositeConfiguration();
		try {
			CONFIG.addConfiguration(new PropertiesConfiguration(
					"acota.properties"));
		} catch (Exception e) {
			LOGGER.warn("acota.properties not found, Using default values.");
		}

		try {
			CONFIG.addConfiguration(new PropertiesConfiguration(this.getClass()
					.getResource(INTERNAL_ACOTA_PROPERTIES_PATH)));
		} catch (ConfigurationException e) {
			throw new AcotaConfigurationException(e);
		}

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
		this.setLuceneTermRelevance(CONFIG.getDouble("lucene.term.relevance"));
		this.setLuceneLabelRelevance(CONFIG.getDouble("lucene.label.relevance"));
	}

	/**
	 * Loads {@linked org.weso.acota.core.business.enhancer.OpenNlpEnhancer}'s
	 * Configuration
	 */
	private void loadOpenNlpEnhancerConfig() {
		this.setOpenNlpEsPosBin(CONFIG.getString("opennlp.es.pos"));
		this.setOpenNlpEsSentBin(CONFIG.getString("opennlp.es.sent"));
		this.setOpenNlpEsTokBin(CONFIG.getString("opennlp.es.tok"));
		this.setOpenNlpEnPosBin(CONFIG.getString("opennlp.en.pos"));
		this.setOpenNlpEnSentBin(CONFIG.getString("opennlp.en.sent"));
		this.setOpenNlpEnTokBin(CONFIG.getString("opennlp.en.tok"));
	}

	/**
	 * Loads {@linked org.weso.acota.core.business.enhancer.TokenizerEnhancer}'s
	 * Configuration
	 */
	private void loadTokenizerEnhancerConfig() {
		this.setTokenizerK(CONFIG.getInteger("tokenizer.k", 1));
		this.setTokenizerLabelRelevance(CONFIG
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
	 * Loads an Array from a String
	 * 
	 * @param planeArray
	 *            String to transform
	 * @return Array generated
	 */
	private String[] loadArray(String planeArray) {
		return planeArray.split("[\\s,;]+");
	}

}