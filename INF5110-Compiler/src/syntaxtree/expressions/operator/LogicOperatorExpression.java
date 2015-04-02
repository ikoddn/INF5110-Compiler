package syntaxtree.expressions.operator;

import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;
import syntaxtree.expressions.Expression;
import syntaxtree.operators.LogicOperator;

import compiler.ErrorMessage;
import compiler.SymbolTable;
import compiler.exception.SemanticException;

public class LogicOperatorExpression extends
		BinaryOperatorExpression<LogicOperator> {

	public LogicOperatorExpression(Expression leftExpression,
			LogicOperator operator, Expression rightExpression) {
		super(leftExpression, operator, rightExpression);
	}

	@Override
	protected DataType checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		Type leftType = leftExpression.checkSemanticsIfNecessary(symbolTable)
				.getType();
		Type rightType = rightExpression.checkSemanticsIfNecessary(symbolTable)
				.getType();

		if (!isAllowed(leftType) || !isAllowed(rightType)) {
			throw new SemanticException(ErrorMessage.UNALLOWED_TYPE_LOGIC);
		}

		return new DataType(Type.BOOL);
	}

	private static boolean isAllowed(Type type) {
		return type == Type.BOOL;
	}
}
