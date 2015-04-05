package compiler;

import java.util.LinkedList;
import java.util.List;

import syntaxtree.datatypes.Type;
import syntaxtree.declarations.ProcedureDecl;

import compiler.exception.SemanticException;

public class StandardLibrary {

	public static void insertInto(SymbolTable symbolTable)
			throws SemanticException {
		List<ProcedureDeclBuilder> list = new LinkedList<ProcedureDeclBuilder>();

		list.add(new ProcedureDeclBuilder("readint", Type.INT));
		list.add(new ProcedureDeclBuilder("readfloat", Type.FLOAT));
		list.add(new ProcedureDeclBuilder("readchar", Type.INT));
		list.add(new ProcedureDeclBuilder("readstring", Type.STRING));
		list.add(new ProcedureDeclBuilder("readline", Type.STRING));
		list.add(new ProcedureDeclBuilder("printint", Type.VOID).addParameter(
				false, "i", Type.INT));
		list.add(new ProcedureDeclBuilder("printfloat", Type.VOID)
				.addParameter(false, "f", Type.FLOAT));
		list.add(new ProcedureDeclBuilder("printstr", Type.VOID).addParameter(
				false, "s", Type.STRING));
		list.add(new ProcedureDeclBuilder("printline", Type.VOID).addParameter(
				false, "s", Type.STRING));

		for (ProcedureDeclBuilder builder : list) {
			ProcedureDecl decl = builder.build();
			decl.setTypeManually(decl.getReturnType());
			symbolTable.insert(decl);
		}
	}
}
