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
		return makeAstStringWithInlineChild("TYPE", name);
	}
}
