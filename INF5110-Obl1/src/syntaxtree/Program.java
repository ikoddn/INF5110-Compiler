package syntaxtree;

import java.util.List;

public class Program extends AstNode {

	private List<Decl> decls;

	public Program(List<Decl> decls) {
		this.decls = decls;
	}

	public String createAstString() {
		return createAstString(0);
	}

	@Override
	public String createAstString(int indentations) {
		String indentationString = generateIndentation(indentations);

		StringBuilder sb = new StringBuilder();
		sb.append(indentationString);
		sb.append("(PROGRAM\n");

		for (Decl decl : decls) {
			sb.append(decl.createAstString(indentations + 1));
			sb.append("\n");
		}

		sb.append(indentationString);
		sb.append(")");

		return sb.toString();
	}
}
