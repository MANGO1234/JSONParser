package mango123.JSON.tests;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import mango123.JSON.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class MakeSureJSONObjectWorks {
	@Test
	public void testCannotParseIncorrectInput1() {
		try {
			String str = "{";
			JSONObject.parse(new JSONTokener(new StringReader(str)));
			fail("JSONException not thrown");
		} catch(JSONException e) {
			//IGNORE
		}
	}
	
	@Test
	public void testCannotParseIncorrectInput2() {
		try {
			String str = "{\"12\": 12";
			JSONObject.parse(new JSONTokener(new StringReader(str)));
			fail("JSONException not thrown");
		} catch(JSONException e) {
			//IGNORE
		}
	}
	
	@Test
	public void testCannotParseIncorrectInput3() {
		try {
			String str = "{\"12\": , \"ab\", \"123\"}";
			JSONObject.parse(new JSONTokener(new StringReader(str)));
			fail("JSONException not thrown");
		} catch(JSONException e) {
			//IGNORE
		}
	}
	
	@Test
	public void testCannotParseIncorrectInput4() {
		try {
			String str = "{:12, \"ab\", \"123\"}";
			JSONObject.parse(new JSONTokener(new StringReader(str)));
			fail("JSONException not thrown");
		} catch(JSONException e) {
			//IGNORE
		}
	}
	
	@Test
	public void testCannotParseIncorrectInput5() {
		try {
			String str = "{\"123:12, \"ab\", \"123\"}";
			JSONObject.parse(new JSONTokener(new StringReader(str)));
			fail("JSONException not thrown");
		} catch(JSONException e) {
			//IGNORE
		}
	}
	
	public static String correctJSONString = "{\"int\": 1, \"long\": 123456789012, " +
	"\"bigInt\": 1234567890123456789012345, \"double\": 1.0e20, \"bigDecimal\": 1.234e1234," +
	"\"str\": \"abc\\u023D\", \"bool1\": true, \"bool2\": false, \"null\": null, " +
	"\"object\": {\"a\":1}, \"array\": [1, 2, 3], \"escape\": \"\\b\\t\\n\\f\\r\\\"\\\\\" }";
	@Test
	public void testJSONIsParsedCorrectlyIntoAJSONObjectWithCorrectTypes() throws Exception {
		JSONObject ob = JSONObject.parse(new JSONTokener(new StringReader(correctJSONString)));
		assertEquals("Integer", 1, ob.getInt("int").intValue());
		assertEquals("Long", 123456789012l, ob.getLong("long").longValue());
		assertEquals("BigInt", new BigInteger("1234567890123456789012345"), ob.getBigInt("bigInt"));
		assertEquals("Double", Double.valueOf("1.0e20"), ob.getDouble("double"));
		assertEquals("BigDecimal", new BigDecimal("1.234e1234"), ob.getDecimal("bigDecimal"));
		assertEquals("String", "abc\u023D", ob.getString("str"));
		assertEquals("true", Boolean.TRUE, ob.getBoolean("bool1"));
		assertEquals("false", Boolean.FALSE, ob.getBoolean("bool2"));
		assertEquals("escape", "\b\t\n\f\r\"\\", ob.getString("escape"));

		try {
			assertNull("null", ob.get("null"));
			Object o = ob.getArray("array");
			if (!(o instanceof JSONArray)) {
				fail("JSONArray");
			}
			o = ob.getObject("object");
			if (!(o instanceof JSONObject)){
				fail("JSONObject");
			}
		} catch(Exception e) {
			throw e;
		}
	}
	
	@Test
	public void testCanParseEmptyJSONObject() {
		try {
			JSONObject.parse(new JSONTokener(new StringReader("{}")));
		} catch(JSONException e) {
			fail("Cannot parse empty JSON object '{}'");
		}
	}
	
	@Test
	public void testPutMethods() throws Exception {
		try {
			JSONObject ob = JSONObject.newEmptyInstance();
			Object o = 1;
			ob.put("int", (Integer) o);
			assertEquals("int", o, ob.get("int"));
			
			o = 123456789012345l;
			ob.put("long", (Long) o);
			assertEquals("long", o, ob.get("long"));
			
			o = new BigInteger("123");
			ob.put("bigInt", (BigInteger) o);
			assertEquals("bigInt", o, ob.get("bigInt"));

			o = new BigDecimal("123.4");
			ob.put("bigDec", (BigDecimal) o);
			assertEquals("bigDec", o, ob.get("bigDec"));
			
			o = 123.4;
			ob.put("double", (Double) o);
			assertEquals("double", o, ob.get("double"));
			
			o = Boolean.TRUE;
			ob.put("boolean", (Boolean) o);
			assertEquals("boolean", Boolean.TRUE, ob.get("boolean"));
			
			ob.putNull("null");
			assertNull("null", ob.get("null"));
		} catch(Exception e) {
			throw e;
		}
	}
	
	@Test
	public void testJSONObjectToStringComformToJSON() {
		try {
			JSONObject ob = JSONObject.parse(new JSONTokener(new StringReader(correctJSONString)));
			String str = ob.toString();
			JSONObject.parse(new JSONTokener(new StringReader(str)));
		} catch(JSONException e) {
			fail(".toString should return valid JSON: " + e.getMessage());
		}
	}
}
