package syntaxtree.actualparameters;

import java.util.List;

import syntaxtree.AstNode;
import syntaxtree.AstStringListBuilder;
import syntaxtree.datatypes.DataType;
import syntaxtree.expressions.Expression;

import compiler.SymbolTable;
import compiler.exception.SemanticException;

public abstract class ActualParameter extends AstNode {

	protected boolean reference;
	protected Expression expression;

	protected ActualParameter(boolean reference, Expression expression) {
		this.reference = reference;
		this.expression = expression;
	}

	public boolean isReference() {
		return reference;
	}

	public Expression getExpression() {
		return expression;
	}

	@Override
	protected DataType checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		return expression.checkSemanticsIfNecessary(symbolTable);
	}

	@Override
	public List<String> makeAstStringList() {
		String label = reference ? "ACTUAL_PARAM ref" : "ACTUAL_PARAM";
		return new AstStringListBuilder(label).addInline(expression).build();
	}
}
