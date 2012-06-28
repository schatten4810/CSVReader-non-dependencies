/**
 *
 */
package jp.schatten.csvreader;

import static org.testng.Assert.*;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 *
 * @author schatten
 */
public class CSVReaderTest {
	public static void main(String[] args) {
		CSVReaderTest test = new CSVReaderTest();
		test.load("src/test/resources/test.csv");
	}

	@DataProvider(name="path")
	public Object[][] csvFilePath () {
		return new Object[][] {{"src/test/resources/test.csv"}};
	}
	@Test(dataProvider="path")
	public void load(String path) {
		CSVReader reader = new CSVReader(path);
		try {
			for (String[] row = reader.fetchrow(); !reader.isEOF(); row = reader.fetchrow() ) {
				// １レコード分の処理
				System.out.println("Rowdata:");
				for (String column : row) {
					System.out.println("  " + column);
				}
				System.out.println("");
				assertEquals(row.length, 4, "カラム数");
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
		} finally {
			reader.close();
		}
	}
}
