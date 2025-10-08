package gov.fdic.tip.exception;

/**
 * @author Prasad Ravva
 * @Project TIP
 * @Module Review Cycle Group
 * @Date 9/28/2025
 * Custom exception class for validation errors.
 */

public class ValidationException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}

