package syntaxtree;

import java.util.Arrays;
import java.util.List;

public abstract class SimpleAstNode extends AstNode {

	public abstract String makeAstString();

	@Override
	public final List<String> makeAstPrint() {
		return Arrays.asList(makeAstString());
	}
}
