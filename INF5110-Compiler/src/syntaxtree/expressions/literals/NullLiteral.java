package syntaxtree.expressions.literals;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;
import bytecode.CodeProcedure;
import bytecode.instructions.PUSHNULL;

public class NullLiteral extends Literal {

	@Override
	public DataType getDataType() {
		return new DataType(Type.NULL);
	}

	@Override
	public void generateCode(CodeProcedure procedure) {
		procedure.addInstruction(new PUSHNULL());
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("NULL_LITERAL").build();
	}
}
