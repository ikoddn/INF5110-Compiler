package syntaxtree.declarations;

import java.util.Arrays;
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
	public List<String> makeAstPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append("(VAR_DECL ");
		sb.append(dataType.makeAstString());
		sb.append(" ");
		sb.append(name.makeAstString());
		sb.append(")");

		return Arrays.asList(sb.toString());
	}
}
