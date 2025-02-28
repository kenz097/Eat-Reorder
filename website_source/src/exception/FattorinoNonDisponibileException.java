package exception;

public class FattorinoNonDisponibileException extends Exception {

	/**
	 * Eccezione lanciata quando non vi sono fattorini disponibili alla consegna
	 */
	private static final long serialVersionUID = 1L;

	public FattorinoNonDisponibileException() {
		// TODO Auto-generated constructor stub
	}

	public FattorinoNonDisponibileException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public FattorinoNonDisponibileException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public FattorinoNonDisponibileException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public FattorinoNonDisponibileException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
