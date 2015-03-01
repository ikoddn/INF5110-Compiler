package syntaxtree.expressions.literals;

import java.util.Arrays;
import java.util.List;

public class NullLiteral extends Literal {

	@Override
	public List<String> makeAstPrint() {
		return Arrays.asList("(NULL_LITERAL)");
	}
}
