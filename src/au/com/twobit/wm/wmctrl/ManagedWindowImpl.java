package au.com.twobit.wm.wmctrl;

import java.awt.Point;
import java.util.StringTokenizer;
import au.com.twobit.wm.ManagedWindow;

public class ManagedWindowImpl implements ManagedWindow {

	private String id;
	private int pid;
	private int desktop;
	private String wmClass;
	private String hostname;
	private Point coords;
	private int height;
	private int width;
	private String title;
	
	transient private static final String SPACE = " ";
	
	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setPid(int pid) {
		this.pid = pid;
	}

	@Override
	public int getPid() {
		return pid;
	}

	@Override
	public void setDesktop(int desktop) {
		this.desktop = desktop;
	}

	@Override
	public int getDesktop() {
		return desktop;
	}

	@Override
	public void setWmClass(String wmClass) {
		this.wmClass = wmClass;
	}

	@Override
	public String getWmClass() {
		return wmClass;
	}

	@Override
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	@Override
	public String getHostname() {
		return hostname;
	}

	@Override
	public void setCoords(Point xy) {
		this.coords = xy;
	}

	@Override
	public Point getCoords() {
		return coords;
	}

	@Override
	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public String getTitle() {
		return title;
	}
	
	public static ManagedWindow parseFromWmctrl(String line) throws WmctrlParseException {
		if ( line == null || line.trim().length() == 0 ) {
			throw new WmctrlParseException();
		}
		
		StringTokenizer tokenizer = new StringTokenizer(line,SPACE);
		int pos = 0;
		int x = 0,y = 0;
		ManagedWindow window = new ManagedWindowImpl();
		try {
			StringBuffer title = new StringBuffer();
			while ( tokenizer.hasMoreTokens() ) {
				String token = tokenizer.nextToken();
				switch( pos ) {
				case	0:
					window.setId(token);
					break;
				case 	1:
					window.setDesktop( Integer.parseInt(token) );
					break;
				case	2:
					window.setPid( Integer.parseInt(token) );
					break;
				case	3:
					x = Integer.parseInt(token);
					break;
				case 	4:
					y = Integer.parseInt(token);
					window.setCoords( new Point(x,y) );
					break;
				case 	5:
					window.setWidth( Integer.parseInt(token) );
					break;
				case	6:
					window.setHeight( Integer.parseInt(token) );
					break;
				case 7:
					window.setWmClass( token );
					break;
				case 8:
					window.setHostname(token);
					break;
					default:
						if ( title.length() > 0 ) {
							title.append(SPACE);
						}
						title.append(token);
						break;
				}
				pos++;
			}
			window.setTitle(title.toString());
		} catch (Exception exception) {
			throw new WmctrlParseException("Couldn't parse Wmctrl Window output: "+exception.getMessage());
		}
		
		return window;
		
	}

	@Override
	public int compareTo(ManagedWindow o) {
		if ( o == null || o.getId() == null ) {
			return 1;
		} else if ( getId() == null ) {
			return -1;
		}
		return getId().compareTo( o.getId() );
	}

	@Override
	public boolean isUserWindow() {
		return ! (ManagedWindow.SYSTEM_IDENTIFIER.equals(getHostname()) &&
				   ManagedWindow.SYSTEM_IDENTIFIER.equals(getWmClass()));
	}
}
