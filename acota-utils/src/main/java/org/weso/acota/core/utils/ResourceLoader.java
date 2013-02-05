package org.weso.acota.core.utils;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.weso.acota.core.utils.ExternalizableConfiguration;

/**
 * @author César Luis Alvargonzález
 * 
 */
public class ResourceLoader implements ExternalizableConfiguration{

	private PropertiesConfiguration configuration;
	
	/**
	 * Zero-Argument Default Constructor
	 */
	public ResourceLoader(){
		this.configuration = new PropertiesConfiguration();
		try{
			configuration.addProperty("opennlp.es.pos", this.getClass().getClassLoader().getResource("resources/open_nlp/es/SpanishPOS.bin").getPath());
			configuration.addProperty("opennlp.es.sent", this.getClass().getClassLoader().getResource("resources/open_nlp/es/SpanishSent.bin").getPath());
			configuration.addProperty("opennlp.es.tok", this.getClass().getClassLoader().getResource("resources/open_nlp/es/SpanishTok.bin").getPath());
			configuration.addProperty("opennlp.en.pos", this.getClass().getClassLoader().getResource("resources/open_nlp/en/EnglishPOS.bin").getPath());
			configuration.addProperty("opennlp.en.sent", this.getClass().getClassLoader().getResource("resources/open_nlp/en/EnglishSent.bin").getPath());
			configuration.addProperty("opennlp.en.tok", this.getClass().getClassLoader().getResource("resources/open_nlp/en/EnglishTok.bin").getPath());
			configuration.addProperty("wordnet.en.dict", this.getClass().getClassLoader().getResource("resources/wordnet_30/en/dict").getPath());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @see ExternalizableConfiguration#getConfiguration()
	 */
	@Override
	public PropertiesConfiguration getConfiguration() {
		return configuration;
	}
	
}
