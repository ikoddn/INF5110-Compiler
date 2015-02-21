package syntaxtree;

import java.util.List;

public class Program extends AstNode {

	private List<Decl> decls;

	public Program(List<Decl> decls) {
		this.decls = decls;
	}

	@Override
	public String getAstString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(PROGRAM\n");

		for (Decl decl : decls) {
			sb.append(decl.getAstString());
			sb.append("\n");
		}

		sb.append(")");

		return sb.toString();
	}
}
