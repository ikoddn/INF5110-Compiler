package syntaxtree.expressions;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.Name;
import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;
import syntaxtree.declarations.ClassDecl;
import syntaxtree.declarations.VariableDecl;
import bytecode.CodeProcedure;
import bytecode.instructions.GETFIELD;
import bytecode.instructions.Instruction;
import bytecode.instructions.LOADGLOBAL;
import bytecode.instructions.LOADLOCAL;
import bytecode.instructions.PUTFIELD;
import bytecode.instructions.STOREGLOBAL;
import bytecode.instructions.STORELOCAL;

import compiler.ErrorMessage;
import compiler.SymbolTable;
import compiler.throwable.SemanticException;

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
	public void generateCode(CodeProcedure procedure) {
		generateCodeForLoad(procedure);
	}

	public void generateCodeForLoad(CodeProcedure procedure) {
		// reference to struct
		// GETFIELD

		Instruction instruction;

		if (expression == null) {
			int number = procedure.variableNumber(name.getString());

			if (number == -1) {
				number = procedure.globalVariableNumber(name.getString());
				instruction = new LOADGLOBAL(number);
			} else {
				instruction = new LOADLOCAL(number);
			}
		} else {
			// TODO UNHACK THIS
			DataType dataType = null;
			try {
				dataType = expression.checkSemanticsIfNecessary(null);
			} catch (SemanticException e) {
				e.printStackTrace();
			}

			String structName = dataType.getName().getString();
			int fieldNumber = procedure.fieldNumber(structName,
					name.getString());
			int structNumber = procedure.structNumber(structName);

			instruction = new GETFIELD(fieldNumber, structNumber);
		}

		procedure.addInstruction(instruction);
	}

	public void generateCodeForStore(CodeProcedure procedure) {
		Instruction instruction;

		if (expression == null) {
			int number = procedure.variableNumber(name.getString());

			if (number == -1) {
				number = procedure.globalVariableNumber(name.getString());
				instruction = new STOREGLOBAL(number);
			} else {
				instruction = new STORELOCAL(number);
			}
		} else {
			// TODO UNHACK THIS
			DataType dataType = null;
			try {
				dataType = expression.checkSemanticsIfNecessary(null);
			} catch (SemanticException e) {
				e.printStackTrace();
			}

			String structName = dataType.getName().getString();
			int fieldNumber = procedure.fieldNumber(structName,
					name.getString());
			int structNumber = procedure.structNumber(structName);

			instruction = new PUTFIELD(fieldNumber, structNumber);
		}

		procedure.addInstruction(instruction);
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
