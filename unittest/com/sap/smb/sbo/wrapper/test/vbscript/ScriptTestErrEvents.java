package com.sap.smb.sbo.wrapper.test.vbscript;

import com.sap.smb.sbo.wrapper.com.Variant;
import com.sap.smb.sbo.wrapper.test.BaseTestCase;

/**
 * Extracted from ScriptTest so everyone can see this Made a test solely because
 * it made the ant test easier
 */
public class ScriptTestErrEvents extends BaseTestCase {

	public void Error(Variant[] args) {
		System.out.println("java callback for error!");
	}

	public void Timeout(Variant[] args) {
		System.out.println("java callback for error!");
	}
}
