package syntaxtree.expressions.literals;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;

import compiler.SymbolTable;
import compiler.exception.SemanticException;

public class FloatLiteral extends Literal {

	private Float number;

	public FloatLiteral(Float number) {
		this.number = number;
	}

	public Float getNumber() {
		return number;
	}

	@Override
	public DataType getType(SymbolTable symbolTable) throws SemanticException {
		return new DataType(Type.FLOAT);
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("FLOAT_LITERAL").addInline(
				number.toString()).build();
	}
}
