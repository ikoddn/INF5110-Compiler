package oblig1parser;

import java.util.Arrays;
import java.util.List;

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
	
	public static List<BinaryOperator> getArithmetic() {
		return Arrays.asList(ASTERISK, HASH, MINUS, PLUS, SLASH);
	}
	
	public static List<BinaryOperator> getLogic() {
		return Arrays.asList(DOUBLE_AMP, DOUBLE_VBAR);
	}
	
	public static List<BinaryOperator> getRelational() {
		return Arrays.asList(EQUAL, GREATER, GREATER_EQUAL, LESS, LESS_EQUAL, LESS_GREATER);
	}
}
