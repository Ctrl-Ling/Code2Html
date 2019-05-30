package cn.file;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import cn.ui.SourceFile;

public class FileToString {

	public String fileToString(String path) throws FileNotFoundException {

		String fileString = "";
		// 生成html文件

		if (SourceFile.name.endsWith(".java")) {
			fileString = new Java2Html().CreateHtml();
		} else if (SourceFile.name.endsWith(".c")) {
			fileString = new C2Html().CreateHtml();
		} else if (SourceFile.name.endsWith(".h")) {
			fileString = new H2Html().CreateHtml();
		}
		new SaveFile().saveFile(fileString);
		return fileString;
	}
}
