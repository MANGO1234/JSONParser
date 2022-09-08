package mango123.JSON;

import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>This class represents a JSON array. It cannot be constructed directly. Instead, use the static
 * {@code JSONArray.parse(JSONTokener)} or {@code JSONArray.newEmptyInstance()} to get an instance
 * of JSONArray.</p>
 * <p>e.g. a JSONTokener containing the input "{@code [1, true, "d", [1], {"a": 1}]" can be passed
 * into {@code .parse()} to get an instance of JSONArray containing the above elements.</p>
 * @version 0.8
 * @see JSONObject
 */
public class JSONArray {
	private List<Object> list;
	
	/**
	 * Prevent direct instantiation. Must use .parse() and .newEmptyInstance()
	 * Package-access to allow JSONParse to access this
	 */
	JSONArray(List<Object> list) {
		this.list = list;
	}

	/**
	 * <p>Creates and returns an empty instance of {@code JSONArray}.</p>
	 * @return an empty instance of JSONArray
	 */
	public static JSONArray newEmptyInstance() {
		return new JSONArray(new ArrayList<Object>());
	}

	/**
	 * Parses and constructs a <code>JSONArray</code> from a <code>JSONTokener</code>.
	 * @param tokener the JSON input
	 * @return a <code>JSONArray</code> that represents the JSON array
	 * @throws JSONException any error that may have occurred during parsing
	 */
	public static JSONArray parse(JSONTokener tokener) throws JSONException {
		//parseArrayFrom() requires the '[' to be read
		if (tokener.nextToken() != JSONTokener.LEFT_SQUARE) {
			throw tokener.newSyntaxError("JSON array needs to start with '[");
		}

		JSONArray array = new JSONArray(JSONParse.parseArray(tokener));

		//if that's not the end of the JSON input -> syntax error
		if (tokener.nextToken() != null) {
			throw tokener.newSyntaxError("JSON array needs to end with ']'");
		}

		return array;
	}

	/**
	 * Returns the length of the JSONArray
	 * @return the length of the JSONArray
	 */
	public int length() {
		return list.size();
	}

	/**
	 * Returns the length of the JSONArray
	 * @return the length of the JSONArray
	 */
	public int size() {
		return list.size();
	}


	/**
	 * Returns <code>true</code> if this array contains no elements.
	 * @return <code>true</code> if this array contains no elements
	 */
	public boolean isEmpty() {
		return list.isEmpty();
	}

	/**
	 * <p>Returns the <code>Object</code> at the specified index in the array, or <code>null</code>
	 * if the element is <code>null</code>.</p>
	 * @param index the array index
	 * @return the <code>Object</code> at the specified index, or null if the elements is null
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index >= size()</code>)
	 */
	public Object get(int index) {
		return list.get(index);
	}

	/**
	 * <p>Returns the <code>String</code> at the specified index in the array, or <code>null</code>
	 * if the element is <code>null</code>.</p>
	 * @param index the array index
	 * @return the <code>String</code> at the specified index, or null if it's not a String
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index >= size()</code>)
	 */
	public Object getString(int index) {
		Object ob = list.get(index);
		return (ob instanceof String) ? (String) ob : null;
	}

	/**
	 * <p>Returns the <code>Integer</code> at the specified index in the array, or <code>null</code>
	 * if the element is not an<code>Integer</code>.</p>
	 * @param index the array index
	 * @return the <code>Integer</code> at the specified index, or null if it's not an Integer
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index >= size()</code>)
	 */
	public Integer getInt(int index) {
		Object ob = list.get(index);
		return (ob instanceof Integer) ? (Integer) ob : null;
	}

	/**
	 * <p>Returns the <code>Long</code> at the specified index in the array, or <code>null</code>
	 * if the element is not an<code>Long</code>.</p>
	 * @param index the array index
	 * @return the <code>Long</code> at the specified index, or null if it's not a Long
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index >= size()</code>)
	 */
	public Long getLong(int index) {
		Object ob = list.get(index);
		return (ob instanceof Long) ? (Long) ob : null;
	}
	
