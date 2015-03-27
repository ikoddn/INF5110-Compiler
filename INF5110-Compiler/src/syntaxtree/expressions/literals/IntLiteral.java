package syntaxtree.expressions.literals;

import java.util.List;

import syntaxtree.AstStringListBuilder;

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
		return new AstStringListBuilder("INT_LITERAL").addInline(
				number.toString()).build();
	}
}
