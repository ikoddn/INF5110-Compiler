package compiler;

public enum ErrorMessage {
	DUPLICATE_DECLARATION("%s is declared multiple times"),
	FIELD_PRIMITIVE_TYPE(
			"Expression is not a class type and does not have a field %s"),
	INCOMPATIBLE_TYPES("The '%s' operator is not compatible between %s and %s"),
	MISSING_MAIN("Missing main procedure"),
	UNDECLARED_FIELD("%s is not a field of %s"),
	UNDECLARED_VARIABLE("%s has not been declared"),
	UNEXPECTED_NODETYPE("%s was not the expected type '%s'");

	private String message;

	private ErrorMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public String getMessage(Object... args) {
		return String.format(message, args);
	}
}
