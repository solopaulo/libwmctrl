package au.com.twobit.wm;

public interface Desktop {
	public int getId();
	public void setId(int id);
	
	public boolean isActive();
	public void setActive(boolean active);
	
	public int getWidth();
	public void setWidth(int width);
	public int getHeight();
	public void setHeight(int height);
	
	public int getUsableWidth();
	public void setUsableWidth(int width);
	public int getUsableHeight();
	public void setUsableHeight(int height);
}
