package syntaxtree;

import java.util.List;

import syntaxtree.declarations.Decl;
import bytecode.CodeFile;

public class Program extends AstNode {

	private List<Decl> decls;

	public Program(List<Decl> decls) {
		this.decls = decls;
	}

	public List<Decl> getDecls() {
		return decls;
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("PROGRAM").addIndented(decls).build();
	}

	public void generateCode(CodeFile codeFile) {
		// TODO Auto-generated method stub
	}
}
