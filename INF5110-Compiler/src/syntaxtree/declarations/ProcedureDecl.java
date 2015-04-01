package syntaxtree.declarations;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.Name;
import syntaxtree.datatypes.DataType;
import syntaxtree.statements.Statement;

import compiler.SymbolTable;
import compiler.exception.SemanticException;

public class ProcedureDecl extends Decl {

	private DataType returnType;
	private List<ParameterDecl> parameterDecls;
	private List<Decl> subDecls;
	private List<Statement> subStatements;

	public ProcedureDecl(Name name, DataType returnType,
			List<ParameterDecl> parameterDecls, List<Decl> subDecls,
			List<Statement> subStatements) {
		super(name);

		this.returnType = returnType;
		this.parameterDecls = parameterDecls;
		this.subDecls = subDecls;
		this.subStatements = subStatements;
	}

	public DataType getReturnType() {
		return returnType;
	}

	public List<ParameterDecl> getParameterDecls() {
		return parameterDecls;
	}

	public List<Decl> getSubDecls() {
		return subDecls;
	}

	public List<Statement> getSubStatements() {
		return subStatements;
	}

	@Override
	public DataType determineType(SymbolTable symbolTable)
			throws SemanticException {
		return returnType;
	}

	@Override
	public List<String> makeAstStringList() {
		AstStringListBuilder ast = new AstStringListBuilder("PROC_DECL");
		ast.addInline(returnType, name);
		ast.addIndented(parameterDecls);

		if (!subDecls.isEmpty()) {
			ast.add("");
			ast.addIndented(subDecls);
		}

		if (!subStatements.isEmpty()) {
			ast.add("");
			ast.addIndented(subStatements);
		}

		return ast.build();
	}
}
