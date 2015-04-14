package compiler;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import oblig1parser.Lexer;
import oblig1parser.ParserSyntaxException;
import oblig1parser.parser;
import syntaxtree.Program;
import bytecode.CodeFile;

import compiler.throwable.SemanticException;

public class Compiler {

	public static final int SUCCESS = 0;
	public static final int SYNTAX_ERROR = 1;
	public static final int SEMANTIC_ERROR = 2;
	public static final int UNKNOWN_ERROR = 3;

	private String inFilename;
	private String astFilename;
	private String binFilename;
	private StandardLibrary standardLibrary;

	public Compiler(String inFilename, String astFilename, String binFilename) {
		this.inFilename = inFilename;
		this.astFilename = astFilename;
		this.binFilename = binFilename;

		standardLibrary = new StandardLibrary();
	}

	public Result compile() throws IOException {
		InputStream inputStream = new FileInputStream(inFilename);
		Lexer lexer = new Lexer(new InputStreamReader(inputStream));
		parser parser = new parser(lexer);
		Program program = null;
		String error = null;

		try {
			program = (Program) parser.parse().value;
		} catch (ParserSyntaxException e) {
			error = e.getMessage();
		} catch (Exception e) {
			error = e.getMessage();
		}

		if (error != null || program == null) {
			return new Result(SYNTAX_ERROR, error);
		}

		SymbolTable symbolTable = new SymbolTable();

		try {
			standardLibrary.insertInto(symbolTable);
			program.checkSemantics(symbolTable);
		} catch (SemanticException e) {
			return new Result(SEMANTIC_ERROR, e.getMessage());
		}

		writeAst(program);

		generateCode(program);

		return new Result(SUCCESS, null);
	}

	private void writeAst(Program program) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
				astFilename));
		List<String> astStringList = program.makeAstStringList();

		for (String line : astStringList) {
			bufferedWriter.write(line);
			bufferedWriter.newLine();
		}

		bufferedWriter.close();
	}

	private void generateCode(Program program) throws IOException {
		CodeFile codeFile = new CodeFile();

		standardLibrary.generateCode(codeFile);
		program.generateCode(codeFile);

		byte[] bytecode = codeFile.getBytecode();
		DataOutputStream stream = new DataOutputStream(new FileOutputStream(
				binFilename));
		stream.write(bytecode);
		stream.close();
	}

	public static void main(String[] args) {
		Compiler compiler = new Compiler(args[0], args[1], args[2]);
		int code;

		try {
			Result result = compiler.compile();
			code = result.getCode();
			String message = result.getMessage();

			if (message != null) {
				System.out.println(message);
			} else {
				System.out.println("Compiled successfully!");
			}
		} catch (IOException e) {
			System.out.println("ERROR: " + e);
			code = UNKNOWN_ERROR;
		}

		System.exit(code);
	}
}
