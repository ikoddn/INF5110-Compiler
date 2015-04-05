package compiler;

import java.util.HashMap;
import java.util.Map;

import syntaxtree.Name;
import syntaxtree.datatypes.DataType;
import syntaxtree.declarations.ClassDecl;
import syntaxtree.declarations.Decl;
import syntaxtree.declarations.ProcedureDecl;
import syntaxtree.declarations.VariableDecl;

import compiler.exception.SemanticException;

public class SymbolTable {

	private Map<Name, ClassDecl> customTypes;
	private Map<Name, ProcedureDecl> procedures;
	private Map<Name, VariableDecl> variables;

	public SymbolTable() {
		customTypes = new HashMap<Name, ClassDecl>();
		procedures = new HashMap<Name, ProcedureDecl>();
		variables = new HashMap<Name, VariableDecl>();
	}

	/**
	 * Copy constructor for {@code SymbolTable}.
	 * 
	 * @param other
	 *            - The {@code SymbolTable} to copy from.
	 */
	public SymbolTable(SymbolTable other) {
		this();

		customTypes.putAll(other.customTypes);
		procedures.putAll(other.procedures);
		variables.putAll(other.variables);
	}

	public ProcedureDecl lookupProcedure(Name name) throws SemanticException {
		ProcedureDecl result = procedures.get(name);

		if (result == null) {
			throw new SemanticException(ErrorMessage.UNDECLARED_PROCEDURE, name);
		}

		return result;
	}

	public ClassDecl lookupType(DataType type) throws SemanticException {
		return lookupType(type.getName());
	}

	public ClassDecl lookupType(Name name) throws SemanticException {
		ClassDecl result = customTypes.get(name);

		if (result == null) {
			throw new SemanticException(ErrorMessage.UNDECLARED_TYPE, name);
		}

		return result;
	}

	public VariableDecl lookupVariable(Name name) throws SemanticException {
		VariableDecl result = variables.get(name);

		if (result == null) {
			throw new SemanticException(ErrorMessage.UNDECLARED_VARIABLE, name);
		}

		return result;
	}

	public void insert(ClassDecl decl) throws SemanticException {
		insert(decl, customTypes);
	}

	public void insert(ProcedureDecl decl) throws SemanticException {
		insert(decl, procedures);
	}

	public void insert(VariableDecl decl) throws SemanticException {
		insert(decl, variables);
	}

	private <T extends Decl> void insert(T decl, Map<Name, T> map)
			throws SemanticException {
		Name name = decl.getName();

		if (customTypes.containsKey(name) || procedures.containsKey(name)
				|| variables.containsKey(name)) {
			throw new SemanticException(ErrorMessage.DUPLICATE_DECLARATION,
					name);
		}

		map.put(name, decl);
	}
}
