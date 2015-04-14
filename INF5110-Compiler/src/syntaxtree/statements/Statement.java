package syntaxtree.statements;

import syntaxtree.AstNode;
import bytecode.CodeProcedure;

public abstract class Statement extends AstNode {

	public abstract void generateCode(CodeProcedure procedure);
}
