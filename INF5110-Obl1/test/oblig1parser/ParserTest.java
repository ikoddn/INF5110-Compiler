package oblig1parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public abstract class ParserTest {

	public static final String CLASS_NAME = "Foo";
	public static final String PARAMETER_NAME = "foo";
	public static final String PROCEDURE_NAME = "foo";
	public static final String VARIABLE_NAME = "foo";
	public static final String VARIABLE_NAME2 = "bar";

	public static final String LINECOMMENT = "// This is a line comment";
	public static final String PROGRAM = Keyword.PROGRAM + " { %s }";
	public static final String CLASS = Keyword.CLASS + " " + CLASS_NAME
			+ " { %s }";
	public static final String VARIABLE = Keyword.VAR + " " + VARIABLE_NAME
			+ " : " + Keyword.INT + ";";
	public static final String PROCEDURE = Keyword.PROC + " " + PROCEDURE_NAME
			+ " : " + Keyword.INT + " (%s) { %s }";
	public static final String PROCEDURE_VOID = Keyword.PROC + " "
			+ PROCEDURE_NAME + " (%s) { %s }";
	public static final String PARAMETER = PARAMETER_NAME + " : " + Keyword.INT;
	public static final String PARAMETER_REF = Keyword.REF + " " + PARAMETER;
	public static final String RETURN_STATEMENT = Keyword.RETURN + " %s";
	public static final String ASSIGN_STATEMENT = VARIABLE_NAME + " := "
			+ VARIABLE_NAME2;

	public static final String[] ARITHMETIC_OPERATORS = { "+", "-", "*", "/",
			"#" };
	public static final String[] LOGIC_OPERATORS = { "&&", "||" };
	public static final String[] RELATIONAL_OPERATORS = { "<", "<=", ">", ">=",
			"=", "<>" };

	public static final String FLOAT_LITERAL = "3.14";
	public static final String INT_LITERAL = "42";
	public static final String STRING_LITERAL = "\"Foo bar\"";
	public static final String[] LITERALS = { FLOAT_LITERAL, INT_LITERAL,
			STRING_LITERAL, Keyword.TRUE.toString(), Keyword.FALSE.toString(),
			Keyword.NULL.toString() };

	protected static InputStream toInputStream(String string) {
		return new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
	}
}
