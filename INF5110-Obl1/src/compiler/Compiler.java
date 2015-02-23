package compiler;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;

import oblig1parser.Lexer;
import oblig1parser.parser;
import syntaxtree.Program;

public class Compiler {
	private String inFilename = null;
	private String outFilename = null;

	public Compiler(String inFilename, String outFilename) {
		this.inFilename = inFilename;
		this.outFilename = outFilename;
	}

	public void compile() throws Exception {
		InputStream inputStream = null;
		inputStream = new FileInputStream(this.inFilename);
		Program program = parse(inputStream);
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
				this.outFilename));

		for (String line : program.makeAstPrint()) {
			bufferedWriter.write(line);
			bufferedWriter.newLine();
		}

		bufferedWriter.close();
	}

	// Testable code
	public Program parse(InputStream input) throws Exception {
		Lexer lexer = new Lexer(input);
		parser parser = new parser(lexer);

		return (Program) parser.parse().value;
	}

	public static void main(String[] args) {
		Compiler compiler = new Compiler(args[0], args[1]);
		try {
			compiler.compile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