	/**
	 * <p>Returns the <code>BigInteger</code> at the specified index in the array, or <code>null</code>
	 * if the element is not an<code>BigIntegr</code>.</p>
	 * @param index the array index
	 * @return the <code>BigInteger</code> at the specified index, or null if it's not a BigInteger
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index >= size()</code>)
	 */
	public BigInteger getBigInt(int index) {
		Object ob = list.get(index);
		return (ob instanceof BigInteger) ? (BigInteger) ob : null;
	}
	
	/**
	 * <p>Returns the <code>Double</code> at the specified index in the array, or <code>null</code>
	 * if the element is not an<code>Double</code>.</p>
	 * @param index the array index
	 * @return the <code>Double</code> at the specified index, or null if it's not a Double
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index >= size()</code>)
	 */
	public Double getDouble(int index) {
		Object ob = list.get(index);
		return (ob instanceof Double) ? (Double) ob : null;
	}
	
	/**
	 * <p>Returns the <code>BigDecimal</code> at the specified index in the array, or <code>null</code>
	 * if the element is not an<code>BigDecimal</code>.</p>
	 * @param index the array index
	 * @return the <code>BigDecimal</code> at the specified index, or null if it's not a BigDecimal
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index >= size()</code>)
	 */
	public BigDecimal getDecimal(int index) {
		Object ob = list.get(index);
		return (ob instanceof BigDecimal) ? (BigDecimal) ob : null;
	}
	
	/**
	 * <p>Returns the <code>Boolean</code> at the specified index in the array, or <code>null</code>
	 * if the element is not an<code>Boolean</code>.</p>
	 * @param index the array index
	 * @return the <code>Boolean</code> at the specified index, or null it's not a boolean
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index >= size()</code>)
	 */
	public Boolean getBool(int index) {
		Object ob = list.get(index);
		return (ob instanceof Boolean) ? (Boolean) ob : null;
	}
	
	/**
	 * <p>Returns the <code>JSONArray</code> at the specified index in the array, or <code>null</code>
	 * if the element is not an<code>JSONArray</code>.</p>
	 * @param index the array index
	 * @return the <code>JSONArray</code> at the specified index, or null  if it's not a JSONArray
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index >= size()</code>)
	 */
	public JSONArray getArray(int index) {
		Object ob = list.get(index);
		return (ob instanceof JSONArray) ? (JSONArray) ob : null;
	}
	
	/**
	 * <p>Returns the <code>JSONObject</code> at the specified index in the array, or <code>null</code>
	 * if the element is not an<code>JSONObject</code>.</p>
	 * @param index the array index
	 * @return the <code>JSONObject</code> at the specified index, or null if it's not a JSONObject
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index >= size()</code>)
	 */
	public JSONObject getObject(int index) {
		Object ob = list.get(index);
		return (ob instanceof JSONObject) ? (JSONObject) ob : null;
	}
	
	/**
	 * <p>Return <code>true</code> if the element at the specified index is <code>null<code>.</p>
	 * @param index the array index
	 * @return <code>true</code> if the specified element is <code>null</code>
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index >= size()</code>)
	 */
	public boolean isNull(int index) {
		return list.get(index) == null;
	}

	/**
	 * <p>Sets the element at the specified index to the specified <code>String</code>.</p>
	 * @param index the array index
	 * @param value the <code>String</code> to be set
	 * @return a reference to this object
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index >= size()</code>)
	 */
	public JSONArray set(int index, String value) {
		list.set(index, value);
		return this;
	}

	/**
	 * <p>Sets the element at the specified index to the specified <code>Integer</code>.</p>
	 * @param index the array index
	 * @param value the <code>Integer</code> to be set
	 * @return a reference to this object
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index >= size()</code>)
	 */
	public JSONArray set(int index, Integer value) {
		list.set(index, value);
		return this;
	}
	
	/**
	 * <p>Sets the element at the specified index to the specified <code>Long</code>.</p>
	 * @param index the array index
	 * @param value the <code>Long</code> to be set
	 * @return a reference to this object
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index >= size()</code>)
	 */
	public JSONArray set(int index, Long value) {
		list.set(index, value);
		return this;
	}
	
