package mango123.JSON;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>This class represents a JSON object. It cannot be constructed directly. Instead, use the static
 * {@code JSONObject.parse(JSONTokener)} or {@code JSONObject.newEmptyInstance()} to get an instance
 * of JSONObject.</p>
 * <p>e.g. a JSONTokener containing the input "{@code {"a": 1, "b": "HELLO", "c": [1, 2, 3]}" can be passed
 * into {@code .parse()} to get an instance of JSONArray containing the above elements.</p>
 * @version 0.8
 * @see JSONArray
 */
public final class JSONObject {
	Map<String, Object> map;
	
	/**
	 * Used privately for the .parse() and .newEmptyInstance()
	 */
	JSONObject(Map<String, Object> map) {
		this.map = map;
	}

	/**
	 * <p>Creates and returns a new instance of JSONObject. This instance will be empty and contain
	 * no key/value mappings.</p>
	 * @returns an empty instance of JSONObject
	 */
	public static JSONObject newEmptyInstance() {
		return new JSONObject(new LinkedHashMap<String, Object>());
	}

	/**
	 * Parses and constructs a <code>JSONObject</code> from a <code>JSONTokener</code>.
	 * @param tokener the JSON input
	 * @return a <code>JSONObject</code> of the JSON data
	 * @throws JSONException if any syntax error is encountered
	 */
	public static JSONObject parse(JSONTokener tokener) throws JSONException {
		//parseObjectFrom() requires the '{' to be read
		if (tokener.nextToken() != JSONTokener.LEFT_BRACE) {
			throw tokener.newSyntaxError("JSON object needs to start with '{");
		}

		JSONObject ob = new JSONObject(JSONParse.parseObject(tokener));
	
		//if that's not the end of the JSON input -> syntax error
		if (tokener.nextToken() != null) {
			throw tokener.newSyntaxError("JSON object needs to end with '}'");
		}

		return ob;
	}

	/**
	 * <p>Returns <code>true</code> if the JSONObject contains the specified key.</p>
	 * @param key the string key
	 * @return <code>true</code> if the JSONObject contains the specified key
	 */
	public boolean hasKey(String key) {
		return map.containsKey(key);
	}
	
	/**
	 * Returns <code>true</code> if the JSONObject contains the specified value.
	 * @param value the value to be tested
	 * @return <code>true</code> if the JSONObject contains the specified value
	 */
	public boolean hasValue(Object value) {
		return map.containsValue(value);
	}

	/**
	 * <p>Get the <code>Object</code> mapped by the specified <code>String</code> key, or <code>null</code>
	 * if the key does not mapped to an <code>Object</code>.</p>
	 * @param key the string key
	 * @return the <code>Object</code> mapped by the key, or null if it cannot be found
	 */
	public Object get(String key) {
		return map.get(key);
	}
	
	/**
	 * <p>Get the <code>String</code> mapped by the specified key, or <code>null</code> if the key
	 * does not mapped to an <code>String</code>.</p>
	 * @param key the string key
	 * @return the <code>String</code> mapped by the key, or null if it cannot be found
	 */
	public String getString(String key) {
		Object v = map.get(key);
		return (v instanceof String) ? (String) v : null;
	}
	
	/**
	 * <p>Get the <code>Integer</code> mapped by the specified key, or <code>null</code> if the key
	 * does not mapped to an <code>Integer</code>.</p>
	 * @param key the string key
	 * @return the <code>Integer</code> mapped by the key, or null if it cannot be found
	 */
	public Integer getInt(String key) {
		Object v = map.get(key);
		return (v instanceof Integer) ? (Integer) v : null;
	}
	
