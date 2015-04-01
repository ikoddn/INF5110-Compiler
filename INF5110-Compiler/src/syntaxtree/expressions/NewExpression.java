package syntaxtree.expressions;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.Name;
import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;

import compiler.ErrorMessage;
import compiler.SymbolTable;
import compiler.exception.SemanticException;

public class NewExpression extends Expression {

	private DataType dataType;

	public NewExpression(DataType dataType) {
		this.dataType = dataType;
	}

	public DataType getDataType() {
		return dataType;
	}

	@Override
	public void checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		Name name = dataType.getName();

		if (dataType.getType() != Type.CLASS) {
			throw new SemanticException(ErrorMessage.INSTANTIATE_PRIMITIVE,
					name);
		}

		symbolTable.lookup(name);
	}

	@Override
	public DataType determineType(SymbolTable symbolTable)
			throws SemanticException {
		return dataType;
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("NEW").addInline(dataType).build();
	}
}
