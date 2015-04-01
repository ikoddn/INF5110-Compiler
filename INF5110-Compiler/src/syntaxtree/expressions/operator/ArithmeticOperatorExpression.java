package syntaxtree.expressions.operator;

import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;
import syntaxtree.expressions.Expression;
import syntaxtree.operators.ArithmeticOperator;

import compiler.ErrorMessage;
import compiler.SymbolTable;
import compiler.exception.SemanticException;

public class ArithmeticOperatorExpression extends BinaryOperatorExpression {

	public ArithmeticOperatorExpression(Expression leftExpression,
			ArithmeticOperator operator, Expression rightExpression) {
		super(leftExpression, operator, rightExpression);
	}

	@Override
	public DataType determineType(SymbolTable symbolTable) throws SemanticException {
		DataType leftType = leftExpression.determineType(symbolTable);
		DataType rightType = rightExpression.determineType(symbolTable);

		switch (leftType.getType()) {
		case FLOAT:
			switch (rightType.getType()) {
			case FLOAT:
			case INT:
				return new DataType(Type.FLOAT);
			default:
				break;
			}
		case INT:
			switch (rightType.getType()) {
			case FLOAT:
				return new DataType(Type.FLOAT);
			case INT:
				return new DataType(Type.INT);
			default:
				break;
			}
		default:
			break;
		}

		throw new SemanticException(ErrorMessage.INCOMPATIBLE_TYPES,
				operator.getSymbol(), leftType.getName().getString(), rightType
						.getName().getString());
	}
}