	/**
	 * <p>Get the <code>Long</code> mapped by the specified key, or <code>null</code> if the key does
	 * not mapped to a <code>Long</code>.</p>
	 * @param key the string key
	 * @return the <code>Long</code> mapped by the key, or null if it cannot be found
	 */
	public Long getLong(String key) {
		Object v = map.get(key);
		return (v instanceof Long) ? (Long) v : null;
	}
	
	
	/**
	 * <p>Get the <code>BigInteger</code> mapped by the specified key, or <code>null</code> if the key
	 * does not mapped to a <code>BigInteger</code>.</p>
	 * @param key the string key
	 * @return the <code>BigInteger</code> mapped by the key, or null if it cannot be found
	 */
	public BigInteger getBigInt(String key) {
		Object v = map.get(key);
		return (v instanceof BigInteger) ? (BigInteger) v : null;
	}
	
	/**
	 * <p>Get the <code>Double</code> mapped by the specified key, or <code>null</code> if the key does
	 * not mapped to a <code>Double</code>.</p>
	 * @param key the string key
	 * @return the <code>Double</code> mapped by the key, or null if it cannot be found
	 */
	public Double getDouble(String key) {
		Object v = map.get(key);
		return (v instanceof Double) ? (Double) v : null;
	}
	
	/**
	 * <p>Get the <code>BigDecimal</code> mapped by the specified> key, or <code>null</code> if the
	 * key does not mapped to a <code>BigDecimal</code>.</p>
	 * @param key the string key
	 * @return the <code>BigDecimal</code> mapped by the key, or null if it cannot be found
	 */
	public BigDecimal getDecimal(String key) {
		Object v = map.get(key);
		return (v instanceof BigDecimal) ? (BigDecimal) v : null;
	}
	
	/**
	 * <p>Get the <code>Boolean</code> mapped by the specified key, or <code>null</code> if the key
	 * does not mapped to a <code>Boolean</code>.</p>
	 * @param key the string key
	 * @return the <code>Boolean</code> mapped by the key, or null if it cannot be found
	 */
	public Boolean getBoolean(String key) {
		Object v = map.get(key);
		return (v instanceof Boolean) ? (Boolean) v : null;
	}

	/**
	 * <p>Get the <code>JSONArray</code> mapped by the specified key, or <code>null</code> if the key
	 * does not mapped to a <code>JSONArray</code>.</p>
	 * @param key the string key
	 * @return the <code>JSONArray</code> mapped by the key, or null if it cannot be found
	 */
	public JSONArray getArray(String key) {
		Object v = map.get(key);
		return (v instanceof JSONArray) ? (JSONArray) v : null;
	}
	
	
	/**
	 * <p>Get the <code>JSONObject</code> mapped by the specified key, or <code>null</code> if the key
	 * does not mapped to a <code>JSONObject</code>.</p>
	 * @param key the string key
	 * @return the <code>JSONObject</code> mapped by the key, or null if it cannot be found
	 */
	public JSONObject getObject(String key) {
		Object v = map.get(key);
		return (v instanceof JSONObject) ? (JSONObject) v : null;
	}
	
	
	/**
	 * <p>Return <code>true</code> if the specified key does not exist or the value mapped by the
	 * key is <code>null</null>.</p>
	 * <p>Use {@link #hasKey(String)} to determine whether the key exists or not if you need it.</b>
	 * @param key the string key
	 * @return <code>true</code> if the key does not exist or the value mapped by it is <code>null</code>
	 */
	public boolean isNull(String key) {
		return map.get(key) == null;
	}

	/**
	 * <p>Put a key/value (<Code>String</code>) pair into this JSONObject.</p>
	 * @param key the string key
	 * @param value the string value
	 * @return a reference to this object
	 */
	public JSONObject put(String key, String value) {
		map.put(key, value);
		return this;
	}

	/**
	 * <p>Put a key/value (<Code>Integer</code>) pair into this JSONObject.</p>
	 * @param key the string key
	 * @param value the <code>Integer</code> value
	 * @return a reference to this object
	 */
	public JSONObject put(String key, Integer value) {
		map.put(key, value);
		return this;
	}
	
	/**
	 * <p>Put a key/value (<Code>Long</code>) pair into this JSONObject.</p>
	 * @param key the string key
	 * @param value the <code>Long</code> value
	 * @return a reference to this object
	 */
	public JSONObject put(String key, Long value) {
		map.put(key, value);
		return this;
	}
	
