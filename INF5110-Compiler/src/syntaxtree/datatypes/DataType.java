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

	public boolean isA(DataType other) {
		if (equals(other)) {
			return true;
		}

		return type == Type.INT && other.type == Type.FLOAT;
	}

	@Override
	protected DataType checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		return this;
	}

	@Override
	public String makeAstString() {
		return new AstStringBuilder("TYPE").addInline(name.getString()).build();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataType other = (DataType) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
