package syntaxtree.expressions.literals;

import java.util.List;

import syntaxtree.AstStringListBuilder;

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
		return new AstStringListBuilder("FLOAT_LITERAL").addInline(
				number.toString()).build();
	}
}
