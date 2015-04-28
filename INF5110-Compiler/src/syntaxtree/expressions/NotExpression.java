package syntaxtree.expressions;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;
import bytecode.CodeProcedure;
import bytecode.instructions.NOT;

import compiler.ErrorMessage;
import compiler.JumpPlaceholder;
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
	public DataType getDataType() {
		return new DataType(Type.BOOL);
	}

	@Override
	public void checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		expression.checkSemantics(symbolTable);
		DataType dataType = expression.getDataType();

		if (dataType.getType() != Type.BOOL) {
			throw new SemanticException(ErrorMessage.NOT_OPERATOR_UNDEFINED,
					dataType.getName());
		}
	}

	@Override
	public void generateCode(CodeProcedure procedure) {
		expression.generateCode(procedure);
		procedure.addInstruction(new NOT());
	}

	@Override
	public JumpPlaceholder generateBoolCode(CodeProcedure procedure) {
		JumpPlaceholder placeholder = expression.generateBoolCode(procedure);

		if (placeholder != null) {
			placeholder.invert();
		} else {
			procedure.addInstruction(new NOT());
		}

		return placeholder;
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("NOT").addInline(expression).build();
	}
}
