package syntaxtree.expressions.operator;

import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;
import syntaxtree.expressions.Expression;
import syntaxtree.operators.LogicOperator;

import compiler.SymbolTable;
import compiler.exception.SemanticException;

public class LogicOperatorExpression extends BinaryOperatorExpression {

	public LogicOperatorExpression(Expression leftExpression,
			LogicOperator operator, Expression rightExpression) {
		super(leftExpression, operator, rightExpression);
	}

	@Override
	public DataType getType(SymbolTable symbolTable) throws SemanticException {
		return new DataType(Type.BOOL);
	}
}
