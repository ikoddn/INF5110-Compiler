package syntaxtree.datatypes;

import syntaxtree.AstStringBuilder;
import syntaxtree.Name;
import syntaxtree.SimpleAstNode;

import compiler.SymbolTable;
import compiler.exception.SemanticException;

public class DataType extends SimpleAstNode {

	protected Name name;
	protected Type type;

	public DataType(Name name) {
		this.name = name;
		this.type = Type.CLASS;
	}

	public DataType(Type type) {
		this.name = type.getName();
		this.type = type;
	}

	public Name getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	@Override
	public DataType determineType(SymbolTable symbolTable)
			throws SemanticException {
		return this;
	}

	@Override
	public String makeAstString() {
		return new AstStringBuilder("TYPE").addInline(name.getString()).build();
	}
}
