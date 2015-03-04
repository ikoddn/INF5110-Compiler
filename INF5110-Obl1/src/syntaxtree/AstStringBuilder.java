package syntaxtree;

public class AstStringBuilder {

	private static final String START = "(";
	private static final String END = ")";
	
	private StringBuilder sb;
	
	public AstStringBuilder(String label) {
		sb = new StringBuilder();
		
		sb.append(START);
		sb.append(label);
	}
	
	public String build() {
		sb.append(END);
		
		return sb.toString();
	}
	
	public AstStringBuilder addInline(String string) {
		sb.append(" ");
		sb.append(string);
		
		return this;
	}
}
