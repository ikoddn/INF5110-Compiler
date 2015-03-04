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
		StringBuilder sb = new StringBuilder();
		sb.append("(NAME ");
		sb.append(string);
		sb.append(")");

		return sb.toString();
	}
}
