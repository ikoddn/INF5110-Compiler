package syntaxtree.expressions;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;

import compiler.ErrorMessage;
import compiler.SymbolTable;
import compiler.exception.SemanticException;

public class NotExpression extends Expression {

	private Expression expression;

	public NotExpression(Expression expression) {
		this.expression = expression;
	}

	public Expression getExpression() {
		return expression;
	}

	@Override
	public void checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		expression.checkSemantics(symbolTable);

		DataType dataType = expression.determineType(symbolTable);

		if (dataType.getType() != Type.BOOL) {
			throw new SemanticException(ErrorMessage.NOT_OPERATOR_UNDEFINED,
					dataType.getName());
		}
	}

	@Override
	public DataType determineType(SymbolTable symbolTable)
			throws SemanticException {
		return new DataType(Type.BOOL);
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("NOT").addInline(expression).build();
	}
}
