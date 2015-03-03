package syntaxtree.declarations;

import syntaxtree.AstNode;

public abstract class Decl extends AstNode {

	protected String name;

	protected Decl(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
