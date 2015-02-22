package syntaxtree;

import java.util.Iterator;
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
		sb.append("(PROGRAM");
		sb.append(NEWLINE);
		
		Iterator<Decl> it = decls.iterator();
		while (it.hasNext()) {
			Decl decl = it.next();
			
			sb.append(decl.createAstString(indentations + 1));
			sb.append(NEWLINE);
			
			if (it.hasNext()) {
				sb.append(NEWLINE);
			}
		}

		sb.append(indentationString);
		sb.append(")");

		return sb.toString();
	}
}
