package syntaxtree.expressions.literals;

import java.util.Arrays;
import java.util.List;

public class BoolLiteral extends Literal {

	private Boolean bool;

	public BoolLiteral(Boolean bool) {
		this.bool = bool;
	}

	public Boolean getBool() {
		return bool;
	}

	@Override
	public List<String> makeAstPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append("(BOOL_LITERAL ");
		sb.append(bool.toString());
		sb.append(")");

		return Arrays.asList(sb.toString());
	}
}
