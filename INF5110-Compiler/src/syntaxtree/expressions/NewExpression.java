package syntaxtree.expressions;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;
import bytecode.CodeProcedure;
import bytecode.instructions.NEW;
import compiler.ErrorMessage;
import compiler.SymbolTable;
import compiler.throwable.SemanticException;

public class NewExpression extends Expression {

	private DataType dataType;

	public NewExpression(DataType dataType) {
		this.dataType = dataType;
	}

	public DataType getDataType() {
		return dataType;
	}

	@Override
	protected DataType checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		if (dataType.getType() != Type.CLASS) {
			throw new SemanticException(ErrorMessage.INSTANTIATE_PRIMITIVE,
					dataType.getName());
		}

		symbolTable.lookupType(dataType);

		return dataType;
	}

	@Override
	public void generateCode(CodeProcedure procedure) {
		String structName = dataType.getName().getString();
		int structNumber = procedure.structNumber(structName);
		procedure.addInstruction(new NEW(structNumber));
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("NEW").addInline(dataType).build();
	}
}
