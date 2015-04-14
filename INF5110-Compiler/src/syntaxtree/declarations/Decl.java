package syntaxtree.declarations;

import syntaxtree.AstNode;
import syntaxtree.Name;
import bytecode.CodeFile;
import bytecode.CodeProcedure;
import compiler.SymbolTable;
import compiler.throwable.SemanticException;

public abstract class Decl extends AstNode {

	protected Name name;

	protected Decl(Name name) {
		this.name = name;
	}

	public Name getName() {
		return name;
	}

	public abstract void generateCode(CodeFile codeFile);

	public abstract void generateCode(CodeProcedure procedure);

	public abstract void insertInto(SymbolTable symbolTable)
			throws SemanticException;
}
