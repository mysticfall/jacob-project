package com.jacob.samples.atl;

import com.sap.smb.sbo.wrapper.activeX.ActiveXComponent;
import com.sap.smb.sbo.wrapper.com.Dispatch;
import com.sap.smb.sbo.wrapper.com.Variant;

class MultiFaceTest {

	/**
	 * standard main() test program
	 * 
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		// this method has been deprecated as being unreliable.
		// shutdown should be done through other means
		// whoever wrote this example should explain what this was intended to
		// do
		// System.runFinalizersOnExit(true);

		ActiveXComponent mf = new ActiveXComponent("MultiFace.Face");
		try {
			// I am now dealing with the default interface (IFace1)
			Dispatch.put(mf, "Face1Name", new Variant("Hello Face1"));
			System.out.println(Dispatch.get(mf, "Face1Name"));

			// get to IFace2 through the IID
			Dispatch f2 = mf
					.QueryInterface("{9BF24410-B2E0-11D4-A695-00104BFF3241}");
			// I am now dealing with IFace2
			Dispatch.put(f2, "Face2Nam", new Variant("Hello Face2"));
			System.out.println(Dispatch.get(f2, "Face2Nam"));

			// get to IFace3 through the IID
			Dispatch f3 = mf
					.QueryInterface("{9BF24411-B2E0-11D4-A695-00104BFF3241}");
			// I am now dealing with IFace3
			Dispatch.put(f3, "Face3Name", new Variant("Hello Face3"));
			System.out.println(Dispatch.get(f3, "Face3Name"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
