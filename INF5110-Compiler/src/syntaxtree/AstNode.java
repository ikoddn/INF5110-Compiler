package syntaxtree;

import java.util.List;

import syntaxtree.datatypes.DataType;

import compiler.SymbolTable;
import compiler.exception.SemanticException;

public abstract class AstNode {

	private DataType type;

	protected AstNode() {
		type = null;
	}

	public void setTypeManually(DataType type) {
		this.type = type;
	}

	protected abstract DataType checkSemantics(SymbolTable symbolTable)
			throws SemanticException;

	public DataType checkSemanticsIfNecessary(SymbolTable symbolTable)
			throws SemanticException {
		if (type == null) {
			type = checkSemantics(symbolTable);
		}

		return type;
	}

	public abstract List<String> makeAstStringList();

}
