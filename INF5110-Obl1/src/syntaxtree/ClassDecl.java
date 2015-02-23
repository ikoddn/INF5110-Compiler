package syntaxtree;

import java.util.LinkedList;
import java.util.List;

public class ClassDecl extends Decl {

	private List<VarDecl> varDecls;

	public ClassDecl(String name, List<VarDecl> varDecls) {
		super(name);
		this.varDecls = varDecls;
	}

	@Override
	public List<String> makeAstPrint() {
		List<String> result = new LinkedList<String>();

		StringBuilder sb = new StringBuilder();
		sb.append("(CLASS_DECL (NAME ");
		sb.append(name);
		sb.append(")");
		result.add(sb.toString());

		for (VarDecl varDecl : varDecls) {
			result.addAll(prependWithIndentation(varDecl.makeAstPrint()));
		}

		result.add(")");

		return result;
	}
}
