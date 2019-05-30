package cn.ui;

import java.io.File;

public class Property {
	public static String KeywordsOfColor; // 关键字颜色
	public static String FontOfFamily; // 字体类型
	public static String FontOfSize; // 字体大小
	public static String FileName; // 文件名字
	public static int IndentedOfCase; // 缩进格数
	public static String codeType; // 编码格式
	public static int NumOfLine = 2;

	Property(String newKeywordsOfColor, String newFontOfFamily, String newFontOfSize, int newIndentedOfCase) {
		KeywordsOfColor = newKeywordsOfColor;
		FontOfFamily = newFontOfFamily;
		FontOfSize = newFontOfSize;
		IndentedOfCase = newIndentedOfCase;
	}

	Property() {
		KeywordsOfColor = "PURPLE";
		FontOfFamily = "Times";
		FontOfSize = "15";
		FileName = "";
		codeType = "UTF-8";
		IndentedOfCase = 2;
	}

	public String getKeywordsOfColor() {
		return KeywordsOfColor;
	}

	public void setKeywordsOfColor(String keywordsOfColor) {
		KeywordsOfColor = keywordsOfColor;
	}

	public String getFontOfFamily() {
		return FontOfFamily;
	}

	public void setFontOfFamily(String fontOfFamily) {
		FontOfFamily = fontOfFamily;
	}

	public String getFileName() {
		return FileName;
	}

	public void setFileName(String file1) {
		FileName = file1;
	}
}
