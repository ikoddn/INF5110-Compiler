package syntaxtree;

import java.util.LinkedList;
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
	public List<String> makeAstPrint() {
		List<String> result = new LinkedList<String>();

		StringBuilder sb = new StringBuilder();
		sb.append("(PROC_DECL ");
		sb.append(returnType.makeAstPrint().get(0));
		sb.append(" (NAME ");
		sb.append(name);
		sb.append(")");
		result.add(sb.toString());

		for (ParameterDecl parameterDecl : parameterDecls) {
			result.addAll(prependAllWithIndentation(parameterDecl
					.makeAstPrint()));
		}

		if (!subDecls.isEmpty()) {
			result.add("");

			for (Decl decl : subDecls) {
				result.addAll(prependAllWithIndentation(decl.makeAstPrint()));
			}
		}

		if (!subStatements.isEmpty()) {
			result.add("");

			for (Statement statement : subStatements) {
				result.addAll(prependAllWithIndentation(statement
						.makeAstPrint()));
			}
		}

		result.add(")");

		return result;
	}
}
