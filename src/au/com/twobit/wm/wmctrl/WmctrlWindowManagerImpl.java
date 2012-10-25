package au.com.twobit.wm.wmctrl;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;

import au.com.twobit.wm.Desktop;
import au.com.twobit.wm.DesktopMissingException;
import au.com.twobit.wm.ManagedWindow;
import au.com.twobit.wm.WindowManager;
import au.com.twobit.wm.WindowMissingException;
import au.com.twobit.wm.config.Corner;
import au.com.twobit.wm.config.UpdatePolicy;

public class WmctrlWindowManagerImpl implements WindowManager {
	
	private UpdatePolicy policy = UpdatePolicy.REALTIME;
	private Collection<Desktop>desktops;
	private Collection<ManagedWindow>windows;
	
	@Override
	public void setUpdatePolicy(UpdatePolicy policy) {
		this.policy = policy;
	}

	@Override
	public Collection<Desktop> getDesktops() {
		if ( UpdatePolicy.REALTIME == policy ) {
			return WmctrlExec.listDesktops();
		}
		
		return desktops;
	}


	@Override
	public Collection<ManagedWindow> getUserWindows() {
		Collection<ManagedWindow>userWindows = new ArrayList<ManagedWindow>();
		for (ManagedWindow w : getWindows()) {
			if ( ! w.isUserWindow() ) {
				continue;
			}
			userWindows.add(w);
		}
		return userWindows;
	}

	@Override
	public void setDesktops(Collection<Desktop> desktops) {
		this.desktops = desktops;
	}



	@Override
	public Desktop getDesktop(ManagedWindow w) throws WindowMissingException,DesktopMissingException {
		ManagedWindow window = verifyWindow(w);
		return getDesktop(window.getDesktop());
	}

	@Override
	public Desktop getDesktop(int desktopId) throws DesktopMissingException {
		Desktop desktop = null;
		for(Desktop d : getDesktops()) {
			if ( d.getId() != desktopId ) {
				continue;
			}
			desktop = d;
			break;
		}
		if ( desktop == null ) {
			throw new DesktopMissingException("No desktop with id "+desktopId);
		}
		return desktop;
	}
	
	@Override
	public Collection<ManagedWindow> getWindows() {
		if ( UpdatePolicy.REALTIME == policy ) {
			return WmctrlExec.listWindows();
		}
		return windows;
	}

	@Override
	public void setWindows(Collection<ManagedWindow> windows) {
		this.windows = windows;
	}

	@Override
	public ManagedWindow findWindowById(String windowId) throws WindowMissingException {
		for (ManagedWindow window : getWindows()) {
			if ( window.getId() != null && window.getId().equals(windowId) ) {
				return window;
			}
		}
		
		throw new WindowMissingException(String.format("Cannot find window with id %s",windowId));
	}

	private ManagedWindow verifyWindow(ManagedWindow w) throws WindowMissingException{
		ManagedWindow window = null;
		try {
			window = findWindowById(w.getId());
		} catch (Exception x) {
			throw new WindowMissingException("Window not valid: "+x.getMessage());
		}
		return window;
	}
	
	@Override
	public Collection<ManagedWindow> searchWindowsByName(String search) throws WindowMissingException {
		if ( search == null ) {
			throw new WindowMissingException("searchWindowsByName: No search term provided");
		}
		search = search.trim().toLowerCase();
		
		Collection<ManagedWindow>windows = getWindows();
		Collection<ManagedWindow>found = new ArrayList<ManagedWindow>();
		for (ManagedWindow w: windows) {
			if ( w.getTitle() == null || 
					(w.getTitle().toLowerCase().indexOf(search) < 0 && w.getWmClass().toLowerCase().indexOf(search) < 0 ) ) {
				continue;
			}
			found.add(w);
		}
		
		return found;
	}

	@Override
	public WindowManager moveWindow(ManagedWindow w, Point xy) throws WindowMissingException {
		ManagedWindow window = verifyWindow(w);
		WmctrlExec.moveWindow(window.getId(), xy);
		return this;
	}

	@Override
	public WindowManager moveWindow(ManagedWindow w, Corner corner) throws WindowMissingException,DesktopMissingException {
		ManagedWindow window = verifyWindow(w);		
		Desktop desktop = getDesktop(window);
		
		Point point = null;
		if ( corner == Corner.TOPLEFT ) {
			point = new Point(0,0);
		} else if ( corner == Corner.TOPRIGHT ) {
			point = new Point( desktop.getUsableWidth()-window.getWidth(),0);
		} else if ( corner == Corner.BOTTOMLEFT ) {
			point = new Point(0, desktop.getUsableHeight()-window.getHeight());
		} else if ( corner == Corner.BOTTOMRIGHT ) {
			point = new Point(desktop.getUsableWidth()-window.getWidth(), desktop.getUsableHeight()-window.getHeight());
		} else if ( corner == Corner.CENTRE_SCREEN ) {
			point = new Point( desktop.getUsableWidth()/2 - window.getWidth()/2,
								desktop.getUsableHeight()/2-window.getHeight()/2);
		}
		
		if ( point != null ) {
			WmctrlExec.moveWindow(window.getId(), point);
		}
		return this;
	}

	@Override
	public WindowManager resizeWindow(ManagedWindow w, int width, int height) throws WindowMissingException {
		ManagedWindow window = verifyWindow(w);		
		WmctrlExec.resizeWindow(window.getId(), width, height);
		return this;
	}

	@Override
	public WindowManager maximizeWindow(ManagedWindow w) throws WindowMissingException {
		ManagedWindow window = verifyWindow(w);
		WmctrlExec.maximizeWindow(window.getId());
		return this;
	}

	@Override
	public WindowManager unmaximizeWindow(ManagedWindow w) throws WindowMissingException {
		ManagedWindow window = verifyWindow(w);
		WmctrlExec.minimizeWindow(window.getId());
		return this;
	}

	@Override
	public WindowManager toggleFullScreen(ManagedWindow w) throws WindowMissingException {
		ManagedWindow window = verifyWindow(w);
		WmctrlExec.toggleFullscreen(window.getId());
		return this;
	}

	@Override
	public WindowManager raise(ManagedWindow w) throws WindowMissingException {
		ManagedWindow window = verifyWindow(w);
		WmctrlExec.raiseWindow(window.getId());
		return this;
	}

	@Override
	public WindowManager unraise(ManagedWindow w) throws WindowMissingException {
		ManagedWindow window = verifyWindow(w);
		WmctrlExec.unraiseWindow(window.getId());
		return this;
	}

	@Override
	public WindowManager swapTo(ManagedWindow w) throws WindowMissingException {
		ManagedWindow window = verifyWindow(w);
		WmctrlExec.swapTo(window.getId());
		return this;
	}
}
