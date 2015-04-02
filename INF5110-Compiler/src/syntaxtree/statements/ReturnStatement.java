package syntaxtree.statements;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;
import syntaxtree.expressions.Expression;

import compiler.SymbolTable;
import compiler.exception.SemanticException;

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
	protected DataType checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		if (expression != null) {
			return expression.checkSemanticsIfNecessary(symbolTable);
		}

		return new DataType(Type.VOID);
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("RETURN_STMT").addInline(expression)
				.build();
	}
}
