package syntaxtree.statements;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;
import syntaxtree.expressions.Expression;
import bytecode.CodeProcedure;
import bytecode.instructions.JMP;
import bytecode.instructions.JMPFALSE;
import bytecode.instructions.JMPTRUE;
import bytecode.instructions.NOP;

import compiler.ErrorMessage;
import compiler.JumpPlaceholder;
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
	public void checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		expression.checkSemantics(symbolTable);
		DataType conditionType = expression.getDataType();

		if (conditionType.getType() != Type.BOOL) {
			throw new SemanticException(ErrorMessage.NON_BOOL_EXPRESSION);
		}

		for (Statement statement : statements) {
			statement.checkSemantics(symbolTable);
		}
	}

	@Override
	public void generateCode(CodeProcedure procedure) {
		int start = procedure.addInstruction(new NOP());

		JumpPlaceholder placeholder = expression.generateBoolCode(procedure);

		int whileAction = procedure.addInstruction(new NOP());

		for (Statement statement : statements) {
			statement.generateCode(procedure);
		}

		int jump = procedure.addInstruction(new JMP(start + 1));

		if (placeholder != null && placeholder.isInverted()) {
			procedure.replaceInstruction(whileAction, new JMPTRUE(jump + 1));
		} else {
			procedure.replaceInstruction(whileAction, new JMPFALSE(jump + 1));
		}

		if (placeholder != null) {
			placeholder.jumpOnFalseTo(jump + 1);
			placeholder.jumpOnTrueTo(whileAction + 1);
		}
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("WHILE_STMT").addInline(expression)
				.addIndented(statements).build();
	}
}
