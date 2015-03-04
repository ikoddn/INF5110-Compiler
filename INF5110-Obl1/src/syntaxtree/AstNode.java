package syntaxtree;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class AstNode {

	protected final static String INDENTATION = "\t";

	public abstract List<String> makeAstStringList();

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

	protected static void addAstStringsInline(List<String> dest, AstNode srcNode) {
		List<String> srcList = srcNode.makeAstStringList();
		appendStringToLastElement(dest, " " + srcList.get(0));

		if (srcList.size() > 1) {
			dest.addAll(srcList.subList(1, srcList.size()));
		}
	}

	protected static String makeAstString(String label) {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(label);
		sb.append(")");
		
		return sb.toString();
	}
	
	protected static List<String> makeAstStringList(String label) {
		return Arrays.asList(makeAstString(label));
	}
	
	protected static String makeAstStringWithInlineChild(
			String label, String childAstString) {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(label);
		sb.append(" ");
		sb.append(childAstString);
		sb.append(")");

		return sb.toString();
	}
	
	protected static List<String> makeAstStringListWithInlineChild(
			String label, String childAstString) {
		return Arrays.asList(makeAstStringWithInlineChild(label, childAstString));
	}
	
	protected static List<String> makeAstStringListWithInlineChild(
			String label, SimpleAstNode child) {
		return makeAstStringListWithInlineChild(label, child.makeAstString());
	}
	
	protected static List<String> makeAstStringListWithInlineChild(
			String label, AstNode child) {
		if (child == null) {
			return makeAstStringList(label);
		}
		
		List<String> childAst = child.makeAstStringList();
		
		if (childAst.size() == 1) {
			return makeAstStringListWithInlineChild(label, childAst.get(0));
		}
		
		List<String> result = new LinkedList<String>();

		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(label);
		sb.append(" ");
		sb.append(childAst.get(0));
		result.add(sb.toString());
		
		result.addAll(childAst.subList(1, childAst.size()));
		appendStringToLastElement(result, ")");

		return result;
	}
	
	protected static List<String> makeAstStringListWithInlineChildren(
			String label, AstNode... children) {
		List<String> result = new LinkedList<String>();

		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(label);
		result.add(sb.toString());

		for (AstNode child : children) {
			addAstStringsInline(result, child);
		}
		
		appendStringToLastElement(result, ")");

		return result;
	}

	protected static List<String> makeAstStringListWithIndentedChildren(
			String label, AstNode... children) {
		return makeAstStringListWithIndentedChildren(label, false,
				Arrays.asList(children));
	}

	protected static <T extends AstNode> List<String> makeAstStringListWithIndentedChildren(
			String label, List<T> children) {
		return makeAstStringListWithIndentedChildren(label, false, children);
	}

	protected static <T extends AstNode> List<String> makeAstStringListWithIndentedChildren(
			String label, boolean spacingBetweenChildren, List<T> children) {
		List<String> result = new LinkedList<String>();

		result.add("(" + label);

		Iterator<T> it = children.iterator();
		while (it.hasNext()) {
			T child = it.next();

			result.addAll(prependAllWithIndentation(child.makeAstStringList()));

			if (spacingBetweenChildren && it.hasNext()) {
				result.add("");
			}
		}

		result.add(")");

		return result;
	}
}
