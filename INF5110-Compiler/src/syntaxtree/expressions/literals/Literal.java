package syntaxtree.expressions.literals;

import syntaxtree.expressions.Expression;

import compiler.SymbolTable;
import compiler.throwable.SemanticException;

public abstract class Literal extends Expression {

	@Override
	public void checkSemantics(SymbolTable parentSymbolTable)
			throws SemanticException {
	}
}
