package syntaxtree;

public abstract class AstNode {

	protected final static String INDENTATION = "\t";
	protected final static String NEWLINE = "\n";
	
	public abstract String createAstString(int indentations);
	
	protected String generateIndentation(int indentations) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < indentations; ++i) {
			sb.append(INDENTATION);
		}
		
		return sb.toString();
	}
}
