package syntaxtree.statements;

import java.util.LinkedList;
import java.util.List;

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
	public List<String> makeAstPrint() {
		List<String> result = new LinkedList<String>();
		result.add("(ASSIGN_STMT");
		result.addAll(prependWithIndentation(leftHandSide.makeAstPrint()));
		result.addAll(prependWithIndentation(rightHandSide.makeAstPrint()));
		result.add(")");

		return result;
	}
}
