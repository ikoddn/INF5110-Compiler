package compiler;

public enum ErrorMessage {
	DUPLICATE_DECLARATION("%s is declared multiple times"),
	FIELD_PRIMITIVE_TYPE(
			"Expression is not a class type and does not have a field %s"),
	INSTANTIATE_PRIMITIVE("Can't instantiate objects of type '%s'"),
	MISSING_MAIN("Missing main procedure"),
	NOT_A_CLASS_DECL("%s is not a class declaration as expected"),
	NOT_OPERATOR_UNDEFINED("The 'not' operator is undefined for %s"),
	UNALLOWED_TYPE_ARITHMETIC("Unallowed type in arithmetic expression"),
	UNALLOWED_TYPE_EQUALITY("Unallowed type in equality expression"),
	UNALLOWED_TYPE_LOGIC("Unallowed type in logical expression"),
	UNALLOWED_TYPE_RELATIONAL("Unallowed type in relational expression"),
	UNDECLARED_FIELD("%s is not a field of %s"),
	UNDECLARED_VARIABLE("%s has not been declared");

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
