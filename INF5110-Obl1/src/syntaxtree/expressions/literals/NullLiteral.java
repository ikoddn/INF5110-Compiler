package syntaxtree.expressions.literals;

import java.util.List;

public class NullLiteral extends Literal {

	@Override
	public List<String> makeAstStringList() {
		return makeAstStringList("NULL_LITERAL");
	}
}
