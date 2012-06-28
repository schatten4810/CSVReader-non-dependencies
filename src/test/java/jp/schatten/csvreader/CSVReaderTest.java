/*
 *
 */
package jp.schatten.csvreader;

//import static org.testng.Assert.*;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;

import java.io.IOException;

/**
 * CSVReaderのテストクラス
 */
public class CSVReaderTest {
	public static void main(String[] args) {
		CSVReaderTest test = new CSVReaderTest();
		test.load("src/test/resources/test.csv");
	}

//	@DataProvider(name="path")
//	public Object[][] csvFilePath () {
//		return new Object[][] {{"src/test/resources/test.csv"}};
//	}
//	@Test(dataProvider="path")
	public void load(String path) {
		CSVReader reader = new CSVReader(path);
		try {
			for (String[] columns = reader.fetchrow(); !reader.isEOF(); columns = reader.fetchrow() ) {
//				assertNotNull(columns);
				// １レコード分の処理
				System.out.println("Rowdata:");
				for (int i = 0; i < columns.length; i++) {
					String column = columns[i];
					System.out.println(i + ":" + column);
				}
				System.out.println("");
//				assertEquals(columns.length, 4, "カラム数");
			}
		} catch (IOException e) {
			System.out.println("CSV読み込み処理中にエラーが発生した。:" + e.getMessage());
			StackTraceElement[] stackTrace = e.getStackTrace();
			String offset = "";
			for (StackTraceElement element : stackTrace) {
				System.out.print(offset);
				System.out.print(element.getClassName());
				System.out.print("#");
				System.out.print(element.getMethodName());
				System.out.print(" : ");
				System.out.println(element.getLineNumber());
				offset = "  at ";
			}
//			fail();
		} finally {
			reader.close();
		}
	}
}
