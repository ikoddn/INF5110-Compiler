package syntaxtree.statements;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;
import syntaxtree.expressions.Expression;

import compiler.SymbolTable;
import compiler.exception.SemanticException;

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
	public DataType determineType(SymbolTable symbolTable)
			throws SemanticException {
		return new DataType(Type.VOID);
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("WHILE_STMT").addInline(expression)
				.addIndented(statements).build();
	}
}
