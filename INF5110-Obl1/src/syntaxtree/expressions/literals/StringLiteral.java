package syntaxtree.expressions.literals;

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
	public List<String> makeAstStringList() {
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		sb.append(text);
		sb.append("\"");

		return makeAstStringListWithInlineChild("STRING_LITERAL", sb.toString());
	}
}
