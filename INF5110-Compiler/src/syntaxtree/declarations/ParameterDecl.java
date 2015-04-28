package syntaxtree.declarations;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.Name;
import syntaxtree.datatypes.DataType;
import bytecode.CodeFile;
import bytecode.CodeProcedure;
import bytecode.type.CodeType;

import compiler.ErrorMessage;
import compiler.throwable.CodeGenerationError;

public class ParameterDecl extends VariableDecl {

	private boolean reference;

	public ParameterDecl(boolean reference, Name name, DataType dataType) {
		super(name, dataType);

		this.reference = reference;
	}

	public boolean isReference() {
		return reference;
	}

	@Override
	public void generateCode(CodeFile codeFile) {
		throw new CodeGenerationError(ErrorMessage.PARAMDECL_NOT_IN_PROCEDURE);
	}

	@Override
	public void generateCode(CodeProcedure procedure) {
		CodeType codeType = dataType.getByteCodeType(procedure);
		procedure.addParameter(name.getString(), codeType);
	}

	@Override
	public List<String> makeAstStringList() {
		String label = reference ? "PARAM_DECL ref" : "PARAM_DECL";
		return new AstStringListBuilder(label).addInline(dataType, name)
				.build();
	}
}
