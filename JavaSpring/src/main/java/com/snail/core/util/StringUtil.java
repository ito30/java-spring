package com.snail.core.util;

public class StringUtil {
	// CONTAIN
	public static boolean contain(String string, String word) {
		return string.indexOf(word) >= 0;
	}

	// NUGROHO.H Modify [2014-11-12]
	// TO INT
	public static int toInt(String string) throws NumberFormatException {
		return Integer.parseInt(string);
	}

	// TO DOUBLE
	public static double commaValueToDouble(String string) {
		if (string == null) {
			return 0;
		} else {
			string = string.replace(",", "");
			return toDouble(string);
		}
	}

	// TO DOUBLE
	public static double dotValueToDouble(String string) {
		if (string == null) {
			return 0;
		} else {
			string = string.replace(".", "");
			return toDouble(string);
		}
	}

	// TO DOUBLE
	public static double toDouble(String string) {
		try {
			return Double.parseDouble(string);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	// IS NULL OR EMPTY
	public static boolean isNullOrEmpty(String string) {
		if (string == null) {
			return true;
		} else {
			return string.trim().isEmpty();
		}
	}

	// IS TRUE
	public static boolean isTrue(String string) {
		if (string == null) {
			return false;
		} else {
			string = string.trim();
			if (string.equals("1")) {
				return true;
			} else if (string.equalsIgnoreCase("true")) {
				return true;
			} else if (string.equalsIgnoreCase("yes")) {
				return true;
			}

			return false;
		}
	}

	// SHORTEN
	public static String shorten(String string) {
		return shorten(string, 10);
	}

	// SHORTEN
	public static String shorten(String string, int threshold) {
		if (string == null) {
			return "";
		} else if (string.length() > threshold) {
			return string.substring(0, threshold - 1) + "..";
		} else {
			return string;
		}
	}

	// IS SAME
	public static boolean isSame(String string1, String string2) {
		if (string1 == null) {
			return string2 == null;
		} else {
			return string1.trim().equalsIgnoreCase(string2);
		}
	}

	// START WITH
	public static boolean beginWith(String string, String prefix) {
		if (string == null) {
			return prefix == null;
		} else {
			return string.startsWith(prefix);
		}
	}

	// TO CAMEL CASE
	public static String toCamelCase(String string) {
		return string.substring(0, 1).toUpperCase()
				+ string.substring(1).toLowerCase();
	}

	// SLICE
	public static String slice(String string, String beginToken, String endToken) {
		int begin = string.indexOf(beginToken);

		if (begin >= 0) {
			begin += beginToken.length();
			int end = string.indexOf(endToken, begin);

			if (end > 0) {
				return string.substring(begin, end).trim();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public static String lastSlice(String string, String beginToken,
			String endToken) {
		int begin = string.lastIndexOf(beginToken);

		if (begin >= 0) {
			begin += beginToken.length();
			int end = string.indexOf(endToken, begin);

			if (end > 0) {
				return string.substring(begin, end).trim();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	// SLICE RIGHT
	public static String sliceRight(String string, String token) {
		int lastIndex = string.lastIndexOf(token);
		int length = token.length();
		int index = lastIndex + length;

		return string.substring(index).trim();
	}

	// SLICE LEFT
	public static String sliceLeft(String string, String token) {
		int index = string.indexOf(token);

		if (index > 0) {
			return string.substring(0, index).trim();
		} else {
			return null;
		}
	}

	public static String removeQuote(String string) {
		return string.replaceAll("^\"|\"$", "");
	}
}
