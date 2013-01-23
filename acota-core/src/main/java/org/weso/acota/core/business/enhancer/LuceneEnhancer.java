package org.weso.acota.core.business.enhancer;

import java.io.IOException;
import java.io.StringReader;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.tika.language.LanguageIdentifier;
import org.weso.acota.core.Configuration;
import org.weso.acota.core.business.enhancer.EnhancerAdapter;
import org.weso.acota.core.business.enhancer.analyzer.lucene.DefaultStopAnalyzer;
import org.weso.acota.core.business.enhancer.analyzer.lucene.EnglishStopAnalyzer;
import org.weso.acota.core.business.enhancer.analyzer.lucene.SpanishStopAnalyzer;
import org.weso.acota.core.entity.ProviderTO;
import org.weso.acota.core.entity.TagTO;
import org.weso.acota.core.exceptions.AcotaConfigurationException;

import static org.weso.acota.core.utils.LanguageUtil.ISO_639_ENGLISH;
import static org.weso.acota.core.utils.LanguageUtil.ISO_639_SPANISH;


/**
 * LuceneEnhancer is an {@link Enhancer} specialized in tokenizing, removing stop-words and
 * cleaning the input data, producing a set of single-word {@link TagTO}s
 * 
 * @author Jose María Álvarez
 * @author César Luis Alvargonzález
 */
public class LuceneEnhancer extends EnhancerAdapter implements Configurable {

	protected static Logger logger = Logger.getLogger(LuceneEnhancer.class);
	
	protected static final String DESCIPTION = "description";
	protected static final String LABEL = "label";
	
	protected double luceneLabelRelevance;
	protected double luceneTermRelevance;

	protected Configuration configuration;
	
	/**
	 * Zero-argument default constructor
	 * @throws AcotaConfigurationException Any exception that occurs while initializing 
	 * a Configuration object
	 */
	public LuceneEnhancer() throws AcotaConfigurationException{
		super();
		LuceneEnhancer.provider = new ProviderTO("Lucene Analizer");
		loadConfiguration(configuration);
	}
	
	@Override
	public void loadConfiguration(Configuration configuration) throws AcotaConfigurationException{
		if(configuration==null)
			configuration = new Configuration();
		this.configuration = configuration;
		this.luceneLabelRelevance = configuration.getLuceneLabelRelevance();
		this.luceneTermRelevance = configuration.getLuceneTermRelevance();
	}
	
	@Override
	protected void preExecute() throws Exception {
		this.suggest = request.getSuggestions();
		this.tags = suggest.getTags();
		suggest.setResource(request.getResource());
	}
	
	@Override
	protected void execute() throws Exception {
		extractLabelTerms();
		extractDescriptionTerms();
	}

	@Override
	protected void postExecute() throws Exception {
		logger.debug("Add providers to request");
		this.request.getTargetProviders().add(provider);
		logger.debug("Add suggestons to request");
		this.request.setSuggestions(suggest);
	}

	/**
	 * Extracts Description Terms
	 * @throws IOException Any exception that occurs while reading Lucene's TokenStream
	 */
	protected void extractDescriptionTerms() throws IOException {
		extractTerms(DESCIPTION, request.getResource().getDescription(),
			luceneTermRelevance);
	}

	/**
	 * Extracts Label Terms
	 * @throws IOException Any exception that occurs while reading Lucene's TokenStream
	 */
	protected void extractLabelTerms() throws IOException {
		extractTerms(LABEL, request.getResource().getLabel(),
					luceneLabelRelevance);
	}

	/**
	 * Tokenizes and removes stop-words (Spanish and English) from the supplied text
	 * @param title FieldName (description, label)
	 * @param text Text to extract the terms
	 * @param relevance Weight which is incremented each matched term
	 * @throws IOException Any exception that occurs while reading Lucene's TokenStream
	 */
	protected void extractTerms(String title, String text, double relevance)
			throws IOException {

		Analyzer analyzer = loadAnalyzer(text);

		logger.debug("Get tokens of texts");
		TokenStream stream = analyzer.tokenStream(title, new StringReader(
				text));
		CharTermAttribute termAttribute = stream
				.getAttribute(CharTermAttribute.class);

		while (stream.incrementToken()) {
			logger.debug("Add tag to suggestions");
			TagTO tag = createTag(termAttribute);
			fillSuggestions(tag, relevance);
		}
	}

	/**
	 * Creates and adds a new tag to the suggest tags set
	 * @param attribute Label's attribute
	 * @return Created tag
	 */
	protected TagTO createTag(CharTermAttribute attribute) {
		TagTO tag = new TagTO();
		tag.setLabel(attribute.toString());
		tag.setProvider(provider);
		tag.setTagged(request.getResource());
		return tag;
	}

	/**
	 * Loads a language analyzer (English, Spanish or Default)
	 * @param text Text to analyze
	 * @return Lucene's {@link Analyzer}
	 */
	protected Analyzer loadAnalyzer(String text) {
		LanguageIdentifier ld = new LanguageIdentifier(text);
		Analyzer analyzer = null;
		if (ld.getLanguage().equals(ISO_639_SPANISH)) {
			analyzer = SpanishStopAnalyzer.getInstance();
		} else if (ld.getLanguage().equals(ISO_639_ENGLISH)) {
			analyzer = EnglishStopAnalyzer.getInstance();
		} else {
			analyzer = DefaultStopAnalyzer.getInstance();
		}
		return analyzer;
	}

}
