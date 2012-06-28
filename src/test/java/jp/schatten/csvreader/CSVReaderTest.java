/**
 *
 */
package jp.schatten.csvreader;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 *
 * @author schatten
 */
public class CSVReaderTest {

	@DataProvider(name="path")
	public String csvFilePath () {
		return "src/test/resources/test.csv";
	}
	@Test(dataProvider="path")
	public void load(String path) {
		CSVReader reader = new CSVReader(path);
		try {
			for (String[] row = reader.fetchrow(); !reader.isEOF(); row = reader.fetchrow() ) {
				// TODO ‚PƒŒƒR[ƒh•ª‚Ìˆ—
			}
		} finally {
			reader.close();
		}
	}
}
