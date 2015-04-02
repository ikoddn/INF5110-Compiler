package syntaxtree.declarations;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.Name;
import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;

import compiler.ErrorMessage;
import compiler.SymbolTable;
import compiler.exception.SemanticException;

public class VariableDecl extends Decl {

	protected DataType dataType;

	public VariableDecl(Name name, DataType dataType) {
		super(name);
		this.dataType = dataType;
	}

	public DataType getDataType() {
		return dataType;
	}

	@Override
	protected DataType checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		if (!isAllowed(dataType)) {
			throw new SemanticException(ErrorMessage.UNALLOWED_TYPE_VARIABLE);
		}

		if (dataType.getType() == Type.CLASS) {
			symbolTable.lookupType(dataType);
		}

		return dataType;
	}

	@Override
	public void insertInto(SymbolTable symbolTable) throws SemanticException {
		symbolTable.insert(this);
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("VAR_DECL").addInline(dataType, name)
				.build();
	}

	private static boolean isAllowed(DataType type) {
		return type.getType() != Type.NULL && type.getType() != Type.VOID;
	}
}
