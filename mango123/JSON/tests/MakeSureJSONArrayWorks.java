package mango123.JSON.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

import mango123.JSON.JSONArray;
import mango123.JSON.JSONException;
import mango123.JSON.JSONObject;
import mango123.JSON.JSONTokener;

public class MakeSureJSONArrayWorks {
	@Test
	public void testCannotParseIncorrectInput1() {
		try {
			String str = "[";
			JSONArray.parse(new JSONTokener(new StringReader(str)));
			fail("JSONException not thrown");
		} catch(JSONException e) {
			//IGNORE
		}
	}
	
	@Test
	public void testCannotParseIncorrectInput2() {
		try {
			String str = "[12, \"12\"";
			JSONArray.parse(new JSONTokener(new StringReader(str)));
			fail("JSONException not thrown");
		} catch(JSONException e) {
			//IGNORE
		}
	}
	
	@Test
	public void testCannotParseIncorrectInput3() {
		try {
			String str = "12, 12]";
			JSONArray.parse(new JSONTokener(new StringReader(str)));
			fail("JSONException not thrown");
		} catch(JSONException e) {
			//IGNORE
		}
	}
	
	@Test
	public void testCannotParseIncorrectInput4() {
		try {
			String str = "1,]";
			JSONArray.parse(new JSONTokener(new StringReader(str)));
			fail("JSONException not thrown");
		} catch(JSONException e) {
			//IGNORE
		}
	}
	
	@Test
	public void testCannotParseIncorrectInput5() {
		try {
			String str = "[1, null,";
			JSONArray.parse(new JSONTokener(new StringReader(str)));
			fail("JSONException not thrown");
		} catch(JSONException e) {
			//IGNORE
		}
	}

	public static String correctJSONString = "[1, 123456789012, 1234567890123456789012345," +
			"1.0e20, 1.234e1234, \"abc\\u023D\", true, false, null, {\"a\":1}, [1], \"\\b\\t\\n\\f\\r\\\"\\\\\"]";
	@Test
	public void testJSONIsParsedCorrectlyIntoAJSONObjectWithCorrectTypes() throws Exception {
		JSONArray arr = JSONArray.parse(new JSONTokener(new StringReader(correctJSONString)));
		assertEquals("Integer", 1, arr.getInt(0).intValue());
		assertEquals("Long", 123456789012l, arr.getLong(1).longValue());
		assertEquals("BigInt", new BigInteger("1234567890123456789012345"), arr.getBigInt(2));
		assertEquals("Double", Double.valueOf("1.0e20"), arr.getDouble(3));
		assertEquals("BigDecimal", new BigDecimal("1.234e1234"), arr.getDecimal(4));
		assertEquals("String", "abc\u023D", arr.get(5));
		assertEquals("true", Boolean.TRUE, arr.get(6));
		assertEquals("false", Boolean.FALSE, arr.get(7));
		assertNull("null", arr.get(8));
		assertEquals("escape", "\b\t\n\f\r\"\\", arr.get(11));

		try {
			Object o = arr.getArray(10);
			if (!(o instanceof JSONArray)) {
				fail("JSONArray");
			}
			o = arr.getObject(9);
			if (!(o instanceof JSONObject)){
				fail("JSONObject");
			}
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	@Test
	public void testCanParseEmptyJSONArray() {
		try {
			JSONArray.parse(new JSONTokener(new StringReader("[]")));
		} catch(JSONException e) {
			fail("Cannot parse empty JSON array '[]'");
		}
	}
	
	@Test
	public void testSetMethods() throws Exception {
		try {
			JSONArray arr = JSONArray.newEmptyInstance();
			arr.addNull(0);

			Object o = 1;
			arr.set(0, (Integer) o);
			assertEquals("int", o, arr.get(0));
			
			o = 123456789012345l;
			arr.set(0, (Long) o);
			assertEquals("long", o, arr.get(0));
			
			o = new BigInteger("123");
			arr.set(0, (BigInteger) o);
			assertEquals("bigInt", o, arr.get(0));

			o = new BigDecimal("123.4");
			arr.set(0, (BigDecimal) o);
			assertEquals("bigDec", o, arr.get(0));
			
			o = 123.4;
			arr.set(0, (Double) o);
			assertEquals("double", o, arr.get(0));
			
			o = Boolean.TRUE;
			arr.set(0, (Boolean) o);
			assertEquals("boolean", Boolean.TRUE, arr.get(0));
			
			arr.setNull(0);
			assertNull("null", arr.get(0));
			assertEquals("length not equal", 1, arr.size());
		} catch(Exception e) {
			throw e;
		}
	}
	
	@Test
	public void testJSONObjectToStringComformToJSON() {
		try {
			JSONArray ob = JSONArray.parse(new JSONTokener(new StringReader(correctJSONString)));
			String str = ob.toString();
			JSONArray.parse(new JSONTokener(new StringReader(str)));
		} catch(JSONException e) {
			fail(".toString should return valid JSON: " + e.getMessage());
		}
	}
}
