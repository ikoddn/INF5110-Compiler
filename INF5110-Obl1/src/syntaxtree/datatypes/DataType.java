package syntaxtree.datatypes;

import java.util.Arrays;
import java.util.List;

import syntaxtree.AstNode;

public abstract class DataType extends AstNode {

	protected String name;

	protected DataType(String name) {
		this.name = name;
	}

	@Override
	public List<String> makeAstPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append("(TYPE ");
		sb.append(name);
		sb.append(")");

		return Arrays.asList(sb.toString());
	}
}
