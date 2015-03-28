package compiler;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import oblig1parser.Lexer;
import oblig1parser.parser;
import syntaxtree.Program;
import bytecode.CodeFile;

public class Compiler {

	private String inFilename = null;
	private String astFilename = null;
	private String binFilename = null;
	public String syntaxError;
	public String error;

	public Compiler(String inFilename, String astFilename, String binFilename) {
		this.inFilename = inFilename;
		this.astFilename = astFilename;
		this.binFilename = binFilename;
	}

	public int compile() throws Exception {
		InputStream inputStream = new FileInputStream(inFilename);
		Lexer lexer = new Lexer(new InputStreamReader(inputStream));
		parser parser = new parser(lexer);
		Program program;

		try {
			program = (Program) parser.parse().value;
		} catch (Exception e) {
			// Do something here?
			throw e; // Or something.
		}
		writeAst(program);
		// Check semantics.
		if (false) { // If it is all ok:
			writeAst(program);
			generateCode(program);
			return 0;
		} else if (false) { // If there is a SYNTAX ERROR (Should not get that
							// for the tests):
			return 1;
		} else { // If there is a SEMANTIC ERROR (Should get that for the test
					// with "_fail" in the name):
			return 2;
		}
	}

	private void writeAst(Program program) throws Exception {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
				astFilename));
		List<String> astStringList = program.makeAstStringList();

		for (String line : astStringList) {
			bufferedWriter.write(line);
			bufferedWriter.newLine();
		}

		bufferedWriter.close();
	}

	private void generateCode(Program program) throws Exception {
		CodeFile codeFile = new CodeFile();
		program.generateCode(codeFile);
		byte[] bytecode = codeFile.getBytecode();
		DataOutputStream stream = new DataOutputStream(new FileOutputStream(
				this.binFilename));
		stream.write(bytecode);
		stream.close();
	}

	public static void main(String[] args) {
		Compiler compiler = new Compiler(args[0], args[1], args[2]);
		int result;

		try {
			result = compiler.compile();

			if (result == 1) {
				System.out.println(compiler.syntaxError);
			} else if (result == 2) {
				System.out.println(compiler.error);
			}

			System.exit(result);
		} catch (Exception e) {
			System.out.println("ERROR: " + e);
			// If unknown error.
			System.exit(3);
		}
	}
}
