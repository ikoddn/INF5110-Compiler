package syntaxtree.expressions.literals;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;

import compiler.SymbolTable;
import compiler.exception.SemanticException;

public class NullLiteral extends Literal {

	@Override
	public DataType getType(SymbolTable symbolTable) throws SemanticException {
		return new DataType(Type.NULL);
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("NULL_LITERAL").build();
	}
}
