package syntaxtree.expressions.literals;

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
	public List<String> makeAstStringList() {
		return makeAstStringListWithInlineChild("INT_LITERAL",
				number.toString());
	}
}
