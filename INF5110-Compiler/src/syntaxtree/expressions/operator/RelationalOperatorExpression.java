package syntaxtree.expressions.operator;

import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;
import syntaxtree.expressions.Expression;
import syntaxtree.operators.RelationalOperator;

import compiler.ErrorMessage;
import compiler.SymbolTable;
import compiler.exception.SemanticException;

public class RelationalOperatorExpression extends
		BinaryOperatorExpression<RelationalOperator> {

	public RelationalOperatorExpression(Expression leftExpression,
			RelationalOperator operator, Expression rightExpression) {
		super(leftExpression, operator, rightExpression);
	}

	@Override
	public void checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		Type leftType = leftExpression.determineType(symbolTable).getType();
		Type rightType = rightExpression.determineType(symbolTable).getType();

		if (!leftType.isA(rightType) || !rightType.isA(leftType)) {
			throw new SemanticException(ErrorMessage.UNALLOWED_TYPE_RELATIONAL);
		}

		switch (operator) {
		case EQUAL:
		case NOT_EQUAL:
			if (!isAllowedEquality(leftType) || !isAllowedEquality(rightType)) {
				throw new SemanticException(
						ErrorMessage.UNALLOWED_TYPE_EQUALITY);
			}
			break;
		case GREATER:
		case GREATER_EQUAL:
		case LESS:
		case LESS_EQUAL:
			if (!isAllowedRelational(leftType)
					|| !isAllowedRelational(rightType)) {
				throw new SemanticException(
						ErrorMessage.UNALLOWED_TYPE_RELATIONAL);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public DataType determineType(SymbolTable symbolTable)
			throws SemanticException {
		return new DataType(Type.BOOL);
	}

	private static boolean isAllowedEquality(Type type) {
		return type != Type.VOID;
	}

	private static boolean isAllowedRelational(Type type) {
		return type == Type.FLOAT || type == Type.INT;
	}
}
