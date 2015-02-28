package oblig1parser;

import static org.junit.Assert.assertTrue;
import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import syntaxtree.ClassDecl;
import syntaxtree.Program;
import syntaxtree.VariableDecl;

@RunWith(Enclosed.class)
public class ProgramParserTest extends ParserTest {

	public static class ParseMethod {

		private Symbol parse(String string) throws Exception {
			Scanner scanner = new Lexer(toInputStream(string));
			parser parser = new parser(scanner);
			return parser.parse();
		}

		@Test
		public void emptyProgram_success() throws Exception {
			assertTrue(parse(String.format(PROGRAM, "")).value instanceof Program);
		}

		@Test
		public void variableDeclInProgram_success() throws Exception {
			Program program = (Program) parse(String.format(PROGRAM, VARIABLE)).value;
			
			assertTrue(program.getDecls().get(0) instanceof VariableDecl);
		}

		@Test
		public void classDeclInProgram_success() throws Exception {
			String classString = String.format(CLASS, "");
			Program program = (Program) parse(String.format(PROGRAM, classString)).value;
			
			assertTrue(program.getDecls().get(0) instanceof ClassDecl);
		}

		@Test
		public void twoDeclInProgram_success() throws Exception {
			String classString = String.format(CLASS, "");
			parse(String.format(PROGRAM, VARIABLE + classString));
		}

		@Test
		public void emptyVoidProcedure_success() throws Exception {
			String procString = String.format(PROCEDURE_VOID, "", "");
			parse(String.format(PROGRAM, procString));
		}

		@Test
		public void emptyProcedure_success() throws Exception {
			String procString = String.format(PROCEDURE, "", "");
			parse(String.format(PROGRAM, procString));
		}

		@Test
		public void emptyProcedureOneParameter_success() throws Exception {
			String procString = String.format(PROCEDURE, PARAMETER, "");
			parse(String.format(PROGRAM, procString));
		}

		@Test
		public void emptyProcedureOneRefParameter_success() throws Exception {
			String procString = String.format(PROCEDURE, PARAMETER_REF, "");
			parse(String.format(PROGRAM, procString));
		}

		@Test(expected = ParserSyntaxException.class)
		public void emptyProcedureOneParameterCommaFirst_exceptionThrown()
				throws Exception {
			String procString = String.format(PROCEDURE, ", " + PARAMETER, "");
			parse(String.format(PROGRAM, procString));
		}

		@Test(expected = ParserSyntaxException.class)
		public void emptyProcedureOneParameterCommaAfter_exceptionThrown()
				throws Exception {
			String procString = String.format(PROCEDURE, PARAMETER + ",", "");
			parse(String.format(PROGRAM, procString));
		}

		@Test
		public void emptyProcedureTwoParameters_success() throws Exception {
			String procString = String.format(PROCEDURE, PARAMETER + ", "
					+ PARAMETER, "");
			parse(String.format(PROGRAM, procString));
		}

		@Test
		public void oneDeclInProcedure_success() throws Exception {
			String procString = String.format(PROCEDURE, "", VARIABLE);
			parse(String.format(PROGRAM, procString));
		}

		@Test
		public void twoDeclInProcedure_success() throws Exception {
			String procString = String.format(PROCEDURE, "", VARIABLE
					+ VARIABLE);
			parse(String.format(PROGRAM, procString));
		}

		@Test
		public void simpleReturnStatementInProcedure_success() throws Exception {
			String returnString = String.format(RETURN_STATEMENT, "") + ";";
			String procString = String.format(PROCEDURE, "", returnString);
			parse(String.format(PROGRAM, procString));
		}

		@Test
		public void varExpressionReturnStatementInProcedure_success()
				throws Exception {
			String returnString = String.format(RETURN_STATEMENT, "foo") + ";";
			String procString = String.format(PROCEDURE, "", returnString);
			parse(String.format(PROGRAM, procString));
		}

		@Test
		public void newExpressionReturnStatementInProcedure_success()
				throws Exception {
			String returnString = String.format(RETURN_STATEMENT, "new Foo")
					+ ";";
			String procString = String.format(PROCEDURE, "", returnString);
			parse(String.format(PROGRAM, procString));
		}

		// callStatementExpressionReturnStatementInProcedure_success() throws
		// Exception

		@Test
		public void literalExpressionReturnStatementInProcedure_success()
				throws Exception {
			for (String literal : LITERALS) {
				String returnString = String.format(RETURN_STATEMENT, literal)
						+ ";";
				String procString = String.format(PROCEDURE, "", returnString);
				parse(String.format(PROGRAM, procString));
			}
		}

		@Test
		public void assignStatementInProcedure_success() throws Exception {
			String procString = String.format(PROCEDURE, "", ASSIGN_STATEMENT
					+ ";");
			parse(String.format(PROGRAM, procString));
		}

		@Test
		public void twoStatementsInProcedure_success() throws Exception {
			String returnString = String.format(RETURN_STATEMENT, "") + ";";
			String procString = String.format(PROCEDURE, "", returnString
					+ returnString);
			parse(String.format(PROGRAM, procString));
		}

		@Test
		public void declBeforeStatementInProcedure_success() throws Exception {
			String statement = String.format(RETURN_STATEMENT, "") + ";";
			String procString = String.format(PROCEDURE, "", VARIABLE
					+ statement);
			parse(String.format(PROGRAM, procString));
		}

		@Test(expected = ParserSyntaxException.class)
		public void declAfterStatementInProcedure_exceptionThrown()
				throws Exception {
			String statement = String.format(RETURN_STATEMENT, "") + ";";
			String procString = String.format(PROCEDURE, "", statement
					+ VARIABLE);
			parse(String.format(PROGRAM, procString));
		}
	}
}
