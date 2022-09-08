package mango123.JSON;
import java.io.*;

public class JSONTokener implements AutoCloseable {
	/**
	 * JSON symbols. They are returned in .nextToken()
	 */
	public static String COMMA = ",";
	public static String COLON = ":";
	public static String LEFT_BRACE = "{";
	public static String RIGHT_BRACE = "}";
	public static String LEFT_SQUARE = "[";
	public static String RIGHT_SQUARE = "]";
	public static String TRUE = "true";
	public static String FALSE = "false";
	public static String NULL = "null";

	private final Reader reader;

	/**
	 * Constructs a <code>JSONTokener</code> from a <code>Reader</code>.
	 * @param reader the input for characters
	 */
	public JSONTokener(Reader reader) {
		this.reader = reader instanceof BufferedReader ? reader : new BufferedReader(reader);
	}

	/**
	 * Constructs a <code>JSONTokener</code> from an <code>InputStream</code>.
	 * @param stream the input for characters
	 */
	public JSONTokener(InputStream stream) {
		this(new InputStreamReader(stream));
	}

	/**
	 * Constructs a <code>JSONTokener</code> from a <code>File</code>.
	 * @param file the input for characters
	 * @throws FileNotFoundException this exception will be thrown if the file is not found
	 */
	public JSONTokener(File file) throws FileNotFoundException {
		this(new FileReader(file));
	}

	
	/**
	 * indicate the end of input has been reached, used in .next(),
	 * .tryReadString(), .tryReadNumber(), and .nextToken()
	 */
	private boolean EOF = false;

	/**
	 * number of lines read so far, use in .newSyntaxError()
	 */
	private int numOfLines = 1;
	private int charOnLine = -1;

	/**
	 * Used when a character is read but it may not be used.
	 */
	private Character pushback;

	/**
	 * <p>Create and return a <code>JSONException</code> with a message detailing the syntax error.</p>
	 * <p>The message you passed into this method will be prepended with "JSON Syntax Error on 
	 * Line(insert line number): "</p>
	 * <p>e.g. "'}' is missing at the end -&gt; Syntax Error on Line 6: '}' is missing at the end"</p>
	 */
	public JSONException newSyntaxError(String message) {
		return new JSONException("Syntax error (line " + numOfLines + " char " + charOnLine + "): " + message);
	}
	
	//TODO: javadoc
	/**
	 * <p>Returns the next JSON token in a string from the input.</p>
	 * <p>If there are no more tokens to be read, <code>null</code> would be returned.</p>
	 * <p>This throws a <code>JSONException</code> if an unrecognized token is encountered
	 * or an <code>IOException</code> occurred. If an IOException occurred, the
	 * <code>JSONException</code> would contains the <code>IOException</code> as a cause,
	 * and you can use .getCause() to get the IOException.</p>
	 * <p>The String returned is either:
	 * <ul>
	 * <li>Variable: a string or number</li>
	 * <li>Constant: <code>true</code>, <code>false</code>, <code>{</code>, etc. see all the
	 * String constants provided by this class. They will be returned so == can be used for comparison
	 * instead of String.equal().</li>
	 * </ul></p>
	 * <p>Examples of string returned for each type of tokens:</p>
	 * @return a String containing the next token, or null if there are no more tokens
	 * @throws JSONException if an unrecognized/malformed token is encountered or an <code>IOException</code> occurred
	 */
	public String nextToken() throws JSONException {
		char ch = nextNonWhitespace();

		//end of input
		if (EOF) {
			return null;
		}
		
		//JSON string
		else if (ch == '\"') {
			return tryReadString();
		}

		//JSON number
		else if (isASCIIDigit(ch) || ch == '-') {
			return tryReadNumber(ch);
		}

		//JSON true
		else if (ch == 't') {
			return tryReadTrue();
		}

		//JSON false
		else if (ch == 'f') {
			return tryReadFalse();
		}
			
		//JSON null
		else if (ch == 'n') {
			return tryReadNull();
		}
			
		//JSON symbol, throw a JSONException if it's not a symbol
		else {
			return tryReadSymbol(ch);
		}
	}

