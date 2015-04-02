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
	protected DataType checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		DataType leftType = leftExpression
				.checkSemanticsIfNecessary(symbolTable);
		DataType rightType = rightExpression
				.checkSemanticsIfNecessary(symbolTable);

		if (!isAllowed(leftType) || !isAllowed(rightType)) {
			throw new SemanticException(ErrorMessage.UNALLOWED_TYPE_RELATIONAL);
		}

		if (!leftType.isA(rightType) && !rightType.isA(leftType)) {
			throw new SemanticException(ErrorMessage.UNALLOWED_TYPE_RELATIONAL);
		}

		return new DataType(Type.BOOL);
	}

	private boolean isAllowed(DataType type) {
		switch (operator) {
		case EQUAL:
		case NOT_EQUAL:
			return isAllowedInEquality(type);
		case GREATER:
		case GREATER_EQUAL:
		case LESS:
		case LESS_EQUAL:
			return isAllowedInRelational(type);
		default:
			return false;
		}
	}

	private static boolean isAllowedInEquality(DataType type) {
		return type.getType() != Type.VOID;
	}

	private static boolean isAllowedInRelational(DataType type) {
		return type.getType() == Type.FLOAT || type.getType() == Type.INT;
	}
}
