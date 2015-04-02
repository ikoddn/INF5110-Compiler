package syntaxtree;

import java.util.List;

import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;
import syntaxtree.declarations.Decl;
import syntaxtree.declarations.ProcedureDecl;
import bytecode.CodeFile;

import compiler.ErrorMessage;
import compiler.SymbolTable;
import compiler.exception.SemanticException;

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
	protected DataType checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		boolean hasValidMain = false;

		for (Decl decl : decls) {
			decl.insertInto(symbolTable);
			decl.checkSemanticsIfNecessary(symbolTable);

			if (!hasValidMain) {
				hasValidMain = isValidMainProcedure(decl);
			}
		}

		if (!hasValidMain) {
			throw new SemanticException(ErrorMessage.MISSING_MAIN);
		}

		return new DataType(Type.VOID);
	}

	private boolean isValidMainProcedure(Decl decl) {
		boolean validMain = false;

		if (MAIN.equals(decl.getName().getString())
				&& decl instanceof ProcedureDecl) {

			ProcedureDecl main = (ProcedureDecl) decl;

			validMain = main.getParameterDecls().isEmpty()
					&& main.getReturnType().getType() == Type.VOID;
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
