package syntaxtree.declarations;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.Name;
import bytecode.CodeFile;
import bytecode.CodeProcedure;
import bytecode.CodeStruct;

import compiler.SymbolTable;
import compiler.throwable.CodeGenerationError;
import compiler.throwable.SemanticException;

public class ClassDecl extends Decl {

	private List<VariableDecl> variableDecls;

	public ClassDecl(Name name, List<VariableDecl> variableDecls) {
		super(name);

		this.variableDecls = variableDecls;
	}

	public List<VariableDecl> getVariableDecls() {
		return variableDecls;
	}

	@Override
	public void checkSemantics(SymbolTable parentSymbolTable)
			throws SemanticException {
		SymbolTable symbolTable = new SymbolTable(parentSymbolTable);

		for (VariableDecl variableDecl : variableDecls) {
			symbolTable.insertVariable(variableDecl);
			variableDecl.checkSemantics(symbolTable);
		}
	}

	@Override
	public void insertInto(SymbolTable symbolTable) throws SemanticException {
		symbolTable.insertType(this);
	}

	@Override
	public void generateCode(CodeFile codeFile) {
		codeFile.addStruct(name.getString());

		CodeStruct struct = new CodeStruct(name.getString());

		for (VariableDecl variableDecl : variableDecls) {
			variableDecl.generateCodeInStruct(struct, codeFile);
		}

		codeFile.updateStruct(struct);
	}

	@Override
	public void generateCode(CodeProcedure procedure) {
		throw new CodeGenerationError("Procedure inside class not allowed");
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("CLASS_DECL").addInline(name)
				.addIndented(variableDecls).build();
	}
}
