package au.com.twobit.wm.wmctrl;

import au.com.twobit.wm.ExecutionException;

public class WmctrlExecutionException extends ExecutionException {

	public WmctrlExecutionException() {
	}

	public WmctrlExecutionException(String message) {
		super(message);
	}

	public WmctrlExecutionException(Throwable cause) {
		super(cause);
	}

	public WmctrlExecutionException(String message, Throwable cause) {
		super(message, cause);
	}

}
