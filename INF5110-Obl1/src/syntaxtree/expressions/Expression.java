package syntaxtree.expressions;

import syntaxtree.AstNode;

public abstract class Expression extends AstNode {

	protected String name;
	
	protected Expression(String name) {
		this.name = name;
	}
}
