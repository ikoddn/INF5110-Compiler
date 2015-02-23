package syntaxtree.statements;

import syntaxtree.expressions.Expression;
import syntaxtree.expressions.Variable;

public class AssignStatement extends Statement {

	private Variable leftHandSide;
	private Expression rightHandSide;
	
	public AssignStatement(Variable leftHandSide, Expression rightHandSide) {
		this.leftHandSide = leftHandSide;
		this.rightHandSide = rightHandSide;
	}
	
	@Override
	public String createAstString(int indentations) {
		String indentation = generateIndentation(indentations);
		
		StringBuilder sb = new StringBuilder();
		sb.append(indentation);
		sb.append("(ASSIGN_STMT");
		sb.append(NEWLINE);
		sb.append(leftHandSide.createAstString(indentations + 1));
		sb.append(NEWLINE);
		sb.append(rightHandSide.createAstString(indentations + 1));
		sb.append(NEWLINE);
		sb.append(indentation);
		sb.append(")");
		
		return sb.toString();
	}
}
