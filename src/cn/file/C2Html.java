package cn.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Collection;

import cn.ui.SourceFile;

public class C2Html {

	private static Collection resWords = new ArrayList();
	static {
		String[] reswString = { "auto", "short", "int", "long", "float", "double", "char", "struct", "union", "enum",
				"typedef", "const", "unsigned", "signed", "extern", "register", "static", "volatile", "void", "if",
				"else", "switch", "case", "for", "do", "while", "goto", "continue", "break", "default", "sizeof",
				"return" };
		for (int i = 0; i < reswString.length; i++)
			resWords.add(reswString[i]);
	}
	private static Collection operators = new ArrayList();
	static {
		String[] operString = { "=", ">", "<", "!", "~", "?", ":", "==", "<=", ">=", "!=", "&&", "||", "++", "--", "+",
				"-", "*", "/", "&", "|", "^", "%", ">>", "<<", ">>>", "+=", "-=", "*=", "/=", "&=", "|=", "^=", "%=",
				"<<=", ">>=", "<<<" };
		for (int i = 0; i < operString.length; i++)
			operators.add(operString[i]);
	}

	public String CreateHtml() {

		// FileInputStream file;
		StreamTokenizer st;
		String htmlFile = "d:/" + SourceFile.name + ".html";
		boolean isVar = false; // 判断是否为变量
		C2HtmlRule cr = new C2HtmlRule();
		cr.htmlCode.setLength(0);
		boolean firstOfComment = false; // 判断是否为注释
		boolean importFile = false;
		try {

			FileInputStream file1;
			file1 = new FileInputStream(SourceFile.PATH);
			InputStreamReader file = new InputStreamReader(file1, "UTF-8");
			Reader r = new BufferedReader(file);
			st = new StreamTokenizer(r);
			st.eolIsSignificant(true);
			st.ordinaryChar('\"');
			st.ordinaryChar('/');
			st.ordinaryChar('\\');
			st.ordinaryChar('.');
			st.ordinaryChar('\'');
			st.ordinaryChar(' ');
			cr.csHtml(cr.START);
			cr.csHead(cr.START);
			cr.csTitle(SourceFile.name + ".html");
			// cr.csStyle();
			cr.csHead(cr.END);
			cr.csBody(cr.START);

			while (st.nextToken() != StreamTokenizer.TT_EOF) {
				switch (st.ttype) {

				case StreamTokenizer.TT_EOL:
					isVar = false;
					firstOfComment = false;
					importFile = false;
					cr.lineComment = false;
					cr.backslash = false;
					cr.csBr();
					break;
				case StreamTokenizer.TT_NUMBER:
					isVar = false;
					firstOfComment = false;
					if (st.nval - (int) st.nval == 0) {
						cr.csNumber(new Integer((int) st.nval).toString());
					} else
						cr.csNumber(new Double(st.nval).toString());
					cr.backslash = false;
					break;
				case StreamTokenizer.TT_WORD:
					if (importFile) {
						cr.csImportFile(st.sval);
						break;
					}
					if (firstOfComment) {
						if ((!cr.comment && !cr.lineComment))
							cr.csChar("/");
					}
					if (resWords.contains(st.sval)) {
						isVar = false;
						cr.csKeyword(st.sval);
						cr.backslash = false;
						break;
					}
					firstOfComment = false;
					cr.csOther(st.sval);
					cr.backslash = false;
					break;
				default:
					isVar = false;

					if (operators.contains(String.valueOf((char) st.ttype))) {

						if (firstOfComment) {
							if (cr.comment) {

								if (String.valueOf((char) st.ttype).equals("/")) {
									firstOfComment = false;
									cr.csComment("*/");
									cr.comment = false;
									cr.backslash = false;
									break;
								} else {

									firstOfComment = false;
								}
							} else {

								if (String.valueOf((char) st.ttype).equals("/")) {
									if (!cr.stringlit) {
										cr.lineComment = true;
										cr.csComment("//");
									}
									firstOfComment = false;
									cr.comment = false;
									cr.backslash = false;
									break;
								} else if (String.valueOf((char) st.ttype).equals("*")) {
									if (!cr.stringlit) {
										cr.comment = true;
										cr.csComment("/*");
									} else {
										cr.comment = false;
										cr.csChar("/*");
									}
									firstOfComment = false;
									cr.lineComment = false;
									cr.backslash = false;
									break;
								} else {
									firstOfComment = false;
									if (!cr.lineComment)
										cr.csChar("/");
									cr.csChar(String.valueOf((char) st.ttype));
									cr.backslash = false;
									break;
								}
							}
						} else {
							if (cr.comment) {

								if (String.valueOf((char) st.ttype).equals("*")) {
									firstOfComment = true;
									cr.backslash = false;
									break;
								} else
									firstOfComment = false;
							} else {

								if (String.valueOf((char) st.ttype).equals("/")) {
									firstOfComment = true;
									cr.backslash = false;
									break;
								} else {
									if (importFile) {
										if (String.valueOf((char) st.ttype).equals("<")) {
											if (importFile) {

												cr.csImportFile("<");
												break;
											} else {
												cr.csChar("<");
												cr.backslash = false;
												break;
											}
										}
										if (String.valueOf((char) st.ttype).equals(">")) {
											if (importFile) {

												cr.csImportFile(">");
												importFile = false;
												break;
											} else {
												cr.csChar(">");
												cr.backslash = false;
												break;
											}
										}
									} else {
										firstOfComment = false;
										cr.csChar(String.valueOf((char) st.ttype));
										cr.backslash = false;
										break;
									}
								}
							}
						}
					}
					if (String.valueOf((char) st.ttype).equals("#")) {
						if (firstOfComment) {
							cr.csChar("/");
							firstOfComment = false;
							importFile = false;
							cr.csOther("#");
							break;
						}
						importFile = true;
						cr.csImportFile("#");
						break;
					}
					if (String.valueOf((char) st.ttype).equals(" ")) {
						if (firstOfComment) {
							cr.csChar("/");
							firstOfComment = false;
						}
						cr.htmlCode.append(" ");
						cr.backslash = false;
						break;
					}
					if (String.valueOf((char) st.ttype).equals("(")) {
						if (firstOfComment) {
							cr.csChar("/");
							firstOfComment = false;
						}
						cr.csChar("(");
						cr.backslash = false;
						break;
					}
					if (String.valueOf((char) st.ttype).equals(")")) {
						if (firstOfComment) {
							cr.csChar("/");
							firstOfComment = false;
						}
						cr.csChar(")");
						cr.backslash = false;
						break;
					}
					if (String.valueOf((char) st.ttype).equals("[")) {
						if (firstOfComment) {
							cr.csChar("/");
							firstOfComment = false;
						}
						cr.csChar("[");
						cr.backslash = false;
						break;
					}
					if (String.valueOf((char) st.ttype).equals("]")) {
						if (firstOfComment) {
							cr.csChar("/");
							firstOfComment = false;
						}
						cr.csChar("]");
						cr.backslash = false;
						break;
					}
					if (String.valueOf((char) st.ttype).equals("\"")) {
						if (firstOfComment) {
							cr.csChar("/");
							firstOfComment = false;
						}
						if (!cr.backslash)
							cr.csStringTwo(!cr.stringlit);
						else
							cr.csString("\"");
						cr.backslash = false;
						break;
					}
					if (String.valueOf((char) st.ttype).equals("\\")) {
						if (firstOfComment) {
							cr.csChar("/");
							firstOfComment = false;
						}
						cr.csOther("\\");
						cr.backslash = !cr.backslash;
						break;
					}
					if (String.valueOf((char) st.ttype).equals("\'")) {
						if (firstOfComment) {
							cr.csChar("/");
							firstOfComment = false;
						}
						if (!cr.backslash && !cr.stringlit2)
							cr.csStringOne(!cr.stringlit);
						else
							cr.csOther("\'");
						cr.backslash = false;
						break;
					}

					if (String.valueOf((char) st.ttype).equals(";")) {
						if (firstOfComment) {
							cr.csChar("/");
							firstOfComment = false;
						}
						cr.csChar(";");
						cr.backslash = false;
						break;
					}
					if (String.valueOf((char) st.ttype).equals(",")) {
						if (firstOfComment) {
							cr.csChar("/");
							firstOfComment = false;
						}
						cr.csChar(",");
						cr.backslash = false;
						break;
					}
					if (String.valueOf((char) st.ttype).equals(".")) {
						if (firstOfComment) {
							cr.csChar("/");
							firstOfComment = false;
						}
						if (importFile) {
							cr.csImportFile(".");
							break;
						}
						cr.csChar(".");
						cr.backslash = false;
						break;
					}
					if (String.valueOf((char) st.ttype).equals("{")) {
						if (firstOfComment) {
							cr.csChar("/");
							firstOfComment = false;
						}
						cr.tabIncrease(true);
						cr.csChar("{");
						cr.backslash = false;
						break;
					}
					if (String.valueOf((char) st.ttype).equals("}")) {
						if (firstOfComment) {
							cr.csChar("/");
							firstOfComment = false;
						}
						cr.tabIncrease(false);
						if (cr.htmlCode.substring(cr.htmlCode.length() - 24).equals("&nbsp;&nbsp;&nbsp;&nbsp;")) {
							cr.htmlCode.delete(cr.htmlCode.length() - 24, cr.htmlCode.length());
						}
						cr.csChar("}");
						cr.backslash = false;
						break;
					}
					if (String.valueOf((char) st.ttype).equals("_")) {
						if (firstOfComment) {
							cr.csChar("/");
							firstOfComment = false;
						}
						if (importFile) {
							cr.csImportFile("_");
							break;
						}
						cr.csChar("_");
						cr.backslash = false;
						break;
					}
					cr.csOther(String.valueOf((char) st.ttype));
					cr.backslash = false;
				}
			}

			cr.csBody(cr.END);
			cr.csHtml(cr.END);
			r.close();
			file.close();
			file1.close();
		} catch (IOException e) {
			;
		}

		return cr.htmlCode.toString();
	}

}