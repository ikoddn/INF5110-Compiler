package oblig1parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import syntaxtree.expressions.Expression;
import syntaxtree.expressions.NewExpression;
import syntaxtree.expressions.NotExpression;
import syntaxtree.expressions.Variable;
import syntaxtree.expressions.binaryoperators.BinaryOperatorExpression;
import syntaxtree.expressions.binaryoperators.arithmetic.Addition;
import syntaxtree.expressions.binaryoperators.arithmetic.Division;
import syntaxtree.expressions.binaryoperators.arithmetic.Exponentiation;
import syntaxtree.expressions.binaryoperators.arithmetic.Multiplication;
import syntaxtree.expressions.binaryoperators.arithmetic.Subtraction;
import syntaxtree.expressions.binaryoperators.logic.LogicalAnd;
import syntaxtree.expressions.binaryoperators.logic.LogicalOr;
import syntaxtree.expressions.literals.BoolLiteral;
import syntaxtree.expressions.literals.FloatLiteral;
import syntaxtree.expressions.literals.IntLiteral;
import syntaxtree.expressions.literals.NullLiteral;
import syntaxtree.expressions.literals.StringLiteral;

@RunWith(Enclosed.class)
public class ExpressionParserTest extends ParserTest {

	private static Symbol parseSymbol(String string) throws Exception {
		Scanner scanner = new Lexer(toInputStream(string));
		ExpParser parser = new ExpParser(scanner);
		return parser.parse();
	}

	@SuppressWarnings("unchecked")
	private static <T extends Expression> T parse(String string)
			throws Exception {
		return (T) parseSymbol(string).value;
	}

	public static class ParseMethod {

		private static String ternaryExpression(BinaryOperator firstOperator,
				BinaryOperator secondOperator) {
			StringBuilder sb = new StringBuilder();
			sb.append(VARIABLE_NAME);
			sb.append(" ");
			sb.append(firstOperator);
			sb.append(" ");
			sb.append(VARIABLE_NAME2);
			sb.append(" ");
			sb.append(secondOperator);
			sb.append(" ");
			sb.append(VARIABLE_NAME3);

			return sb.toString();
		}

		private static void assertLeftHandSideLeftAssociative(Expression exp) {
			assertTrue(exp instanceof BinaryOperatorExpression);

			BinaryOperatorExpression binopExp = (BinaryOperatorExpression) exp;

			assertTrue(binopExp.getLeftHandSide() instanceof Variable);
			assertTrue(binopExp.getRightHandSide() instanceof Variable);

			Variable leftVar = (Variable) binopExp.getLeftHandSide();
			Variable rightVar = (Variable) binopExp.getRightHandSide();

			assertEquals(VARIABLE_NAME, leftVar.getName());
			assertEquals(VARIABLE_NAME2, rightVar.getName());
		}

		private static void assertRightHandSideRightAssociative(Expression exp) {
			assertTrue(exp instanceof BinaryOperatorExpression);

			BinaryOperatorExpression binopExp = (BinaryOperatorExpression) exp;

			assertTrue(binopExp.getLeftHandSide() instanceof Variable);
			assertTrue(binopExp.getRightHandSide() instanceof Variable);

			Variable leftVar = (Variable) binopExp.getLeftHandSide();
			Variable rightVar = (Variable) binopExp.getRightHandSide();

			assertEquals(VARIABLE_NAME2, leftVar.getName());
			assertEquals(VARIABLE_NAME3, rightVar.getName());
		}

		@Test
		public void notExpression_success() throws Exception {
			NotExpression notExp = parse("not " + VARIABLE_NAME);
			Variable var = (Variable) notExp.getExpression();

			assertEquals(VARIABLE_NAME, var.getName());
			assertNull(var.getExpression());
		}

		@Test
		public void logicalOrAssociativity_leftAssociative() throws Exception {
			LogicalOr exp = parse(ternaryExpression(BinaryOperator.DOUBLE_VBAR,
					BinaryOperator.DOUBLE_VBAR));

			assertTrue(exp.getLeftHandSide() instanceof LogicalOr);
			assertTrue(exp.getRightHandSide() instanceof Variable);
			assertEquals(VARIABLE_NAME3,
					((Variable) exp.getRightHandSide()).getName());
			assertLeftHandSideLeftAssociative(exp.getLeftHandSide());
		}

		@Test
		public void logicalAndAssociativity_leftAssociative() throws Exception {
			LogicalAnd exp = parse(ternaryExpression(BinaryOperator.DOUBLE_AMP,
					BinaryOperator.DOUBLE_AMP));

			assertTrue(exp.getLeftHandSide() instanceof LogicalAnd);
			assertTrue(exp.getRightHandSide() instanceof Variable);
			assertEquals(VARIABLE_NAME3,
					((Variable) exp.getRightHandSide()).getName());
			assertLeftHandSideLeftAssociative(exp.getLeftHandSide());
		}

		@Test
		public void additionAssociativity_leftAssociative() throws Exception {
			Addition exp = parse(ternaryExpression(BinaryOperator.PLUS,
					BinaryOperator.PLUS));

			assertTrue(exp.getLeftHandSide() instanceof Addition);
			assertTrue(exp.getRightHandSide() instanceof Variable);
			assertEquals(VARIABLE_NAME3,
					((Variable) exp.getRightHandSide()).getName());
			assertLeftHandSideLeftAssociative(exp.getLeftHandSide());
		}

		@Test
		public void subtractionAssociativity_leftAssociative() throws Exception {
			Subtraction exp = parse(ternaryExpression(BinaryOperator.MINUS,
					BinaryOperator.MINUS));

			assertTrue(exp.getLeftHandSide() instanceof Subtraction);
			assertTrue(exp.getRightHandSide() instanceof Variable);
			assertEquals(VARIABLE_NAME3,
					((Variable) exp.getRightHandSide()).getName());
			assertLeftHandSideLeftAssociative(exp.getLeftHandSide());
		}

