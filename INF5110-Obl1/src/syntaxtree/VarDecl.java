package syntaxtree;

public class VarDecl extends Decl {

	private String type;

	public VarDecl(String name, String type) {
		super(name);
		this.type = type;
	}

	@Override
	public String getAstString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(VAR_DECL (TYPE ");
		sb.append(type);
		sb.append(") (NAME ");
		sb.append(name);
		sb.append("))");
		
		return sb.toString();
	}
}
