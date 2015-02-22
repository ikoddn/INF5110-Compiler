package compiler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class CompilerTest {
	
	private static InputStream toInputStream(String string) {
		return new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
	}

	public static class ParseMethod {
		
		private static final String PROGRAM = "program { %s }";
		private static final String CLASS = "class Foo { %s }";
		private static final String VARIABLE = "var bar : int;";
		private static final String PROCEDURE = "proc baz : int (%s) { %s }";
		private static final String PROCEDURE_VOID = "proc baz (%s) { %s }";
		private static final String PARAMETER = "qux : int";
		private static final String PARAMETER_REF = "ref qux : int";
		
		private void parse(String string) throws Exception {
			Compiler compiler = new Compiler("", "");
			compiler.parse(toInputStream(string));
		}
		
		@Test
		public void emptyProgram_success() throws Exception {
			parse(String.format(PROGRAM, ""));
		}
		
		@Test
		public void variableDecl_success() throws Exception {
			parse(String.format(PROGRAM, VARIABLE));
		}
		
		@Test
		public void emptyClass_success() throws Exception {
			String classString = String.format(CLASS, "");
			parse(String.format(PROGRAM, classString));
		}
		
		@Test
		public void twoDeclInProgram_success() throws Exception {
			String classString = String.format(CLASS, "");
			parse(String.format(PROGRAM, VARIABLE + classString));
		}
		
		@Test
		public void oneVariableDeclInClass_success() throws Exception {
			String classString = String.format(CLASS, VARIABLE);
			parse(String.format(PROGRAM, classString));
		}
		
		@Test
		public void twoVariableDeclInClass_success() throws Exception {
			String classString = String.format(CLASS, VARIABLE + VARIABLE);
			parse(String.format(PROGRAM, classString));
		}
		
		@Test
		public void emptyVoidProcedure_success() throws Exception {
			String procString = String.format(PROCEDURE_VOID, "", "");
			parse(String.format(PROGRAM, procString));
		}
		
		@Test
		public void emptyProcedure_success() throws Exception {
			String procString = String.format(PROCEDURE, "", "");
			parse(String.format(PROGRAM, procString));
		}
		
		@Test
		public void emptyProcedureOneParameter_success() throws Exception {
			String procString = String.format(PROCEDURE, PARAMETER, "");
			parse(String.format(PROGRAM, procString));
		}
		
		@Test
		public void emptyProcedureOneRefParameter_success() throws Exception {
			String procString = String.format(PROCEDURE, PARAMETER_REF, "");
			parse(String.format(PROGRAM, procString));
		}
		
		@Test(expected = Exception.class)
		public void emptyProcedureOneParameterCommaFirst_exceptionThrown() throws Exception {
			String procString = String.format(PROCEDURE, ", " + PARAMETER, "");
			parse(String.format(PROGRAM, procString));
		}
		
		@Test(expected = Exception.class)
		public void emptyProcedureOneParameterCommaAfter_exceptionThrown() throws Exception {
			String procString = String.format(PROCEDURE, PARAMETER + ",", "");
			parse(String.format(PROGRAM, procString));
		}
		
		@Test
		public void emptyProcedureTwoParameters_success() throws Exception {
			String procString = String.format(PROCEDURE, PARAMETER + ", " + PARAMETER, "");
			parse(String.format(PROGRAM, procString));
		}
	}
}
