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
 * For non Spanish or English text, it tokenizes, converts to
 * lower case and removes words to long (>50) and to short(<3).
 * 
 * @author César Luis Alvargonzález
 */
public class DefaultStopAnalyzer extends Analyzer {
	
	/**
	 * Zero-argument default constructor.
	 */
	public DefaultStopAnalyzer(){}
	
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
