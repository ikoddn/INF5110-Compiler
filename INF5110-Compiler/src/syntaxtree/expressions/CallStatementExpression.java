package syntaxtree.expressions;

import java.util.List;

import syntaxtree.datatypes.DataType;
import syntaxtree.statements.CallStatement;
import bytecode.CodeProcedure;

import compiler.SymbolTable;
import compiler.throwable.SemanticException;

public class CallStatementExpression extends Expression {

	private CallStatement callStatement;

	public CallStatementExpression(CallStatement callStatement) {
		this.callStatement = callStatement;
	}

	public CallStatement getCallStatement() {
		return callStatement;
	}

	@Override
	public DataType getDataType() {
		return callStatement.getDataType();
	}

	@Override
	public void checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		callStatement.checkSemantics(symbolTable);
	}

	@Override
	public void generateCode(CodeProcedure procedure) {
		callStatement.generateCode(procedure);
	}

	@Override
	public List<String> makeAstStringList() {
		return callStatement.makeAstStringList();
	}
}
