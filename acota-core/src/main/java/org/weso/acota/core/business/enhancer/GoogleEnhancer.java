package org.weso.acota.core.business.enhancer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map.Entry;

import javax.xml.transform.TransformerException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;
import org.weso.acota.core.Configuration;
import org.weso.acota.core.business.enhancer.EnhancerAdapter;
import org.weso.acota.core.entity.ProviderTO;
import org.weso.acota.core.entity.TagTO;
import org.weso.acota.core.exceptions.DocumentBuilderException;
import org.weso.acota.core.utils.DocumentBuilderHelper;

import com.sun.org.apache.xpath.internal.XPathAPI;

/**
 * @author Chema
 * @author César Luis Alvargonzález
 */
public class GoogleEnhancer extends EnhancerAdapter implements Configurable {

	protected static Logger logger = Logger.getLogger(GoogleEnhancer.class);
	
	protected static final String TEXT_XML = "text/xml";
	protected static final String APPLICATION_XML = "application/xml";
	
	protected String googleUrl;
	protected String googleEncoding;
	
	protected double googleRelevance;
	
	protected Configuration configuration;
	
	public GoogleEnhancer() throws ConfigurationException{
		super();
		GoogleEnhancer.provider = new ProviderTO("Google Enhancer");
		loadConfiguration(configuration);
	}
	
	@Override
	public void loadConfiguration(Configuration configuration) throws ConfigurationException{
		if(configuration==null)
			configuration = new Configuration();
		this.configuration = configuration;
		this.googleUrl = configuration.getGoogleUrl();
		this.googleEncoding = configuration.getGoogleEncoding();
		this.googleRelevance = configuration.getGoogleRelevance();
	}
	
	@Override
	protected void execute() throws Exception {	
		checkTagsWithSamenLabels();
		addLabelsWeightsSuggestions();
	}

	@Override
	protected void preExecute() throws Exception {
		this.suggest = request.getSuggestions();
		this.labels = suggest.getLabels();
		this.tags = suggest.getTags();
		suggest.setResource(request.getResource());
	}

	@Override
	protected void postExecute() throws Exception {
		this.request.getTargetProviders().add(provider);
		this.request.setSuggestions(suggest);
	}

	protected void checkTagsWithSamenLabels() throws MalformedURLException,
			IOException, UnsupportedEncodingException,
			DocumentBuilderException, TransformerException {
		for (Entry<String, Double> label : labels.entrySet()) {
			URL url = new URL(googleUrl + label.getKey().toString());
			getGoogleRequest(url);
		}
	}

	protected void getGoogleRequest(URL url) throws IOException,
			DocumentBuilderException, TransformerException {
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Accept", APPLICATION_XML);
			try{
				connection.connect();
			}catch(Exception e){
				
				connection.connect();
			}
			if (isValidResponse(connection)) {
				Document document = processResponse(connection);
				processDocument(document);
			}
		} finally {
			if (null != connection) {
				connection.disconnect();
			}
		}
	}

	protected Document processResponse(HttpURLConnection connection)
			throws UnsupportedEncodingException, IOException,
			DocumentBuilderException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), googleEncoding));
		StringBuilder response = new StringBuilder();
		try {
			String line = null;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\n');
			}
		} finally {
			rd.close();
		}
		return DocumentBuilderHelper.getDocumentFromString(response.toString());
	}

	protected boolean isValidResponse(HttpURLConnection connection)
			throws IOException {
		return connection.getResponseCode() == HttpURLConnection.HTTP_OK
				&& connection.getContentType().contains(TEXT_XML);
	}

	/**
	 * 
	 * @param result
	 * @throws DocumentBuilderException
	 * @throws TransformerException
	 */
	protected void processDocument(Document result) throws TransformerException {
		NodeIterator it = XPathAPI.selectNodeIterator(result, "//suggestion/@data");
		Node node = null;

		while ((node = it.nextNode()) != null) {
			TagTO tag = new TagTO(node.getNodeValue(), provider,
					request.getResource());
			tags.add(tag);
		}
	}
	
	/**
	 * Add labels and weight to suggestions
	 */
	protected void addLabelsWeightsSuggestions() {
		for (TagTO tag : tags) {
			if (tag.getProvider().equals(provider)) {
				fillSuggestions(tag.getLabel(), googleRelevance);
			}
		}
	}

}
