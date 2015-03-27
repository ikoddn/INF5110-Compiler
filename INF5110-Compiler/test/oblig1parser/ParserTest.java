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
	public static final String VARIABLE_NAME3 = "baz";
	public static final String VARIABLE_NAME4 = "qux";

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

	public static final String IF_STATEMENT = Keyword.IF + " %s "
			+ Keyword.THEN + " { %s } ";
	public static final String IFELSE_STATEMENT = IF_STATEMENT + Keyword.ELSE
			+ " { %s }";
	public static final String WHILE_STATEMENT = Keyword.WHILE + " %s "
			+ Keyword.DO + " { %s } ";
	public static final String RETURN_STATEMENT = Keyword.RETURN + " %s;";
	public static final String ASSIGN_STATEMENT = VARIABLE_NAME + " := "
			+ VARIABLE_NAME2 + ";";
	public static final String CALL_STATEMENT = VARIABLE_NAME + " ( %s )";

	protected static InputStream toInputStream(String string) {
		return new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
	}
}
