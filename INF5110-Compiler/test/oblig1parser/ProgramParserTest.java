package oblig1parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;

import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import syntaxtree.Program;
import syntaxtree.declarations.ClassDecl;
import syntaxtree.declarations.VariableDecl;

@RunWith(Enclosed.class)
public class ProgramParserTest extends ParserBase {

	private static Symbol parseSymbol(String string) throws Exception {
		Scanner scanner = new Lexer(new StringReader(string));
		parser parser = new parser(scanner);
		return parser.parse();
	}

	private static Program parse(String string) throws Exception {
		return (Program) parseSymbol(string).value;
	}

	public static class ParseMethod {

		@Test
		public void emptyProgram_success() throws Exception {
			Program program = parse(String.format(PROGRAM, ""));

			assertTrue(program.getDecls().isEmpty());
		}

		@Test
		public void lineCommentBeforeEmptyProgram_success() throws Exception {
			Program program = parse(String.format(LINECOMMENT + "\n" + PROGRAM,
					""));

			assertTrue(program.getDecls().isEmpty());
		}

		@Test
		public void lineCommentInsideEmptyProgram_success() throws Exception {
			Program program = parse(String.format(PROGRAM, LINECOMMENT + "\n"));

			assertTrue(program.getDecls().isEmpty());
		}

		@Test
		public void variableDeclInProgram_success() throws Exception {
			Program program = parse(String.format(PROGRAM, VARIABLE));

			assertEquals(1, program.getDecls().size());
			assertTrue(program.getDecls().get(0) instanceof VariableDecl);
		}

		@Test
		public void classDeclInProgram_success() throws Exception {
			Program program = parse(String.format(PROGRAM,
					String.format(CLASS, "")));

			assertEquals(1, program.getDecls().size());
			assertTrue(program.getDecls().get(0) instanceof ClassDecl);
		}

		@Test
		public void twoDeclInProgram_success() throws Exception {
			Program program = parse(String.format(PROGRAM,
					VARIABLE + String.format(CLASS, "")));

			assertEquals(2, program.getDecls().size());
		}
	}
}
