package syntaxtree.expressions.operator;

import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;
import syntaxtree.expressions.Expression;
import syntaxtree.operators.ArithmeticOperator;
import bytecode.instructions.ADD;
import bytecode.instructions.DIV;
import bytecode.instructions.EXP;
import bytecode.instructions.Instruction;
import bytecode.instructions.MUL;
import bytecode.instructions.SUB;

import compiler.ErrorMessage;
import compiler.SymbolTable;
import compiler.throwable.SemanticError;
import compiler.throwable.SemanticException;

public class ArithmeticOperatorExpression extends
		BinaryOperatorExpression<ArithmeticOperator> {

	private DataType dataType;

	public ArithmeticOperatorExpression(Expression leftExpression,
			ArithmeticOperator operator, Expression rightExpression) {
		super(leftExpression, operator, rightExpression);
		dataType = null;
	}

	@Override
	public DataType getDataType() {
		if (dataType == null) {
			throw new SemanticError(ErrorMessage.UNDETERMINED_TYPE);
		}

		return dataType;
	}

	@Override
	public void checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		leftExpression.checkSemantics(symbolTable);
		rightExpression.checkSemantics(symbolTable);

		DataType leftType = leftExpression.getDataType();
		DataType rightType = rightExpression.getDataType();

		if (!isAllowed(leftType) || !isAllowed(rightType)) {
			throw new SemanticException(ErrorMessage.UNALLOWED_TYPE_ARITHMETIC);
		}

		if (operator == ArithmeticOperator.EXPONENTIATION
				|| leftType.getType() == Type.FLOAT
				|| rightType.getType() == Type.FLOAT) {
			dataType = new DataType(Type.FLOAT);
		} else {
			dataType = new DataType(Type.INT);
		}
	}

	@Override
	protected Instruction getByteCodeInstruction() {
		switch (operator) {
		case ADDITION:
			return new ADD();
		case DIVISION:
			return new DIV();
		case EXPONENTIATION:
			return new EXP();
		case MULTIPLICATION:
			return new MUL();
		case SUBTRACTION:
			return new SUB();
		default:
			return null;
		}
	}

	private static boolean isAllowed(DataType dataType) {
		Type type = dataType.getType();
		return type == Type.INT || type == Type.FLOAT;
	}
}
