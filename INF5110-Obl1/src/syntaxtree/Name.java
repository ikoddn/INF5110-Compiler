package syntaxtree;

public class Name extends SimpleAstNode {

	private String string;

	public Name(String string) {
		this.string = string;
	}

	public String getString() {
		return string;
	}

	@Override
	public String makeAstString() {
		return makeAstStringWithInlineChild("NAME", string);
	}
}
