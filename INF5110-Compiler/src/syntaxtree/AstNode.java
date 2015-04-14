package syntaxtree;

import java.util.List;

import compiler.SymbolTable;
import compiler.throwable.SemanticException;

public abstract class AstNode {

	public abstract void checkSemantics(SymbolTable symbolTable)
			throws SemanticException;

	public abstract List<String> makeAstStringList();

}
