package syntaxtree.statements;

import java.util.LinkedList;
import java.util.List;

import syntaxtree.expressions.Expression;

public class WhileStatement extends Statement {

	private Expression expression;
	private List<Statement> statements;

	public WhileStatement(Expression expression, List<Statement> statements) {
		this.expression = expression;
		this.statements = statements;
	}

	public Expression getExpression() {
		return expression;
	}

	public List<Statement> getStatements() {
		return statements;
	}

	@Override
	public List<String> makeAstPrint() {
		List<String> result = new LinkedList<String>();
		result.add("(WHILE_STMT ");
		addInline(result, expression);

		Statement[] array = new Statement[statements.size()];
		List<String> body = makeParentAstPrint("", statements.toArray(array));
		result.addAll(prependAllWithIndentation(body));
		result.add(")");

		return result;
	}
}
