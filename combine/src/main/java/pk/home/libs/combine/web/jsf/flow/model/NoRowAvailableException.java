package pk.home.libs.combine.web.jsf.flow.model;

/**
 * Custom exception to be thrown by the default <code>DataModel</code>
 * implementations. This exception extends IllegalArgumentException and is
 * package private so the TCK will continue to pass, but the exception type is
 * more descriptive.
 */
class NoRowAvailableException extends IllegalArgumentException {

	private static final long serialVersionUID = 3794135774633215459L;

	// ------------------------------------------------------------ Constructors

	public NoRowAvailableException() {
		super();
	}

	public NoRowAvailableException(String s) {
		super(s);
	}

	public NoRowAvailableException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoRowAvailableException(Throwable cause) {
		super(cause);
	}

}
