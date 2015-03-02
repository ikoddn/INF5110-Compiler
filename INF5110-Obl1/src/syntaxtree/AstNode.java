package syntaxtree;

import java.util.LinkedList;
import java.util.List;

public abstract class AstNode {

	protected final static String INDENTATION = "\t";

	public abstract List<String> makeAstPrint();

	protected static List<String> prependAllWithIndentation(List<String> list) {
		List<String> result = new LinkedList<String>();

		for (String string : list) {
			result.add(INDENTATION + string);
		}

		return result;
	}

	protected static void appendStringToLastElement(List<String> list,
			String string) {
		int lastIndex = list.size() - 1;
		list.set(lastIndex, list.get(lastIndex) + string);
	}

	protected static void addInline(List<String> dest, AstNode srcNode) {
		List<String> srcList = srcNode.makeAstPrint();
		appendStringToLastElement(dest, srcList.get(0));

		if (srcList.size() > 1) {
			dest.addAll(srcList.subList(1, srcList.size()));
		}
	}
	
	protected static List<String> makeParentAstPrint(String label, AstNode... children) {
		List<String> result = new LinkedList<String>();
		
		result.add("(" + label);
		
		for (AstNode child : children) {
			result.addAll(prependAllWithIndentation(child.makeAstPrint()));
		}
		
		result.add(")");
		
		return result;
	}
}
