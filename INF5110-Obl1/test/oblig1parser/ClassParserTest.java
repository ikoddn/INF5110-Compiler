package oblig1parser;

import static org.junit.Assert.assertEquals;
import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import syntaxtree.ClassDecl;

@RunWith(Enclosed.class)
public class ClassParserTest extends ParserTest {

	public static class ParseMethod {

		private Symbol parse(String string) throws Exception {
			Scanner scanner = new Lexer(toInputStream(string));
			ClassParser parser = new ClassParser(scanner);
			return parser.parse();
		}

		@Test
		public void emptyClass_success() throws Exception {
			ClassDecl value = (ClassDecl) parse(String.format(CLASS, "")).value;
			assertEquals(CLASS_NAME, value.getName());
		}
	}
}
