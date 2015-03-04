package syntaxtree;

public class Name extends SimpleAstNode {

	private String name;

	public Name(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String makeAstString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(NAME ");
		sb.append(name);
		sb.append(")");

		return sb.toString();
	}
}
