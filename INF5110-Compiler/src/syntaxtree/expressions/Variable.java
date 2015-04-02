package syntaxtree.expressions;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.Name;
import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;
import syntaxtree.declarations.ClassDecl;
import syntaxtree.declarations.VariableDecl;

import compiler.ErrorMessage;
import compiler.SymbolTable;
import compiler.exception.SemanticException;

public class Variable extends Expression {

	private Name name;
	private Expression expression;

	public Variable(Name name) {
		this(name, null);
	}

	public Variable(Name name, Expression expression) {
		this.name = name;
		this.expression = expression;
	}

	public Name getName() {
		return name;
	}

	public Expression getExpression() {
		return expression;
	}

	@Override
	protected DataType checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		if (expression == null) {
			return symbolTable.lookupVariable(name).checkSemanticsIfNecessary(
					symbolTable);
		}

		DataType expressionType = expression
				.checkSemanticsIfNecessary(symbolTable);

		if (expressionType.getType() != Type.CLASS) {
			throw new SemanticException(ErrorMessage.FIELD_PRIMITIVE_TYPE, name);
		}

		ClassDecl classDecl = symbolTable.lookupType(expressionType);

		for (VariableDecl variableDecl : classDecl.getVariableDecls()) {
			if (variableDecl.getName().equals(name)) {
				return variableDecl.checkSemanticsIfNecessary(symbolTable);
			}
		}

		throw new SemanticException(ErrorMessage.UNDECLARED_FIELD, name,
				classDecl.getName());
	}

	@Override
	public List<String> makeAstStringList() {
		if (expression == null) {
			return name.makeAstStringList();
		}

		return new AstStringListBuilder(" .").addInline(expression, name)
				.build();
	}
}
