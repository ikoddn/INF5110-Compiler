package syntaxtree.expressions;

import java.util.Arrays;
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
	public List<String> makeAstPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append("(NEW ");
		sb.append(classType.makeAstPrint().get(0));
		sb.append(")");

		return Arrays.asList(sb.toString());
	}
}
