package syntaxtree;

import java.util.List;

import syntaxtree.datatypes.DataType;
import compiler.SymbolTable;
import compiler.throwable.SemanticException;

public abstract class AstNode {

	private DataType type;

	protected AstNode() {
		type = null;
	}

	public final void setTypeManually(DataType type) {
		this.type = type;
	}

	public final DataType checkSemanticsIfNecessary(SymbolTable symbolTable)
			throws SemanticException {
		if (type == null) {
			type = checkSemantics(symbolTable);
		}

		return type;
	}

	protected abstract DataType checkSemantics(SymbolTable symbolTable)
			throws SemanticException;

	public abstract List<String> makeAstStringList();

}
