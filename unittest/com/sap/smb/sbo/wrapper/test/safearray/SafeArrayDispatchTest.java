package com.sap.smb.sbo.wrapper.test.safearray;

import com.sap.smb.sbo.wrapper.activeX.ActiveXComponent;
import com.sap.smb.sbo.wrapper.com.ComException;
import com.sap.smb.sbo.wrapper.com.Dispatch;
import com.sap.smb.sbo.wrapper.com.SafeArray;
import com.sap.smb.sbo.wrapper.com.Variant;
import com.sap.smb.sbo.wrapper.test.BaseTestCase;

/**
 * Test class to verify dispatch with SafeArray
 */
public class SafeArrayDispatchTest extends BaseTestCase {
	public void testDispatchWithSafeArray() {
		try {
			String scriptCommand = "1+(2*4)-3";
			String lang = "VBScript";
			ActiveXComponent sControl = new ActiveXComponent("ScriptControl");
			Dispatch.put(sControl, "Language", lang);

			Variant result = Dispatch.call(sControl, "Eval", scriptCommand);
			assertTrue(result.toString().equals("6"));

			// wrap the script control in a variant
			Variant v = new Variant(sControl);

			// create a safe array of type dispatch
			SafeArray sa = new SafeArray(Variant.VariantDispatch, 1);

			// put the variant in the array
			sa.setVariant(0, v);

			// take it back out
			Variant v2 = sa.getVariant(0);
			Dispatch d = v2.toDispatch();

			// make sure you can call eval on it
			result = Dispatch.call(d, "Eval", scriptCommand);
			assertTrue(result.toString().equals("6"));
		} catch (ComException e) {
			e.printStackTrace();
			fail("script failure " + e);
		}
	}
}
