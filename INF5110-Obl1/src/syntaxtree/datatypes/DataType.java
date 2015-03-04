package syntaxtree.datatypes;

import syntaxtree.AstStringBuilder;
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
		return new AstStringBuilder("TYPE").addInline(name).build();
	}
}
