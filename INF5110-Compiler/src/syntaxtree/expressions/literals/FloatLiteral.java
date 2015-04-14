package syntaxtree.expressions.literals;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;
import bytecode.CodeProcedure;
import bytecode.instructions.PUSHFLOAT;

public class FloatLiteral extends Literal {

	private Float number;

	public FloatLiteral(Float number) {
		this.number = number;
	}

	public Float getNumber() {
		return number;
	}

	@Override
	public DataType getDataType() {
		return new DataType(Type.FLOAT);
	}

	@Override
	public void generateCode(CodeProcedure procedure) {
		procedure.addInstruction(new PUSHFLOAT(number));
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("FLOAT_LITERAL").addInline(
				number.toString()).build();
	}
}
