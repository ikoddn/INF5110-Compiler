package syntaxtree.statements;

import java.util.List;

import syntaxtree.actualparameters.ActualParameter;

public class CallStatement extends Statement {

	private String name;
	private List<ActualParameter> actualParameters;

	public CallStatement(String name, List<ActualParameter> actualParameters) {
		this.name = name;
		this.actualParameters = actualParameters;
	}

	public String getName() {
		return name;
	}

	public List<ActualParameter> getActualParameters() {
		return actualParameters;
	}

	@Override
	public List<String> makeAstPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append("CALL_STMT (NAME ");
		sb.append(name);
		sb.append(")");

		return makeParentAstPrint(sb.toString(), actualParameters);
	}
}
