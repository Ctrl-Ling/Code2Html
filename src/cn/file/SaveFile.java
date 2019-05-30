package cn.file;

import cn.ui.*;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class SaveFile {
	public void saveFile(String htmlFile) {
		try {
			String temp = View.Address;
			if (!temp.endsWith("\\")) {
				temp = temp + "\\";
			}
			BufferedWriter bf = new BufferedWriter(new FileWriter(new File(temp + SourceFile.name + ".html")));
			bf.write(htmlFile);
			bf.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
