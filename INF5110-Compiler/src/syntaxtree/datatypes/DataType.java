package syntaxtree.datatypes;

import syntaxtree.AstStringBuilder;
import syntaxtree.Name;
import syntaxtree.SimpleAstNode;
import bytecode.CodeFile;
import bytecode.CodeProcedure;
import bytecode.type.BoolType;
import bytecode.type.CodeType;
import bytecode.type.FloatType;
import bytecode.type.IntType;
import bytecode.type.RefType;
import bytecode.type.StringType;
import bytecode.type.VoidType;

import compiler.SymbolTable;
import compiler.throwable.SemanticException;

public class DataType extends SimpleAstNode {

	protected Name name;
	protected Type type;

	public DataType(Name name) {
		this.name = name;
		this.type = Type.CLASS;
	}

	public DataType(Type type) {
		this.name = type.getName();
		this.type = type;
	}

	public Name getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	private CodeType getPrimitiveByteCodeType() {
		switch (type) {
		case BOOL:
			return BoolType.TYPE;
		case FLOAT:
			return FloatType.TYPE;
		case INT:
			return IntType.TYPE;
		case STRING:
			return StringType.TYPE;
		case VOID:
			return VoidType.TYPE;
		case NULL:
		default:
			return null;
		}
	}

	public CodeType getByteCodeType(CodeFile codeFile) {
		if (type == Type.CLASS) {
			int structNumber = codeFile.structNumber(name.getString());
			return new RefType(structNumber);
		}

		return getPrimitiveByteCodeType();
	}

	public CodeType getByteCodeType(CodeProcedure procedure) {
		if (type == Type.CLASS) {
			int structNumber = procedure.structNumber(name.getString());
			return new RefType(structNumber);
		}

		return getPrimitiveByteCodeType();
	}

	public boolean isA(DataType other) {
		if (type == other.type) {
			if (type == Type.CLASS) {
				return name.equals(other.name);
			}

			return true;
		} else if (type == Type.INT) {
			return other.type == Type.FLOAT;
		} else if (type == Type.NULL) {
			return other.type == Type.CLASS;
		}

		return false;
	}

	@Override
	public void checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
	}

	@Override
	public String makeAstString() {
		return new AstStringBuilder("TYPE").addInline(name.getString()).build();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataType other = (DataType) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