	/**
	 * <p>Put a key/value (<Code>BigInteger</code>) pair into this JSONObject.</p>
	 * <p>This method only accepts an instance of BigInteger and not its subclasses as an num. It
	 * will throw an IllegalArgumentException if you try to pass an instance of BigDecimal's subclass
	 * as num.</p>
	 * @param key the string key
	 * @param value the <code>BigInteger</code> value
	 * @return a reference to this object
	 * @throw IllegalArgumentException if an instance of a subclass of BigInteger is passed as num
	 */
	public JSONObject put(String key, BigInteger value) {
		if (value.getClass() == BigInteger.class) map.put(key, value);
		else throw new IllegalArgumentException(".put() does not accept a subclass of BigInteger");
		return this;
	}
		
	/**
	 * <p>Put a key/value (<Code>Double</code>) pair into this JSONObject.</p>
	 * @param key the string key
	 * @param value the <code>Double</code> value
	 * @return a reference to this object
	 */
	public JSONObject put(String key, Double value) {
		map.put(key, value);
		return this;
	}

	
	/**
	 * <p>Put a key/value (<Code>BigDecimal</code>) pair into this JSONObject.</p>
	 * <p>This method only accepts an instance of BigDecimal and not its subclasses as an num. It
	 * will throw an IllegalArgumentException if you try to pass an instance of BigDecimal's subclass
	 * as num.</p>
	 * @param key the string key
	 * @param value the <code>BigDecimal</code> value
	 * @return a reference to this object
	 * @throw IllegalArgumentException if an instance of a subclass of BigDecimal is passed as num
	 */
	public JSONObject put(String key, BigDecimal value) {
		if (value.getClass() == BigDecimal.class) map.put(key, value);
		else throw new IllegalArgumentException(".put() does not accept a subclass of BigDecimal");
		return this;
	}

	
	/**
	 * <p>Put a key/value (<Code>Boolean</code>) pair into this JSONObject.</p>
	 * @param key the string key
	 * @param value the <code>Boolean</code> value
	 * @return a reference to this object
	 */
	public JSONObject put(String key, Boolean value) {
		map.put(key, value);
		return this;
	}
	
	/**
	 * <p>Put a key/value (<Code>JSONObject</code>) pair into this JSONObject.</p>
	 * @param key the string key
	 * @param value the <code>JSONOBject</code>
	 * @return a reference to this object
	 */
	public JSONObject put(String key, JSONObject value) {
		map.put(key, value);
		return this;
	}

	/**
	 * <p>Put a key/value (<Code>JSONArray</code>) pair into this JSONObject.</p>
	 * @param key the string key
	 * @param value the <code>JSONArray</code>
	 * @return a reference to this object
	 */
	public JSONObject put(String key, JSONArray value) {
		map.put(key, value);
		return this;
	}
	
	/**
	 * <p>Associate <code>null</code> with the specified key in this JSONObject.</p>
	 * @param key the string key
	 * @return a reference to this object
	 */
	public JSONObject putNull(String key) {
		map.put(key, null);
		return this;
	}

	/**
	 * <p>Remove all mappings from this JSONObject</p>
	 * @return a reference to this object
	 */
	public JSONObject clear() {
		map.clear();
		return this;
	}

	/**
	 * <p>Returns the string representation of the JSONObject in JSON.</p>
	 * <p>The format will be the most compact (no whitespaces) and strictly conform to JSON.</p>
	 * <p>Example: {"a":1,"d":[true,false,null],"b":{"a":0,"b":1},"c":"d"}</p>
	 * @return String JSON representation of the JSONObject
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder().append('{');

		int size = map.size(), i = 0;
		for (Map.Entry<String, Object> m : map.entrySet()) {
			str.append('"').append(m.getKey()).append('"').append(':');
			
			if (m.getValue() instanceof String) {
				str.append('"')
					.append(JSONTokener.escapeStr((String) m.getValue()))
					.append('"');
			}
			else {
				str.append(m.getValue());
			}

			if (++i < size) {
				str.append(',');
			}
		}
		str.append('}');
		return str.toString();
	}
}