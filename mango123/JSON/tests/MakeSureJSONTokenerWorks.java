package mango123.JSON.tests;
import java.io.StringReader;
import mango123.JSON.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class MakeSureJSONTokenerWorks {

	public static String allTokens = "\"HELLO\" \"unicode\\u002F\\uAAD1\" 1 -1 1.2 -1.2 1e10 -1e10 1.2e10 -1.2e10 1.2e0 1e-10 true false null [ ] { } : ,";
	public static String[] allTokensCheck = allTokens.split(" ");

	@Test
	public void testCorrectlyParseAllTokenTypes() {
		JSONTokener tokener = new JSONTokener(new StringReader(allTokens));
		try {
			String token;
			int i = 0;
			while ( (token = tokener.nextToken()) != null ) {
				if (token.startsWith("\"unicode")) {
					assertEquals("\"unicode\u002F\uAAD1\"", token);
					i++;
				}
				else {
					assertEquals(allTokensCheck[i++], token);
				}
			}
			assertEquals(i, allTokensCheck.length);
		} catch(JSONException e) {
			fail(e.getMessage());
		}
	}

	public static String[] correctEscape = "\"/\\/\\b\" \"\\f\" \"\\n\" \"\\r\" \"\\t\" \"\\\\\" \"\\\"\" \"\\u212F\"".split(" ");
	public static String[] correctEscapeCompanion = "\"//\b\" \"\f\" \"\n\" \"\r\" \"\t\" \"\\\" \"\"\" \"\u212F\"".split(" ");
	@Test
	public void testCorrectParsingOfEscapedSequence() {
		try {
			for (int i = 0; i < correctEscape.length; i++) {
				String token = new JSONTokener(new StringReader(correctEscape[i])).nextToken();
				assertEquals(correctEscapeCompanion[i], token);
			}
		}
		catch(JSONException e) {
			fail(e.getMessage());
		}
	}
	
	public static String[] invalidTokens = {"- 1.2", "1.2e 10", "tru", "fals", "nul", ">", "\"12\t12\"", "\"12\\u123G\"", "\\u123G"};
	@Test
	public void testWillThrowExceptionOnInvalidTokens() {
		for (String token : invalidTokens) {
			try {
				JSONTokener tokener = new JSONTokener(new StringReader(token));
				tokener.nextToken();
				fail("No JSONException thrown: " + token + " has been regarded as a valid token");
			} catch(JSONException e) {
				//swallow
			}
		}
	}
}
