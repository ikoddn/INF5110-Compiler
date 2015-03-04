package syntaxtree.expressions.literals;

import java.util.List;

import syntaxtree.AstStringListBuilder;

public class BoolLiteral extends Literal {

	private Boolean bool;

	public BoolLiteral(Boolean bool) {
		this.bool = bool;
	}

	public Boolean getBool() {
		return bool;
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("BOOL_LITERAL").addInline(
				bool.toString()).build();
	}
}
