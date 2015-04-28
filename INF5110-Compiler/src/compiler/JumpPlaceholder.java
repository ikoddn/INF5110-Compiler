package compiler;

import bytecode.CodeProcedure;
import bytecode.instructions.JMPFALSE;
import bytecode.instructions.JMPTRUE;

public class JumpPlaceholder {

	private int place;
	private CodeProcedure procedure;

	private boolean inverted;
	private boolean jumpOnFalse;
	private boolean jumpOnTrue;

	public JumpPlaceholder(CodeProcedure procedure, int place) {
		this.place = place;
		this.procedure = procedure;

		inverted = false;
		jumpOnFalse = false;
		jumpOnTrue = false;
	}

	public boolean isInverted() {
		return inverted;
	}

	public void jumpOnFalse() {
		jumpOnFalse = true;
	}

	public void jumpOnTrue() {
		jumpOnTrue = true;
	}

	public void invert() {
		inverted = !inverted;
	}

	public void jumpOnFalseTo(int jumpOnFalseTo) {
		if (jumpOnFalse && !inverted) {
			procedure.replaceInstruction(place, new JMPFALSE(jumpOnFalseTo));
		} else if (!jumpOnFalse && inverted) {
			procedure.replaceInstruction(place, new JMPTRUE(jumpOnFalseTo));
		}
	}

	public void jumpOnTrueTo(int jumpOnTrueTo) {
		if (jumpOnTrue && !inverted) {
			procedure.replaceInstruction(place, new JMPTRUE(jumpOnTrueTo));
		} else if (!jumpOnTrue && inverted) {
			procedure.replaceInstruction(place, new JMPFALSE(jumpOnTrueTo));
		}
	}
}
