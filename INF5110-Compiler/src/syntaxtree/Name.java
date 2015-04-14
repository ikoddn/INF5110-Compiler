package syntaxtree;

import syntaxtree.datatypes.DataType;
import compiler.SymbolTable;
import compiler.throwable.SemanticException;

public class Name extends SimpleAstNode {

	private String string;

	public Name(String string) {
		this.string = string;
	}

	public String getString() {
		return string;
	}

	@Override
	protected DataType checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public String makeAstString() {
		return new AstStringBuilder("NAME").addInline(string).build();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((string == null) ? 0 : string.hashCode());
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
		Name other = (Name) obj;
		if (string == null) {
			if (other.string != null)
				return false;
		} else if (!string.equals(other.string))
			return false;
		return true;
	}
}
