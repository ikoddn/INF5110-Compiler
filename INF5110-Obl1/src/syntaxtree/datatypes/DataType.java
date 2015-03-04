package syntaxtree.datatypes;

import syntaxtree.SimpleAstNode;

public abstract class DataType extends SimpleAstNode {

	protected String name;

	protected DataType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String makeAstString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(TYPE ");
		sb.append(name);
		sb.append(")");

		return sb.toString();
	}
}
