package tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java_cup.internal_error;

public class ParserGenerator {

	public static void generate(String cupFile, String prefix)
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
		cupArgs.add(cupFile);

		java_cup.Main.main(cupArgs.toArray(new String[] {}));
	}

	public static void main(String[] args) throws internal_error, Exception {
		CupFileGenerator cupGen = new CupFileGenerator("grammars/oblig1.cup");
		String outputBase = "test-grammars/";
		
		cupGen.generateWithStartWith("class_decl", outputBase + "class_decl.cup");
		generate(outputBase + "class_decl.cup", "Class");
	}
}
