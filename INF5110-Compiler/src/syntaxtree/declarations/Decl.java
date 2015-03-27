package syntaxtree.declarations;

import syntaxtree.AstNode;
import syntaxtree.Name;

public abstract class Decl extends AstNode {

	protected Name name;

	protected Decl(Name name) {
		this.name = name;
	}

	public Name getName() {
		return name;
	}
}
