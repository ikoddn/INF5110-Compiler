package syntaxtree;

import java.util.List;

import syntaxtree.datatypes.DataType;
import syntaxtree.statements.Statement;

public class ProcedureDecl extends Decl {

	private DataType returnType;
	private List<ParameterDecl> parameterDecls;
	private List<Decl> subDecls;
	private List<Statement> subStatements;

	public ProcedureDecl(String name, DataType returnType,
			List<ParameterDecl> parameterDecls, List<Decl> subDecls,
			List<Statement> subStatements) {
		super(name);

		this.returnType = returnType;
		this.parameterDecls = parameterDecls;
		this.subDecls = subDecls;
		this.subStatements = subStatements;
	}

	@Override
	public String createAstString(int indentations) {
		String indentation = generateIndentation(indentations);

		StringBuilder sb = new StringBuilder();
		sb.append(indentation);
		sb.append("(PROC_DECL (TYPE ");
		sb.append(returnType.getName());
		sb.append(") (NAME ");
		sb.append(name);
		sb.append(")");
		sb.append(NEWLINE);

		for (ParameterDecl parameterDecl : parameterDecls) {
			sb.append(parameterDecl.createAstString(indentations + 1));
			sb.append(NEWLINE);
		}

		if (!subDecls.isEmpty()) {
			sb.append(NEWLINE);

			for (Decl decl : subDecls) {
				sb.append(decl.createAstString(indentations + 1));
				sb.append(NEWLINE);
			}
		}
		
		if (!subStatements.isEmpty()) {
			sb.append(NEWLINE);
			
			for (Statement statement : subStatements) {
				sb.append(statement.createAstString(indentations + 1));
				sb.append(NEWLINE);
			}
		}

		sb.append(indentation);
		sb.append(")");

		return sb.toString();
	}
}
