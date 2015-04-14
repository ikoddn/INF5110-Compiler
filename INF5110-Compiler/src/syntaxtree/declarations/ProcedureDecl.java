package syntaxtree.declarations;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.Name;
import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;
import syntaxtree.statements.ReturnStatement;
import syntaxtree.statements.Statement;
import bytecode.CodeFile;
import bytecode.CodeProcedure;
import bytecode.type.CodeType;

import compiler.ErrorMessage;
import compiler.SymbolTable;
import compiler.throwable.SemanticException;

public class ProcedureDecl extends Decl {

	public static final String MAIN = "Main";

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

	public boolean isValidMain() {
		return MAIN.equals(name.getString()) && parameterDecls.isEmpty()
				&& returnType.getType() == Type.VOID;
	}

	@Override
	protected DataType checkSemantics(SymbolTable parentSymbolTable)
			throws SemanticException {
		SymbolTable symbolTable = new SymbolTable(parentSymbolTable);

		if (!isAllowed(returnType)) {
			throw new SemanticException(ErrorMessage.UNALLOWED_TYPE_PROCEDURE);
		}

		if (returnType.getType() == Type.CLASS) {
			symbolTable.lookupType(returnType);
		}

		for (ParameterDecl parameterDecl : parameterDecls) {
			symbolTable.insert(parameterDecl);
			parameterDecl.checkSemanticsIfNecessary(symbolTable);
		}

		for (Decl subDecl : subDecls) {
			subDecl.insertInto(symbolTable);
			subDecl.checkSemanticsIfNecessary(symbolTable);
		}

		boolean lastStatementIsReturn = false;

		for (Statement statement : subStatements) {
			DataType type = statement.checkSemanticsIfNecessary(symbolTable);

			lastStatementIsReturn = statement instanceof ReturnStatement;

			if (lastStatementIsReturn) {
				if (!type.isA(returnType)) {
					throw new SemanticException(
							ErrorMessage.UNALLOWED_TYPE_RETURN);
				}
			}
		}

		if (!lastStatementIsReturn && returnType.getType() != Type.VOID) {
			throw new SemanticException(ErrorMessage.MISSING_RETURN,
					returnType.getName());
		}

		return returnType;
	}

	@Override
	public void insertInto(SymbolTable symbolTable) throws SemanticException {
		symbolTable.insert(this);
	}

	@Override
	public void generateCode(CodeFile codeFile) {
		codeFile.addProcedure(name.getString());
		CodeType codeTypeReturn = returnType.getByteCodeType(codeFile);

		CodeProcedure procedure = new CodeProcedure(name.getString(),
				codeTypeReturn, codeFile);

		for (ParameterDecl parameterDecl : parameterDecls) {
			parameterDecl.generateCode(procedure);
		}

		for (Decl decl : subDecls) {
			decl.generateCode(procedure);
		}

		for (Statement statement : subStatements) {
			statement.generateCode(procedure);
		}

		codeFile.updateProcedure(procedure);
	}

	@Override
	public void generateCode(CodeProcedure procedure) {
		// Procedures allowed inside procedures in Compila15, but the bytecode
		// does not support it
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

	private static boolean isAllowed(DataType type) {
		return type.getType() != Type.NULL;
	}
}
