package oblig1parser;

import static org.junit.Assert.assertEquals;
import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import syntaxtree.ClassDecl;
import syntaxtree.VariableDecl;

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
			String classString = String.format(CLASS, "");
			ClassDecl classDecl = (ClassDecl) parse(classString).value;

			assertEquals(CLASS_NAME, classDecl.getName());
		}

		@Test
		public void oneVariableDeclInClass_success() throws Exception {
			String classString = String.format(CLASS, VARIABLE);
			ClassDecl classDecl = (ClassDecl) parse(classString).value;
			VariableDecl variableDecl = classDecl.getVariableDecls().get(0);

			assertEquals(VARIABLE_NAME, variableDecl.getName());
		}

		@Test
		public void twoVariableDeclInClass_success() throws Exception {
			String classString = String.format(CLASS, VARIABLE + VARIABLE);
			ClassDecl classDecl = (ClassDecl) parse(classString).value;
			VariableDecl variableDecl1 = classDecl.getVariableDecls().get(0);
			VariableDecl variableDecl2 = classDecl.getVariableDecls().get(1);

			assertEquals(VARIABLE_NAME, variableDecl1.getName());
			assertEquals(VARIABLE_NAME, variableDecl2.getName());
		}
	}
}
