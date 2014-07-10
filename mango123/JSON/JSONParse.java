package mango123.JSON;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains common methods that both JSONObject and JSONArray uses
 */
class JSONParse {
	private JSONParse(){};

	/**
	 * Parse a JSON Object, assuming the starting '{' has been read by the tokener
	 */
	static Map<String, Object> parseObject(JSONTokener tokener) throws JSONException {
		Map<String, Object> ob = new LinkedHashMap<String, Object>();
		boolean start = true;

		while (true) {
			//start by checking whether there is a key
			String key;
			String token = tokener.nextToken();
	
			//make an extra clause in case of empty object
			if (start) {
				if (token == JSONTokener.RIGHT_BRACE) return ob;
				start = false;
			}
			if (token != null && token.charAt(0) == '"') {
				key = token.substring(1, token.length() - 1);
			}
			else {
				throw tokener.newSyntaxError("missing key");
			}

			//follow by colon
			if (tokener.nextToken() != JSONTokener.COLON) {
				throw tokener.newSyntaxError("missing ':'");
			}

			//follow by a value
			ob.put(key, toCorrespondingPOJO(tokener.nextToken(), tokener));
			
			//follow by either '}' (return the JSONObject) or ',' (do nothing and continue)
			token = tokener.nextToken();
			if (token == JSONTokener.RIGHT_BRACE) {
				return ob;
			}
			else if (token != JSONTokener.COMMA) {
				throw tokener.newSyntaxError("missing '}'");
			}
		}
	}

	/**
	 * Parse a JSON Array, assuming the starting '[' has been read by the tokener
	 */
	static List<Object> parseArray(JSONTokener tokener) throws JSONException {
		List<Object> list = new ArrayList<Object>();
		boolean start = true;

		while (true) {
			String token = tokener.nextToken();

			//extra clause in case of empty JSONArray "[]"
			if (start) {
				if (token == JSONTokener.RIGHT_SQUARE) return list;
				start = false;
			}
			list.add(toCorrespondingPOJO(token, tokener));

			//either a ',' or ']'
			token = tokener.nextToken();
			if (token == JSONTokener.RIGHT_SQUARE) {
				return list;
			}
			else if (token != JSONTokener.COMMA) {
				throw tokener.newSyntaxError("missing ']'");
			}
		}
	}

	static Object toCorrespondingPOJO(String token, JSONTokener tokener) throws JSONException {
		if (token == null) {
			throw tokener.newSyntaxError("missing value");
		}
		char id = token.charAt(0);

		if (id == '"') {                                        //String
			return token.substring(1, token.length() - 1);
		}
		else if (Character.isDigit(id) || id == '-') {        //Number
			return parseNumber(token);
		}
		else if (token == JSONTokener.LEFT_BRACE) {           //JSONObject/Map
			return new JSONObject(parseObject(tokener));
		}
		else if (token == JSONTokener.LEFT_SQUARE) {          //JSONArray/List
			return new JSONArray(parseArray(tokener));
		}
		else if (token == JSONTokener.TRUE) {                 //true
			return Boolean.TRUE;
		}
		else if (token == JSONTokener.FALSE) {                //false
			return Boolean.FALSE;
		}
		else if (token == JSONTokener.NULL) {                 //null
			return null;
		}
		throw tokener.newSyntaxError("missing value");
	}

	/**
	 * Parse a number JSON token into an Object (Integer, Long, Double, BigInteger, or BigDecimal)
	 */
	static Object parseNumber(String token) {
		if (token.indexOf('.') == -1 && token.indexOf('e') == -1 && token.indexOf('E') == -1) {
			try {
				return Integer.valueOf(token);
			} catch(NumberFormatException e) {} //swallow
			
			try {
				return Long.valueOf(token);
			} catch(NumberFormatException e) {} //swallow

			//token that's an integer can always be parse by BigInteger
			return new BigInteger(token);
		}
		else {
			try {
				Double num = Double.valueOf(token);
				if (!num.isInfinite()) { //if it's infinite use BigDecimal instead
					return num;
				}
			} catch(NumberFormatException e) {} //swallow
	
			//token that's a floating point number can always be parse by BigDecimal
			return new BigDecimal(token);
		}
	}
}