	/**
	 * <p>Sets the element at the specified index to the specified <code>Double</code>.</p>
	 * @param index the array index
	 * @param value the <code>Double</code> to be set
	 * @return a reference to this object
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index >= size()</code>)
	 */
	public JSONArray set(int index, Double value) {
		list.set(index, value);
		return this;
	}
	
	/**
	 * <p>Sets the element at the specified index to the specified <code>BigInteger</code>.</p>
	 * @param index the array index
	 * @param value the <code>BigInteger</code> to be set
	 * @return a reference to this object
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index >= size()</code>)
	 */
	public JSONArray set(int index, BigInteger value) {
		list.set(index, value);
		return this;
	}
	
	/**
	 * <p>Sets the element at the specified index to the specified <code>BigDecimal</code>.</p>
	 * @param index the array index
	 * @param value the <code>BigDecimal</code> to be set
	 * @return a reference to this object
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index >= size()</code>)
	 */
	public JSONArray set(int index, BigDecimal value) {
		list.set(index, value);
		return this;
	}
	
	/**
	 * <p>Sets the element at the specified index to the specified <code>Boolean</code>.</p>
	 * @param index the array index
	 * @param value the <code>Boolean</code> to be set
	 * @return a reference to this object
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index >= size()</code>)
	 */
	public JSONArray set(int index, Boolean value) {
		list.set(index, value);
		return this;
	}
	
	/**
	 * <p>Sets the element at the specified index to the specified <code>JSONObject</code>.</p>
	 * @param index the array index
	 * @param value the <code>JSONObject</code> to be set
	 * @return a reference to this object
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index >= size()</code>)
	 */
	public JSONArray set(int index, JSONObject value) {
		list.set(index, value);
		return this;
	}

	/**
	 * <p>Sets the element at the specified index to the specified <code>JSONArray</code>.</p>
	 * @param index the array index
	 * @param value the <code>JSONArray</code> to be set
	 * @return a reference to this object
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index >= size()</code>)
	 */
	public JSONArray set(int index, JSONArray value) {
		list.set(index, value);
		return this;
	}
	
	/**
	 * <p>Set the element at an index to <code>null</code>.</p>
	 * @param index the array index
	 * @return a reference to this object
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index >= size()</code>)
	 */
	public JSONArray setNull(int index) {
		list.set(index, null);
		return this;
	}

	/**
	 * <p>Inserts the specified <code>String</code> at the specified index.</p>
	 * @param index the array index
	 * @param value the <code>String</code> to be inserted
	 * @return a reference to this object
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index > size()</code>)
	 */
	public JSONArray add(int index, String value) {
		list.add(index, value);
		return this;
	}
	

	/**
	 * <p>Inserts the specified <code>Integer</code> at the specified index.</p>
	 * @param index the array index
	 * @param value the <code>Integer</code> to be inserted
	 * @return a reference to this object
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index > size()</code>)
	 */
	public JSONArray add(int index, Integer value) {
		list.add(index, value);
		return this;
	}
	
	/**
	 * <p>Inserts the specified <code>Long</code> at the specified index.</p>
	 * @param index the array index
	 * @param value the <code>Long</code> to be inserted
	 * @return a reference to this object
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index > size()</code>)
	 */
	public JSONArray add(int index, Long value) {
		list.add(index, value);
		return this;
	}
	

	/**
	 * <p>Inserts the specified <code>Double</code> at the specified index.</p>
	 * @param index the array index
	 * @param value the <code>Double</code> to be inserted
	 * @return a reference to this object
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index > size()</code>)
	 */
	public JSONArray add(int index, Double value) {
		list.add(index, value);
		return this;
	}
	

	/**
	 * <p>Inserts the specified <code>BigInteger</code> at the specified index.</p>
	 * <p>This method only accepts an instance of <code>BigInteger</code> and not its subclasses as
	 * the value. It will throw an <code>IllegalArgumentException</code> if you try to pass an
	 * instance of <code>BigInteger</code>'s subclass as value.</p>
	 * @param index the array index
	 * @param value the <code>BigInteger</code> to be inserted
	 * @return a reference to this object
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index > size()</code>)
	 * @throw IllegalArgumentException if an instance of a subclass of BigInteger is passed as the value
	 */
	public JSONArray add(int index, BigInteger value) {
		list.add(index, value);
		return this;
	}
	

