package syntaxtree;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class AstStringListBuilder {

	private static final String START = "(";
	private static final String END = ")";
	private static final String INDENTATION = "\t";

	private boolean lastElementIndented;
	private List<String> list;

	public AstStringListBuilder(String label) {
		list = new LinkedList<String>();

		list.add(START + label);
		lastElementIndented = false;
	}

	public List<String> build() {
		if (lastElementIndented) {
			list.add(END);
		} else {
			appendStringToLastElement(END);
		}

		return list;
	}

	public AstStringListBuilder add(String string) {
		list.add(string);
		lastElementIndented = false;

		return this;
	}

	public AstStringListBuilder addIndented(AstStringListBuilder other) {
		return addStringsIndented(other.build());
	}

	public AstStringListBuilder addIndented(AstNode child) {
		return addStringsIndented(child.makeAstStringList());
	}

	public <T extends AstNode> AstStringListBuilder addIndented(List<T> children) {
		for (T child : children) {
			addIndented(child);
		}

		return this;
	}

	public AstStringListBuilder addIndented(AstNode... children) {
		return addIndented(Arrays.asList(children));
	}

	public AstStringListBuilder addInline(String string) {
		appendStringToLastElement(" " + string);
		lastElementIndented = false;

		return this;
	}

	public AstStringListBuilder addInline(SimpleAstNode child) {
		if (child != null) {
			addInline(child.makeAstString());
		}

		return this;
	}

	public AstStringListBuilder addInline(AstNode child) {
		if (child == null) {
			return this;
		}

		List<String> childAst = child.makeAstStringList();

		addInline(childAst.get(0));

		if (childAst.size() > 1) {
			list.addAll(childAst.subList(1, childAst.size()));
		}

		return this;
	}

	public <T extends AstNode> AstStringListBuilder addInline(List<T> children) {
		for (T child : children) {
			addInline(child);
		}

		return this;
	}

	public AstStringListBuilder addInline(AstNode... children) {
		return addInline(Arrays.asList(children));
	}

	private AstStringListBuilder addStringsIndented(List<String> childList) {
		list.addAll(prependAllWithIndentation(childList));
		lastElementIndented = true;

		return this;
	}

	private void appendStringToLastElement(String string) {
		int lastIndex = list.size() - 1;
		list.set(lastIndex, list.get(lastIndex) + string);
	}

	private static List<String> prependAllWithIndentation(List<String> list) {
		List<String> result = new LinkedList<String>();

		for (String string : list) {
			result.add(INDENTATION + string);
		}

		return result;
	}
}
