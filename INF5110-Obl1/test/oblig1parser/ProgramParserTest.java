package oblig1parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import syntaxtree.ClassDecl;
import syntaxtree.Program;
import syntaxtree.VariableDecl;

@RunWith(Enclosed.class)
public class ProgramParserTest extends ParserTest {

	public static class ParseMethod {

		private Symbol parseSymbol(String string) throws Exception {
			Scanner scanner = new Lexer(toInputStream(string));
			parser parser = new parser(scanner);
			return parser.parse();
		}

		private Program parse(String string) throws Exception {
			return (Program) parseSymbol(string).value;
		}

		@Test
		public void emptyProgram_success() throws Exception {
			assertTrue(parseSymbol(String.format(PROGRAM, "")).value instanceof Program);
		}

		@Test
		public void lineCommentBeforeEmptyProgram_success() throws Exception {
			parse(String.format(LINECOMMENT + "\n" + PROGRAM, ""));
		}

		@Test
		public void lineCommentInsideEmptyProgram_success() throws Exception {
			parse(String.format(PROGRAM, LINECOMMENT + "\n"));
		}

		@Test
		public void variableDeclInProgram_success() throws Exception {
			Program program = (Program) parseSymbol(String.format(PROGRAM,
					VARIABLE)).value;

			assertTrue(program.getDecls().get(0) instanceof VariableDecl);
			assertEquals(VARIABLE_NAME, program.getDecls().get(0).getName());
		}

		@Test
		public void classDeclInProgram_success() throws Exception {
			String classString = String.format(CLASS, "");
			Program program = (Program) parseSymbol(String.format(PROGRAM,
					classString)).value;

			assertTrue(program.getDecls().get(0) instanceof ClassDecl);
			assertEquals(CLASS_NAME, program.getDecls().get(0).getName());
		}

		@Test
		public void twoDeclInProgram_success() throws Exception {
			String classString = String.format(CLASS, "");
			parseSymbol(String.format(PROGRAM, VARIABLE + classString));
		}
	}
}
