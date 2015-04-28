package syntaxtree.expressions;

import syntaxtree.AstNode;
import syntaxtree.datatypes.DataType;
import bytecode.CodeProcedure;

import compiler.JumpPlaceholder;

public abstract class Expression extends AstNode {

	public JumpPlaceholder generateBoolCode(CodeProcedure procedure) {
		generateCode(procedure);
		return null;
	}

	public abstract void generateCode(CodeProcedure procedure);

	public abstract DataType getDataType();
}
