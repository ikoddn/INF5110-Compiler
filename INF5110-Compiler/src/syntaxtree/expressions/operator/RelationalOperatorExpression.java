package syntaxtree.expressions.operator;

import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;
import syntaxtree.expressions.Expression;
import syntaxtree.operators.RelationalOperator;

import compiler.SymbolTable;
import compiler.exception.SemanticException;

public class RelationalOperatorExpression extends BinaryOperatorExpression {

	public RelationalOperatorExpression(Expression leftExpression,
			RelationalOperator operator, Expression rightExpression) {
		super(leftExpression, operator, rightExpression);
	}

	@Override
	public DataType getType(SymbolTable symbolTable) throws SemanticException {
		return new DataType(Type.BOOL);
	}
}
