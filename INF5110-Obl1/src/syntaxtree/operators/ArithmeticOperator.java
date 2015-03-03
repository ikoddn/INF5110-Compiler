package syntaxtree.operators;

public enum ArithmeticOperator implements Operator {
	ADDITION("+"),
	DIVISION("/"),
	EXPONENTIATION("#"),
	MULTIPLICATION("*"),
	SUBTRACTION("-");

	private String symbol;

	private ArithmeticOperator(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String getSymbol() {
		return symbol;
	}

	@Override
	public String makeAstString() {
		return "ARIT_OP " + symbol;
	}
	
	@Override
	public String toString() {
		return symbol;
	}
}
