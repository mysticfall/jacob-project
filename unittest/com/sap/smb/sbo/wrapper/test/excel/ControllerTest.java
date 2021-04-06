package com.sap.smb.sbo.wrapper.test.excel;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import com.sap.smb.sbo.wrapper.activeX.ActiveXComponent;
import com.sap.smb.sbo.wrapper.com.ComThread;
import com.sap.smb.sbo.wrapper.com.Dispatch;
import com.sap.smb.sbo.wrapper.com.Variant;
import com.sap.smb.sbo.wrapper.test.BaseTestCase;

/**
 * this test verifies that you can call toString() on a Variant extracted from
 * Excel that contains a 2 dimensional array of doubles. 1.14M5 and earlier blew
 * up on this because two objects pointed at the same windows memory space SF 1840487
 */
public class ControllerTest extends BaseTestCase {

	private Controller controller;

	protected void setUp() {
		controller = new Controller();
	}

	public void testGetColumnA() {
		List<String> list = controller.getColumnA(super
				.getWindowsFilePathToPackageResource("teste.xls", this
						.getClass()));
		assertEquals(50, list.size());
	}

	public void testGetColumnB() {
		List<String> list = controller.getColumnB(super
				.getWindowsFilePathToPackageResource("teste.xls", this
						.getClass()));
		assertEquals(40, list.size());
	}

	/**
	 * This class looks bad because it is a compressed version that was
	 * originally in 3 different files as part of a bug submission. I didn't
	 * want to simplify it because it might no longer demonstrate the problem we
	 * were trying to fix
	 */
	public class Controller {

		private List<String> columnA;

		private List<String> columnB;

		public List<String> getColumnA(String pathToTest) {
			load(pathToTest);
			return columnA;
		}

		public List<String> getColumnB(String pathToTest) {
			load(pathToTest);
			return columnB;
		}

		public void load(String pathToTest) {
			if (columnA == null || columnB == null) {
				File excelFile = new File(pathToTest);
				executaExcelCallBack(excelFile.getAbsolutePath(), "password");
			}
		}

		public void executaExcelCallBack(String path, String password) {
			// ComThread.InitSTA();
			ComThread.InitMTA();
			ActiveXComponent excel = new ActiveXComponent("Excel.Application");

			try {

				excel.setProperty("Visible", false);
				Dispatch workbooks = excel.getProperty("Workbooks")
						.toDispatch();

				Dispatch workbook = Dispatch.call(workbooks, "Open", path, // FileName
						3, // UpdateLinks
						false, // Readonly
						5, // Format
						password // Password
						).toDispatch();

				Dispatch sheets = Dispatch.call(workbook, "Worksheets")
						.toDispatch();
				System.out.println("Before executa");
				executa(excel, sheets);
				System.out.println("After executa");

				Dispatch.call(workbook, "Close", new Variant(false));
				Dispatch.call(workbooks, "Close");
				System.out.println("After Close");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("Before Quit");
				excel.invoke("Quit", new Variant[] {});
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("After Quit, Before Release()");
				ComThread.Release();
				System.out.println("After Release()");
			}
		}

		public static final int CALC_AUTOMATICO = -4105;

		public static final int CALC_MANUAL = -4135;

		public void informarValorCelula(String celula, Dispatch sheet,
				String valor) {
			System.out.println("Entered informarValorCelula");
			Dispatch cel = obterCelula(celula, sheet);
			Dispatch.put(cel, "Value", valor);
			System.out.println("Exiting informarValorCelula");
		}

		public Variant obterValorCelula(String celula, Dispatch sheet) {
			System.out.println("Entered obterValorCelula");
			Dispatch d = obterCelula(celula, sheet);
			Variant returnedValue = Dispatch.get(d, "Value");
			System.out.println("Exiting obterValorCelula");
			return returnedValue;
		}

		public Dispatch obterCelula(String celula, Dispatch sheet) {
			System.out.println("Entered obterCelula");
			Dispatch d = Dispatch.invoke(sheet, "Range", Dispatch.Get,
					new Object[] { celula }, new int[1]).toDispatch();
			System.out.println("Exiting obterCelula");
			return d;
		}

		public List<String> obterValoresRange(String celulas, Dispatch sheet) {
			List<String> valores = new LinkedList<String>();

			// obtem valor das celulas como um Variant
			Variant var = obterValorCelula(celulas, sheet);

			String arrayAsString = null;
			System.out
					.println("Calling toString() on the Variant that is an array will blow up "
							+ var.getvt() + " --> " + arrayAsString);
			arrayAsString = var.toString();
			StringTokenizer st = new StringTokenizer(arrayAsString, "\n");
			while (st.hasMoreTokens()) {
				valores.add(st.nextToken().trim());
			}
			return valores;
		}

		public void executa(ActiveXComponent xl, Dispatch sheets) {

			System.out.println("Entered private ExcellCallBack executa()");
			Dispatch sheet = Dispatch.call(sheets, "Item", "Plan1")
					.toDispatch();
			columnA = obterValoresRange("A1:A50", sheet);
			columnB = obterValoresRange("B1:B40", sheet);
			System.out.println("Exiting private ExcellCallBack executa()");
		}
	}
}
