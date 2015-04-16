package syntaxtree.expressions.operator;

import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;
import syntaxtree.expressions.Expression;
import syntaxtree.operators.LogicOperator;
import bytecode.CodeProcedure;
import bytecode.instructions.JMP;
import bytecode.instructions.JMPFALSE;
import bytecode.instructions.JMPTRUE;
import bytecode.instructions.NOP;
import bytecode.instructions.PUSHBOOL;

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
	public void generateCode(CodeProcedure procedure) {
		leftExpression.generateCode(procedure);
		int shortCircuit = procedure.addInstruction(new NOP());
		rightExpression.generateCode(procedure);
		int regularJump = procedure.addInstruction(new NOP());
		int pushBool;

		if (operator == LogicOperator.AND) {
			pushBool = procedure.addInstruction(new PUSHBOOL(false));
			procedure.replaceInstruction(shortCircuit, new JMPFALSE(pushBool));
		} else {
			pushBool = procedure.addInstruction(new PUSHBOOL(true));
			procedure.replaceInstruction(shortCircuit, new JMPTRUE(pushBool));
		}

		procedure.replaceInstruction(regularJump, new JMP(pushBool + 1));
	}

	private static boolean isAllowed(DataType type) {
		return type.getType() == Type.BOOL;
	}
}
