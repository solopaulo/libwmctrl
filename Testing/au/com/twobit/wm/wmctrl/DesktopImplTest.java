package au.com.twobit.wm.wmctrl;

import junit.framework.Assert;

import org.junit.Test;

import au.com.twobit.wm.Desktop;

public class DesktopImplTest {

	private final String SAMPLE_WMCTRL_OUTPUT_DESKTOP_1 = 
			"0  * DG: 2560x1600  VP: 1280,0  WA: 0,24 1280x776  N/A";
	private final String SAMPLE_WMCTRL_OUTPUT_DESKTOP_2 =
			"1  - DG: 2048x1200  VP: 1280,0  WA: 0,24 768x324  N/A";
	
	@Test
	public void testWmCtrlDesktopParseGood1() {
		Desktop desktop = null;
		try {
			desktop = DesktopImpl.parseFromWmctrl(SAMPLE_WMCTRL_OUTPUT_DESKTOP_1);
		} catch (WmctrlParseException x) {
			x.printStackTrace();
			Assert.fail(x.getMessage());
		}
		Assert.assertEquals(0,desktop.getId());
		Assert.assertTrue(desktop.isActive());
		Assert.assertEquals(2560, desktop.getWidth());
		Assert.assertEquals(1600, desktop.getHeight());
		Assert.assertEquals(1280, desktop.getUsableWidth());
		Assert.assertEquals(776,desktop.getUsableHeight());
	}
	
	@Test
	public void testWmCtrlDesktopParseGood2() {
		Desktop desktop = null;
		try {
			desktop = DesktopImpl.parseFromWmctrl(SAMPLE_WMCTRL_OUTPUT_DESKTOP_2);
		} catch (WmctrlParseException x) {
			x.printStackTrace();
			Assert.fail(x.getMessage());
		}
		Assert.assertEquals(1,desktop.getId());
		Assert.assertFalse(desktop.isActive());
		Assert.assertEquals(2048, desktop.getWidth());
		Assert.assertEquals(1200, desktop.getHeight());
		Assert.assertEquals(768, desktop.getUsableWidth());
		Assert.assertEquals(324,desktop.getUsableHeight());
	}
	
	@Test
	public void testWmCtrlDesktopParseNull() {
		try {
			DesktopImpl.parseFromWmctrl(null);
		} catch (WmctrlParseException x) {
			return;
		}
		Assert.fail("Parsing a null line should result in WmctrlParseException being thrown");
	}
	
	@Test
	public void testWmCtrlDesktopParseBlank() {
		try {
			DesktopImpl.parseFromWmctrl("");
		} catch (WmctrlParseException x) {
			return;
		}
		Assert.fail("Parsing an empty line should result in WmctrlParseException being thrown");
	}

	@Test
	public void testWmCtrlDesktopParseNonsense() {
		try {
			DesktopImpl.parseFromWmctrl("jibber jabber job jib jab");
		} catch (WmctrlParseException x) {
			return;
		}
		Assert.fail("Parsing an incorrectly formatted line should result in WmctrlParseException being thrown");
	}
}

