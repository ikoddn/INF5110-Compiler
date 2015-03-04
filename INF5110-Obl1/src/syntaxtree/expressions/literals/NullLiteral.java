package syntaxtree.expressions.literals;

import java.util.List;

import syntaxtree.AstStringListBuilder;

public class NullLiteral extends Literal {

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("NULL_LITERAL").build();
	}
}
