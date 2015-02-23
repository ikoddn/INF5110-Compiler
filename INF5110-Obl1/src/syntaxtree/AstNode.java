package syntaxtree;

import java.util.LinkedList;
import java.util.List;

public abstract class AstNode {

	protected final static String INDENTATION = "\t";
	protected final static String NEWLINE = "\n";

	public abstract List<String> makeAstPrint();

	protected static List<String> prependWithIndentation(List<String> list) {
		List<String> result = new LinkedList<String>();

		for (String string : list) {
			result.add(INDENTATION + string);
		}

		return result;
	}
}
