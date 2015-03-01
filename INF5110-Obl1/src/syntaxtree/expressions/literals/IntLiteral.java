package syntaxtree.expressions.literals;

import java.util.Arrays;
import java.util.List;

public class IntLiteral extends Literal {

	private Integer number;

	public IntLiteral(Integer number) {
		this.number = number;
	}

	public Integer getNumber() {
		return number;
	}

	@Override
	public List<String> makeAstPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append("(INT_LITERAL ");
		sb.append(number.toString());
		sb.append(")");

		return Arrays.asList(sb.toString());
	}
}
