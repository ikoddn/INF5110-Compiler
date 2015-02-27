package tools;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CupFileGenerator {

	private static final String START_WITH = "start with ";

	private String inputFile;

	public CupFileGenerator(String inputFile) {
		this.inputFile = inputFile;
	}

	public void generateWithStartWith(String newStartWith, String outputFile)
			throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(inputFile));
		String cupFile = new String(encoded, StandardCharsets.UTF_8);

		int endFirst = cupFile.indexOf(START_WITH) + START_WITH.length();
		int beginSecond = cupFile.indexOf(";", endFirst);

		StringBuilder sb = new StringBuilder();
		sb.append(cupFile.substring(0, endFirst));
		sb.append(newStartWith);
		sb.append(cupFile.substring(beginSecond));

		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
		writer.write(sb.toString());
		writer.close();
	}
}
