package au.com.twobit.wm;

import java.awt.Point;
import java.util.Collection;

import au.com.twobit.wm.config.Corner;
import au.com.twobit.wm.config.UpdatePolicy;

public interface WindowManager {
	
	public void setUpdatePolicy(UpdatePolicy policy);
	public Collection<Desktop> getDesktops();
	public void setDesktops(Collection<Desktop> desktops);
	public Desktop getDesktop(ManagedWindow window) throws WindowMissingException,DesktopMissingException;
	public Desktop getDesktop(int desktopId) throws DesktopMissingException;
	
	public Collection<ManagedWindow> getWindows();
	public void setWindows(Collection<ManagedWindow> windows);
	public Collection<ManagedWindow> getUserWindows();
	
	public ManagedWindow findWindowById(String windowId) throws WindowMissingException;
	public Collection<ManagedWindow> searchWindowsByName(String search) throws WindowMissingException;

	public WindowManager swapTo(ManagedWindow window) throws WindowMissingException;
	public WindowManager moveWindow(ManagedWindow window, Point xy) throws WindowMissingException;
	public WindowManager moveWindow(ManagedWindow window, Corner corner) throws WindowMissingException,DesktopMissingException;
	public WindowManager resizeWindow(ManagedWindow window, int width, int height) throws WindowMissingException;
	public WindowManager maximizeWindow(ManagedWindow window) throws WindowMissingException;
	public WindowManager unmaximizeWindow(ManagedWindow window) throws WindowMissingException;
	public WindowManager toggleFullScreen(ManagedWindow window) throws WindowMissingException;
	public WindowManager raise(ManagedWindow window) throws WindowMissingException;
	public WindowManager unraise(ManagedWindow window) throws  WindowMissingException;	
}
