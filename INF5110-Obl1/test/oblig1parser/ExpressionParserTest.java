package oblig1parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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

import syntaxtree.expressions.CallStatementExpression;
import syntaxtree.expressions.Expression;
import syntaxtree.expressions.NewExpression;
import syntaxtree.expressions.NotExpression;
import syntaxtree.expressions.Variable;
import syntaxtree.expressions.literals.BoolLiteral;
import syntaxtree.expressions.literals.FloatLiteral;
import syntaxtree.expressions.literals.IntLiteral;
import syntaxtree.expressions.literals.NullLiteral;
import syntaxtree.expressions.literals.StringLiteral;
import syntaxtree.expressions.operator.ArithmeticOperatorExpression;
import syntaxtree.expressions.operator.BinaryOperatorExpression;
import syntaxtree.expressions.operator.LogicOperatorExpression;
import syntaxtree.expressions.operator.RelationalOperatorExpression;
import syntaxtree.operators.ArithmeticOperator;
import syntaxtree.operators.LogicOperator;
import syntaxtree.operators.Operator;
import syntaxtree.operators.RelationalOperator;

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

		private static String ternaryExpression(String firstOperator,
				String secondOperator) {
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

		private static String ternaryExpression(Operator firstOperator,
				Operator secondOperator) {
			return ternaryExpression(firstOperator.getSymbol(),
					secondOperator.getSymbol());
		}

		private static void assertLeftHandSideLeftAssociative(Expression exp) {
			assertTrue(exp instanceof BinaryOperatorExpression);

			BinaryOperatorExpression binopExp = (BinaryOperatorExpression) exp;

			assertTrue(binopExp.getLeftExpression() instanceof Variable);
			assertTrue(binopExp.getRightExpression() instanceof Variable);

			Variable leftVar = (Variable) binopExp.getLeftExpression();
			Variable rightVar = (Variable) binopExp.getRightExpression();

			assertEquals(VARIABLE_NAME, leftVar.getName());
			assertEquals(VARIABLE_NAME2, rightVar.getName());
		}

		private static void assertRightHandSideRightAssociative(Expression exp) {
			assertTrue(exp instanceof BinaryOperatorExpression);

			BinaryOperatorExpression binopExp = (BinaryOperatorExpression) exp;

			assertTrue(binopExp.getLeftExpression() instanceof Variable);
			assertTrue(binopExp.getRightExpression() instanceof Variable);

			Variable leftVar = (Variable) binopExp.getLeftExpression();
			Variable rightVar = (Variable) binopExp.getRightExpression();

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

		/* Precedence BEGIN */
		@Test
		public void logicalOrVsLogicalAndPrecedence_logicalAndHighest()
				throws Exception {
			LogicOperatorExpression exp = parse(ternaryExpression(
					LogicOperator.OR, LogicOperator.AND));

			assertEquals(LogicOperator.OR, exp.getOperator());
			assertTrue(exp.getLeftExpression() instanceof Variable);
			assertTrue(exp.getRightExpression() instanceof LogicOperatorExpression);

			exp = (LogicOperatorExpression) exp.getRightExpression();
			assertEquals(LogicOperator.AND, exp.getOperator());
		}

		@Test
		public void logicalAndVsLogicalOrPrecedence_logicalAndHighest()
				throws Exception {
			LogicOperatorExpression exp = parse(ternaryExpression(
					LogicOperator.AND, LogicOperator.OR));

			assertEquals(LogicOperator.OR, exp.getOperator());
			assertTrue(exp.getLeftExpression() instanceof LogicOperatorExpression);
			assertTrue(exp.getRightExpression() instanceof Variable);

			exp = (LogicOperatorExpression) exp.getLeftExpression();
			assertEquals(LogicOperator.AND, exp.getOperator());
		}

		@Test
		public void logicalAndVsNotPrecedence_notHighest() throws Exception {
			LogicOperatorExpression exp = parse(VARIABLE_NAME
					+ LogicOperator.AND + " not " + VARIABLE_NAME2);

			assertEquals(LogicOperator.AND, exp.getOperator());
			assertTrue(exp.getLeftExpression() instanceof Variable);
			assertTrue(exp.getRightExpression() instanceof NotExpression);
		}

		@Test
		public void notVsLogicalAndPrecedence_notHighest() throws Exception {
			LogicOperatorExpression exp = parse("not " + VARIABLE_NAME
					+ LogicOperator.AND + VARIABLE_NAME2);

			assertEquals(LogicOperator.AND, exp.getOperator());
			assertTrue(exp.getLeftExpression() instanceof NotExpression);
			assertTrue(exp.getRightExpression() instanceof Variable);
		}

		@Test
		public void notVsRelationalOperatorPrecedence_relationalOperatorHighest()
				throws Exception {
			for (RelationalOperator op : RelationalOperator.values()) {
				NotExpression exp = parse("not " + VARIABLE_NAME + op
						+ VARIABLE_NAME2);

				assertTrue(exp.getExpression() instanceof RelationalOperatorExpression);
			}
		}

		@Test
		public void relationalOperatorVsAdditionOrSubtractionPrecedence_additionOrSubtractionHighest()
				throws Exception {
			for (RelationalOperator op : RelationalOperator.values()) {
				RelationalOperatorExpression exp = parse(ternaryExpression(op,
						ArithmeticOperator.ADDITION));
				assertEquals(op, exp.getOperator());
				assertTrue(exp.getLeftExpression() instanceof Variable);
				assertTrue(exp.getRightExpression() instanceof ArithmeticOperatorExpression);

				ArithmeticOperatorExpression innerExp = (ArithmeticOperatorExpression) exp
						.getRightExpression();
				assertEquals(ArithmeticOperator.ADDITION,
						innerExp.getOperator());

				exp = parse(ternaryExpression(op,
						ArithmeticOperator.SUBTRACTION));
				assertEquals(op, exp.getOperator());
				assertTrue(exp.getLeftExpression() instanceof Variable);
				assertTrue(exp.getRightExpression() instanceof ArithmeticOperatorExpression);

				innerExp = (ArithmeticOperatorExpression) exp
						.getRightExpression();
				assertEquals(ArithmeticOperator.SUBTRACTION,
						innerExp.getOperator());
			}
		}

		@Test
		public void additionOrSubtractionVsRelationalOperatorPrecedence_additionOrSubtractionHighest()
				throws Exception {
			for (RelationalOperator op : RelationalOperator.values()) {
				RelationalOperatorExpression exp = parse(ternaryExpression(
						ArithmeticOperator.ADDITION, op));
				assertEquals(op, exp.getOperator());
				assertTrue(exp.getLeftExpression() instanceof ArithmeticOperatorExpression);
				assertTrue(exp.getRightExpression() instanceof Variable);

				ArithmeticOperatorExpression innerExp = (ArithmeticOperatorExpression) exp
						.getLeftExpression();
				assertEquals(ArithmeticOperator.ADDITION,
						innerExp.getOperator());

				exp = parse(ternaryExpression(ArithmeticOperator.SUBTRACTION,
						op));
				assertEquals(op, exp.getOperator());
				assertTrue(exp.getLeftExpression() instanceof ArithmeticOperatorExpression);
				assertTrue(exp.getRightExpression() instanceof Variable);

				innerExp = (ArithmeticOperatorExpression) exp
						.getLeftExpression();
				assertEquals(ArithmeticOperator.SUBTRACTION,
						innerExp.getOperator());
			}
		}

		@Test
		public void additionOrSubtractionVsMultiplicationOrDivisionPrecedence_multiplicationOrDivisionHighest()
				throws Exception {
			ArithmeticOperatorExpression exp = parse(ternaryExpression(
					ArithmeticOperator.ADDITION,
					ArithmeticOperator.MULTIPLICATION));
			assertEquals(ArithmeticOperator.ADDITION, exp.getOperator());
			assertTrue(exp.getLeftExpression() instanceof Variable);
			assertTrue(exp.getRightExpression() instanceof ArithmeticOperatorExpression);
			ArithmeticOperatorExpression innerExp = (ArithmeticOperatorExpression) exp
					.getRightExpression();
			assertEquals(ArithmeticOperator.MULTIPLICATION,
					innerExp.getOperator());

			exp = parse(ternaryExpression(ArithmeticOperator.SUBTRACTION,
					ArithmeticOperator.MULTIPLICATION));
			assertEquals(ArithmeticOperator.SUBTRACTION, exp.getOperator());
			assertTrue(exp.getLeftExpression() instanceof Variable);
			assertTrue(exp.getRightExpression() instanceof ArithmeticOperatorExpression);
			innerExp = (ArithmeticOperatorExpression) exp.getRightExpression();
			assertEquals(ArithmeticOperator.MULTIPLICATION,
					innerExp.getOperator());

			exp = parse(ternaryExpression(ArithmeticOperator.ADDITION,
					ArithmeticOperator.DIVISION));
			assertEquals(ArithmeticOperator.ADDITION, exp.getOperator());
			assertTrue(exp.getLeftExpression() instanceof Variable);
			assertTrue(exp.getRightExpression() instanceof ArithmeticOperatorExpression);
			innerExp = (ArithmeticOperatorExpression) exp.getRightExpression();
			assertEquals(ArithmeticOperator.DIVISION, innerExp.getOperator());

			exp = parse(ternaryExpression(ArithmeticOperator.SUBTRACTION,
					ArithmeticOperator.DIVISION));
			assertEquals(ArithmeticOperator.SUBTRACTION, exp.getOperator());
			assertTrue(exp.getLeftExpression() instanceof Variable);
			assertTrue(exp.getRightExpression() instanceof ArithmeticOperatorExpression);
			innerExp = (ArithmeticOperatorExpression) exp.getRightExpression();
			assertEquals(ArithmeticOperator.DIVISION, innerExp.getOperator());
		}

		@Test
		public void multiplicationOrDivisionVsAdditionOrSubtractionPrecedence_multiplicationOrDivisionHighest()
				throws Exception {
			ArithmeticOperatorExpression exp = parse(ternaryExpression(
					ArithmeticOperator.MULTIPLICATION,
					ArithmeticOperator.ADDITION));
			assertEquals(ArithmeticOperator.ADDITION, exp.getOperator());
			assertTrue(exp.getLeftExpression() instanceof ArithmeticOperatorExpression);
			assertTrue(exp.getRightExpression() instanceof Variable);
			ArithmeticOperatorExpression innerExp = (ArithmeticOperatorExpression) exp
					.getLeftExpression();
			assertEquals(ArithmeticOperator.MULTIPLICATION,
					innerExp.getOperator());

			exp = parse(ternaryExpression(ArithmeticOperator.MULTIPLICATION,
					ArithmeticOperator.SUBTRACTION));
			assertEquals(ArithmeticOperator.SUBTRACTION, exp.getOperator());
			assertTrue(exp.getLeftExpression() instanceof ArithmeticOperatorExpression);
			assertTrue(exp.getRightExpression() instanceof Variable);
			innerExp = (ArithmeticOperatorExpression) exp.getLeftExpression();
			assertEquals(ArithmeticOperator.MULTIPLICATION,
					innerExp.getOperator());

			exp = parse(ternaryExpression(ArithmeticOperator.DIVISION,
					ArithmeticOperator.ADDITION));
			assertEquals(ArithmeticOperator.ADDITION, exp.getOperator());
			assertTrue(exp.getLeftExpression() instanceof ArithmeticOperatorExpression);
			assertTrue(exp.getRightExpression() instanceof Variable);
			innerExp = (ArithmeticOperatorExpression) exp.getLeftExpression();
			assertEquals(ArithmeticOperator.DIVISION, innerExp.getOperator());

			exp = parse(ternaryExpression(ArithmeticOperator.DIVISION,
					ArithmeticOperator.SUBTRACTION));
			assertEquals(ArithmeticOperator.SUBTRACTION, exp.getOperator());
			assertTrue(exp.getLeftExpression() instanceof ArithmeticOperatorExpression);
			assertTrue(exp.getRightExpression() instanceof Variable);
			innerExp = (ArithmeticOperatorExpression) exp.getLeftExpression();
			assertEquals(ArithmeticOperator.DIVISION, innerExp.getOperator());
		}

		@Test
		public void multiplicationOrDivisionVsExponentiationPrecedence_exponentiationHighest()
				throws Exception {
			ArithmeticOperatorExpression exp = parse(ternaryExpression(
					ArithmeticOperator.MULTIPLICATION,
					ArithmeticOperator.EXPONENTIATION));
			assertEquals(ArithmeticOperator.MULTIPLICATION, exp.getOperator());
			assertTrue(exp.getLeftExpression() instanceof Variable);
			assertTrue(exp.getRightExpression() instanceof ArithmeticOperatorExpression);
			ArithmeticOperatorExpression innerExp = (ArithmeticOperatorExpression) exp
					.getRightExpression();
			assertEquals(ArithmeticOperator.EXPONENTIATION,
					innerExp.getOperator());

			exp = parse(ternaryExpression(ArithmeticOperator.DIVISION,
					ArithmeticOperator.EXPONENTIATION));
			assertEquals(ArithmeticOperator.DIVISION, exp.getOperator());
			assertTrue(exp.getLeftExpression() instanceof Variable);
			assertTrue(exp.getRightExpression() instanceof ArithmeticOperatorExpression);
			innerExp = (ArithmeticOperatorExpression) exp.getRightExpression();
			assertEquals(ArithmeticOperator.EXPONENTIATION,
					innerExp.getOperator());
		}

		@Test
		public void exponentiationVsMultiplicationOrDivisionPrecedence_exponentiationHighest()
				throws Exception {
			ArithmeticOperatorExpression exp = parse(ternaryExpression(
					ArithmeticOperator.EXPONENTIATION,
					ArithmeticOperator.MULTIPLICATION));
			assertEquals(ArithmeticOperator.MULTIPLICATION, exp.getOperator());
			assertTrue(exp.getLeftExpression() instanceof ArithmeticOperatorExpression);
			assertTrue(exp.getRightExpression() instanceof Variable);
			ArithmeticOperatorExpression innerExp = (ArithmeticOperatorExpression) exp
					.getLeftExpression();
			assertEquals(ArithmeticOperator.EXPONENTIATION,
					innerExp.getOperator());

			exp = parse(ternaryExpression(ArithmeticOperator.EXPONENTIATION,
					ArithmeticOperator.DIVISION));
			assertEquals(ArithmeticOperator.DIVISION, exp.getOperator());
			assertTrue(exp.getLeftExpression() instanceof ArithmeticOperatorExpression);
			assertTrue(exp.getRightExpression() instanceof Variable);
			innerExp = (ArithmeticOperatorExpression) exp.getLeftExpression();
			assertEquals(ArithmeticOperator.EXPONENTIATION,
					innerExp.getOperator());
		}

		@Test
		public void exponentiationVsDotPrecedence_dotHighest() throws Exception {
			ArithmeticOperatorExpression exp = parse(ternaryExpression(
					ArithmeticOperator.EXPONENTIATION.getSymbol(), "."));

			assertTrue(exp.getLeftExpression() instanceof Variable);
			assertTrue(exp.getRightExpression() instanceof Variable);

			Variable varLeft = (Variable) exp.getLeftExpression();
			Variable varRight = (Variable) exp.getRightExpression();

			assertNull(varLeft.getExpression());
			assertTrue(varRight.getExpression() instanceof Variable);
		}

		@Test
		public void dotVsExponentiationPrecedence_dotHighest() throws Exception {
			ArithmeticOperatorExpression exp = parse(ternaryExpression(".",
					ArithmeticOperator.EXPONENTIATION.getSymbol()));

			assertTrue(exp.getLeftExpression() instanceof Variable);
			assertTrue(exp.getRightExpression() instanceof Variable);

			Variable varLeft = (Variable) exp.getLeftExpression();
			Variable varRight = (Variable) exp.getRightExpression();

			assertTrue(varLeft.getExpression() instanceof Variable);
			assertNull(varRight.getExpression());
		}

		/* Precedence END */

		/* Associativity BEGIN */
		@Test
		public void logicalOrAssociativity_leftAssociative() throws Exception {
			LogicOperatorExpression exp = parse(ternaryExpression(
					LogicOperator.OR, LogicOperator.OR));

			assertTrue(exp.getLeftExpression() instanceof LogicOperatorExpression);
			assertTrue(exp.getRightExpression() instanceof Variable);
			assertEquals(VARIABLE_NAME3,
					((Variable) exp.getRightExpression()).getName());
			assertLeftHandSideLeftAssociative(exp.getLeftExpression());
		}

		@Test
		public void logicalAndAssociativity_leftAssociative() throws Exception {
			LogicOperatorExpression exp = parse(ternaryExpression(
					LogicOperator.AND, LogicOperator.AND));

			assertTrue(exp.getLeftExpression() instanceof LogicOperatorExpression);
			assertTrue(exp.getRightExpression() instanceof Variable);
			assertEquals(VARIABLE_NAME3,
					((Variable) exp.getRightExpression()).getName());
			assertLeftHandSideLeftAssociative(exp.getLeftExpression());
		}

		@Test
		public void additionAssociativity_leftAssociative() throws Exception {
			ArithmeticOperatorExpression exp = parse(ternaryExpression(
					ArithmeticOperator.ADDITION, ArithmeticOperator.ADDITION));

			assertTrue(exp.getLeftExpression() instanceof ArithmeticOperatorExpression);
			assertTrue(exp.getRightExpression() instanceof Variable);
			assertEquals(VARIABLE_NAME3,
					((Variable) exp.getRightExpression()).getName());
			assertLeftHandSideLeftAssociative(exp.getLeftExpression());
		}

		@Test
		public void subtractionAssociativity_leftAssociative() throws Exception {
			BinaryOperatorExpression exp = parse(ternaryExpression(
					ArithmeticOperator.SUBTRACTION,
					ArithmeticOperator.SUBTRACTION));

			System.out.println(ternaryExpression(
					ArithmeticOperator.SUBTRACTION,
					ArithmeticOperator.SUBTRACTION));
			
			for (String s : exp.getLeftExpression().makeAstPrint()) {
				System.out.println(s);
			}
			
			for (String s : exp.getRightExpression().makeAstPrint()) {
				System.out.println(s);
			}
			
			assertTrue(exp.getLeftExpression() instanceof ArithmeticOperatorExpression);
			assertTrue(exp.getRightExpression() instanceof Variable);
			assertEquals(VARIABLE_NAME3,
					((Variable) exp.getRightExpression()).getName());
			assertLeftHandSideLeftAssociative(exp.getLeftExpression());
		}

		@Test
		public void multiplicationAssociativity_leftAssociative()
				throws Exception {
			ArithmeticOperatorExpression exp = parse(ternaryExpression(
					ArithmeticOperator.MULTIPLICATION,
					ArithmeticOperator.MULTIPLICATION));

			assertTrue(exp.getLeftExpression() instanceof ArithmeticOperatorExpression);
			assertTrue(exp.getRightExpression() instanceof Variable);
			assertEquals(VARIABLE_NAME3,
					((Variable) exp.getRightExpression()).getName());
			assertLeftHandSideLeftAssociative(exp.getLeftExpression());
		}

		@Test
		public void divisionAssociativity_leftAssociative() throws Exception {
			ArithmeticOperatorExpression exp = parse(ternaryExpression(
					ArithmeticOperator.DIVISION, ArithmeticOperator.DIVISION));

			assertTrue(exp.getLeftExpression() instanceof ArithmeticOperatorExpression);
			assertTrue(exp.getRightExpression() instanceof Variable);
			assertEquals(VARIABLE_NAME3,
					((Variable) exp.getRightExpression()).getName());
			assertLeftHandSideLeftAssociative(exp.getLeftExpression());
		}

		@Test
		public void exponentiationAssociativity_rightAssociative()
				throws Exception {
			ArithmeticOperatorExpression exp = parse(ternaryExpression(
					ArithmeticOperator.EXPONENTIATION,
					ArithmeticOperator.EXPONENTIATION));

			assertTrue(exp.getLeftExpression() instanceof Variable);
			assertEquals(VARIABLE_NAME,
					((Variable) exp.getLeftExpression()).getName());
			assertTrue(exp.getRightExpression() instanceof ArithmeticOperatorExpression);
			assertRightHandSideRightAssociative(exp.getRightExpression());
		}

		@Test(expected = ParserSyntaxException.class)
		public void lessThanAssociativity_exceptionThrown() throws Exception {
			parse(ternaryExpression(RelationalOperator.LESS,
					RelationalOperator.LESS));
		}

		@Test(expected = ParserSyntaxException.class)
		public void lessThanOrEqualToAssociativity_exceptionThrown()
				throws Exception {
			parse(ternaryExpression(RelationalOperator.LESS_EQUAL,
					RelationalOperator.LESS_EQUAL));
		}

		@Test(expected = ParserSyntaxException.class)
		public void greaterThanAssociativity_exceptionThrown() throws Exception {
			parse(ternaryExpression(RelationalOperator.GREATER,
					RelationalOperator.GREATER));
		}

		@Test(expected = ParserSyntaxException.class)
		public void greaterThanOrEqualToAssociativity_exceptionThrown()
				throws Exception {
			parse(ternaryExpression(RelationalOperator.GREATER_EQUAL,
					RelationalOperator.GREATER_EQUAL));
		}

		@Test(expected = ParserSyntaxException.class)
		public void equalToAssociativity_exceptionThrown() throws Exception {
			parse(ternaryExpression(RelationalOperator.EQUAL,
					RelationalOperator.EQUAL));
		}

		@Test(expected = ParserSyntaxException.class)
		public void notEqualToAssociativity_exceptionThrown() throws Exception {
			parse(ternaryExpression(RelationalOperator.NOT_EQUAL,
					RelationalOperator.NOT_EQUAL));
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
		public void dotAssociativity_leftAssociative() throws Exception {
			Variable exp = parse(ternaryExpression(".", "."));

			assertEquals(VARIABLE_NAME3, exp.getName());
			assertTrue(exp.getExpression() instanceof Variable);

			Variable innerExp = (Variable) exp.getExpression();
			assertEquals(VARIABLE_NAME2, innerExp.getName());
			assertTrue(innerExp.getExpression() instanceof Variable);

			Variable innerestExp = (Variable) innerExp.getExpression();
			assertEquals(VARIABLE_NAME, innerestExp.getName());
			assertNull(innerestExp.getExpression());
		}

		/* Associativity END */

		@Test
		public void expressionInsideParentheses_success() throws Exception {
			Variable var = parse("(" + VARIABLE_NAME + ")");

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
			CallStatementExpression exp = parse(String.format(CALL_STATEMENT,
					VARIABLE_NAME2));

			assertEquals(VARIABLE_NAME, exp.getCallStatement().getName());
		}

		@Test
		public void newExpression_success() throws Exception {
			NewExpression exp = parse("new " + CLASS_NAME);

			assertEquals(CLASS_NAME, exp.getClassType().getName());
		}

		@Test
		public void variable_success() throws Exception {
			Variable var = parse(VARIABLE_NAME);

			assertEquals(VARIABLE_NAME, var.getName());
		}
	}

	@RunWith(Parameterized.class)
	public static class ParseMethodBinaryOperator {

		@Parameter
		public Operator operator;

		@Parameters(name = "binary operator {0}")
		public static Iterable<Object[]> data() {
			List<Object[]> result = new ArrayList<Object[]>();

			for (Operator op : ArithmeticOperator.values()) {
				result.add(new Object[] { op });
			}

			for (Operator op : LogicOperator.values()) {
				result.add(new Object[] { op });
			}

			for (Operator op : RelationalOperator.values()) {
				result.add(new Object[] { op });
			}

			return result;
		}

		@Test
		public void binaryOperatorBetweenExpressions_success() throws Exception {
			StringBuilder sb = new StringBuilder();
			sb.append(VARIABLE_NAME);
			sb.append(operator.getSymbol());
			sb.append(VARIABLE_NAME2);

			BinaryOperatorExpression binop = parse(sb.toString());

			Variable leftVar = (Variable) binop.getLeftExpression();
			Variable rightVar = (Variable) binop.getRightExpression();

			assertEquals(VARIABLE_NAME, leftVar.getName());
			assertEquals(VARIABLE_NAME2, rightVar.getName());
			assertNull(leftVar.getExpression());
			assertNull(rightVar.getExpression());
		}
	}
}
