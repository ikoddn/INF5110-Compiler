package syntaxtree.expressions.literals;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;

import compiler.SymbolTable;
import compiler.exception.SemanticException;

public class IntLiteral extends Literal {

	private Integer number;

	public IntLiteral(Integer number) {
		this.number = number;
	}

	public Integer getNumber() {
		return number;
	}

	@Override
	public DataType determineType(SymbolTable symbolTable) throws SemanticException {
		return new DataType(Type.INT);
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("INT_LITERAL").addInline(
				number.toString()).build();
	}
}
