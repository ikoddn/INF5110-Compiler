package oblig1parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import syntaxtree.expressions.Expression;
import syntaxtree.expressions.NewExpression;
import syntaxtree.expressions.Variable;
import syntaxtree.expressions.literals.FloatLiteral;
import syntaxtree.expressions.literals.IntLiteral;
import syntaxtree.expressions.literals.StringLiteral;

@RunWith(Enclosed.class)
public class ExpressionParserTest extends ParserTest {

	public static class ParseMethod {

		private Symbol parseSymbol(String string) throws Exception {
			Scanner scanner = new Lexer(toInputStream(string));
			ExpParser parser = new ExpParser(scanner);
			return parser.parse();
		}
		
		private Expression parse(String string) throws Exception {
			return (Expression) parseSymbol(string).value;
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
			Variable var = (Variable) parseSymbol("(" + VARIABLE_NAME + ")").value;

			assertEquals(VARIABLE_NAME, var.getName());
		}

		@Test
		public void intLiteral_success() throws Exception {
			String literal = "42";
			IntLiteral intLiteral = (IntLiteral) parse(literal);
			
			assertEquals(literal, "" + intLiteral.getNumber().intValue());
		}
		
		@Test
		public void intLiteralZero_success() throws Exception {
			String literal = "0";
			IntLiteral intLiteral = (IntLiteral) parse(literal);
			
			assertEquals(literal, "" + intLiteral.getNumber().intValue());
		}
		
		@Test
		public void intLiteralStartsWithZero_success() throws Exception {
			String literal = "042";
			IntLiteral intLiteral = (IntLiteral) parse(literal);
			assertEquals("42", "" + intLiteral.getNumber().intValue());
		}
		
		@Test
		public void floatLiteral_success() throws Exception {
			String literal = "3.14";
			FloatLiteral floatLiteral = (FloatLiteral) parse(literal);
			
			assertEquals(literal, "" + floatLiteral.getNumber());
		}
		
		@Test(expected = ParserSyntaxException.class)
		public void floatLiteralStartsWithDot_exceptionThrown() throws Exception {
			String literal = ".14";
			parse(literal);
		}
		
		@Test(expected = ParserSyntaxException.class)
		public void floatLiteralEndsWithDot_exceptionThrown() throws Exception {
			String literal = "3.";
			parse(literal);
		}
		
		@Test
		public void stringLiteral_success() throws Exception {
			String literal = "\"ThisIsAString\"";
			StringLiteral stringLiteral = (StringLiteral) parse(literal);
			
			assertEquals("ThisIsAString", stringLiteral.getText());
		}
		
		@Test
		public void stringLiteralWithWhitespace_success() throws Exception {
			String literal = " \" This is a string \" ";
			StringLiteral stringLiteral = (StringLiteral) parse(literal);
			
			assertEquals(" This is a string ", stringLiteral.getText());
		}
		
		@Test
		public void stringLiteralWithTabular_success() throws Exception {
			String literal = "\"ThisIs\tAString\"";
			StringLiteral stringLiteral = (StringLiteral) parse(literal);
			
			assertEquals("ThisIs\tAString", stringLiteral.getText());
		}
		
		@Test
		public void stringLiteralWithBackslash_success() throws Exception {
			String literal = "\"ThisIs\\AString\"";
			StringLiteral stringLiteral = (StringLiteral) parse(literal);
			
			assertEquals("ThisIs\\AString", stringLiteral.getText());
		}
		
		@Test
		public void stringLiteralWithQuotationMark_success() throws Exception {
			String literal = "\"ThisIs\\\"AString\"";
			StringLiteral stringLiteral = (StringLiteral) parse(literal);
			
			assertEquals("ThisIs\"AString", stringLiteral.getText());
		}
		
		@Test(expected = Error.class)
		public void stringLiteralWithUnixNewline_errorThrown() throws Exception {
			String literal = "\"ThisIs\nAString\"";
			parse(literal);
		}
		
		@Test(expected = Error.class)
		public void stringLiteralWithWindowsNewline_errorThrown() throws Exception {
			String literal = "\"ThisIs\r\nAString\"";
			parse(literal);
		}

		@Test
		public void callStatement_success() throws Exception {
			fail();
		}

		@Test
		public void newExpression_success() throws Exception {
			NewExpression exp = (NewExpression) parseSymbol("new " + CLASS_NAME).value;

			assertEquals(CLASS_NAME, exp.getClassType().getName());
		}

		@Test
		public void variable_success() throws Exception {
			Variable var = (Variable) parseSymbol(VARIABLE_NAME).value;

			assertEquals(VARIABLE_NAME, var.getName());
		}
	}
}
