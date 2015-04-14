package syntaxtree.expressions;

import syntaxtree.AstNode;
import syntaxtree.datatypes.DataType;
import bytecode.CodeProcedure;

public abstract class Expression extends AstNode {

	public abstract void generateCode(CodeProcedure procedure);

	public abstract DataType getDataType();
}
