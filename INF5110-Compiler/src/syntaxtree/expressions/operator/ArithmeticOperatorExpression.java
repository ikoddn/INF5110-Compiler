package syntaxtree.expressions.operator;

import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;
import syntaxtree.expressions.Expression;
import syntaxtree.operators.ArithmeticOperator;

import compiler.ErrorMessage;
import compiler.SymbolTable;
import compiler.exception.SemanticException;

public class ArithmeticOperatorExpression extends
		BinaryOperatorExpression<ArithmeticOperator> {

	public ArithmeticOperatorExpression(Expression leftExpression,
			ArithmeticOperator operator, Expression rightExpression) {
		super(leftExpression, operator, rightExpression);
	}

	@Override
	public void checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		Type leftType = leftExpression.determineType(symbolTable).getType();
		Type rightType = rightExpression.determineType(symbolTable).getType();

		if (!isAllowed(leftType) || !isAllowed(rightType)) {
			throw new SemanticException(ErrorMessage.UNALLOWED_TYPE_ARITHMETIC);
		}
	}

	@Override
	public DataType determineType(SymbolTable symbolTable)
			throws SemanticException {
		Type leftType = leftExpression.determineType(symbolTable).getType();
		Type rightType = rightExpression.determineType(symbolTable).getType();

		if (!isAllowed(leftType) || !isAllowed(rightType)) {
			throw new SemanticException(ErrorMessage.UNALLOWED_TYPE_ARITHMETIC);
		}

		if (operator == ArithmeticOperator.EXPONENTIATION) {
			return new DataType(Type.FLOAT);
		}

		if (leftType == Type.FLOAT || rightType == Type.FLOAT) {
			return new DataType(Type.FLOAT);
		}

		return new DataType(Type.INT);
	}

	private static boolean isAllowed(Type type) {
		return type == Type.INT || type == Type.FLOAT;
	}
}
