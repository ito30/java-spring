package com.snail.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.soap.SOAPMessage;

public class SoapDebugUtil {

	// WRITE TO
	public static void writeToFile(SOAPMessage message, String filename) {
		FileOutputStream out = null;

		try {
			File file = new File(filename);
			out = new FileOutputStream(file, false);

			if (!file.exists()) {
				file.createNewFile();
			}

			message.writeTo(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}

				System.out.println("Log: " + filename);
			}
		}
	}
}
