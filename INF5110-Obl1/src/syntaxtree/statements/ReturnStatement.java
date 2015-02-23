package syntaxtree.statements;

import syntaxtree.expressions.Expression;

public class ReturnStatement extends Statement {

	private Expression expression;
	
	public ReturnStatement() {
		this(null);
	}
	
	public ReturnStatement(Expression expression) {
		this.expression = expression;
	}
	
	@Override
	public String createAstString(int indentations) {
		StringBuilder sb = new StringBuilder();
		sb.append(generateIndentation(indentations));
		sb.append("(RETURN_STMT");
		
		if (expression != null) {
			sb.append(" (NAME ");
			sb.append("--fix this--"); // TODO
			sb.append(")");
		}
		
		sb.append(")");
		
		return sb.toString();
	}

}
