package syntaxtree.expressions;

import java.util.List;

import syntaxtree.datatypes.DataType;
import syntaxtree.statements.CallStatement;

import compiler.SymbolTable;
import compiler.exception.SemanticException;

public class CallStatementExpression extends Expression {

	private CallStatement callStatement;

	public CallStatementExpression(CallStatement callStatement) {
		this.callStatement = callStatement;
	}

	public CallStatement getCallStatement() {
		return callStatement;
	}

	@Override
	protected DataType checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		return callStatement.checkSemanticsIfNecessary(symbolTable);
	}

	@Override
	public List<String> makeAstStringList() {
		return callStatement.makeAstStringList();
	}
}
