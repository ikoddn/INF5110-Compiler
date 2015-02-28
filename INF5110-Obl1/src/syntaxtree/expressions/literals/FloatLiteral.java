package syntaxtree.expressions.literals;

import java.util.Arrays;
import java.util.List;

public class FloatLiteral extends Literal {

	private Float number;

	public FloatLiteral(Float number) {
		super(number.toString());

		this.number = number;
	}

	public Float getNumber() {
		return number;
	}

	@Override
	public List<String> makeAstPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append("(FLOAT_LITERAL ");
		sb.append(number.toString());
		sb.append(")");

		return Arrays.asList(sb.toString());
	}
}
