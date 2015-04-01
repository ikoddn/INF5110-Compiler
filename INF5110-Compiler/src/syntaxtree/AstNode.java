package syntaxtree;

import java.util.List;

import syntaxtree.datatypes.DataType;

import compiler.SymbolTable;
import compiler.exception.SemanticException;

public abstract class AstNode {

	public void checkSemantics(SymbolTable parentSymbolTable)
			throws SemanticException {
		// TODO make abstract
		throw new SemanticException("Not implemented");
	}

	public DataType getType(SymbolTable symbolTable) throws SemanticException {
		// TODO make abstract
		throw new SemanticException("Not implemented");
	}

	public abstract List<String> makeAstStringList();

}
