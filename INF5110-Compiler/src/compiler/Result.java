package compiler;

public class Result {

	private int code;
	private ErrorMessage error;

	public Result(int code, ErrorMessage error) {
		this.code = code;
		this.error = error;
	}

	public int getCode() {
		return code;
	}

	public ErrorMessage getError() {
		return error;
	}
}
