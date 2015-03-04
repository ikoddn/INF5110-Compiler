package syntaxtree.operators;

public enum LogicOperator implements Operator {
	AND("&&"),
	OR("||");

	private String symbol;

	private LogicOperator(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String getSymbol() {
		return symbol;
	}

	@Override
	public String makeAstLabel() {
		return "LOG_OP " + symbol;
	}
	
	@Override
	public String toString() {
		return symbol;
	}
}
