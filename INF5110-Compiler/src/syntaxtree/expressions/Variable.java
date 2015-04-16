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
import compiler.throwable.SemanticError;
import compiler.throwable.SemanticException;

public class Variable extends Expression {

	private Name name;
	private Expression expression;
	private DataType dataType;

	public Variable(Name name) {
		this(name, null);
	}

	public Variable(Name name, Expression expression) {
		this.name = name;
		this.expression = expression;
		dataType = null;
	}

	public Name getName() {
		return name;
	}

	public Expression getExpression() {
		return expression;
	}

	@Override
	public DataType getDataType() {
		if (dataType == null) {
			throw new SemanticError(ErrorMessage.UNDETERMINED_TYPE);
		}

		return dataType;
	}

	@Override
	public void checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		if (expression == null) {
			VariableDecl decl = symbolTable.lookupVariable(name);
			dataType = decl.getDataType();

			return;
		}

		expression.checkSemantics(symbolTable);
		DataType expressionType = expression.getDataType();

		if (expressionType.getType() != Type.CLASS) {
			throw new SemanticException(ErrorMessage.FIELD_PRIMITIVE_TYPE, name);
		}

		ClassDecl classDecl = symbolTable.lookupType(expressionType);

		for (VariableDecl variableDecl : classDecl.getVariableDecls()) {
			if (name.equals(variableDecl.getName())) {
				dataType = variableDecl.getDataType();

				return;
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
			expression.generateCode(procedure);

			DataType expressionType = expression.getDataType();

			String structName = expressionType.getName().getString();
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
			expression.generateCode(procedure);

			DataType expressionType = expression.getDataType();

			String structName = expressionType.getName().getString();
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
