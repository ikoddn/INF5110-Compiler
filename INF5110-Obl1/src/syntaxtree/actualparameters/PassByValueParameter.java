package syntaxtree.actualparameters;

import java.util.LinkedList;
import java.util.List;

import syntaxtree.expressions.Expression;

public class PassByValueParameter extends ActualParameter {

	private Expression expression;

	public PassByValueParameter(Expression expression) {
		this.expression = expression;
	}

	public Expression getExpression() {
		return expression;
	}

	@Override
	public List<String> makeAstPrint() {
		List<String> result = new LinkedList<String>();
		result.add("(ACTUAL_PARAM ");
		addInline(result, expression);
		appendStringToLastElement(result, ")");

		return result;
	}
}
