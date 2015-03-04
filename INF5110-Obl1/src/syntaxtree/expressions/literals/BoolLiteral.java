package syntaxtree.expressions.literals;

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
	public List<String> makeAstStringList() {
		return makeAstStringListWithInlineChild("BOOL_LITERAL", bool.toString());
	}
}
