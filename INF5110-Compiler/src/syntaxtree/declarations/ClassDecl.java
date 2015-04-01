package syntaxtree.declarations;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.Name;
import syntaxtree.datatypes.DataType;

import compiler.SymbolTable;
import compiler.exception.SemanticException;

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
		SymbolTable symbolTable = parentSymbolTable.makeCopy();

		for (VariableDecl variableDecl : variableDecls) {
			symbolTable.insert(variableDecl.getName(), variableDecl);

			variableDecl.checkSemantics(symbolTable);
		}
	}

	@Override
	public DataType determineType(SymbolTable symbolTable) throws SemanticException {
		return new DataType(name);
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("CLASS_DECL").addInline(name)
				.addIndented(variableDecls).build();
	}
}
