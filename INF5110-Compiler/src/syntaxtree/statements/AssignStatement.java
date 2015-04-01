package syntaxtree.statements;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;
import syntaxtree.expressions.Expression;
import syntaxtree.expressions.Variable;

import compiler.SymbolTable;
import compiler.exception.SemanticException;

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
	public DataType determineType(SymbolTable symbolTable)
			throws SemanticException {
		return new DataType(Type.VOID);
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("ASSIGN_STMT").addIndented(
				leftHandSide, rightHandSide).build();
	}
}
