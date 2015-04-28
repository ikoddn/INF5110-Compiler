package compiler.throwable;

import compiler.ErrorMessage;

public class CodeGenerationError extends Error {

	private static final long serialVersionUID = 1L;

	public CodeGenerationError(String message) {
		super(message);
	}

	public CodeGenerationError(ErrorMessage message) {
		super(message.getMessage());
	}
}