	/**
	 * <p>Inserts the specified <code>BigDecimal</code> at the specified index.</p>
	 * <p>This method only accepts an instance of Bigdecimal and not its subclasses as the value. It
	 * will throw an IllegalArgumentException if you try to pass an instance of BigDecimal's subclass
	 * as value.</p>
	 * @param index the array index
	 * @param value the <code>BigDecimal</code> to be inserted
	 * @return a reference to this object
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index > size()</code>)
	 * @throw IllegalArgumentException if an instance of a subclass of BigDecimal is passed as the value
	 */
	public JSONArray add(int index, BigDecimal value) {
		list.add(index, value);
		return this;
	}

	/**
	 * <p>Inserts the specified <code>Boolean</code> at the specified index.</p>
	 * @param index the array index
	 * @param value the <code>Boolean</code> to be inserted
	 * @return a reference to this object
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index > size()</code>)
	 */
	public JSONArray add(int index, Boolean value) {
		list.add(index, value);
		return this;
	}
	
	/**
	 * <p>Inserts the specified <code>JSONObject</code> at the specified index.</p>
	 * @param index the array index
	 * @param value the <code>JSONObject</code> to be inserted
	 * @return a reference to this object
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index > size()</code>)
	 */
	public JSONArray add(int index, JSONObject value) {
		list.add(index, value);
		return this;
	}

	/**
	 * <p>Inserts the specified <code>JSONArray</code> at the specified index.</p>
	 * @param index the array index
	 * @param value the <code>JSONArray</code> to be inserted
	 * @return a reference to this object
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index > size()</code>)
	 */
	public JSONArray add(int index, JSONArray value) {
		list.add(index, value);
		return this;
	}

	/**
	 * <p>Inserts <code>null</code> at the specified index.</p>
	 * @param index the array index
	 * @return a reference to this object
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index > size()</code>)
	 */
	public JSONArray addNull(int index) {
		list.add(index, null);
		return this;
	}

	/**
	 * <p>Removes all elements from this list.</p>
	 * @return a reference to this object
	 */
	public JSONArray clear() {
		list.clear();
		return this;
	}

	/**
	 * <p>Remove the element at the specified index and shift the subsequent elements to the left.</p>
	 * @param index the array index
	 * @return a reference to this object
	 * @throw ArrayIndexOutOfBoundsException if the index is out of range (<code>index < 0 ||
	 *        index >= size()</code>)
	 */
	public JSONArray remove(int index) {
		list.remove(index);
		return this;
	}

	/**
	 * <p>Returns an iterator over the elements of this JSONArray in proper sequence.</p>
	 * <p>The iterator supports the <code>.remove()</code> method defined by the
	 * <code>Iterator</code> interface.</p>
	 * <p>This iterator can throw a <code>ConcurrentModificationException</code> if a runtime 
	 * concurrent modification is encountered.</p>
	 * @return an iterator of this array
	 */
	public Iterator<Object> iterator() {
		return list.iterator();
	}

	/**
	 * <p>Returns an array containing all of the elements in this JSONArray.</p>
	 * @return an array verson of JSONArray
	 */
	public Object[] toArray() {
		return list.toArray();
	}
	
	/**
	 * <p>Returns the string representation of the JSONArray in JSON.</p>
	 * <p>The format will be the most compact (no whitespaces) and strictly conform to JSON.</p>
	 * <p>Example: [true,12,"a"]</p>
	 * @return String JSON representation of the JSONArray
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder().append('[');

		int size = list.size(), i = 0;
		for (Object ob : list) {
			if (ob instanceof String) {
				str.append('"')
						.append(JSONTokener.escapeStr((String) ob))
						.append('"');
			}
			else {
				str.append(ob);
			}

			if (++i < size) {
				str.append(',');
			}
		}
		str.append(']');
		return str.toString();
	}
}