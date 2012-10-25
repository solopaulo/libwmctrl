package au.com.twobit.wm.wmctrl;

import java.awt.Point;

import org.junit.Assert;
import org.junit.Test;

import au.com.twobit.wm.ManagedWindow;

public class ManagedWindowImplTest {
	private final String SAMPLE_WMCTRL_OUTPUT_WINDOW_1 =
			"0x0400023d  0 7452   54   88   928  484  gnome-terminal.Gnome-terminal  dubuntu paul@dubuntu: ~/Downloads/eclipse";
	private final String SAMPLE_WMCTRL_OUTPUT_WINDOW_2 =
			"0x042000b7  0 3231   -1280 24   1280 776  google-chrome.Google-chrome  dubuntu Roundcube Webmail :: Welcome to Roundcube Webmail - Google Chrome";

	@Test
	public void testWmctrlParseWindowsGood_1() {
		ManagedWindow window = null;
		try {
			window = ManagedWindowImpl.parseFromWmctrl(SAMPLE_WMCTRL_OUTPUT_WINDOW_1);
		} catch (Exception x) {
			Assert.fail("Parse failed: "+x.getMessage());
		}
		
		Assert.assertNotNull(window);
		Assert.assertEquals("0x0400023d", window.getId());
		Assert.assertEquals(0,window.getDesktop());
		Assert.assertEquals(7452,window.getPid());
		Assert.assertEquals(new Point(54,88),window.getCoords());
		Assert.assertEquals(928, window.getWidth());
		Assert.assertEquals(484,window.getHeight());
		Assert.assertEquals("gnome-terminal.Gnome-terminal",window.getWmClass());
		Assert.assertEquals("dubuntu", window.getHostname());
		Assert.assertEquals("paul@dubuntu: ~/Downloads/eclipse",window.getTitle());
	}
	
	@Test
	public void testWmctrlParseWindowsGood_2() {
		ManagedWindow window = null;
		try {
			window = ManagedWindowImpl.parseFromWmctrl(SAMPLE_WMCTRL_OUTPUT_WINDOW_2);
		} catch (Exception x) {
			Assert.fail("Parse failed: "+x.getMessage());
		}
		
		Assert.assertNotNull(window);
		Assert.assertEquals("0x042000b7", window.getId());
		Assert.assertEquals(0,window.getDesktop());
		Assert.assertEquals(3231,window.getPid());
		Assert.assertEquals(new Point(-1280,24),window.getCoords());
		Assert.assertEquals(1280, window.getWidth());
		Assert.assertEquals(776,window.getHeight());
		Assert.assertEquals("google-chrome.Google-chrome",window.getWmClass());
		Assert.assertEquals("dubuntu", window.getHostname());
		Assert.assertEquals("Roundcube Webmail :: Welcome to Roundcube Webmail - Google Chrome",window.getTitle());
	}

	@Test
	public void testWmCtrlWindowParseNull() {
		try {
			ManagedWindowImpl.parseFromWmctrl(null);
		} catch (WmctrlParseException x) {
			return;
		}
		Assert.fail("Parsing a null line should result in WmctrlParseException being thrown");
	}
	
	@Test
	public void testWmCtrlWindowParseBlank() {
		try {
			ManagedWindowImpl.parseFromWmctrl("");
		} catch (WmctrlParseException x) {
			return;
		}
		Assert.fail("Parsing an empty line should result in WmctrlParseException being thrown");
	}

	@Test
	public void testWmCtrlWindowParseNonsense() {
		try {
			ManagedWindowImpl.parseFromWmctrl("jibber jabber job jib jab");
		} catch (WmctrlParseException x) {
			return;
		}
		Assert.fail("Parsing an incorrectly formatted line should result in WmctrlParseException being thrown");
	}
	
}
