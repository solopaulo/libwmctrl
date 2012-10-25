package au.com.twobit.wm.wmctrl;

import java.util.StringTokenizer;

import au.com.twobit.wm.Desktop;

public class DesktopImpl implements Desktop {
	private int id = -1;
	private boolean active = false;
	private int width = 0;
	private int height = 0;
	private int usableWidth = 0;
	private int usableHeight = 0;
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public int getUsableWidth() {
		return usableWidth;
	}

	@Override
	public void setUsableWidth(int width) {
		this.usableWidth = width;
	}

	@Override
	public int getUsableHeight() {
		return usableHeight;
	}

	@Override
	public void setUsableHeight(int height) {
		this.usableHeight = height;
	}
	
	public static Desktop parseFromWmctrl(String line) throws WmctrlParseException {
		if ( line == null || line.trim().length() == 0 ) {
			throw new WmctrlParseException();
		}
		
		StringTokenizer tokenizer = new StringTokenizer(line," ");
		int pos = 0;
		Desktop desktop = new DesktopImpl();
		try {
			while ( tokenizer.hasMoreTokens() ) {
				String token = tokenizer.nextToken();
				switch( pos ) {
				case	0:
					desktop.setId( Integer.parseInt(token) );
					break;
				case 	1:
					desktop.setActive( token.equals("*") );
					break;
				case	2:
					break;
				case	3:
					desktop.setWidth( Integer.parseInt(token.substring(0, token.indexOf("x") ) ));
					desktop.setHeight( Integer.parseInt(token.substring(token.indexOf("x")+1 ) ));
					break;
				case 	4:
					break;
				case 	5:
					break;
				case	6:
					break;
				case 7:
					break;
				case 8:
					desktop.setUsableWidth( Integer.parseInt(token.substring(0, token.indexOf("x") ) ));
					desktop.setUsableHeight( Integer.parseInt(token.substring(token.indexOf("x") +1) ));
					break;
				}
				pos++;
			}
		} catch (Exception x) {
			throw new WmctrlParseException("Couldn't parse Wmctrl Desktop output: "+x.getMessage());
		}
		return desktop;
	}
}
