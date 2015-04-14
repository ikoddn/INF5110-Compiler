package syntaxtree.expressions.literals;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;
import bytecode.CodeProcedure;
import bytecode.instructions.PUSHSTRING;
import compiler.SymbolTable;
import compiler.throwable.SemanticException;

public class StringLiteral extends Literal {

	private String text;

	public StringLiteral(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	@Override
	protected DataType checkSemantics(SymbolTable parentSymbolTable)
			throws SemanticException {
		return new DataType(Type.STRING);
	}

	@Override
	public void generateCode(CodeProcedure procedure) {
		int id = procedure.addStringConstant(text);
		procedure.addInstruction(new PUSHSTRING(id));
	}

	@Override
	public List<String> makeAstStringList() {
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		sb.append(text);
		sb.append("\"");

		return new AstStringListBuilder("STRING_LITERAL").addInline(
				sb.toString()).build();
	}
}
