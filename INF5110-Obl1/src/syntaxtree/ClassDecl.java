package syntaxtree;

import java.util.LinkedList;
import java.util.List;

public class ClassDecl extends Decl {

	private List<VariableDecl> variableDecls;

	public ClassDecl(String name, List<VariableDecl> variableDecls) {
		super(name);

		this.variableDecls = variableDecls;
	}

	public List<VariableDecl> getVariableDecls() {
		return variableDecls;
	}

	@Override
	public List<String> makeAstPrint() {
		List<String> result = new LinkedList<String>();

		StringBuilder sb = new StringBuilder();
		sb.append("(CLASS_DECL (NAME ");
		sb.append(name);
		sb.append(")");
		result.add(sb.toString());

		for (VariableDecl variableDecl : variableDecls) {
			result.addAll(prependWithIndentation(variableDecl.makeAstPrint()));
		}

		result.add(")");

		return result;
	}
}
