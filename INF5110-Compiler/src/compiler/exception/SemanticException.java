package compiler.exception;

import compiler.ErrorMessage;

public class SemanticException extends Exception {

	private static final long serialVersionUID = 1L;

	public SemanticException() {
		super();
	}

	public SemanticException(String message) {
		super(message);
	}

	public SemanticException(ErrorMessage message) {
		super(message.getMessage());
	}

	public SemanticException(ErrorMessage message, Object... args) {
		super(message.getMessage(args));
	}
}
