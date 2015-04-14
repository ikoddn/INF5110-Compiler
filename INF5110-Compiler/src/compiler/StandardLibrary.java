package compiler;

import java.util.LinkedList;
import java.util.List;

import syntaxtree.datatypes.Type;
import syntaxtree.declarations.ProcedureDecl;
import bytecode.CodeFile;
import bytecode.CodeProcedure;
import bytecode.type.CodeType;

import compiler.throwable.SemanticException;

public class StandardLibrary {

	private List<ProcedureSignature> list;

	public StandardLibrary() {
		list = new LinkedList<ProcedureSignature>();

		list.add(new ProcedureSignature("readint", Type.INT));
		list.add(new ProcedureSignature("readfloat", Type.FLOAT));
		list.add(new ProcedureSignature("readchar", Type.INT));
		list.add(new ProcedureSignature("readstring", Type.STRING));
		list.add(new ProcedureSignature("readline", Type.STRING));
		list.add(new ProcedureSignature("printint", Type.VOID).addParameter(
				false, "i", Type.INT));
		list.add(new ProcedureSignature("printfloat", Type.VOID).addParameter(
				false, "f", Type.FLOAT));
		list.add(new ProcedureSignature("printstr", Type.VOID).addParameter(
				false, "s", Type.STRING));
		list.add(new ProcedureSignature("printline", Type.VOID).addParameter(
				false, "s", Type.STRING));
	}

	public void insertInto(SymbolTable symbolTable) throws SemanticException {
		for (ProcedureSignature signature : list) {
			ProcedureDecl decl = signature.buildDecl();
			symbolTable.insert(decl);
		}
	}

	public void generateCode(CodeFile codeFile) {
		for (ProcedureSignature signature : list) {
			String name = signature.getName().getString();
			CodeType returnType = signature.getReturnType().getByteCodeType(
					codeFile);

			codeFile.addProcedure(name);
			CodeProcedure procedure = new CodeProcedure(name, returnType,
					codeFile);
			codeFile.updateProcedure(procedure);
		}
	}
}
