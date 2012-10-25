package au.com.twobit.wm;

import java.awt.Point;

public interface ManagedWindow extends Comparable<ManagedWindow> {
	public final String SYSTEM_IDENTIFIER = "N/A";
	
	public void setId(String id);
	public String getId();
	
	public void setPid(int pid);
	public int getPid();
	
	public void setDesktop(int desktop);
	public int getDesktop();
	
	public void setWmClass(String wmclass);
	public String getWmClass();
	
	public void setHostname(String hostname);
	public String getHostname();
	
	public void setCoords(Point xy);
	public Point getCoords();
	
	public void setHeight(int height);
	public int getHeight();
	
	public void setWidth(int width);
	public int getWidth();
	
	public void setTitle(String title);
	public String getTitle();
	
	public boolean isUserWindow();
}
