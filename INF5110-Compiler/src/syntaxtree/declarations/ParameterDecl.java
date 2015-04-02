package syntaxtree.declarations;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.Name;
import syntaxtree.datatypes.DataType;

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
	public List<String> makeAstStringList() {
		String label = reference ? "PARAM_DECL ref" : "PARAM_DECL";
		return new AstStringListBuilder(label).addInline(dataType, name)
				.build();
	}
}
