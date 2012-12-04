package org.weso.acota.core.business.enhancer.lucene.analyzer;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

public class SpecialCaseStopAnalyzer extends Analyzer {

	@Override
	public final TokenStream tokenStream(String arg0, Reader arg1) {
		return null;
	}

	@Override
	public final TokenStream reusableTokenStream(String fieldName, Reader reader)
			throws IOException {
		return super.reusableTokenStream(fieldName, reader);
	}
	
}
