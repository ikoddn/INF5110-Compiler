package syntaxtree.statements;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;
import syntaxtree.expressions.Expression;
import bytecode.CodeProcedure;
import bytecode.instructions.JMP;
import bytecode.instructions.JMPFALSE;
import bytecode.instructions.NOP;
import compiler.ErrorMessage;
import compiler.SymbolTable;
import compiler.throwable.SemanticException;

public class WhileStatement extends Statement {

	private Expression expression;
	private List<Statement> statements;

	public WhileStatement(Expression expression, List<Statement> statements) {
		this.expression = expression;
		this.statements = statements;
	}

	public Expression getExpression() {
		return expression;
	}

	public List<Statement> getStatements() {
		return statements;
	}

	@Override
	protected DataType checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		DataType dataType = expression.checkSemanticsIfNecessary(symbolTable);

		if (dataType.getType() != Type.BOOL) {
			throw new SemanticException(ErrorMessage.NON_BOOL_EXPRESSION);
		}

		for (Statement statement : statements) {
			statement.checkSemanticsIfNecessary(symbolTable);
		}

		return new DataType(Type.VOID);
	}

	@Override
	public void generateCode(CodeProcedure procedure) {
		int start = procedure.addInstruction(new NOP());
		
		expression.generateCode(procedure);
		
		int whileAction = procedure.addInstruction(new NOP());
		
		for (Statement statement : statements) {
			statement.generateCode(procedure);
		}
		
		procedure.addInstruction(new JMP(start));
		
		int afterPosition = procedure.addInstruction(new NOP());
		procedure.replaceInstruction(whileAction, new JMPFALSE(afterPosition));
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("WHILE_STMT").addInline(expression)
				.addIndented(statements).build();
	}
}
