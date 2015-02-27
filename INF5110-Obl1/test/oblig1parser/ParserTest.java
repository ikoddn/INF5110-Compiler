package oblig1parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public abstract class ParserTest {

	public static final String CLASS_NAME = "Foo";

	public static final String PROGRAM = "program { %s }";
	public static final String CLASS = "class " + CLASS_NAME + " { %s }";
	public static final String VARIABLE = "var foo : int;";
	public static final String PROCEDURE = "proc foo : int (%s) { %s }";
	public static final String PROCEDURE_VOID = "proc foo (%s) { %s }";
	public static final String PARAMETER = "foo : int";
	public static final String PARAMETER_REF = "ref foo : int";
	public static final String RETURN_STATEMENT = "return %s";
	public static final String ASSIGN_STATEMENT = "foo := bar";

	public static final String[] ARITHMETIC_OPERATORS = { "+", "-", "*", "/",
			"#" };
	public static final String[] LOGIC_OPERATORS = { "&&", "||" };
	public static final String[] RELATIONAL_OPERATORS = { "<", "<=", ">", ">=",
			"=", "<>" };

	public static final String FLOAT_LITERAL = "3.14";
	public static final String INT_LITERAL = "42";
	public static final String STRING_LITERAL = "\"Foo bar\"";
	public static final String[] LITERALS = { FLOAT_LITERAL, INT_LITERAL,
			STRING_LITERAL, "true", "false", "null" };

	protected static InputStream toInputStream(String string) {
		return new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
	}
}
