package compiler.throwable;

import compiler.ErrorMessage;

public class SemanticError extends Error {

	private static final long serialVersionUID = 1L;

	public SemanticError(ErrorMessage message) {
		super(message.getMessage());
	}
}
