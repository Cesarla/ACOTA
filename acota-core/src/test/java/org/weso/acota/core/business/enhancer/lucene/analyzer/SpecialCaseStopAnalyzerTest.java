package org.weso.acota.core.business.enhancer.lucene.analyzer;

import static org.junit.Assert.*;
import java.io.StringReader;

import org.junit.Before;
import org.junit.Test;
import org.weso.acota.core.business.enhancer.lucene.analyzer.SpecialCaseStopAnalyzer;

public class SpecialCaseStopAnalyzerTest {
	
	protected SpecialCaseStopAnalyzer specialCaseAnalyzer;
	
	@Before
	public void startUp(){
		this.specialCaseAnalyzer = new SpecialCaseStopAnalyzer();
	}
	
	@Test
	public void tokenStreamTest(){
		assertTrue(null == specialCaseAnalyzer.tokenStream("", new StringReader("text")));
	}
	
}
