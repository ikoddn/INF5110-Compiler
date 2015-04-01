package syntaxtree.actualparameters;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.datatypes.DataType;
import syntaxtree.expressions.Expression;

import compiler.SymbolTable;
import compiler.exception.SemanticException;

public class PassByValueParameter extends ActualParameter {

	private Expression expression;

	public PassByValueParameter(Expression expression) {
		this.expression = expression;
	}

	public Expression getExpression() {
		return expression;
	}

	@Override
	public DataType determineType(SymbolTable symbolTable)
			throws SemanticException {
		return expression.determineType(symbolTable);
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("ACTUAL_PARAM").addInline(expression)
				.build();
	}
}
