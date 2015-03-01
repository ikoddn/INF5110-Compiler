package syntaxtree.statements;

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
		return makeParentAstPrint("ASSIGN_STMT", leftHandSide, rightHandSide);
	}
}
