package org.weso.acota.core.entity;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.weso.acota.core.entity.LabelTO;

/**
 * @author César Luis Alvargonzález
 *
 */
public class LabelTOTest {

	protected LabelTO labelTO;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.labelTO = new LabelTO();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void emptyConstructorName() {
		assertTrue(null==labelTO.name);
	}
	
	@Test
	public void emptyConstructorWeight() {
		assertTrue(0==labelTO.weight);
	}
	
	@Test
	public void ConstructorName() {
		this.labelTO = new LabelTO("foo",3);
		assertEquals("foo",labelTO.name);
	}
	
	@Test
	public void ConstructorWeight() {
		this.labelTO = new LabelTO("foo",3);
		assertTrue(3==labelTO.weight);
	}
	
	@Test
	public void getNameEmpty(){
		assertTrue(null==labelTO.getName());
	}
	
	@Test
	public void getNameTest(){
		this.labelTO = new LabelTO("foo",3);
		assertEquals("foo",labelTO.getName());
	}

	@Test
	public void getWeightEmpty(){
		assertTrue(0==labelTO.getWeight());
	}
	
	@Test
	public void getWeightTest(){
		this.labelTO = new LabelTO("foo",3);
		assertTrue(3==labelTO.getWeight());
	}
	
	@Test
	public void setNameEmpty(){
		labelTO.setName(null);
		assertTrue(null==labelTO.getName());
	}
	
	@Test
	public void setNameTest(){
		labelTO.setName("foo");
		assertEquals("foo",labelTO.getName());
	}

	@Test
	public void setWeightTest(){
		labelTO.setWeight(5);
		assertTrue(5==labelTO.getWeight());
	}
	
	@Test
	public void hashCodeEmpty(){
		assertTrue(961==labelTO.hashCode());
	}
	
	@Test
	public void hashCodeTest(){
		this.labelTO = new LabelTO("foo",3);
		assertTrue(3149758==labelTO.hashCode());
	}
	
	@Test 
	public void equalstEmptyNull(){
		assertFalse(new LabelTO("foo",3).equals(null));
	}
	
	@Test 
	public void equalstSameInstances(){
		LabelTO l1 = new LabelTO("foo",3);
		assertTrue(l1.equals(l1));
	}
	
	
	@Test
	public void equalsDifferentClasses(){
		String aux = new String ();
		assertFalse(labelTO.equals(aux));
	}
	
	@Test 
	public void equalsDifferentObjects(){
		assertFalse(labelTO.equals(new LabelTO("foo",3)));
	}
	
	@Test 
	public void equalstNameNulls(){
		LabelTO l1 = new LabelTO(null,3);
		LabelTO l2 = new LabelTO(null,2);
		assertFalse(l1.equals(l2));
	}
	
	@Test 
	public void equalstDifferentWeight(){
		LabelTO l1 = new LabelTO("foo",3);
		LabelTO l2 = new LabelTO("foo",2);
		assertFalse(l1.equals(l2));
	}
	
	@Test 
	public void equalstDifferentName(){
		LabelTO l1 = new LabelTO("fo",3);
		LabelTO l2 = new LabelTO("foo",2);
		assertFalse(l1.equals(l2));
	}
	
	@Test 
	public void equalstTest(){
		LabelTO l1 = new LabelTO("foo",3);
		LabelTO l2 = new LabelTO("foo",3);
		assertTrue(l1.equals(l2));
	}
	
	
	@Test
	public void toStringTest(){
		LabelTO l1 = new LabelTO("foo",3);
		assertEquals("LabelTO [name=foo, weight=3.0]",l1.toString());
	}
	
}
