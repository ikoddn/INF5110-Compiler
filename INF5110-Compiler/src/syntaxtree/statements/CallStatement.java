package syntaxtree.statements;

import java.util.Iterator;
import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.Name;
import syntaxtree.actualparameters.ActualParameter;
import syntaxtree.datatypes.DataType;
import syntaxtree.declarations.ParameterDecl;
import syntaxtree.declarations.ProcedureDecl;

import compiler.ErrorMessage;
import compiler.SymbolTable;
import compiler.exception.SemanticException;

public class CallStatement extends Statement {

	private Name name;
	private List<ActualParameter> actualParameters;

	public CallStatement(Name name, List<ActualParameter> actualParameters) {
		this.name = name;
		this.actualParameters = actualParameters;
	}

	public Name getName() {
		return name;
	}

	public List<ActualParameter> getActualParameters() {
		return actualParameters;
	}

	@Override
	protected DataType checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		ProcedureDecl decl = symbolTable.lookupProcedure(name);
		List<ParameterDecl> formalParameters = decl.getParameterDecls();

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

			DataType actualType = actualParam
					.checkSemanticsIfNecessary(symbolTable);
			DataType formalType = formalParam
					.checkSemanticsIfNecessary(symbolTable);

			if (!actualType.isA(formalType)) {
				throw new SemanticException(
						ErrorMessage.NOT_MATCHING_SIGNATURE, name);
			}
		}

		return decl.checkSemanticsIfNecessary(symbolTable);
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("CALL_STMT").addInline(name)
				.addIndented(actualParameters).build();
	}
}
