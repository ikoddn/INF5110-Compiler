package oblig1parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import syntaxtree.expressions.Variable;
import syntaxtree.statements.AssignStatement;
import syntaxtree.statements.IfStatement;
import syntaxtree.statements.ReturnStatement;
import syntaxtree.statements.Statement;

@RunWith(Enclosed.class)
public class StatementParserTest extends ParserTest {

	private static Symbol parseSymbol(String string) throws Exception {
		Scanner scanner = new Lexer(toInputStream(string));
		StatementParser parser = new StatementParser(scanner);
		return parser.parse();
	}

	@SuppressWarnings("unchecked")
	private static <T extends Statement> T parse(String string)
			throws Exception {
		return (T) parseSymbol(string).value;
	}

	public static class ParseMethod {

		@Test(expected = ParserSyntaxException.class)
		public void ifNoExpression_exceptionThrown() throws Exception {
			parse(String.format(IF_STATEMENT, "", ""));
		}

		@Test
		public void ifVarExpressionEmptyBody_success() throws Exception {
			IfStatement stmt = parse(String.format(IF_STATEMENT, VARIABLE_NAME,
					""));

			assertTrue(stmt.getExpression() instanceof Variable);
			assertTrue(stmt.getIfBodyStatements().isEmpty());
			assertTrue(stmt.getElseBodyStatements().isEmpty());
		}

		@Test
		public void ifExpressionOneIfBodyStatement_success() throws Exception {
			IfStatement stmt = parse(String.format(IF_STATEMENT, VARIABLE_NAME,
					ASSIGN_STATEMENT));

			assertTrue(stmt.getExpression() instanceof Variable);
			assertEquals(1, stmt.getIfBodyStatements().size());
			assertTrue(stmt.getIfBodyStatements().get(0) instanceof AssignStatement);
			assertTrue(stmt.getElseBodyStatements().isEmpty());
		}

		@Test
		public void ifExpressionTwoIfBodyStatement_success() throws Exception {
			String ifBody = ASSIGN_STATEMENT
					+ String.format(RETURN_STATEMENT, VARIABLE_NAME3);
			IfStatement stmt = parse(String.format(IF_STATEMENT, VARIABLE_NAME,
					ifBody));

			assertTrue(stmt.getExpression() instanceof Variable);
			assertEquals(2, stmt.getIfBodyStatements().size());
			assertTrue(stmt.getIfBodyStatements().get(0) instanceof AssignStatement);
			assertTrue(stmt.getIfBodyStatements().get(1) instanceof ReturnStatement);
			assertTrue(stmt.getElseBodyStatements().isEmpty());
		}

		@Test
		public void ifElseExpressionEmptyBodies_success() throws Exception {
			IfStatement stmt = parse(String.format(IFELSE_STATEMENT,
					VARIABLE_NAME, "", ""));

			assertTrue(stmt.getExpression() instanceof Variable);
			assertTrue(stmt.getIfBodyStatements().isEmpty());
			assertTrue(stmt.getElseBodyStatements().isEmpty());
		}

		@Test
		public void ifElseExpressionOneElseBodyStatement_success()
				throws Exception {
			IfStatement stmt = parse(String.format(IFELSE_STATEMENT,
					VARIABLE_NAME, "", ASSIGN_STATEMENT));

			assertTrue(stmt.getExpression() instanceof Variable);
			assertTrue(stmt.getIfBodyStatements().isEmpty());
			assertEquals(1, stmt.getElseBodyStatements().size());
			assertTrue(stmt.getElseBodyStatements().get(0) instanceof AssignStatement);
		}

		@Test
		public void ifElseExpressionTwoElseBodyStatement_success()
				throws Exception {
			String elseBody = ASSIGN_STATEMENT
					+ String.format(RETURN_STATEMENT, VARIABLE_NAME3);
			IfStatement stmt = parse(String.format(IFELSE_STATEMENT,
					VARIABLE_NAME, "", elseBody));

			assertTrue(stmt.getExpression() instanceof Variable);
			assertTrue(stmt.getIfBodyStatements().isEmpty());
			assertEquals(2, stmt.getElseBodyStatements().size());
			assertTrue(stmt.getElseBodyStatements().get(0) instanceof AssignStatement);
			assertTrue(stmt.getElseBodyStatements().get(1) instanceof ReturnStatement);
		}
	}
}
