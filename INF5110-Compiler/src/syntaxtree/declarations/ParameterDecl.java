package syntaxtree.declarations;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.Name;
import syntaxtree.datatypes.DataType;

public class ParameterDecl extends Decl {

	private boolean reference;
	private DataType dataType;

	public ParameterDecl(boolean reference, Name name, DataType dataType) {
		super(name);

		this.reference = reference;
		this.dataType = dataType;
	}

	public boolean isReference() {
		return reference;
	}

	public DataType getDataType() {
		return dataType;
	}

	@Override
	public List<String> makeAstStringList() {
		String label = reference ? "PARAM_DECL ref" : "PARAM_DECL";
		return new AstStringListBuilder(label).addInline(dataType, name)
				.build();
	}
}
