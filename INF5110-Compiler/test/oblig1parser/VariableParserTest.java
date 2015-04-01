package oblig1parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
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

import syntaxtree.expressions.Variable;

@RunWith(Enclosed.class)
public class VariableParserTest extends ParserBase {

	private static Symbol parseSymbol(String string) throws Exception {
		Scanner scanner = new Lexer(new StringReader(string));
		VarParser parser = new VarParser(scanner);
		parser.setSystemErrOutputSuppressed(true);

		return parser.parse();
	}

	private static Variable parse(String string) throws Exception {
		return (Variable) parseSymbol(string).value;
	}

	public static class ParseMethod {

		@Test
		public void lowercaseName_success() throws Exception {
			String name = "foo";

			assertEquals(name, parse(name).getName().getString());
		}

		@Test
		public void uppercaseName_success() throws Exception {
			String name = "FOO";

			assertEquals(name, parse(name).getName().getString());
		}

		@Test(expected = ParserSyntaxException.class)
		public void digitsName_exceptionThrown() throws Exception {
			parse("1234");
		}

		@Test
		public void digitsPrecededByLetterName_success() throws Exception {
			String name = "f1234";

			assertEquals(name, parse(name).getName().getString());
		}

		// @Test(expected = ScannerError.class)
		public void underscoreName_errorThrown() throws Exception {
			parse("_");
		}

		@Test
		public void underscorePrecededByLetterName_success() throws Exception {
			String name = "f_";

			assertEquals(name, parse(name).getName().getString());
		}

		// @Test(expected = ScannerError.class)
		public void dollarSymbolName_errorThrown() throws Exception {
			parse("$");
		}

		@Test
		public void scandinavianLetterName_success() throws Exception {
			parse("ø");
		}

		@Test
		public void expressionDotName_success() throws Exception {
			Variable var = parse(VARIABLE_NAME + "." + VARIABLE_NAME2);

			assertEquals(VARIABLE_NAME2, var.getName().getString());
			assertTrue(var.getExpression() instanceof Variable);

			Variable innerVar = (Variable) var.getExpression();
			assertEquals(VARIABLE_NAME, innerVar.getName().getString());
			assertNull(innerVar.getExpression());
		}
	}

	@RunWith(Parameterized.class)
	public static class ParseMethodKeyword {

		@Parameter
		public Keyword keyword;

		@Parameters(name = "keyword {0}")
		public static Iterable<Object[]> data() {
			List<Object[]> result = new ArrayList<Object[]>();

			for (Keyword keyword : Keyword.values()) {
				result.add(new Object[] { keyword });
			}

			return result;
		}

		@Test(expected = ParserSyntaxException.class)
		public void keyword_exceptionThrown() throws Exception {
			parse(keyword.toString());
		}
	}
}