		@Test
		public void multiplicationAssociativity_leftAssociative()
				throws Exception {
			Multiplication exp = parse(ternaryExpression(
					BinaryOperator.ASTERISK, BinaryOperator.ASTERISK));

			assertTrue(exp.getLeftHandSide() instanceof Multiplication);
			assertTrue(exp.getRightHandSide() instanceof Variable);
			assertEquals(VARIABLE_NAME3,
					((Variable) exp.getRightHandSide()).getName());
			assertLeftHandSideLeftAssociative(exp.getLeftHandSide());
		}

		@Test
		public void divisionAssociativity_leftAssociative() throws Exception {
			Division exp = parse(ternaryExpression(BinaryOperator.SLASH,
					BinaryOperator.SLASH));

			assertTrue(exp.getLeftHandSide() instanceof Division);
			assertTrue(exp.getRightHandSide() instanceof Variable);
			assertEquals(VARIABLE_NAME3,
					((Variable) exp.getRightHandSide()).getName());
			assertLeftHandSideLeftAssociative(exp.getLeftHandSide());
		}

		@Test
		public void exponentiationAssociativity_rightAssociative()
				throws Exception {
			Exponentiation exp = parse(ternaryExpression(BinaryOperator.HASH,
					BinaryOperator.HASH));

			assertTrue(exp.getLeftHandSide() instanceof Variable);
			assertEquals(VARIABLE_NAME,
					((Variable) exp.getLeftHandSide()).getName());
			assertTrue(exp.getRightHandSide() instanceof Exponentiation);
			assertRightHandSideRightAssociative(exp.getRightHandSide());
		}

		@Test(expected = ParserSyntaxException.class)
		public void lessThanAssociativity_exceptionThrown() throws Exception {
			parse(ternaryExpression(BinaryOperator.LESS, BinaryOperator.LESS));
		}

		@Test(expected = ParserSyntaxException.class)
		public void lessThanOrEqualToAssociativity_exceptionThrown()
				throws Exception {
			parse(ternaryExpression(BinaryOperator.LESS_EQUAL,
					BinaryOperator.LESS_EQUAL));
		}

		@Test(expected = ParserSyntaxException.class)
		public void greaterThanAssociativity_exceptionThrown() throws Exception {
			parse(ternaryExpression(BinaryOperator.GREATER,
					BinaryOperator.GREATER));
		}

		@Test(expected = ParserSyntaxException.class)
		public void greaterThanOrEqualToAssociativity_exceptionThrown()
				throws Exception {
			parse(ternaryExpression(BinaryOperator.GREATER_EQUAL,
					BinaryOperator.GREATER_EQUAL));
		}

		@Test(expected = ParserSyntaxException.class)
		public void equalToAssociativity_exceptionThrown() throws Exception {
			parse(ternaryExpression(BinaryOperator.EQUAL, BinaryOperator.EQUAL));
		}

		@Test(expected = ParserSyntaxException.class)
		public void notEqualToAssociativity_exceptionThrown() throws Exception {
			parse(ternaryExpression(BinaryOperator.LESS_GREATER,
					BinaryOperator.LESS_GREATER));
		}

		@Test
		public void notAssociativity_success() throws Exception {
			NotExpression exp = parse("not not " + VARIABLE_NAME);

			assertTrue(exp.getExpression() instanceof NotExpression);
			NotExpression innerExp = (NotExpression) exp.getExpression();
			assertTrue(innerExp.getExpression() instanceof Variable);
			assertEquals(VARIABLE_NAME,
					((Variable) innerExp.getExpression()).getName());
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
		public void floatLiteralStartsWithDot_exceptionThrown()
				throws Exception {
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
		public void stringLiteralWithWindowsNewline_errorThrown()
				throws Exception {
			String literal = "\"ThisIs\r\nAString\"";
			parse(literal);
		}

		@Test
		public void trueLiteral_success() throws Exception {
			String literal = "true";
			BoolLiteral boolLiteral = (BoolLiteral) parse(literal);

			assertEquals(literal, boolLiteral.getBool().toString());
		}

		@Test
		public void falseLiteral_success() throws Exception {
			String literal = "false";
			BoolLiteral boolLiteral = (BoolLiteral) parse(literal);

			assertEquals(literal, boolLiteral.getBool().toString());
		}

		@Test
		public void nullLiteral_success() throws Exception {
			assertTrue(parse("null") instanceof NullLiteral);
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

	@RunWith(Parameterized.class)
	public static class ParseMethodBinaryOperator {

		@Parameter
		public BinaryOperator binaryOperator;

		@Parameters(name = "binary operator {0}")
		public static Iterable<Object[]> data() {
			List<Object[]> result = new ArrayList<Object[]>();

			for (BinaryOperator binop : BinaryOperator.values()) {
				result.add(new Object[] { binop });
			}

			return result;
		}

		@Test
		public void binaryOperatorBetweenExpressions_success() throws Exception {
			StringBuilder sb = new StringBuilder();
			sb.append(VARIABLE_NAME);
			sb.append(binaryOperator.toString());
			sb.append(VARIABLE_NAME2);

			BinaryOperatorExpression binop = parse(sb.toString());

			Variable leftVar = (Variable) binop.getLeftHandSide();
			Variable rightVar = (Variable) binop.getRightHandSide();

			assertEquals(VARIABLE_NAME, leftVar.getName());
			assertEquals(VARIABLE_NAME2, rightVar.getName());
			assertNull(leftVar.getExpression());
			assertNull(rightVar.getExpression());
		}
	}
}
