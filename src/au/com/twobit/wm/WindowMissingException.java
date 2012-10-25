package au.com.twobit.wm;

public class WindowMissingException extends Exception {
	public WindowMissingException() {
	}
	
	public WindowMissingException(String message) {
		super(message);
	}
	
	public WindowMissingException(String message, Exception x) {
		super(message,x);
	}
}
