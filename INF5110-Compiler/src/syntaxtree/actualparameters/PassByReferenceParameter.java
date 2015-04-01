package syntaxtree.actualparameters;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.datatypes.DataType;
import syntaxtree.expressions.Variable;

import compiler.SymbolTable;
import compiler.exception.SemanticException;

public class PassByReferenceParameter extends ActualParameter {

	private Variable variable;

	public PassByReferenceParameter(Variable variable) {
		this.variable = variable;
	}

	public Variable getVariable() {
		return variable;
	}

	@Override
	public DataType determineType(SymbolTable symbolTable)
			throws SemanticException {
		return variable.determineType(symbolTable);
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("ACTUAL_PARAM ref").addInline(variable)
				.build();
	}
}
