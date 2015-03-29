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

public class Compiler {

	public static final int SUCCESS = 0;
	public static final int SYNTAX_ERROR = 1;
	public static final int SEMANTIC_ERROR = 2;
	public static final int UNKNOWN_ERROR = 3;

	private String inFilename;
	private String astFilename;
	private String binFilename;

	public Compiler(String inFilename, String astFilename, String binFilename) {
		this.inFilename = inFilename;
		this.astFilename = astFilename;
		this.binFilename = binFilename;
	}

	public Result compile() throws IOException {
		InputStream inputStream = new FileInputStream(inFilename);
		Lexer lexer = new Lexer(new InputStreamReader(inputStream));
		parser parser = new parser(lexer);
		Program program = null;
		ErrorMessage error = null;

		try {
			program = (Program) parser.parse().value;
		} catch (ParserSyntaxException e) {
			System.out.println("caught ParserSyntaxException");
			error = new ErrorMessage(e.getMessage());
		} catch (Exception e) {
			System.out.println("caught general");
			error = new ErrorMessage(e.getMessage());
		}

		if (error != null || program == null) {
			return new Result(SYNTAX_ERROR, error);
		}

		error = program.checkSemantics();

		if (error != null) {
			return new Result(SEMANTIC_ERROR, error);
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
		program.generateCode(codeFile);
		byte[] bytecode = codeFile.getBytecode();
		DataOutputStream stream = new DataOutputStream(new FileOutputStream(
				this.binFilename));
		stream.write(bytecode);
		stream.close();
	}

	public static void main(String[] args) {
		Compiler compiler = new Compiler(args[0], args[1], args[2]);
		int code;

		try {
			Result result = compiler.compile();

			System.out.println(result.getError().getMessage());
			code = result.getCode();
		} catch (IOException e) {
			System.out.println("ERROR: " + e);
			code = UNKNOWN_ERROR;
		}

		System.exit(code);
	}
}
