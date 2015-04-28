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

public class IfStatement extends Statement {

	private Expression expression;
	private List<Statement> ifBodyStatements;
	private List<Statement> elseBodyStatements;

	public IfStatement(Expression expression, List<Statement> ifBodyStatements,
			List<Statement> elseBodyStatements) {
		this.expression = expression;
		this.ifBodyStatements = ifBodyStatements;
		this.elseBodyStatements = elseBodyStatements;
	}

	public Expression getExpression() {
		return expression;
	}

	public List<Statement> getIfBodyStatements() {
		return ifBodyStatements;
	}

	public List<Statement> getElseBodyStatements() {
		return elseBodyStatements;
	}

	@Override
	public void checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		expression.checkSemantics(symbolTable);
		DataType conditionType = expression.getDataType();

		if (conditionType.getType() != Type.BOOL) {
			throw new SemanticException(ErrorMessage.NON_BOOL_EXPRESSION);
		}

		for (Statement statement : ifBodyStatements) {
			statement.checkSemantics(symbolTable);
		}

		for (Statement statement : elseBodyStatements) {
			statement.checkSemantics(symbolTable);
		}
	}

	@Override
	public void generateCode(CodeProcedure procedure) {
		JumpPlaceholder placeholder = expression.generateBoolCode(procedure);

		int ifAction = procedure.addInstruction(new NOP());

		for (Statement statement : ifBodyStatements) {
			statement.generateCode(procedure);
		}

		int ifBodyDone = procedure.addInstruction(new NOP());

		if (!elseBodyStatements.isEmpty()) {
			for (Statement statement : elseBodyStatements) {
				statement.generateCode(procedure);
			}

			int elseBodyDone = procedure.addInstruction(new NOP());

			procedure.replaceInstruction(ifBodyDone, new JMP(elseBodyDone));
		}

		if (placeholder != null && placeholder.isInverted()) {
			procedure.replaceInstruction(ifAction, new JMPTRUE(ifBodyDone + 1));
		} else {
			procedure
					.replaceInstruction(ifAction, new JMPFALSE(ifBodyDone + 1));
		}

		if (placeholder != null) {
			placeholder.jumpOnFalseTo(ifBodyDone + 1);
			placeholder.jumpOnTrueTo(ifAction + 1);
		}
	}

	@Override
	public List<String> makeAstStringList() {
		AstStringListBuilder ast = new AstStringListBuilder("IF_STMT");

		AstStringListBuilder ifBody = new AstStringListBuilder("");
		ifBody.addIndented(ifBodyStatements);

		ast.addInline(expression).addIndented(ifBody);

		if (!elseBodyStatements.isEmpty()) {
			AstStringListBuilder elseBody = new AstStringListBuilder("");
			elseBody.addIndented(elseBodyStatements);

			ast.add("ELSE").addIndented(elseBody);
		}

		return ast.build();
	}
}
