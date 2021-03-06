package org.weso.acota.core.business.enhancer;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.weso.acota.core.CoreConfiguration;
import org.weso.acota.core.entity.ProviderTO;
import org.weso.acota.core.entity.TagTO;
import org.weso.acota.core.exceptions.AcotaConfigurationException;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;

/**
 * WordnetEnhancer is an {@link Enhancer} specialized in increasing the weight 
 * of the terms that match with the synonyms of the founded terms, if the term
 * was already in the {@link TagTO}'s Map, or adds the term to
 * the {@link TagTO}'s Map.
 * 
 * @author César Luis Alvargonzález
 */
public class WordnetEnhancer extends EnhancerAdapter implements Configurable {

	protected String wordnetEnDict;
	protected double wordnetRelevance;
	
	protected IDictionary dicionary;
	
	protected CoreConfiguration configuration;
	
	/**
	 * Zero-argument default constructor
	 * @throws IOException Any exception that occurs while initializing Wordnet's dictionary
	 * @throws AcotaConfigurationException Any exception that occurs while initializing 
	 * a Configuration object
	 */
	public WordnetEnhancer() throws IOException, AcotaConfigurationException  {
		super();
		WordnetEnhancer.provider = new ProviderTO("Wordnet Enhancer");
		loadConfiguration(configuration);
		
		URL url = new URL ("file", null, wordnetEnDict) ;
		this.dicionary = new Dictionary ( url ) ;
		dicionary.open();
	}
	
	@Override
	public void loadConfiguration(CoreConfiguration configuration) throws AcotaConfigurationException{
		if(configuration==null)
			configuration = new CoreConfiguration();
		this.configuration = configuration;
		this.wordnetEnDict = configuration.getWordnetEnDict();
		this.wordnetRelevance = configuration.getWordnetRelevance();	
	}

	@Override
	protected void execute() throws Exception {
		Set<Entry<String, TagTO>> backupSet = new HashSet<Entry<String, TagTO>>();
		for (Entry<String, TagTO> label : tags.entrySet()) {
			backupSet.add(label);
		}
		for (Entry<String, TagTO> label : backupSet) {
			findSynonims(label.getKey());
		}
	}

	@Override
	protected void preExecute() throws Exception {
		this.suggest = request.getSuggestions();
		this.tags = suggest.getTags();
		suggest.setResource(request.getResource());

	}

	@Override
	protected void postExecute() throws Exception {
		this.request.getTargetProviders().add(provider);
		this.request.setSuggestions(suggest);

	}

	/**
	 * Increases the weight of the terms that match with the synonyms of
	 * the label founded
	 * @param label Label founded
	 */
	protected void findSynonims(String label){
		IIndexWord idxWord = dicionary.getIndexWord (label , POS . NOUN ) ;
		if(idxWord!=null){
			IWordID wordID = idxWord.getWordIDs () . get (0) ;
			IWord word = dicionary.getWord ( wordID ) ;
			ISynset synset = word.getSynset () ;
			String cleanWord = "";
			for ( IWord w : synset.getWords () ){
				cleanWord = w.getLemma().replace('_', ' ').toLowerCase();
				if(!cleanWord.equals(label)){
					TagTO tag = tags.get(cleanWord);
					if(tag == null)
						tag = new TagTO(cleanWord, provider, suggest.getResource());
					fillSuggestions(tag, wordnetRelevance);
				}
			}
		}
	}

}
