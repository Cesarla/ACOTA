package org.weso.acota.core.business.enhancer.lucene.analyzer;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LengthFilter;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.util.Version;

/**
 * 
 * @author César Luis Alvargonzález
 *
 */
public class DefaultStopAnalyzer extends Analyzer {

	@Override
	public final TokenStream tokenStream(String arg0, Reader reader) {

		TokenStream result = new StandardTokenizer(Version.LUCENE_31, reader);  
		
		result = new LowerCaseFilter(Version.LUCENE_31, result);
		
		result = new LengthFilter(false, result, 3, 50);
		
		return result;
	}
	
	@Override
	public final TokenStream reusableTokenStream(String fieldName, Reader reader)
			throws IOException {
		return super.reusableTokenStream(fieldName, reader);
	}
	
}
