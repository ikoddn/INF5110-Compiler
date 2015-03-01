package syntaxtree.expressions.literals;

import java.util.Arrays;
import java.util.List;

public class StringLiteral extends Literal {

	private String text;

	public StringLiteral(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	@Override
	public List<String> makeAstPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append("(STRING_LITERAL ");
		sb.append(text);
		sb.append(")");

		return Arrays.asList(sb.toString());
	}
}
