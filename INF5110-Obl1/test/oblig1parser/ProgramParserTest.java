package oblig1parser;

import static org.junit.Assert.*;
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

		private Symbol parseSymbol(String string) throws Exception {
			Scanner scanner = new Lexer(toInputStream(string));
			parser parser = new parser(scanner);
			return parser.parse();
		}
		
		private Program parse(String string) throws Exception {
			return (Program) parseSymbol(string).value;
		}

		@Test
		public void emptyProgram_success() throws Exception {
			assertTrue(parseSymbol(String.format(PROGRAM, "")).value instanceof Program);
		}
		
		@Test
		public void lineCommentBeforeEmptyProgram_success() throws Exception {
			parse(String.format(LINECOMMENT + "\n" + PROGRAM, ""));
		}
		
		@Test
		public void lineCommentInsideEmptyProgram_success() throws Exception {
			parse(String.format(PROGRAM, LINECOMMENT + "\n"));
		}

		@Test
		public void variableDeclInProgram_success() throws Exception {
			Program program = (Program) parseSymbol(String.format(PROGRAM, VARIABLE)).value;
			
			assertTrue(program.getDecls().get(0) instanceof VariableDecl);
			assertEquals(VARIABLE_NAME, program.getDecls().get(0).getName());
		}

		@Test
		public void classDeclInProgram_success() throws Exception {
			String classString = String.format(CLASS, "");
			Program program = (Program) parseSymbol(String.format(PROGRAM, classString)).value;
			
			assertTrue(program.getDecls().get(0) instanceof ClassDecl);
			assertEquals(CLASS_NAME, program.getDecls().get(0).getName());
		}

		@Test
		public void twoDeclInProgram_success() throws Exception {
			String classString = String.format(CLASS, "");
			parseSymbol(String.format(PROGRAM, VARIABLE + classString));
		}

		@Test
		public void emptyVoidProcedure_success() throws Exception {
			String procString = String.format(PROCEDURE_VOID, "", "");
			parseSymbol(String.format(PROGRAM, procString));
		}

		@Test
		public void emptyProcedure_success() throws Exception {
			String procString = String.format(PROCEDURE, "", "");
			parseSymbol(String.format(PROGRAM, procString));
		}

		@Test
		public void emptyProcedureOneParameter_success() throws Exception {
			String procString = String.format(PROCEDURE, PARAMETER, "");
			parseSymbol(String.format(PROGRAM, procString));
		}

		@Test
		public void emptyProcedureOneRefParameter_success() throws Exception {
			String procString = String.format(PROCEDURE, PARAMETER_REF, "");
			parseSymbol(String.format(PROGRAM, procString));
		}

		@Test(expected = ParserSyntaxException.class)
		public void emptyProcedureOneParameterCommaFirst_exceptionThrown()
				throws Exception {
			String procString = String.format(PROCEDURE, ", " + PARAMETER, "");
			parseSymbol(String.format(PROGRAM, procString));
		}

		@Test(expected = ParserSyntaxException.class)
		public void emptyProcedureOneParameterCommaAfter_exceptionThrown()
				throws Exception {
			String procString = String.format(PROCEDURE, PARAMETER + ",", "");
			parseSymbol(String.format(PROGRAM, procString));
		}

		@Test
		public void emptyProcedureTwoParameters_success() throws Exception {
			String procString = String.format(PROCEDURE, PARAMETER + ", "
					+ PARAMETER, "");
			parseSymbol(String.format(PROGRAM, procString));
		}

		@Test
		public void oneDeclInProcedure_success() throws Exception {
			String procString = String.format(PROCEDURE, "", VARIABLE);
			parseSymbol(String.format(PROGRAM, procString));
		}

		@Test
		public void twoDeclInProcedure_success() throws Exception {
			String procString = String.format(PROCEDURE, "", VARIABLE
					+ VARIABLE);
			parseSymbol(String.format(PROGRAM, procString));
		}

		@Test
		public void simpleReturnStatementInProcedure_success() throws Exception {
			String returnString = String.format(RETURN_STATEMENT, "");
			String procString = String.format(PROCEDURE, "", returnString);
			parseSymbol(String.format(PROGRAM, procString));
		}

		@Test
		public void varExpressionReturnStatementInProcedure_success()
				throws Exception {
			String returnString = String.format(RETURN_STATEMENT, "foo");
			String procString = String.format(PROCEDURE, "", returnString);
			parseSymbol(String.format(PROGRAM, procString));
		}

		@Test
		public void newExpressionReturnStatementInProcedure_success()
				throws Exception {
			String returnString = String.format(RETURN_STATEMENT, "new Foo");
			String procString = String.format(PROCEDURE, "", returnString);
			parseSymbol(String.format(PROGRAM, procString));
		}

		@Test
		public void assignStatementInProcedure_success() throws Exception {
			String procString = String.format(PROCEDURE, "", ASSIGN_STATEMENT);
			parseSymbol(String.format(PROGRAM, procString));
		}

		@Test
		public void twoStatementsInProcedure_success() throws Exception {
			String returnString = String.format(RETURN_STATEMENT, "");
			String procString = String.format(PROCEDURE, "", returnString
					+ returnString);
			parseSymbol(String.format(PROGRAM, procString));
		}

		@Test
		public void declBeforeStatementInProcedure_success() throws Exception {
			String statement = String.format(RETURN_STATEMENT, "");
			String procString = String.format(PROCEDURE, "", VARIABLE
					+ statement);
			parseSymbol(String.format(PROGRAM, procString));
		}

		@Test(expected = ParserSyntaxException.class)
		public void declAfterStatementInProcedure_exceptionThrown()
				throws Exception {
			String statement = String.format(RETURN_STATEMENT, "");
			String procString = String.format(PROCEDURE, "", statement
					+ VARIABLE);
			parseSymbol(String.format(PROGRAM, procString));
		}
	}
}
