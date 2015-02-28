package oblig1parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import syntaxtree.expressions.NewExpression;
import syntaxtree.expressions.Variable;

@RunWith(Enclosed.class)
public class ExpressionParserTest extends ParserTest {

	public static class ParseMethod {

		private Symbol parse(String string) throws Exception {
			Scanner scanner = new Lexer(toInputStream(string));
			ExpParser parser = new ExpParser(scanner);
			return parser.parse();
		}

		@Test
		public void logarithmicOperatorBetweenExpressions_success()
				throws Exception {
			fail();
		}

		@Test
		public void notExpression_success() throws Exception {
			fail();
		}

		@Test
		public void relationalOperatorBetweenExpressions_success()
				throws Exception {
			fail();
		}

		@Test
		public void arithmeticOperatorBetweenExpressions_success()
				throws Exception {
			fail();
		}

		@Test
		public void expressionInsideParentheses_success() throws Exception {
			Variable var = (Variable) parse("(" + VARIABLE_NAME + ")").value;

			assertEquals(VARIABLE_NAME, var.getName());
		}

		@Test
		public void literal_success() throws Exception {
			fail();
		}

		@Test
		public void callStatement_success() throws Exception {
			fail();
		}

		@Test
		public void newExpression_success() throws Exception {
			NewExpression exp = (NewExpression) parse("new " + CLASS_NAME).value;

			assertEquals(CLASS_NAME, exp.getClassType().getName());
		}

		@Test
		public void variable_success() throws Exception {
			Variable var = (Variable) parse(VARIABLE_NAME).value;

			assertEquals(VARIABLE_NAME, var.getName());
		}
	}
}
