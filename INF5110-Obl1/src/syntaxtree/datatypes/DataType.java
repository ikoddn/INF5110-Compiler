package syntaxtree.datatypes;

public abstract class DataType {

	protected String name;
	
	protected DataType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
