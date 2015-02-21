package syntaxtree;

public abstract class Decl extends AstNode {

	protected String name;
	
	protected Decl(String name) {
		this.name = name;
	}
}
