package syntaxtree.actualparameters;

import java.util.List;

import syntaxtree.AstNode;
import syntaxtree.AstStringListBuilder;
import syntaxtree.datatypes.DataType;
import syntaxtree.expressions.Expression;
import bytecode.CodeProcedure;

import compiler.SymbolTable;
import compiler.throwable.SemanticException;

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

	public DataType getDataType() {
		return expression.getDataType();
	}

	@Override
	public void checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		expression.checkSemantics(symbolTable);
	}

	@Override
	public List<String> makeAstStringList() {
		String label = reference ? "ACTUAL_PARAM ref" : "ACTUAL_PARAM";
		return new AstStringListBuilder(label).addInline(expression).build();
	}

	public abstract void generateCode(CodeProcedure procedure);
}
