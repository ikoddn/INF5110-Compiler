package syntaxtree;

import syntaxtree.datatypes.DataType;

public class ParameterDecl extends Decl {

	private boolean reference;
	private DataType dataType;

	public ParameterDecl(boolean reference, String name, DataType dataType) {
		super(name);

		this.reference = reference;
		this.dataType = dataType;
	}

	@Override
	public String createAstString(int indentations) {
		StringBuilder sb = new StringBuilder();
		sb.append(generateIndentation(indentations));
		sb.append("(PARAM_DECL ");

		if (reference) {
			sb.append("ref ");
		}

		sb.append("(TYPE ");
		sb.append(dataType.getName());
		sb.append(") (NAME ");
		sb.append(name);
		sb.append("))");

		return sb.toString();
	}
}
