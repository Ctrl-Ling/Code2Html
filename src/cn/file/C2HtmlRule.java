package cn.file;

import java.util.ArrayList;
import java.util.Collection;

import cn.ui.Property;

public class C2HtmlRule {
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

	public static void tabIncrease(boolean up) {

		// 缩进个数
		String TAB = "";
		for (int i = 0; i < Property.IndentedOfCase; i++)
			TAB = TAB + "&nbsp;";
		if (up) {
			tabWidth++;
		} else {
			tabWidth--;
		}
		tab = " ";
		for (int i = tabWidth; i > 0; i--) {
			tab = tab + TAB;
		}
	}

	public static void csTitle(String str) {
		// 设置默认字体大小
		htmlCode.append("<TITLE>" + str + "</TITLE>" + "<style type=\"text/css\">body{ font-family: "
				+ Property.FontOfFamily + ";font-size:" + Property.FontOfSize + ";} </style>001");
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
		htmlCode.append(tab);
	}

	public static void csChar(String str) {
		if (comment || lineComment) {
			csComment(str);
		} else if (stringlit) {
			csStringList(str);
		} else {
			if (str.equals("<")) { // <转义成&lt;，避免显示错误
				str = "&lt;";
			}
			htmlCode.append("<font color=red>" + str + "</font>");
		}
	}

	public static void csImportFile(String str) {
		if (comment || lineComment) {
			csComment(str);
		} else if (stringlit) {
			csStringList(str);
		} else {
			if (str.equals("<")) { // <转义成&lt;，避免显示错误
				str = "&lt;";
			}
			htmlCode.append("<font color=green>" + str + "</font>");
			// htmlCode.append(" ");
		}
	}

	public static void csOther(String str) {
		if (comment || lineComment) {
			csComment(str);
		} else if (stringlit) {
			csStringList(str);
		} else {
			htmlCode.append("<font color=black>" + str + "</font>");
			// htmlCode.append(" ");
		}
	}

	public static void csKeyword(String str) {
		if (comment || lineComment) {
			csComment(str);
		} else if (stringlit) {
			csStringList(str);
		} else {
			htmlCode.append("<font color=" + Property.KeywordsOfColor + ">" + str + "</font>");
			// htmlCode.append(" ");
		}
	}

	public static void csString(String str) {
		if (comment || lineComment) {
			csComment(str);
		} else if (stringlit) {
			csStringList(str);
		} else {
			htmlCode.append("<font color=blue>" + str + "</font>");
			// htmlCode.append(" ");
		}
	}

	public static void csNumber(String str) {
		if (comment || lineComment) {
			csComment(str);
		} else if (stringlit) {
			csStringList(str);
		} else {
			htmlCode.append("<font color=purple>" + str + "</font>");
			// htmlCode.append(" ");
		}
	}

	public static void csComment(String str) {
		htmlCode.append("<font color=GREEN>" + str + "</font>");
		// htmlCode.append(" ");
	}

	public static void csStringList(String str) {
		htmlCode.append(str);
		// htmlCode.append(" ");
	}

	public static void csStringOne(boolean start) {
		if (comment || lineComment) {
			csComment("\"");
		} else if (start) {
			htmlCode.append("<font color=blue>\"");
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
			htmlCode.append("<font color=blue>\"");
			stringlit = true;
			stringlit2 = true;
		} else {
			htmlCode.append("\"</font>");
			stringlit = false;
			stringlit2 = false;
		}
	}
}
