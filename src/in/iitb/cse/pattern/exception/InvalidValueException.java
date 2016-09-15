package in.iitb.cse.pattern.exception;

public class InvalidValueException extends Throwable {

	public InvalidValueException(Throwable e) {
		super(e);
	}

	public InvalidValueException(String msg) {
		super(msg);
	}
}
