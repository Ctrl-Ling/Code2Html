package cn.ui;

import java.io.File;

public class Property {
	public static String KeywordsOfColor; // �ؼ�����ɫ
	public static String FontOfFamily; // ��������
	public static String FontOfSize; // �����С
	public static String FileName; // �ļ�����
	public static int IndentedOfCase; // ��������
	public static String codeType; // �����ʽ
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
