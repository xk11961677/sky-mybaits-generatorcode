package codeGenerate.factory;

import java.io.*;

public class ToolUtil{

	public ToolUtil() {
	}

	public static void writeFile(File file, String mesInfo) throws IOException {
		if (file == null) {
			throw new IllegalStateException("logFile can not be null!");
		} else {
			@SuppressWarnings("resource")
			Writer txtWriter = new FileWriter(file, true);
			txtWriter.write((new StringBuilder(String.valueOf(mesInfo))).append("\n").toString());
			txtWriter.flush();
			return;
		}
	}
}
