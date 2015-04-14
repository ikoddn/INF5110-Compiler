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
import compiler.throwable.SemanticException;

public class ArithmeticOperatorExpression extends
		BinaryOperatorExpression<ArithmeticOperator> {

	public ArithmeticOperatorExpression(Expression leftExpression,
			ArithmeticOperator operator, Expression rightExpression) {
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

	private static boolean isAllowed(Type type) {
		return type == Type.INT || type == Type.FLOAT;
	}
}
