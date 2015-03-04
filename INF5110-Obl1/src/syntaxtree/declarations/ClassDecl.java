package syntaxtree.declarations;

import java.util.List;

import syntaxtree.AstStringListBuilder;
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
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("CLASS_DECL").addInline(name)
				.addIndented(variableDecls).build();
	}
}
