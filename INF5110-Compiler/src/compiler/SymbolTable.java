package compiler;

import java.util.HashMap;
import java.util.Map;

import syntaxtree.AstNode;
import syntaxtree.Name;

import compiler.exception.SemanticException;

public class SymbolTable {

	private Map<Name, AstNode> map;

	public SymbolTable() {
		map = new HashMap<Name, AstNode>();
	}

	public AstNode lookup(Name name) throws SemanticException {
		AstNode result = map.get(name);

		if (result == null) {
			throw new SemanticException(ErrorMessage.UNDECLARED_VARIABLE, name);
		}

		return result;
	}

	public void insert(Name name, AstNode astNode) throws SemanticException {
		if (map.containsKey(name)) {
			throw new SemanticException(ErrorMessage.DUPLICATE_DECLARATION,
					name);
		}

		map.put(name, astNode);
	}

	public SymbolTable makeCopy() {
		SymbolTable copy = new SymbolTable();
		copy.map.putAll(map);

		return copy;
	}
}
