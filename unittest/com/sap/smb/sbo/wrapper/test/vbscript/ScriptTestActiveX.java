package com.sap.smb.sbo.wrapper.test.vbscript;

import com.sap.smb.sbo.wrapper.activeX.ActiveXComponent;
import com.sap.smb.sbo.wrapper.com.ComException;
import com.sap.smb.sbo.wrapper.com.ComThread;
import com.sap.smb.sbo.wrapper.com.DispatchEvents;
import com.sap.smb.sbo.wrapper.com.Variant;
import com.sap.smb.sbo.wrapper.test.BaseTestCase;

/**
 * In this case the component is created and used in the same thread and it's an
 * Apartment Threaded component, so we call InitSTA.
 * <p>
 * May need to run with some command line options (including from inside
 * Eclipse). Look in the docs area at the Jacob usage document for command line
 * options.
 */
public class ScriptTestActiveX extends BaseTestCase {
	public void testActiveXScript() {
		ComThread.InitSTA(true);
		DispatchEvents de = null;

		try {
			String lang = "VBScript";
			ActiveXComponent sC = new ActiveXComponent("ScriptControl");
			sC.setProperty("Language", lang);
			ScriptTestErrEvents te = new ScriptTestErrEvents();
			de = new DispatchEvents(sC, te);
			if (de == null) {
				System.out
						.println("null returned when trying to create DispatchEvents");
			}
			Variant result;
			result = sC.invoke("Eval", getSampleVPScriptForEval());
			// call it twice to see the objects reused
			result = sC.invoke("Eval", getSampleVPScriptForEval());
			// call it 3 times to see the objects reused
			result = sC.invoke("Eval", getSampleVPScriptForEval());
			System.out.println("eval(" + getSampleVPScriptForEval() + ") = "
					+ result);
		} catch (ComException e) {
			e.printStackTrace();
		} finally {
			Integer I = null;
			for (int i = 1; i < 1000000; i++) {
				I = new Integer(i);
			}
			System.out.println(I);
			ComThread.Release();
			ComThread.quitMainSTA();
		}
	}
}
