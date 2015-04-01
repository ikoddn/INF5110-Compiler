package syntaxtree.expressions;

import java.util.List;

import syntaxtree.AstNode;
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
	public DataType getType(SymbolTable symbolTable) throws SemanticException {
		if (expression == null) {
			return symbolTable.lookup(name).getType(symbolTable);
		}

		DataType expressionType = expression.getType(symbolTable);

		if (expressionType.getType() != Type.CLASS) {
			throw new SemanticException(ErrorMessage.FIELD_PRIMITIVE_TYPE,
					name.getString());
		}

		Name expressionTypeName = expressionType.getName();
		AstNode astNode = symbolTable.lookup(expressionTypeName);

		if (!(astNode instanceof ClassDecl)) {
			throw new SemanticException(ErrorMessage.UNEXPECTED_NODETYPE,
					expressionTypeName.getString(), "class declaration");
		}

		ClassDecl classDecl = (ClassDecl) astNode;

		for (VariableDecl variableDecl : classDecl.getVariableDecls()) {
			if (variableDecl.getName().equals(name)) {
				return variableDecl.getType(symbolTable);
			}
		}

		throw new SemanticException(ErrorMessage.UNDECLARED_FIELD,
				name.getString(), classDecl.getName().getString());
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
