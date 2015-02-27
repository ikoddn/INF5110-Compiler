package oblig1parser;

import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;
import oblig1parser.ParserSyntaxException;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ProgramParserTest extends ParserTest {

	public static class ParseMethod {

		private Symbol parseProgram(String string) throws Exception {
			Scanner scanner = new Lexer(toInputStream(string));
			parser parser = new parser(scanner);
			return parser.parse();
		}

		@Test
		public void emptyProgram_success() throws Exception {
			parseProgram(String.format(PROGRAM, ""));
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
