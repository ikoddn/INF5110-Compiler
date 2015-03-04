package syntaxtree.declarations;

import java.util.List;

import syntaxtree.Name;
import syntaxtree.datatypes.DataType;

public class VariableDecl extends Decl {

	private DataType dataType;

	public VariableDecl(Name name, DataType dataType) {
		super(name);
		this.dataType = dataType;
	}

	public DataType getDataType() {
		return dataType;
	}

	@Override
	public List<String> makeAstStringList() {
		return makeAstStringListWithInlineChildren("VAR_DECL", dataType, name);
	}
}
