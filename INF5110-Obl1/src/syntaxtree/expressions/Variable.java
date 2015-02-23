package syntaxtree.expressions;

public class Variable extends Expression {
	
	public Variable(String name) {
		super(name);
	}

	@Override
	public String createAstString(int indentations) {
		StringBuilder sb = new StringBuilder();
		sb.append(generateIndentation(indentations));
		sb.append("(NAME ");
		sb.append(name);
		sb.append(")");
		
		return sb.toString();
	}
}
