package syntaxtree.statements;

import java.util.Iterator;
import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.Name;
import syntaxtree.actualparameters.ActualParameter;
import syntaxtree.datatypes.DataType;
import syntaxtree.declarations.ParameterDecl;
import syntaxtree.declarations.ProcedureDecl;
import bytecode.CodeProcedure;
import bytecode.instructions.CALL;

import compiler.ErrorMessage;
import compiler.SymbolTable;
import compiler.throwable.SemanticError;
import compiler.throwable.SemanticException;

public class CallStatement extends Statement {

	private Name name;
	private List<ActualParameter> actualParameters;
	private DataType dataType;

	public CallStatement(Name name, List<ActualParameter> actualParameters) {
		this.name = name;
		this.actualParameters = actualParameters;
		dataType = null;
	}

	public Name getName() {
		return name;
	}

	public List<ActualParameter> getActualParameters() {
		return actualParameters;
	}

	public DataType getDataType() {
		if (dataType == null) {
			throw new SemanticError(ErrorMessage.UNDETERMINED_TYPE);
		}

		return dataType;
	}

	@Override
	public void checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		ProcedureDecl decl = symbolTable.lookupProcedure(name);
		List<ParameterDecl> formalParameters = decl.getParameterDecls();
		dataType = decl.getReturnType();

		if (actualParameters.size() != formalParameters.size()) {
			throw new SemanticException(ErrorMessage.NOT_MATCHING_SIGNATURE,
					name);
		}

		Iterator<ParameterDecl> it = formalParameters.iterator();
		for (ActualParameter actualParam : actualParameters) {
			ParameterDecl formalParam = it.next();

			if (actualParam.isReference() != formalParam.isReference()) {
				throw new SemanticException(
						ErrorMessage.NOT_MATCHING_SIGNATURE, name);
			}

			actualParam.checkSemantics(symbolTable);
			DataType actualType = actualParam.getDataType();
			DataType formalType = formalParam.getDataType();

			if (!actualType.isA(formalType)) {
				throw new SemanticException(
						ErrorMessage.NOT_MATCHING_SIGNATURE, name);
			}
		}
	}

	@Override
	public void generateCode(CodeProcedure procedure) {
		int procedureNumber = procedure.procedureNumber(name.getString());

		for (ActualParameter parameter : actualParameters) {
			parameter.generateCode(procedure);
		}

		procedure.addInstruction(new CALL(procedureNumber));
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("CALL_STMT").addInline(name)
				.addIndented(actualParameters).build();
	}
}
