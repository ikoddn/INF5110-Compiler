package syntaxtree.statements;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.Name;
import syntaxtree.actualparameters.ActualParameter;
import syntaxtree.datatypes.DataType;

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
	public DataType determineType(SymbolTable symbolTable)
			throws SemanticException {
		return symbolTable.lookup(name).determineType(symbolTable);
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("CALL_STMT").addInline(name)
				.addIndented(actualParameters).build();
	}
}
