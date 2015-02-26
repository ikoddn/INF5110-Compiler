package parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;
import oblig1parser.ClassParser;
import oblig1parser.Lexer;
import oblig1parser.parser;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ParserTest {

	private static InputStream toInputStream(String string) {
		return new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
	}

	public static class ParseMethod {

		private static final String PROGRAM = "program { %s }";
		private static final String CLASS = "class Foo { %s }";
		private static final String VARIABLE = "var foo : int;";
		private static final String PROCEDURE = "proc foo : int (%s) { %s }";
		private static final String PROCEDURE_VOID = "proc foo (%s) { %s }";
		private static final String PARAMETER = "foo : int";
		private static final String PARAMETER_REF = "ref foo : int";
		private static final String RETURN_STATEMENT = "return %s";
		private static final String ASSIGN_STATEMENT = "foo := bar";

		private Symbol parseProgram(String string) throws Exception {
			Scanner scanner = new Lexer(toInputStream(string));
			parser parser = new parser(scanner);
			return parser.parse();
		}
		
		private Symbol parseClass(String string) throws Exception {
			Scanner scanner = new Lexer(toInputStream(string));
			ClassParser parser = new ClassParser(scanner);
			return parser.parse();
		}

		@Test
		public void emptyProgram_success() throws Exception {
			parseProgram(String.format(PROGRAM, ""));
		}
		
		@Test
		public void emptyClass_success() throws Exception {
			parseClass(String.format(CLASS, ""));
		}

		@Test
		public void variableDecl_success() throws Exception {
			parseProgram(String.format(PROGRAM, VARIABLE));
		}

		@Test
		public void classInProgram_success() throws Exception {
			String classString = String.format(CLASS, "");
			parseProgram(String.format(PROGRAM, classString));
		}

		@Test
		public void twoDeclInProgram_success() throws Exception {
			String classString = String.format(CLASS, "");
			parseProgram(String.format(PROGRAM, VARIABLE + classString));
		}

		@Test
		public void oneVariableDeclInClass_success() throws Exception {
			String classString = String.format(CLASS, VARIABLE);
			parseProgram(String.format(PROGRAM, classString));
		}

		@Test
		public void twoVariableDeclInClass_success() throws Exception {
			String classString = String.format(CLASS, VARIABLE + VARIABLE);
			parseProgram(String.format(PROGRAM, classString));
		}

		@Test
		public void emptyVoidProcedure_success() throws Exception {
			String procString = String.format(PROCEDURE_VOID, "", "");
			parseProgram(String.format(PROGRAM, procString));
		}

		@Test
		public void emptyProcedure_success() throws Exception {
			String procString = String.format(PROCEDURE, "", "");
			parseProgram(String.format(PROGRAM, procString));
		}

		@Test
		public void emptyProcedureOneParameter_success() throws Exception {
			String procString = String.format(PROCEDURE, PARAMETER, "");
			parseProgram(String.format(PROGRAM, procString));
		}

		@Test
		public void emptyProcedureOneRefParameter_success() throws Exception {
			String procString = String.format(PROCEDURE, PARAMETER_REF, "");
			parseProgram(String.format(PROGRAM, procString));
		}

		@Test(expected = ParserSyntaxException.class)
		public void emptyProcedureOneParameterCommaFirst_exceptionThrown()
				throws Exception {
			String procString = String.format(PROCEDURE, ", " + PARAMETER, "");
			parseProgram(String.format(PROGRAM, procString));
		}

		@Test(expected = ParserSyntaxException.class)
		public void emptyProcedureOneParameterCommaAfter_exceptionThrown()
				throws Exception {
			String procString = String.format(PROCEDURE, PARAMETER + ",", "");
			parseProgram(String.format(PROGRAM, procString));
		}

		@Test
		public void emptyProcedureTwoParameters_success() throws Exception {
			String procString = String.format(PROCEDURE, PARAMETER + ", "
					+ PARAMETER, "");
			parseProgram(String.format(PROGRAM, procString));
		}

		@Test
		public void oneDeclInProcedure_success() throws Exception {
			String procString = String.format(PROCEDURE, "", VARIABLE);
			parseProgram(String.format(PROGRAM, procString));
		}

		@Test
		public void twoDeclInProcedure_success() throws Exception {
			String procString = String.format(PROCEDURE, "", VARIABLE
					+ VARIABLE);
			parseProgram(String.format(PROGRAM, procString));
		}

		@Test
		public void simpleReturnStatementInProcedure_success() throws Exception {
			String returnString = String.format(RETURN_STATEMENT, "") + ";";
			String procString = String.format(PROCEDURE, "", returnString);
			parseProgram(String.format(PROGRAM, procString));
		}

		@Test
		public void expressionReturnStatementInProcedure_success()
				throws Exception {
			String returnString = String.format(RETURN_STATEMENT, "foo") + ";";
			String procString = String.format(PROCEDURE, "", returnString);
			parseProgram(String.format(PROGRAM, procString));
		}

		@Test
		public void assignStatementInProcedure_success() throws Exception {
			String procString = String.format(PROCEDURE, "", ASSIGN_STATEMENT
					+ ";");
			parseProgram(String.format(PROGRAM, procString));
		}

		@Test
		public void twoStatementsInProcedure_success() throws Exception {
			String returnString = String.format(RETURN_STATEMENT, "") + ";";
			String procString = String.format(PROCEDURE, "", returnString
					+ returnString);
			parseProgram(String.format(PROGRAM, procString));
		}

		@Test
		public void declBeforeStatementInProcedure_success() throws Exception {
			String statement = String.format(RETURN_STATEMENT, "") + ";";
			String procString = String.format(PROCEDURE, "", VARIABLE
					+ statement);
			parseProgram(String.format(PROGRAM, procString));
		}

		@Test(expected = ParserSyntaxException.class)
		public void declAfterStatementInProcedure_exceptionThrown()
				throws Exception {
			String statement = String.format(RETURN_STATEMENT, "") + ";";
			String procString = String.format(PROCEDURE, "", statement
					+ VARIABLE);
			parseProgram(String.format(PROGRAM, procString));
		}
	}
}
