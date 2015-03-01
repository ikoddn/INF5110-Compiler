package oblig1parser;

public enum BinaryOperator {
	ASTERISK("*"),
	DOUBLE_AMP("&&"),
	DOUBLE_VBAR("||"),
	EQUAL("="),
	GREATER(">"),
	GREATER_EQUAL(">="),
	HASH("#"),
	LESS("<"),
	LESS_EQUAL("<="),
	LESS_GREATER("<>"),
	MINUS("-"),
	PLUS("+"),
	SLASH("/");

	private String name;

	private BinaryOperator(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
