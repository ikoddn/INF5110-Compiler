package syntaxtree;

import syntaxtree.datatypes.DataType;

public class VarDecl extends Decl {

	private DataType dataType;

	public VarDecl(String name, DataType dataType) {
		super(name);
		this.dataType = dataType;
	}

	@Override
	public String createAstString(int indentations) {
		StringBuilder sb = new StringBuilder();
		sb.append(generateIndentation(indentations));
		sb.append("(VAR_DECL (TYPE ");
		sb.append(dataType.getName());
		sb.append(") (NAME ");
		sb.append(name);
		sb.append("))");
		
		return sb.toString();
	}
}
