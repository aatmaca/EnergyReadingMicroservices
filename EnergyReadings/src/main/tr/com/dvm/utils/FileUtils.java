package tr.com.dvm.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

	public static void writeToFile(StringBuilder sb, String fileName) throws IOException {
		File file = new File(Constants.TEST_FOLDER + fileName);
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		bw.write(sb.toString());
		bw.flush();
		bw.close();
	}

	public static void appendToFile(StringBuilder sb, String fileName) throws IOException {
		File file = new File(Constants.TEST_FOLDER + fileName);
		BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
		bw.write(sb.toString());
		// bw.newLine();
		bw.flush();
		bw.close();
	}
	
	public static boolean isFileExists(String fileName){
		return new File(Constants.TEST_FOLDER + fileName).exists();
	}
}