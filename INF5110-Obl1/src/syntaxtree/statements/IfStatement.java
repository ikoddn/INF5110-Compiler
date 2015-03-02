package syntaxtree.statements;

import java.util.LinkedList;
import java.util.List;

import syntaxtree.expressions.Expression;

public class IfStatement extends Statement {

	private Expression expression;
	private List<Statement> ifBodyStatements;
	private List<Statement> elseBodyStatements;

	public IfStatement(Expression expression, List<Statement> ifBodyStatements,
			List<Statement> elseBodyStatements) {
		this.expression = expression;
		this.ifBodyStatements = ifBodyStatements;
		this.elseBodyStatements = elseBodyStatements;
	}

	public Expression getExpression() {
		return expression;
	}

	public List<Statement> getIfBodyStatements() {
		return ifBodyStatements;
	}

	public List<Statement> getElseBodyStatements() {
		return elseBodyStatements;
	}

	@Override
	public List<String> makeAstPrint() {
		List<String> result = new LinkedList<String>();
		result.add("(IF_STMT ");
		addInline(result, expression);

		Statement[] array = new Statement[ifBodyStatements.size()];
		List<String> ifBody = makeParentAstPrint("",
				ifBodyStatements.toArray(array));
		result.addAll(prependAllWithIndentation(ifBody));

		if (!elseBodyStatements.isEmpty()) {
			result.add("ELSE");

			array = new Statement[elseBodyStatements.size()];
			List<String> elseBody = makeParentAstPrint("", array);
			result.addAll(prependAllWithIndentation(elseBody));
		}

		result.add(")");

		return result;
	}
}
