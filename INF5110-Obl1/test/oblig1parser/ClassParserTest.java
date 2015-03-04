package oblig1parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;

import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import syntaxtree.declarations.ClassDecl;

@RunWith(Enclosed.class)
public class ClassParserTest extends ParserTest {

	private static Symbol parseSymbol(String string) throws Exception {
		Scanner scanner = new Lexer(new StringReader(string));
		ClassParser parser = new ClassParser(scanner);
		return parser.parse();
	}

	private static ClassDecl parse(String string) throws Exception {
		return (ClassDecl) parseSymbol(string).value;
	}

	public static class ParseMethod {

		@Test
		public void emptyClass_success() throws Exception {
			ClassDecl classDecl = parse(String.format(CLASS, ""));

			assertEquals(CLASS_NAME, classDecl.getName());
			assertTrue(classDecl.getVariableDecls().isEmpty());
		}

		@Test
		public void oneVariableDeclInClass_success() throws Exception {
			ClassDecl classDecl = parse(String.format(CLASS, VARIABLE));

			assertEquals(1, classDecl.getVariableDecls().size());
		}

		@Test
		public void twoVariableDeclInClass_success() throws Exception {
			ClassDecl classDecl = parse(String.format(CLASS, VARIABLE
					+ VARIABLE));

			assertEquals(2, classDecl.getVariableDecls().size());
		}
		
		@Test(expected = ParserSyntaxException.class)
		public void classDeclInClass_exceptionThrown() throws Exception {
			parse(String.format(CLASS, String.format(CLASS, "")));
		}
		
		@Test(expected = ParserSyntaxException.class)
		public void procedureDeclInClass_exceptionThrown() throws Exception {
			parse(String.format(CLASS, String.format(PROCEDURE, "", "")));
		}
	}
}
