package syntaxtree.statements;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.Name;
import syntaxtree.actualparameters.ActualParameter;

public class CallStatement extends Statement {

	private Name name;
	private List<ActualParameter> actualParameters;

	public CallStatement(Name name, List<ActualParameter> actualParameters) {
		this.name = name;
		this.actualParameters = actualParameters;
	}

	public Name getName() {
		return name;
	}

	public List<ActualParameter> getActualParameters() {
		return actualParameters;
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("CALL_STMT").addInline(name)
				.addIndented(actualParameters).build();
	}
}
