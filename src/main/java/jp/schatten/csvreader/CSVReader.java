/**
 *
 */
package jp.schatten.csvreader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class CSVReader {
	private String filePath;
	private boolean initialized;
	private boolean eof;
	private Reader reader;
	private String encoding = "UTF-8";
	public CSVReader(String path) {
		filePath = path;
		initialized = false;
		eof = true;
		reader = null;
	}
	private void initialize() throws IOException {
		reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), encoding));
		eof = false;
		initialized = true;
	}
	public String[] fetchrow() throws IOException {
		if (!initialized) {
			initialize();
		}
		List<String> list = new ArrayList<String>();
		StringBuilder data = new StringBuilder();
		boolean quarted = false;
		boolean endRedord = false;
		while (true) {
			int code = reader.read();
			if (code == -1) {
				eof = true;
				break;
			}
			char ch = (char)code;
			switch (ch) {
			case '"':
				if (quarted) {
					code = reader.read();
					if (code == -1) {
						eof = true;
						quarted = false;
						endRedord = true;
						break;
					}
					char ch2 = (char)code;
					switch (ch2) {
					case 0x000D: // '\r'
						quarted = false;
						endRedord = true;
						break;
					case 0x000A: // '\n'
						quarted = false;
						endRedord = true;
						break;
					case ',':
						quarted = false;
						list.add(data.toString());
						data = new StringBuilder();
						break;
					default:
						data.append(ch);
						data.append(ch2);
						break;
					}
				} else {
					quarted = true;
				}
				break;
			case ',':
				if (quarted) {
					data.append(ch);
				}
				break;
			case 0x000D: // '\r'
				if (quarted) {
					data.append(ch);
				} else {
					endRedord = true;
				}
				break;
			case 0x000A: // '\n'
				if (quarted) {
					data.append(ch);
				} else {
					endRedord = true;
				}
				break;
			default:
				data.append(ch);
				break;
			}
			if (endRedord) {
				break;
			}
		}
		list.add(data.toString());
		return list.toArray(new String[list.size()]);
	}
	public boolean isEOF() {
		return eof;
	}
	public void close() {
		try {
			reader.close();
		} catch (IOException e) {
			System.out.println("CSVReader close exception :" + e.getMessage());
		}
	}

}
