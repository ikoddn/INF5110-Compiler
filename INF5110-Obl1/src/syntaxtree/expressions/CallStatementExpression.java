package syntaxtree.expressions;

import java.util.List;

import syntaxtree.statements.CallStatement;

public class CallStatementExpression extends Expression {

	private CallStatement callStatement;

	public CallStatementExpression(CallStatement callStatement) {
		this.callStatement = callStatement;
	}

	public CallStatement getCallStatement() {
		return callStatement;
	}

	@Override
	public List<String> makeAstStringList() {
		return callStatement.makeAstStringList();
	}
}
