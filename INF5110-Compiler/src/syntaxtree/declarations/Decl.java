package syntaxtree.declarations;

import syntaxtree.AstNode;
import syntaxtree.Name;

import compiler.SymbolTable;
import compiler.exception.SemanticException;

public abstract class Decl extends AstNode {

	protected Name name;

	protected Decl(Name name) {
		this.name = name;
	}

	public Name getName() {
		return name;
	}

	public abstract void insertInto(SymbolTable symbolTable)
			throws SemanticException;
}
