package compiler.exception;

import syntaxtree.Name;

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

	public SemanticException(ErrorMessage message, Name... args) {
		super(createMessage(message, args));
	}

	private static String createMessage(ErrorMessage message, Name... names) {
		Object[] args = new String[names.length];

		for (int i = 0; i < names.length; ++i) {
			args[i] = names[i].getString();
		}

		return String.format(message.getMessage(), args);
	}
}
