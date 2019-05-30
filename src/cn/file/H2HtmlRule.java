package cn.file;

import java.util.ArrayList;
import java.util.Collection;

import cn.ui.Property;

public class H2HtmlRule {
	public static boolean comment = false;
	public static boolean lineComment = false;
	public static boolean START = true;
	public static boolean END = false;
	public static boolean backslash = false;
	public static boolean stringlit = false;
	public static boolean stringlit2 = false;
	public static int tabWidth = 0;
	public static String tab = " ";
	public static Collection varList = new ArrayList(); // 变量列表数组
	public static StringBuffer htmlCode = new StringBuffer();

	public static void csTitle(String str) {
		// 设置默认字体大小
		htmlCode.append("<TITLE>" + str + "</TITLE>" + "<style type=\"text/css\">body{ font-family: "
				+ Property.FontOfFamily + ";font-size:" + Property.FontOfSize + ";} </style>");
	}

	public static void csHead(boolean start) {
		if (start) {
			htmlCode.append("<HEAD>");
		} else {
			htmlCode.append("</HEAD>");
		}
	}

	public static void csBody(boolean start) {
		if (start) {
			htmlCode.append("<BODY>");
		} else {
			htmlCode.append("</BODY>");
		}
	}

	public static void csStyle() {
		htmlCode.append("<style type=\"text/css\">");
		htmlCode.append("<!--");
		htmlCode.append("@import url(COfCss.css);");
		htmlCode.append("-->");
		htmlCode.append("</style>");
	}

	public static void csHtml(boolean start) {
		if (start) {
			htmlCode.append("<HTML>");
		} else {
			htmlCode.append("</HTML>");
		}
	}

	public static void csBr() {
		if (Property.NumOfLine < 10)
			htmlCode.append("<BR>00" + Property.NumOfLine);
		else if (Property.NumOfLine < 100)
			htmlCode.append("<BR>0" + Property.NumOfLine);
		else
			htmlCode.append("<BR>" + Property.NumOfLine);
		Property.NumOfLine++;
	}

	public static void csChar(String str) {
		if (comment || lineComment) {
			csComment(str);
		} else if (stringlit) {
			csStringList(str);
		} else {
			htmlCode.append("<font class=\"red\">" + str + "</font>");
		}
	}

	public static void csImportFile(String str) {
		if (comment || lineComment) {
			csComment(str);
		} else if (stringlit) {
			csStringList(str);
		} else {
			htmlCode.append("<font class=\"green\">" + str + "</font>");
			htmlCode.append(" ");
		}
	}

	public static void csOther(String str) {
		if (comment || lineComment) {
			csComment(str);
		} else if (stringlit) {
			csStringList(str);
		} else {
			htmlCode.append("<font class=\"black\">" + str + "</font>");
			htmlCode.append(" ");
		}
	}

	public static void csString(String str) {
		if (comment || lineComment) {
			csComment(str);
		} else if (stringlit) {
			csStringList(str);
		} else {
			htmlCode.append("<font class=\"blue\">" + str + "</font>");
			htmlCode.append(" ");
		}
	}

	public static void csNumber(String str) {
		if (comment || lineComment) {
			csComment(str);
		} else if (stringlit) {
			csStringList(str);
		} else {
			htmlCode.append("<font class=\"purple\">" + str + "</font>");
			htmlCode.append(" ");
		}
	}

	public static void csComment(String str) {
		htmlCode.append("<font class=\"blue\">" + str + "</font>");
		htmlCode.append(" ");
	}

	public static void csStringList(String str) {
		htmlCode.append(str);
		htmlCode.append(" ");
	}

	public static void csStringOne(boolean start) {
		if (comment || lineComment) {
			csComment("\"");
		} else if (start) {
			htmlCode.append("<font class=\"blue\">\"");
			stringlit = true;
		} else {
			htmlCode.append("\"</font>");
			stringlit = false;
		}
	}

	public static void csStringTwo(boolean start) {
		if (comment || lineComment) {
			csComment("\"");
		} else if (start) {
			htmlCode.append("<font class=\"blue\">\"");
			stringlit = true;
			stringlit2 = true;
		} else {
			htmlCode.append("\"</font>");
			stringlit = false;
			stringlit2 = false;
		}
	}
}
