package syntaxtree;

import java.util.List;

import syntaxtree.datatypes.VoidType;
import syntaxtree.declarations.Decl;
import syntaxtree.declarations.ProcedureDecl;
import bytecode.CodeFile;
import compiler.ErrorMessage;
import compiler.ErrorMessages;

public class Program extends AstNode {

	private static final String MAIN = "Main";

	private List<Decl> decls;

	public Program(List<Decl> decls) {
		this.decls = decls;
	}

	public List<Decl> getDecls() {
		return decls;
	}

	@Override
	public ErrorMessage checkSemantics() {
		ErrorMessage result = null;
		boolean hasValidMain = false;

		for (Decl decl : decls) {
			if (!hasValidMain) {
				hasValidMain = isValidMainProcedure(decl);
			}
		}

		if (!hasValidMain) {
			result = new ErrorMessage(ErrorMessages.MISSING_MAIN);
		}
		
		return result;
	}

	private boolean isValidMainProcedure(Decl decl) {
		boolean validMain = false;

		if (MAIN.equals(decl.getName().getString())
				&& decl instanceof ProcedureDecl) {

			ProcedureDecl main = (ProcedureDecl) decl;

			validMain = main.getParameterDecls().isEmpty()
					&& main.getReturnType() instanceof VoidType;
		}

		return validMain;
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("PROGRAM").addIndented(decls).build();
	}

	public void generateCode(CodeFile codeFile) {
		// TODO Auto-generated method stub
	}
}
