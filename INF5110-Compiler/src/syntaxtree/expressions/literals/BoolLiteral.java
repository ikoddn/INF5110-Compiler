package syntaxtree.expressions.literals;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;

import compiler.SymbolTable;
import compiler.exception.SemanticException;

public class BoolLiteral extends Literal {

	private Boolean bool;

	public BoolLiteral(Boolean bool) {
		this.bool = bool;
	}

	public Boolean getBool() {
		return bool;
	}

	@Override
	public DataType determineType(SymbolTable symbolTable) throws SemanticException {
		return new DataType(Type.BOOL);
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("BOOL_LITERAL").addInline(
				bool.toString()).build();
	}
}
