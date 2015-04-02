package compiler;

public enum ErrorMessage {
	ASSIGN_INVALID_TYPE("Cannot convert from %s to %s"),
	DUPLICATE_DECLARATION("%s is declared multiple times"),
	FIELD_PRIMITIVE_TYPE("Expression is not a class type"
			+ " and does not have a field %s"),
	INSTANTIATE_PRIMITIVE("Can't instantiate objects of type '%s'"),
	MISSING_MAIN("Missing main procedure"),
	MISSING_RETURN("Procedure must return a result of type %s"),
	NON_BOOL_EXPRESSION("The expression is not a bool expression as expected"),
	NOT_MATCHING_SIGNATURE("The parameters does not match the signature of %s"),
	NOT_OPERATOR_UNDEFINED("The 'not' operator is undefined for %s"),
	UNALLOWED_TYPE_ARITHMETIC("Unallowed type in arithmetic expression"),
	UNALLOWED_TYPE_LOGIC("Unallowed type in logical expression"),
	UNALLOWED_TYPE_PARAM("Unallowed type in procedure parameter"),
	UNALLOWED_TYPE_PROCEDURE("Unallowed return type of procedure"),
	UNALLOWED_TYPE_RELATIONAL("Unallowed type in relational expression"),
	UNALLOWED_TYPE_RETURN(
			"Type in return statement does not match return type of procedure"),
	UNALLOWED_TYPE_VARIABLE("Unallowed type of variable"),
	UNDECLARED_PROCEDURE("The procedure %s has not been declared"),
	UNDECLARED_FIELD("%s is not a field of %s"),
	UNDECLARED_TYPE("The type %s has not been declared"),
	UNDECLARED_VARIABLE("The variable %s has not been declared");

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
