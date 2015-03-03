package syntaxtree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import syntaxtree.declarations.Decl;

public class Program extends AstNode {

	private List<Decl> decls;

	public Program(List<Decl> decls) {
		this.decls = decls;
	}

	public List<Decl> getDecls() {
		return decls;
	}

	@Override
	public List<String> makeAstPrint() {
		List<String> result = new LinkedList<String>();
		result.add("(PROGRAM");

		Iterator<Decl> it = decls.iterator();
		while (it.hasNext()) {
			Decl decl = it.next();

			result.addAll(prependAllWithIndentation(decl.makeAstPrint()));

			if (it.hasNext()) {
				result.add("");
			}
		}

		result.add(")");

		return result;
	}
}
