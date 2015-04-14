package syntaxtree.expressions.literals;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;
import bytecode.CodeProcedure;
import bytecode.instructions.PUSHNULL;
import compiler.SymbolTable;
import compiler.throwable.SemanticException;

public class NullLiteral extends Literal {

	@Override
	protected DataType checkSemantics(SymbolTable parentSymbolTable)
			throws SemanticException {
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
