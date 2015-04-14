package syntaxtree.expressions;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;
import bytecode.CodeProcedure;
import bytecode.instructions.NOT;
import compiler.ErrorMessage;
import compiler.SymbolTable;
import compiler.throwable.SemanticException;

public class NotExpression extends Expression {

	private Expression expression;

	public NotExpression(Expression expression) {
		this.expression = expression;
	}

	public Expression getExpression() {
		return expression;
	}

	@Override
	protected DataType checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		DataType dataType = expression.checkSemanticsIfNecessary(symbolTable);

		if (dataType.getType() != Type.BOOL) {
			throw new SemanticException(ErrorMessage.NOT_OPERATOR_UNDEFINED,
					dataType.getName());
		}

		return new DataType(Type.BOOL);
	}

	@Override
	public void generateCode(CodeProcedure procedure) {
		expression.generateCode(procedure);
		procedure.addInstruction(new NOT());
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("NOT").addInline(expression).build();
	}
}
