package syntaxtree;

import java.util.Arrays;
import java.util.List;

import syntaxtree.datatypes.DataType;

public class VarDecl extends Decl {

	private DataType dataType;

	public VarDecl(String name, DataType dataType) {
		super(name);
		this.dataType = dataType;
	}

	@Override
	public List<String> makeAstPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append("(VAR_DECL ");
		sb.append(dataType.makeAstPrint().get(0));
		sb.append(" (NAME ");
		sb.append(name);
		sb.append("))");

		return Arrays.asList(sb.toString());
	}
}
