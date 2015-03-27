package oblig1parser;

public enum Keyword {
	BOOL("bool"),
	CLASS("class"),
	DO("do"),
	ELSE("else"),
	FALSE("false"),
	FLOAT("float"),
	IF("if"),
	INT("int"),
	NEW("new"),
	NOT("not"),
	NULL("null"),
	PROC("proc"),
	PROGRAM("program"),
	REF("ref"),
	RETURN("return"),
	STRING("string"),
	THEN("then"),
	TRUE("true"),
	VAR("var"),
	WHILE("while");

	private String name;

	private Keyword(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
