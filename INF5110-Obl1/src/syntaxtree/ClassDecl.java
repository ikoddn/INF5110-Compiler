package syntaxtree;

import java.util.List;

public class ClassDecl extends Decl {

	private List<VarDecl> varDecls;

	public ClassDecl(String name, List<VarDecl> varDecls) {
		super(name);
		this.varDecls = varDecls;
	}

	@Override
	public String getAstString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(CLASS_DECL (NAME ");
		sb.append(name);
		sb.append(")\n");

		if (varDecls != null) {
			for (VarDecl varDecl : varDecls) {
				if (varDecl != null) {
					sb.append(varDecl.getAstString());
					sb.append("\n");
				}
			}
		}

		sb.append(")");

		return sb.toString();
	}
}
