package compiler.throwable;

public class CodeGenerationError extends Error {

	private static final long serialVersionUID = 1L;

	public CodeGenerationError(String message) {
		super(message);
	}
}