	/**
	 * Closes the tokener.
	 * @throws IOException
	 */
	public void close() throws IOException {
		reader.close();
	}

	/**
	 * Gets the next character in the input.
	 * EOF will be set to true if there are no more characters.
	 */
	private char next() throws JSONException {
		try {
			int ch = reader.read();
			if (ch == -1) EOF = true;
			else          charOnLine++;
			return (char) ch;
		}
		catch(IOException e) {
			throw new JSONException(e);
		}
	}

	/**
	 * Returns the next non-whitespace char. Needs to check EOF in the methods that use this.
	 */
	private char nextNonWhitespace() throws JSONException {
		//get char from pushback if there it exists, else read it
		char ch;
		if (pushback != null) {
			ch = pushback;
			pushback = null;
		}
		else {
			ch = next();	
		}

		//skip all whitespace
		while (true) {
			if (isNewLine(ch)) {
				numOfLines++;
				charOnLine = -1;
			}
			else if (Character.isWhitespace(ch)) {} //don't do anything for whitespace
			else {
				return ch; //when EOF or valid character
			}
			ch = next();
		}
	}
	
	/**
	 * Try read a string (find the next valid and unescaped '"'),
	 * this will throw JSONException if a syntax error is found
	 */
	private String tryReadString() throws JSONException {
		StringBuilder str = new StringBuilder().append('"');

		char ch = next();
		while (!EOF) {
			if (ch == '\\') {
				str.append(tryReadEscape());
			}
			else if (ch == '"') {
				return str.append('"').toString();
			}
			else if (Character.isISOControl(ch)) {
				throw newSyntaxError("a JSON string cannot contain control character (e.g. \\t): " + ch);
			}
			else {
				str.append(ch);
			}
			ch = next();
		}

		//only reached if EOF (end of reader/stream) is reached
		throw newSyntaxError("missing closing '\"' -> " + str.toString());
	}

	/**
	 * Used in .tryReadString() to convert \t, \\, \u1234 etc. to corresponding char
	 */
	private char tryReadEscape() throws JSONException {
		char ch = next();
		switch (ch) {
		case '"':
		case '\\':
			case '/':
			return ch;
		case 'b':
			return '\b';
		case 'f':
			return '\f';
		case 'n':
			return '\n';
		case 'r':
			return '\r';
		case 't':
			return '\t';
		case 'u':
			char[] str = new char[4];
			for (int i = 0; i < 4; i++) {
				str[i] = next();
			}

			int num = 0;
			for (int i = 0; i < 4; i++) {
				char c = str[i];
				if (isASCIIDigit(c)) {
					num += (c - 48) << (12 - i * 4);
				}
				else if (c >= 'A' && c <= 'F') {
					num += (c - 55) << (12 - i * 4);
				}
				else {
					throw newSyntaxError("invalid unicode character '\\u" + new String(str) + "'");
				}
			}
			return (char) num;
		default:
			throw newSyntaxError("invalid escape sequence \\" + ch +
			      ", valid escape sequences are \\b\\f\\n\\r\\t\\/\\\"\\\\");
		}
	}
	
