package syntaxtree.declarations;

import java.util.List;

import syntaxtree.Name;

public class ClassDecl extends Decl {

	private List<VariableDecl> variableDecls;

	public ClassDecl(Name name, List<VariableDecl> variableDecls) {
		super(name);

		this.variableDecls = variableDecls;
	}

	public List<VariableDecl> getVariableDecls() {
		return variableDecls;
	}

	@Override
	public List<String> makeAstPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append("CLASS_DECL ");
		sb.append(name.makeAstString());

		return makeParentAstPrint(sb.toString(), variableDecls);
	}
}
