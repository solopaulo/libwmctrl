package au.com.twobit.wm;

public class DesktopMissingException extends Exception {
	public DesktopMissingException() {
		super();
	}
	
	public DesktopMissingException(String message) {
		super(message);
	}
	
	public DesktopMissingException(String message,Exception x) {
		super(message,x);
	}
}
