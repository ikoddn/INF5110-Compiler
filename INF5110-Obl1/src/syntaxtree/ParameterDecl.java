package syntaxtree;

import java.util.Arrays;
import java.util.List;

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
	public List<String> makeAstPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append("(PARAM_DECL ");

		if (reference) {
			sb.append("ref ");
		}

		sb.append(dataType.makeAstPrint().get(0));
		sb.append(" (NAME ");
		sb.append(name);
		sb.append("))");

		return Arrays.asList(sb.toString());
	}
}
