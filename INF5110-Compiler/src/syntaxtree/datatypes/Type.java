package syntaxtree.datatypes;

import syntaxtree.Name;

public enum Type {
	BOOL("bool"),
	CLASS(null),
	FLOAT("float"),
	INT("int"),
	NULL("null"),
	STRING("string"),
	VOID("void");

	private Name name;

	private Type(String name) {
		if (name != null) {
			this.name = new Name(name);
		}
	}

	public Name getName() {
		return name;
	}

	public boolean isA(Type other) {
		if (this == other) {
			return true;
		}

		return this == INT && other == FLOAT;
	}
}
