package syntaxtree;

import java.util.List;

import syntaxtree.declarations.Decl;
import syntaxtree.declarations.ProcedureDecl;
import bytecode.CodeFile;

import compiler.ErrorMessage;
import compiler.SymbolTable;
import compiler.throwable.SemanticException;

public class Program extends AstNode {

	private List<Decl> decls;

	public Program(List<Decl> decls) {
		this.decls = decls;
	}

	public List<Decl> getDecls() {
		return decls;
	}

	@Override
	public void checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		boolean hasValidMain = false;

		for (Decl decl : decls) {
			decl.insertInto(symbolTable);
			decl.checkSemantics(symbolTable);

			if (!hasValidMain && decl instanceof ProcedureDecl) {
				ProcedureDecl proc = (ProcedureDecl) decl;
				hasValidMain = proc.isValidMain();
			}
		}

		if (!hasValidMain) {
			throw new SemanticException(ErrorMessage.MISSING_MAIN);
		}
	}

	public void generateCode(CodeFile codeFile) {
		for (Decl decl : decls) {
			decl.generateCode(codeFile);
		}

		codeFile.setMain(ProcedureDecl.MAIN);
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("PROGRAM").addIndented(decls).build();
	}
}
