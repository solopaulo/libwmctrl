package au.com.twobit.wm.wmctrl;

import java.util.Collection;
import java.util.Random;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import au.com.twobit.wm.ManagedWindow;
import au.com.twobit.wm.WindowManager;
import au.com.twobit.wm.WindowMissingException;

public class WmctrlWindowManagerTest {

	private WindowManager manager;
	private Collection<ManagedWindow> windows;
	
	@Before
	public void setUp() {
		manager = new WmctrlWindowManagerImpl();
		windows = manager.getWindows();
	}
	
	@Test
	public void testFindWindowByIdSuccessful() {
		int randomIndex = new Random().nextInt(windows.size());
		Assert.assertTrue(randomIndex >= 0);
		Assert.assertTrue(randomIndex < windows.size());
		
		ManagedWindow findable = (windows.toArray(new ManagedWindow[] { }))[randomIndex];
		Assert.assertNotNull(findable);
		
		ManagedWindow window = null;
		try {
			window = manager.findWindowById( findable.getId() );
		} catch (WindowMissingException x) {
			x.printStackTrace();
			Assert.fail(x.getMessage());
		}
		Assert.assertNotNull(window);
		Assert.assertEquals(findable.getId(), window.getId());
	}
	
	@Test
	public void testFindWindowByIdFailure() {
		String id = "000000000000000000000000000";
		try {
			manager.findWindowById(id);
		} catch (WindowMissingException x) {
			Assert.assertNotNull( x );
			Assert.assertEquals(String.format("Cannot find window with id %s",id),x.getMessage());
			return;
		}
		Assert.fail();
	}
	
	@Test
	public void testFindWindowByIdNullFailure() {
		try {
			manager.findWindowById(null);
		} catch (WindowMissingException x) {
			Assert.assertNotNull(x);
			Assert.assertEquals("Cannot find window with id null",x.getMessage());
			return;
		}
		Assert.fail();
	}
	
	@Test
	public void testSearchWindowsByNameSuccess() {
		// pick a random window
		int randomIndex = new Random().nextInt(windows.size());
		Assert.assertTrue(randomIndex >= 0);
		Assert.assertTrue(randomIndex < windows.size());
		ManagedWindow searchable = (windows.toArray(new ManagedWindow[] { }))[randomIndex];
		Assert.assertNotNull(searchable);
		
		ManagedWindow window = null;
		String substring = searchable.getTitle();
		try {
			int pos = substring.length() / 4;
			substring = substring.substring(pos,substring.length()-pos);
		} catch (Exception x) { }
		// substring must be only a portion of the title
		Assert.assertFalse(searchable.getTitle().equals(substring));

		// search for the substring
		Collection<ManagedWindow>results = null;
		try {
			results = manager.searchWindowsByName(substring);
		} catch (WindowMissingException x) {
			Assert.fail(x.getMessage());
		}
		
		// must have at least 1 result
		Assert.assertNotNull(results);
		Assert.assertTrue(results.size() > 0);
	}
	
	@Test
	public void testSearchWindowsByNameFailure() {
		String id = "000000000000000000000000000";
		try {
			Assert.assertEquals(0,manager.searchWindowsByName(id).size());
			return;
		} catch (WindowMissingException x) { }
		Assert.fail();
	}
	
	@Test
	public void testSearchWindowsByNameNullFailure() {
		String id = null;
		try {
			Assert.assertEquals(0,manager.searchWindowsByName(id).size());			
		} catch (WindowMissingException x) {
			Assert.assertNotNull(x);
			Assert.assertEquals("searchWindowsByName: No search term provided", x.getMessage());
			return;
		}
		Assert.fail();
	}
}
