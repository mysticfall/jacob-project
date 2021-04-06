package com.sap.smb.sbo.wrapper.com;

import com.sap.smb.sbo.wrapper.activeX.ActiveXComponent;
import com.sap.smb.sbo.wrapper.com.Dispatch;
import com.sap.smb.sbo.wrapper.com.Variant;
import com.sap.smb.sbo.wrapper.test.BaseTestCase;

/**
 * Test some of the Dispatch utility methods
 * <p>
 * May need to run with some command line options (including from inside
 * Eclipse). Look in the docs area at the Jacob usage document for command line
 * options.
 */
public class DispatchTest extends BaseTestCase {

	/**
	 * Verify this detects word's exit
	 */
	public void testDispatchHasExited() {
		String pid = "Word.Application";
		ActiveXComponent axc = new ActiveXComponent(pid);
		assertEquals(0, Dispatch.hasExited(axc));
		axc.invoke("Quit", new Variant[] {});
		// should take some amount of time for Word to Quit so should = !exited
		assertEquals(0, Dispatch.hasExited(axc));
		try {
			// sleep some reasonable amount of time waiting for it to quit
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			fail("should not have been interrupted");
		}
		assertEquals(1, Dispatch.hasExited(axc));
	}
}
