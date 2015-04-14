package syntaxtree.statements;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.datatypes.DataType;
import syntaxtree.expressions.Expression;
import syntaxtree.expressions.Variable;
import bytecode.CodeProcedure;

import compiler.ErrorMessage;
import compiler.SymbolTable;
import compiler.throwable.SemanticException;

public class AssignStatement extends Statement {

	private Variable leftHandSide;
	private Expression rightHandSide;

	public AssignStatement(Variable leftHandSide, Expression rightHandSide) {
		this.leftHandSide = leftHandSide;
		this.rightHandSide = rightHandSide;
	}

	public Variable getLeftHandSide() {
		return leftHandSide;
	}

	public Expression getRightHandSide() {
		return rightHandSide;
	}

	@Override
	public void checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		leftHandSide.checkSemantics(symbolTable);
		rightHandSide.checkSemantics(symbolTable);

		DataType leftType = leftHandSide.getDataType();
		DataType rightType = rightHandSide.getDataType();

		if (!rightType.isA(leftType)) {
			throw new SemanticException(ErrorMessage.ASSIGN_INVALID_TYPE,
					leftType.getName(), rightType.getName());
		}
	}

	@Override
	public void generateCode(CodeProcedure procedure) {
		rightHandSide.generateCode(procedure);
		leftHandSide.generateCodeForStore(procedure);
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("ASSIGN_STMT").addIndented(
				leftHandSide, rightHandSide).build();
	}
}
