package syntaxtree.expressions;

import java.util.LinkedList;
import java.util.List;

public class NotExpression extends Expression {

	private Expression expression;

	public NotExpression(Expression expression) {
		this.expression = expression;
	}

	public Expression getExpression() {
		return expression;
	}

	@Override
	public List<String> makeAstPrint() {
		List<String> result = new LinkedList<String>();

		result.add("(NOT ");
		addInline(result, expression);
		appendStringToLastElement(result, ")");

		return result;
	}
}
