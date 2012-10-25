package au.com.twobit.wm.wmctrl;

public class WmctrlParseException extends Exception {
	public WmctrlParseException() {
	}
	
	public WmctrlParseException(String message) {
		super(message);
	}
	
	public WmctrlParseException(String message,Exception x) {
		super(message,x);
	}
}
