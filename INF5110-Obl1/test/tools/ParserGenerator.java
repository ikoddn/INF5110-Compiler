package tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java_cup.internal_error;

public class ParserGenerator {

	private CupFileGenerator cupGen;
	private String outputBase;

	public ParserGenerator(String cupFile, String outputBase) {
		cupGen = new CupFileGenerator(cupFile);
		this.outputBase = outputBase;
	}

	public void generate(String nonTerminal, String parserPrefix)
			throws internal_error, Exception {
		String newCupFile = outputBase + nonTerminal + ".cup";
		cupGen.generateWithStartWith(nonTerminal, newCupFile);
		generateParser(newCupFile, parserPrefix);
	}

	public static void generateParser(String cupFile, String prefix)
			throws internal_error, IOException, Exception {

		String parserName = prefix + "Parser";
		String symName = prefix + "Sym";

		List<String> cupArgs = new ArrayList<String>();
		cupArgs.add("-destdir");
		cupArgs.add("test-gen/oblig1parser");

		cupArgs.add("-parser");
		cupArgs.add(parserName);

		cupArgs.add("-symbols");
		cupArgs.add(symName);

		cupArgs.add("-interface");
		cupArgs.add("-nowarn");
		cupArgs.add("-nosummary");

		cupArgs.add(cupFile);

		java_cup.Main.main(cupArgs.toArray(new String[] {}));
	}

	public static void main(String[] args) throws internal_error, Exception {
		ParserGenerator pg = new ParserGenerator("grammars/oblig1.cup",
				"test-grammars/");

		pg.generate("class_decl", "Class");
		pg.generate("exp", "Exp");
		pg.generate("var", "Var");
		pg.generate("stmt", "Statement");
		
		System.out.println("Done.");
	}
}
