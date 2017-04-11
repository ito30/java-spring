package com.snail.core.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil {

	// WRITE
	public static boolean write(String filename, String content) {
		return write(filename, content.getBytes(), false);
	}
	
	// WRITE APPEND
	public static boolean writeAppend(String filename, String content) {
		return write(filename, content.getBytes(), true);
	}
	
	// WRITE
		public static boolean write(String filename, byte[] bytes) {
			return write(filename, bytes, false);
		}

	// WRITE
	public static boolean write(String filename, byte[] bytes, boolean append) {
		FileOutputStream out = null;

		try {
			File file = new File(filename);
			out = new FileOutputStream(file, append);

			if (!file.exists()) {
				file.createNewFile();
			}

			out.write(bytes);
			out.flush();

			return true;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
			}

			System.out.println("FileUtil.write(): " + filename);
		}
	}
	
	// READ
	public static String read(String filename) {
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(filename));

			String currLine;
			StringBuffer strBuff = new StringBuffer();
			while ((currLine = reader.readLine()) != null) {
				strBuff.append(currLine);
			}

			return strBuff.toString();
		} catch (Exception e) {
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}
	}

	// GET ABSOLUTE FILE
	public static File getAbsoluteFile(String filename) throws IOException {
		File file = new File(filename);

		if (!file.exists()) {
			file.createNewFile();
		}

		return file.getAbsoluteFile();
	}

	// CREATE DIRECTORY
	public static boolean createDirectory(String dirName) {
		File file = new File(dirName);
		if (!file.exists()) {
			return file.mkdirs();
		}
		return false;
	}

}
