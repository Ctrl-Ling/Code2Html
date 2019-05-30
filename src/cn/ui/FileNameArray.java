package cn.ui;

import java.io.File;

public class FileNameArray {

	private String FileName;
	private File file;

	FileNameArray(String name) {
		FileName = name;
		file = new File(FileName);
	}

	public String getFileName() {
		return FileName;
	}

	public void setFileName(String fileName) {
		FileName = fileName;
	}

	public String getName() {
		return file.getName();
	}

	public String getPath() {
		return file.getParent();
	}

}
