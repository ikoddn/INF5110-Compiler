package oblig1parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;

import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import syntaxtree.actualparameters.PassByReferenceParameter;
import syntaxtree.actualparameters.PassByValueParameter;
import syntaxtree.expressions.Variable;
import syntaxtree.statements.AssignStatement;
import syntaxtree.statements.CallStatement;
import syntaxtree.statements.IfStatement;
import syntaxtree.statements.ReturnStatement;
import syntaxtree.statements.Statement;
import syntaxtree.statements.WhileStatement;

@RunWith(Enclosed.class)
public class StatementParserTest extends ParserBase {

	private static Symbol parseSymbol(String string) throws Exception {
		Scanner scanner = new Lexer(new StringReader(string));
		StatementParser parser = new StatementParser(scanner);
		parser.setSystemErrOutputSuppressed(true);

		return parser.parse();
	}

	@SuppressWarnings("unchecked")
	private static <T extends Statement> T parse(String string)
			throws Exception {
		return (T) parseSymbol(string).value;
	}

	public static class ParseMethod {

		@Test
		public void assign_success() throws Exception {
			AssignStatement stmt = parse(ASSIGN_STATEMENT);

			assertEquals(VARIABLE_NAME, stmt.getLeftHandSide().getName()
					.getString());
			assertTrue(stmt.getRightHandSide() instanceof Variable);
			assertEquals(VARIABLE_NAME2, ((Variable) stmt.getRightHandSide())
					.getName().getString());
		}

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

		@Test(expected = ParserSyntaxException.class)
		public void whileNoExpression_exceptionThrown() throws Exception {
			parse(String.format(WHILE_STATEMENT, "", ""));
		}

		@Test
		public void whileVarExpressionEmptyBody_success() throws Exception {
			WhileStatement stmt = parse(String.format(WHILE_STATEMENT,
					VARIABLE_NAME, ""));

			assertTrue(stmt.getExpression() instanceof Variable);
			assertTrue(stmt.getStatements().isEmpty());
		}

		@Test
		public void whileExpressionOneBodyStatement_success() throws Exception {
			WhileStatement stmt = parse(String.format(WHILE_STATEMENT,
					VARIABLE_NAME, ASSIGN_STATEMENT));

			assertEquals(1, stmt.getStatements().size());
			assertTrue(stmt.getStatements().get(0) instanceof AssignStatement);
		}

		@Test
		public void whileExpressionTwoBodyStatement_success() throws Exception {
			String body = ASSIGN_STATEMENT
					+ String.format(RETURN_STATEMENT, VARIABLE_NAME3);
			WhileStatement stmt = parse(String.format(WHILE_STATEMENT,
					VARIABLE_NAME, body));

			assertEquals(2, stmt.getStatements().size());
		}

		@Test
		public void returnVoid_success() throws Exception {
			ReturnStatement stmt = parse(String.format(RETURN_STATEMENT, ""));

			assertNull(stmt.getExpression());
		}

		@Test
		public void returnVarExpression_success() throws Exception {
			ReturnStatement stmt = parse(String.format(RETURN_STATEMENT,
					VARIABLE_NAME));

			assertTrue(stmt.getExpression() instanceof Variable);
			assertEquals(VARIABLE_NAME, ((Variable) stmt.getExpression())
					.getName().getString());
		}

		@Test
		public void callNoParameter_success() throws Exception {
			CallStatement stmt = parse(String.format(CALL_STATEMENT + ";", ""));

			assertEquals(VARIABLE_NAME, stmt.getName().getString());
			assertTrue(stmt.getActualParameters().isEmpty());
		}

		@Test
		public void callOneByValueParameter_success() throws Exception {
			CallStatement stmt = parse(String.format(CALL_STATEMENT + ";",
					VARIABLE_NAME));

			assertEquals(1, stmt.getActualParameters().size());

			PassByValueParameter param = (PassByValueParameter) stmt
					.getActualParameters().get(0);

			assertTrue(param.getExpression() instanceof Variable);
			assertEquals(VARIABLE_NAME, ((Variable) param.getExpression())
					.getName().getString());
		}

		@Test
		public void callTwoByValueParameter_success() throws Exception {
			CallStatement stmt = parse(String.format(CALL_STATEMENT + ";",
					VARIABLE_NAME + ", " + VARIABLE_NAME2));

			assertEquals(2, stmt.getActualParameters().size());
			assertTrue(stmt.getActualParameters().get(0) instanceof PassByValueParameter);
			assertTrue(stmt.getActualParameters().get(1) instanceof PassByValueParameter);
		}

		@Test
		public void callOneByReferenceParameter_success() throws Exception {
			CallStatement stmt = parse(String.format(CALL_STATEMENT + ";",
					Keyword.REF + " " + VARIABLE_NAME));

			assertEquals(1, stmt.getActualParameters().size());

			PassByReferenceParameter param = (PassByReferenceParameter) stmt
					.getActualParameters().get(0);

			Variable variable = (Variable) param.getExpression();
			assertEquals(VARIABLE_NAME, variable.getName().getString());
		}

		@Test
		public void callTwoByReferenceParameter_success() throws Exception {
			CallStatement stmt = parse(String.format(CALL_STATEMENT + ";",
					Keyword.REF + " " + VARIABLE_NAME + ", " + Keyword.REF
							+ " " + VARIABLE_NAME2));

			assertEquals(2, stmt.getActualParameters().size());
			assertTrue(stmt.getActualParameters().get(0) instanceof PassByReferenceParameter);
			assertTrue(stmt.getActualParameters().get(1) instanceof PassByReferenceParameter);
		}

		@Test(expected = ParserSyntaxException.class)
		public void callOneByValueParameterCommaFirst_exceptionThrown()
				throws Exception {
			parse(String.format(CALL_STATEMENT, ", " + VARIABLE_NAME));
		}

		@Test(expected = ParserSyntaxException.class)
		public void callOneByValueParameterCommaAfter_exceptionThrown()
				throws Exception {
			parse(String.format(CALL_STATEMENT, VARIABLE_NAME + ","));
		}
	}
}
