package syntaxtree.declarations;

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
		StringBuilder sb = new StringBuilder();
		sb.append("CLASS_DECL (NAME ");
		sb.append(name);
		sb.append(")");

		return makeParentAstPrint(sb.toString(), variableDecls);
	}
}