	/**
	 * Try read a number and will throw JSONException if a syntax error is found.
	 */
	private String tryReadNumber(char ch) throws JSONException {
		StringBuilder str = new StringBuilder().append(ch);

		//'-' should be followed by a digit
		if (ch == '-') {
			ch = next();
			if (!isASCIIDigit(ch)) {
				throw newSyntaxError("expects numeric character after '-', but it is not found");
			}
			str.append(ch);
		}
		
		//if digit is not a zero, we can go with more digits
		if (ch != '0') {
			ch = next();
			while (isASCIIDigit(ch)) {
				str.append(ch);
				ch = next();
			}
		}
		else {
			ch = next();
		}

		//if there is a dot, try read decimals
		if (ch == '.') {
			str.append('.');

			ch = next();
			if (!isASCIIDigit(ch)) {
				throw newSyntaxError("expects numeric character after '.', but it is not found");
			}

			while (isASCIIDigit(ch)) {
				str.append(ch);
				ch = next();
			}
		}

		//if there is a scientific notation, read it
		if (ch == 'e' || ch == 'E') {
			str.append(ch);

			//if there is a + or - sign, the next number should be a digit
			ch = next();
			if (ch == '+' || ch == '-') {
				str.append(ch);
				ch = next();
				if (!isASCIIDigit(ch)) {
					throw newSyntaxError("expects numeric character after '" + ch + 
					                     "', but it is not found");
				}
			}
			else if (!isASCIIDigit(ch)) {
				throw newSyntaxError("expects numeric character after '" + ch + 
				                     "', but it is not found");
			}

			while (isASCIIDigit(ch)) {
				str.append(ch);
				ch = next();
			}
		}

		//push back the extra character
		pushback = ch;
		return str.toString();
	}

	private String tryReadSymbol(char ch) throws JSONException {
		switch(ch) {
		case ':':
			return COLON;
		case ',':
			return COMMA;
		case '{':
			return LEFT_BRACE;
		case '}':
			return RIGHT_BRACE;
		case '[':
			return LEFT_SQUARE;
		case ']':
			return RIGHT_SQUARE;
		}
		throw newSyntaxError("Unrecognized symbol starting with '" + ch + "'");
	}
	
	/**
	 * try read a "true" and will throw JSONException if a syntax error is found
	 */
	private String tryReadTrue() throws JSONException {
		if (next() == 'r' && next() == 'u' && next() == 'e') {
			return TRUE;
		}
		throw newSyntaxError("Unrecognized symbol starting with 't'");
	}

	/**
	 * try read a "false" and will throw JSONException if a syntax error is found
	 */
	private String tryReadFalse() throws JSONException {
		if (next() == 'a' && next() == 'l' && next() == 's' && next() == 'e') {
			return FALSE;
		}
		throw newSyntaxError("Unrecognized symbol starting with 'f'");
	}

	/**
	 * try read a "null" and will throw JSONException if a syntax error is found
	 */
	private String tryReadNull() throws JSONException {
		if (next() == 'u' && next() == 'l' && next() == 'l') {
			return NULL;
		}
		throw newSyntaxError("Unrecognized symbol starting with 'n'");
	}


	/**
	 * determine whether a character is a new line -> see "http://en.wikipedia.org/wiki/Newline"
	 * and "http://download.oracle.com/javase/6/docs/api/java/lang/Character.html#isWhitespace(char)"
	 */
	private boolean isNewLine(char ch) throws JSONException {
		if (ch == '\r') {
			ch = next();
			if (ch == '\n') {
				return true;
			}
			else {
				pushback = ch;
			}
		}
		else if (ch == '\n' || ch == '\f' || ch == '\u000B'
		        || ch == '\u0085' || ch == '\u2028' || ch == '\u2029') {
			return  true;
		}
		return false;
	}

	/**
	 * Check whether a character is digit in the ASCII range
	 */
	private boolean isASCIIDigit(char ch) {
		return ch >= '0' && ch <= '9';
	}

	public static String escapeStr(String s) {
		StringBuilder b = new StringBuilder();
		int l = s.length();
		for (int i = 0; i < l; i++) {
			char ch = s.charAt(i);
			switch (ch) {
				case '\b':
					b.append("\\b");
					break;
				case '\f':
					b.append("\\f");
					break;
				case '\t':
					b.append("\\t");
					break;
				case '\n':
					b.append("\\n");
					break;
				case '\r':
					b.append("\\r");
					break;
				case '\\':
					b.append("\\\\");
					break;
				case '\"':
					b.append("\\\"");
					break;
				default:
					if (Character.isISOControl(ch)) {
						b.append("\\u00");
						b.append(Integer.toHexString(ch));
					}
					else {
						b.append(ch);
					}
			}
		}
		return b.toString();
	}
}
