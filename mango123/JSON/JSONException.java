package mango123.JSON;

/**
 * <p>A package-wide Exception for handling any JSONError such as syntax error.</p>
 * <p>Code are basically the same as <code>org.json.JSONException</code> from <a href="http://json.org/javadoc/org/json/JSONException.html">json.org</a>.</p>
 * @author JSON.org
 */
public class JSONException extends Exception {
	private static final long serialVersionUID = 0L;
	private Throwable cause;

	/**
	 * Constructs a JSONException with an explanatory <code>String</code> message.
	 * @param message Message contained inside the exception.
	 */
	public JSONException(String message) {
		super(message);
	}

	/**
	 * Constructs a JSONException with a cause.
	 * @param cause the cause of the JSONException
	 */
	public JSONException(Throwable cause) {
		super(cause.getMessage());
		this.cause = cause;
	}

	/**
	 * Gets the cause of this JSONException.
	 * @return the cause of this JSONException
	 */
	public Throwable getCause() {
		return this.cause;
	}
}
