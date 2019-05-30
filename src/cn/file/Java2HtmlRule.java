package cn.file;

import java.util.ArrayList;
import java.util.Collection;

import cn.ui.Property;

public class Java2HtmlRule {

	public static boolean comment = false;
	public static boolean lineComment = false;
	public static boolean START = true;
	public static boolean END = false;
	public static boolean backslash = false;
	public static boolean stringlit = false;
	public static boolean stringlit2 = false;
	public static int tabWidth = 0;
	public static String tab = " ";
	public static Collection varList = new ArrayList(); // �����б�����
	public static StringBuffer htmlCode = new StringBuffer();

	public static void tabIncrease(boolean up) {
		// ��������
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

	public static void prTitle(String str) {
		// ����Ĭ�����塢��С����һ���к�
		htmlCode.append("<TITLE>" + str + "</TITLE>" + "<style type=\"text/css\">body{ font-family: "
				+ Property.FontOfFamily + ";font-size:" + Property.FontOfSize + ";} </style>001");
		// htmlCode.append("/n");
	}

	// keyword
	public static void prPrimitive(String str) {
		if (comment || lineComment) // ��ע����
		{
			prJComment(str);
		} else if (stringlit) {
			prStringLit(str);
		} else {
			htmlCode.append("<b><FONT color=" + Property.KeywordsOfColor + ">" + str + "</FONT></b>");
			htmlCode.append(" ");
		}
	}

	// ��������
	public static void prVar(String str) {
		if (comment || lineComment) {
			prJComment(str);
		} else if (stringlit) {
			prStringLit(str);
		} else {
			htmlCode.append("<FONT color=blue>" + str + "</FONT>");
			// htmlCode.append(" ");
		}
	}

	public static void prFlow(String str) {
		if (comment || lineComment) {
			prJComment(str);
		} else if (stringlit) {
			prStringLit(str);
		} else {
			htmlCode.append("<b><FONT color=" + Property.KeywordsOfColor + ">" + str + "</FONT></b>");
			// htmlCode.append(" ");
		}
	}

	// �ؼ��ֲ���
	public static void prResWord(String str) {
		if (comment || lineComment) {
			prJComment(str);
		} else if (stringlit) {
			prStringLit(str);
		} else {
			// �ؼ���������ɫ
			htmlCode.append("<b><FONT color=" + Property.KeywordsOfColor + ">" + str + "</FONT></b>");
			htmlCode.append(" ");
		}
	}

	public static void prNormal(String str) {
		if (comment || lineComment) {
			prJComment(str);
		} else if (stringlit) {
			prStringLit(str);
		} else {
			htmlCode.append(str);
			// htmlCode.append(" ");
		}
	}

	public static void prJComment(String str) {
		htmlCode.append("<FONT color=green>" + str + "</FONT>");
		htmlCode.append(" ");
	}

	public static void prOther(String str) {
		if (comment || lineComment) {
			prJComment(str);
		} else if (stringlit) {
			prStringLit(str);
		} else {
			htmlCode.append("<b><FONT color=black>" + str + "</FONT></b>");
			// htmlCode.append(" ");
		}
	}

	// �Ե�б�ܽ��в���
	public static void prOperator(String str) {
		// ��ע����
		if (comment || lineComment) {
			prJComment(str);
		} else if (stringlit) {
			prStringLit(str);
		} else {
			if (str.equals("<")) { // <ת���&lt;��������ʾ����
				str = "&lt;";
			}
			htmlCode.append("<FONT color=black>" + str + "</FONT>"); // ����ֱ������Ϊ��ɫ
		}
	}

	// number
	public static void prNumber(String str) {
		if (comment || lineComment) {
			prJComment(str);
		} else if (stringlit) {
			prStringLit(str);
		} else {
			htmlCode.append("<FONT color=black>" + str + "</FONT>");
		}
	}

	public static void prStringLit(String str) {
		htmlCode.append(str);
	}

	public static void prHead(boolean start) {
		if (start) {
			htmlCode.append("<HEAD>");
			// htmlCode.append("/n");
		} else {
			htmlCode.append("</HEAD>");
			// htmlCode.append("/n");
		}
	}

	public static void prBody(boolean start) {
		if (start) {

			htmlCode.append("<BODY bgcolor=white color=black>");
			htmlCode.append(" ");
			// �޸������С
			// htmlCode.append("<tt><font
			// style=\"font-size:300%;color:black\">");
			htmlCode.append(" ");
		} else {
			htmlCode.append("</FONT></tt>");
			htmlCode.append(" ");
			htmlCode.append("</BODY>");
			htmlCode.append(" ");
		}
	}

	public static void prComment(boolean start) {
		if (start) {
			htmlCode.append("<!--");
			// htmlCode.append("/n");
		} else {
			htmlCode.append("-->");
			// htmlCode.append("/n");
		}
	}

	public static void prHtml(boolean start) {
		if (start) {
			htmlCode.append("<HTML>");
			// htmlCode.append("/n");
		} else {
			htmlCode.append("</HTML>");
			// htmlCode.append("/n");
		}
	}

	public static void prBr(boolean start) {

		if (start) // �к����
		{
			if (Property.NumOfLine < 10)
				htmlCode.append("<BR>00" + Property.NumOfLine);
			else if (Property.NumOfLine < 100)
				htmlCode.append("<BR>0" + Property.NumOfLine);
			else
				htmlCode.append("<BR>" + Property.NumOfLine);
			Property.NumOfLine++;
			// htmlCode.append("/n");
		} else {
			htmlCode.append("<BR>");
			// htmlCode.append("/n");
		}
		htmlCode.append(tab);

	}

	public static void prP(boolean start) {
		if (start) {
			htmlCode.append("<P>");
			// htmlCode.append("/n");
		} else {
			htmlCode.append("</P>");
			// htmlCode.append("/n");
		}
	}

	public static void prTable(boolean start) {
		if (start) {
			htmlCode.append("<TABLE>");
			// htmlCode.append("/n");
		} else {
			htmlCode.append("</TABLE>");
			// htmlCode.append("/n");
		}
	}

	public static void prTr(boolean start) {
		if (start) {
			htmlCode.append("<TR>");
			// htmlCode.append("/n");
		} else {
			htmlCode.append("</TR>");
			// htmlCode.append("/n");
		}
	}

	public static void prTd(boolean start) {
		if (start) {
			htmlCode.append("<TD>");
			// htmlCode.append("/n");
		} else {
			htmlCode.append("</TD>");
			// htmlCode.append("/n");
		}
	}

	public static void prStringOne(boolean start) {
		if (comment || lineComment) {
			prJComment("\'");
		} else if (start) {
			htmlCode.append("<FONT color=blue>\'");
			stringlit = true;
		} else {
			htmlCode.append("\'</FONT>");
			stringlit = false;
		}
	}

	public static void prStringTwo(boolean start) {
		if (comment || lineComment) {
			prJComment("\"");
		} else if (start) {
			htmlCode.append("<FONT color=blue>\"");
			stringlit = true;
			stringlit2 = true;
		} else {
			htmlCode.append("\"</FONT>");
			stringlit = false;
			stringlit2 = false;
		}
	}

}