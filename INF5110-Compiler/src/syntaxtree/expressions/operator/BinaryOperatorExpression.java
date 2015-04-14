package syntaxtree.expressions.operator;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.expressions.Expression;
import syntaxtree.operators.Operator;
import bytecode.CodeProcedure;
import bytecode.instructions.Instruction;

public abstract class BinaryOperatorExpression<O extends Operator> extends
		Expression {

	protected Expression leftExpression;
	protected O operator;
	protected Expression rightExpression;

	protected BinaryOperatorExpression(Expression leftExpression, O operator,
			Expression rightExpression) {
		this.leftExpression = leftExpression;
		this.operator = operator;
		this.rightExpression = rightExpression;
	}

	public Expression getLeftExpression() {
		return leftExpression;
	}

	public O getOperator() {
		return operator;
	}

	public Expression getRightExpression() {
		return rightExpression;
	}

	@Override
	public final void generateCode(CodeProcedure procedure) {
		leftExpression.generateCode(procedure);
		rightExpression.generateCode(procedure);
		procedure.addInstruction(getByteCodeInstruction());
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder(operator.makeAstLabel()).addIndented(
				leftExpression, rightExpression).build();
	}

	protected abstract Instruction getByteCodeInstruction();
}
