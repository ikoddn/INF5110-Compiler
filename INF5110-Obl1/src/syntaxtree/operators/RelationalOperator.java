package syntaxtree.operators;

public enum RelationalOperator implements Operator {
	EQUAL("="),
	GREATER(">"),
	GREATER_EQUAL(">="),
	LESS("<"),
	LESS_EQUAL("<="),
	NOT_EQUAL("<>");

	private String symbol;

	private RelationalOperator(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String getSymbol() {
		return symbol;
	}

	@Override
	public String makeAstString() {
		return "REL_OP " + symbol;
	}

	@Override
	public String toString() {
		return symbol;
	}
}
