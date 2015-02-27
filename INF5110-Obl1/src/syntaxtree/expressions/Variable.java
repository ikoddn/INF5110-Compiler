package syntaxtree.expressions;

import java.util.Arrays;
import java.util.List;

public class Variable extends Expression {

	private String name;

	public Variable(String name) {
		this.name = name;
	}

	@Override
	public List<String> makeAstPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append("(NAME ");
		sb.append(name);
		sb.append(")");

		return Arrays.asList(sb.toString());
	}
}
