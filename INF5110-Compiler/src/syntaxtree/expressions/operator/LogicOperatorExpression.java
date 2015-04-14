package syntaxtree.expressions.operator;

import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;
import syntaxtree.expressions.Expression;
import syntaxtree.operators.LogicOperator;
import bytecode.instructions.AND;
import bytecode.instructions.Instruction;
import bytecode.instructions.OR;

import compiler.ErrorMessage;
import compiler.SymbolTable;
import compiler.throwable.SemanticException;

public class LogicOperatorExpression extends
		BinaryOperatorExpression<LogicOperator> {

	public LogicOperatorExpression(Expression leftExpression,
			LogicOperator operator, Expression rightExpression) {
		super(leftExpression, operator, rightExpression);
	}

	@Override
	public DataType getDataType() {
		return new DataType(Type.BOOL);
	}

	@Override
	public void checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		leftExpression.checkSemantics(symbolTable);
		rightExpression.checkSemantics(symbolTable);

		DataType leftType = leftExpression.getDataType();
		DataType rightType = rightExpression.getDataType();

		if (!isAllowed(leftType) || !isAllowed(rightType)) {
			throw new SemanticException(ErrorMessage.UNALLOWED_TYPE_LOGIC);
		}
	}

	@Override
	protected Instruction getByteCodeInstruction() {
		if (operator == LogicOperator.AND) {
			return new AND();
		}

		return new OR();
	}

	private static boolean isAllowed(DataType type) {
		return type.getType() == Type.BOOL;
	}
}
