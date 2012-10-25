package au.com.twobit.wm.wmctrl;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

import au.com.twobit.wm.Desktop;
import au.com.twobit.wm.ManagedWindow;
import au.com.twobit.wm.WindowManager;
import au.com.twobit.wm.config.Corner;

public final class WmctrlExec {
	private static String WM_EXEC 							= "/usr/bin/wmctrl";
	private static String WM_EXEC_FLAG_WINDOW_PROPERTY 	= "-b";
	private static String WM_EXEC_FLAG_MOVE_WINDOW			= "-e";
	private static String WM_EXEC_FLAG_WINDOW_SELECT		= "-ir";
	private static String WM_EXEC_FLAG_WINDOW_SWAPTO		= "-a";
	private static String WM_EXEC_OPTIONS_HEX_WINDOWID		= "-i";
	private static String WM_EXEC_OPTIONS_DESKTOP			= "-d";
	private static String WM_EXEC_OPTIONS_WINDOW			= "-lxpG";
	private static String WM_EXEC_MOVE_WINDOW_OPTIONS		= "%s,%s,%s,%s,%s";
	private static String WM_EXEC_WINDOW_PROPERTY_FULLSCREEN
														= "toggle,fullscreen";
	private static String WM_EXEC_WINDOW_PROPERTY_MAXIMIZE
										= "add,maximized_horz,maximized_vert";
	private static String WM_EXEC_WINDOW_PROPERTY_MINIMIZE
										= "remove,maximized_horz,maximized_vert";
	private static String WM_EXEC_WINDOW_PROPERTY_RAISE	= "add,above";
	private static String WM_EXEC_WINDOW_PROPERTY_UNRAISE 	= "remove,above";	
	
	public static Collection<Desktop> listDesktops() {
		Collection<Desktop> desktops = new ArrayList<Desktop>();
		try {
			for (String line : getOutputFromCommand(WM_EXEC,WM_EXEC_OPTIONS_DESKTOP)) {
				desktops.add( DesktopImpl.parseFromWmctrl(line) );
			}
		} catch (Exception x) {
			x.printStackTrace();
		}
		return desktops;
	}
	
	public static Collection<ManagedWindow> listWindows() {
		Collection<ManagedWindow> windows = new ArrayList<ManagedWindow>();
		try {
			for (String line : getOutputFromCommand(WM_EXEC,WM_EXEC_OPTIONS_WINDOW)) {
				windows.add( ManagedWindowImpl.parseFromWmctrl(line) );
			}
		} catch (Exception x) {
			x.printStackTrace();			
		}
		return windows;
	}
	
	public static void moveWindow(String id, Point xy) {
		getOutputFromCommand(WM_EXEC,
							 WM_EXEC_FLAG_MOVE_WINDOW,
							 String.format(WM_EXEC_MOVE_WINDOW_OPTIONS,0,(int)xy.getX(),(int)xy.getY(),-1,-1),
							 WM_EXEC_FLAG_WINDOW_SELECT,
							 id);		
	}
	
	public static void toggleFullscreen(String windowId) {
		getOutputFromCommand(WM_EXEC,
							 WM_EXEC_FLAG_WINDOW_PROPERTY,
							 WM_EXEC_WINDOW_PROPERTY_FULLSCREEN,
							 WM_EXEC_FLAG_WINDOW_SELECT,
							 windowId);
	}
	
	public static void resizeWindow(String id, int width, int height) {
		getOutputFromCommand(WM_EXEC,
							 WM_EXEC_FLAG_MOVE_WINDOW,
							 String.format(WM_EXEC_MOVE_WINDOW_OPTIONS,0,-1,-1,width,height),
							 WM_EXEC_FLAG_WINDOW_SELECT,
							 id);
	}

	public static void minimizeWindow(String id) {
		getOutputFromCommand(WM_EXEC,
							 WM_EXEC_FLAG_WINDOW_PROPERTY,
							 WM_EXEC_WINDOW_PROPERTY_MINIMIZE,
							 WM_EXEC_FLAG_WINDOW_SELECT,
							 id);
	}
	
	public static void maximizeWindow(String id) {
		getOutputFromCommand(WM_EXEC,
				 WM_EXEC_FLAG_WINDOW_PROPERTY,
				 WM_EXEC_WINDOW_PROPERTY_MAXIMIZE,
				 WM_EXEC_FLAG_WINDOW_SELECT,
				 id);
	}
	
	public static void raiseWindow(String id) {
		getOutputFromCommand(WM_EXEC,
				 WM_EXEC_FLAG_WINDOW_PROPERTY,
				 WM_EXEC_WINDOW_PROPERTY_RAISE,
				 WM_EXEC_FLAG_WINDOW_SELECT,
				 id);
	}
	
	public static void unraiseWindow(String id) {
		getOutputFromCommand(WM_EXEC,
				 WM_EXEC_FLAG_WINDOW_PROPERTY,
				 WM_EXEC_WINDOW_PROPERTY_UNRAISE,
				 WM_EXEC_FLAG_WINDOW_SELECT,
				 id);
	}
	
	public static void swapTo(String id) {
		getOutputFromCommand(WM_EXEC,
				WM_EXEC_FLAG_WINDOW_SWAPTO,
				id,
				WM_EXEC_OPTIONS_HEX_WINDOWID);
	}
	private static Collection<String> getOutputFromCommand(String ... params) {
		Collection<String>output = new ArrayList<String>();
		ProcessBuilder builder = new ProcessBuilder(params);
		Process p = null;
		try {
			p = builder.start();
			BufferedReader reader = new BufferedReader( new InputStreamReader( p.getInputStream()) );
			String line = null;
			while ( (line = reader.readLine()) != null ) {
				output.add(line);
			}
			p.waitFor();
			reader.close();
		} catch (Exception x) {
			x.printStackTrace();
		}
		return output;
	}
	
	public static void main(String [] args) {
		try {
			int wait = 5;
			WindowManager manager = new WmctrlWindowManagerImpl();
			ManagedWindow window = manager.searchWindowsByName("terminal").iterator().next();
			manager.swapTo(window);
			
			for (int i = 0; i < 5; i++) {
				manager.moveWindow(window, Corner.TOPLEFT);
				manager.moveWindow(window, Corner.TOPRIGHT);
				manager.moveWindow(window, Corner.BOTTOMRIGHT);
				manager.moveWindow(window, Corner.BOTTOMLEFT);			
			}
				
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
}
