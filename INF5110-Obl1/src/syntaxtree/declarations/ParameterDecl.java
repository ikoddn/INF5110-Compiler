package syntaxtree.declarations;

import java.util.Arrays;
import java.util.List;

import syntaxtree.Name;
import syntaxtree.datatypes.DataType;

public class ParameterDecl extends Decl {

	private boolean reference;
	private DataType dataType;

	public ParameterDecl(boolean reference, Name name, DataType dataType) {
		super(name);

		this.reference = reference;
		this.dataType = dataType;
	}

	public boolean isReference() {
		return reference;
	}

	public DataType getDataType() {
		return dataType;
	}

	@Override
	public List<String> makeAstPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append("(PARAM_DECL ");

		if (reference) {
			sb.append("ref ");
		}

		sb.append(dataType.makeAstString());
		sb.append(" ");
		sb.append(name.makeAstString());
		sb.append(")");

		return Arrays.asList(sb.toString());
	}
}
