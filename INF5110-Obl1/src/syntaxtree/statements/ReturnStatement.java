package syntaxtree.statements;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import syntaxtree.expressions.Expression;

public class ReturnStatement extends Statement {

	private Expression expression;

	public ReturnStatement() {
		this(null);
	}

	public ReturnStatement(Expression expression) {
		this.expression = expression;
	}

	public Expression getExpression() {
		return expression;
	}

	@Override
	public List<String> makeAstPrint() {
		List<String> result;

		if (expression == null) {
			result = Arrays.asList("(RETURN_STMT)");
		} else {
			result = new LinkedList<String>();
			result.add("(RETURN_STMT ");
			addInline(result, expression);
			appendStringToLastElement(result, ")");
		}

		return result;
	}

}
