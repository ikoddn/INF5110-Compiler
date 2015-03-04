package syntaxtree.expressions.literals;

import java.util.List;

public class FloatLiteral extends Literal {

	private Float number;

	public FloatLiteral(Float number) {
		this.number = number;
	}

	public Float getNumber() {
		return number;
	}

	@Override
	public List<String> makeAstStringList() {
		return makeAstStringListWithInlineChild("FLOAT_LITERAL",
				number.toString());
	}
}
