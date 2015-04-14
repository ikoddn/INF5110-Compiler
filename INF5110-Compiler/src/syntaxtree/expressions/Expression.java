package syntaxtree.expressions;

import syntaxtree.AstNode;
import bytecode.CodeProcedure;

public abstract class Expression extends AstNode {

	public abstract void generateCode(CodeProcedure procedure);
}
