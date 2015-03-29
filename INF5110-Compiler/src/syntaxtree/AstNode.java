package syntaxtree;

import java.util.List;

import compiler.ErrorMessage;

public abstract class AstNode {

	public ErrorMessage checkSemantics() {
		// TODO make abstract
		return null;
	}
	
	public abstract List<String> makeAstStringList();

}
