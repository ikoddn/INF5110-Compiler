package syntaxtree;

import java.util.List;

public class ClassDecl extends Decl {

	private List<VarDecl> varDecls;

	public ClassDecl(String name, List<VarDecl> varDecls) {
		super(name);
		this.varDecls = varDecls;
	}

	@Override
	public String createAstString(int indentations) {
		String indentationString = generateIndentation(indentations);
		
		StringBuilder sb = new StringBuilder();
		sb.append(indentationString);
		sb.append("(CLASS_DECL (NAME ");
		sb.append(name);
		sb.append(")");
		sb.append(NEWLINE);

		for (VarDecl varDecl : varDecls) {
			sb.append(varDecl.createAstString(indentations + 1));
			sb.append(NEWLINE);
		}

		sb.append(indentationString);
		sb.append(")");

		return sb.toString();
	}
}
