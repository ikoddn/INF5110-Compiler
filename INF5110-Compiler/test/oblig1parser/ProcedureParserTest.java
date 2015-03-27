package oblig1parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;

import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import syntaxtree.datatypes.IntType;
import syntaxtree.datatypes.VoidType;
import syntaxtree.declarations.ParameterDecl;
import syntaxtree.declarations.ProcedureDecl;

@RunWith(Enclosed.class)
public class ProcedureParserTest extends ParserTest {

	private static Symbol parseSymbol(String string) throws Exception {
		Scanner scanner = new Lexer(new StringReader(string));
		ProcedureParser parser = new ProcedureParser(scanner);
		return parser.parse();
	}

	private static ProcedureDecl parse(String string) throws Exception {
		return (ProcedureDecl) parseSymbol(string).value;
	}

	public static class ParseMethod {

		@Test
		public void emptyVoidProcedure_success() throws Exception {
			ProcedureDecl proc = parse(String.format(PROCEDURE_VOID, "", ""));

			assertEquals(PROCEDURE_NAME, proc.getName().getString());
			assertTrue(proc.getParameterDecls().isEmpty());
			assertTrue(proc.getReturnType() instanceof VoidType);
			assertTrue(proc.getSubDecls().isEmpty());
			assertTrue(proc.getSubStatements().isEmpty());
		}

		@Test
		public void emptyProcedure_success() throws Exception {
			ProcedureDecl proc = parse(String.format(PROCEDURE, "", ""));

			assertEquals(PROCEDURE_NAME, proc.getName().getString());
			assertTrue(proc.getParameterDecls().isEmpty());
			assertTrue(proc.getReturnType() instanceof IntType);
			assertTrue(proc.getSubDecls().isEmpty());
			assertTrue(proc.getSubStatements().isEmpty());
		}

		@Test
		public void emptyProcedureOneParameter_success() throws Exception {
			ProcedureDecl proc = parse(String.format(PROCEDURE, PARAMETER, ""));

			assertEquals(1, proc.getParameterDecls().size());

			ParameterDecl param = proc.getParameterDecls().get(0);

			assertEquals(PARAMETER_NAME, param.getName().getString());
			assertFalse(param.isReference());
			assertTrue(param.getDataType() instanceof IntType);

		}

		@Test
		public void emptyProcedureOneRefParameter_success() throws Exception {
			ProcedureDecl proc = parse(String.format(PROCEDURE, PARAMETER_REF,
					""));

			assertEquals(1, proc.getParameterDecls().size());

			ParameterDecl param = proc.getParameterDecls().get(0);

			assertEquals(PARAMETER_NAME, param.getName().getString());
			assertTrue(param.isReference());
			assertTrue(param.getDataType() instanceof IntType);
		}

		@Test(expected = ParserSyntaxException.class)
		public void emptyProcedureOneParameterCommaFirst_exceptionThrown()
				throws Exception {
			parse(String.format(PROCEDURE, ", " + PARAMETER, ""));
		}

		@Test(expected = ParserSyntaxException.class)
		public void emptyProcedureOneParameterCommaAfter_exceptionThrown()
				throws Exception {
			parse(String.format(PROCEDURE, PARAMETER + ",", ""));
		}

		@Test
		public void emptyProcedureTwoParameters_success() throws Exception {
			ProcedureDecl proc = parse(String.format(PROCEDURE, PARAMETER
					+ ", " + PARAMETER, ""));

			assertEquals(2, proc.getParameterDecls().size());
		}

		@Test
		public void oneDeclInProcedure_success() throws Exception {
			ProcedureDecl proc = parse(String.format(PROCEDURE, "", VARIABLE));

			assertEquals(1, proc.getSubDecls().size());
		}

		@Test
		public void twoDeclInProcedure_success() throws Exception {
			ProcedureDecl proc = parse(String.format(PROCEDURE, "", VARIABLE
					+ VARIABLE));

			assertEquals(2, proc.getSubDecls().size());
		}

		@Test
		public void oneStatementInProcedure_success() throws Exception {
			ProcedureDecl proc = parse(String.format(PROCEDURE, "",
					ASSIGN_STATEMENT));

			assertEquals(1, proc.getSubStatements().size());
		}

		@Test
		public void twoStatementsInProcedure_success() throws Exception {
			ProcedureDecl proc = parse(String.format(PROCEDURE, "",
					ASSIGN_STATEMENT + ASSIGN_STATEMENT));

			assertEquals(2, proc.getSubStatements().size());
		}

		@Test
		public void declBeforeStatementInProcedure_success() throws Exception {
			ProcedureDecl proc = parse(String.format(PROCEDURE, "", VARIABLE
					+ ASSIGN_STATEMENT));

			assertEquals(1, proc.getSubDecls().size());
			assertEquals(1, proc.getSubStatements().size());
		}

		@Test(expected = ParserSyntaxException.class)
		public void declAfterStatementInProcedure_exceptionThrown()
				throws Exception {
			parse(String.format(PROCEDURE, "", ASSIGN_STATEMENT + VARIABLE));
		}
	}
}
