package truthtablegenerator;

/**
 * An exception class used to pass error messages
 * 
 * @author Bryce, McAllister, Tyler
 */
public class ValidationException extends Exception {

	/**
	 * Creates a new instance of <code>NewException</code> without detail message.
	 */
	public ValidationException() {
	}

	/**
	 * Constructs an instance of <code>NewException</code> with the specified
	 * detail message.
	 *
	 * @param msg the detail message.
	 */
	public ValidationException(String msg) {
		super(msg);
	}
}
