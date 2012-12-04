package org.weso.acota.core.business.enhancer.lucene.analyzer;

import java.io.IOException;
import java.io.Reader;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LengthFilter;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.util.Version;

public class EnglishStopAnalyzer extends Analyzer {
	
	protected static Logger logger = Logger.getLogger(EnglishStopAnalyzer.class);
	
	public EnglishStopAnalyzer() {}

	@Override
	public final TokenStream tokenStream(String arg0, Reader reader) {
		TokenStream result = new StandardTokenizer(Version.LUCENE_31, reader);
		
		result = new LengthFilter(false, result, 3, 50);
		
		result = new StopFilter(Version.LUCENE_31,result, StopAnalyzer.ENGLISH_STOP_WORDS_SET); 
	
		return result;
	}
	
	@Override
	public final TokenStream reusableTokenStream(String fieldName, Reader reader)
			throws IOException {
		return super.reusableTokenStream(fieldName, reader);
	}
}
