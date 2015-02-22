package compiler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Before;
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
		private static final String CLASS = "class %s { %s }";
		
		private Compiler compiler;

		@Before
		public void setUpBefore() {
			compiler = new Compiler("", "");
		}
		
		@Test
		public void emptyProgram_success() throws Exception {
			String programString = String.format(PROGRAM, "");
			
			compiler.parse(toInputStream(programString));
		}
		
		@Test
		public void emptyClass_success() throws Exception {
			String classString = String.format(CLASS, "Name", "");
			String programString = String.format(PROGRAM, classString);
			
			compiler.parse(toInputStream(programString));
		}
	}
}
