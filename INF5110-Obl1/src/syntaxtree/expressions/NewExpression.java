package syntaxtree.expressions;

import java.util.List;

import syntaxtree.datatypes.ClassType;

public class NewExpression extends Expression {

	private ClassType classType;

	public NewExpression(ClassType classType) {
		this.classType = classType;
	}

	public ClassType getClassType() {
		return classType;
	}

	@Override
	public List<String> makeAstStringList() {
		return makeAstStringListWithInlineChild("NEW", classType);
	}
}
