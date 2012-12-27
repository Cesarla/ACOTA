package org.weso.acota.core.business.enhancer;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.configuration.ConfigurationException;
import org.weso.acota.core.Configuration;
import org.weso.acota.core.entity.ProviderTO;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;

/**
 * 
 * @author César Luis Alvargonzález
 *
 */
public class WordnetEnhancer extends EnhancerAdapter implements Configurable {

	protected String wordnetEnDict;
	protected double wordnetRelevance;
	
	protected IDictionary dicionary;
	
	protected Configuration configuration;
	
	public WordnetEnhancer() throws ConfigurationException, IOException {
		super();
		WordnetEnhancer.provider = new ProviderTO("Wordnet Enhancer");
		loadConfiguration(configuration);
		
		URL url = new URL ("file",null,wordnetEnDict) ;
		this.dicionary = new Dictionary ( url ) ;
		dicionary.open();
	}
	
	@Override
	public void loadConfiguration(Configuration configuration) throws ConfigurationException{
		if(configuration==null)
			configuration = new Configuration();
		this.configuration = configuration;
		this.wordnetEnDict = configuration.getWordnetEnDict();
		this.wordnetRelevance = configuration.getWordnetRelevance();	
	}

	@Override
	protected void execute() throws Exception {
		Set<Entry<String, Double>> backupSet = new HashSet<Entry<String, Double>>();
		for (Entry<String, Double> label : labels.entrySet()) {
			backupSet.add(label);
		}
		for (Entry<String, Double> label : backupSet) {
			findSynonims(label.getKey());
		}
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

	protected void findSynonims(String label){
		IIndexWord idxWord = dicionary.getIndexWord (label , POS . NOUN ) ;
		if(idxWord!=null){
			IWordID wordID = idxWord.getWordIDs () . get (0) ;
			IWord word = dicionary.getWord ( wordID ) ;
			ISynset synset = word.getSynset () ;
			String cleanWord = "";
			for ( IWord w : synset.getWords () ){
				cleanWord = w.getLemma().replace('_', ' ').toLowerCase();
				if(!cleanWord.equals(label))
					fillSuggestions(cleanWord, wordnetRelevance);
			}
		}
	}

}
