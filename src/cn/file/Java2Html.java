package cn.file;

import java.util.*;

import cn.ui.Property;
import cn.ui.SourceFile;

import java.io.*;

public class Java2Html {

	private static Collection primitives = new ArrayList();
	static {
		String[] primString = { "int", "byte", "boolean", "short", "long", "char", "float", "double", "String", "void",
				"StringBuffer", "Collection" };
		for (int i = 0; i < primString.length; i++)
			primitives.add(primString[i]);
	}

	private static Collection operators = new ArrayList();
	static {
		String[] operString = { "=", ">", "<", "!", "~", "?", ":", "==", "<=", ">=", "!=", "&&", "||", "++", "--", "+",
				"-", "*", "/", "&", "|", "^", "%", ">>", "<<", ">>>", "+=", "-=", "*=", "/=", "&=", "|=", "^=", "%=",
				"<<=", ">>=", "<<<" };
		for (int i = 0; i < operString.length; i++)
			operators.add(operString[i]);
	}

	private static Collection string = new ArrayList();
	static {
		String[] stringString = { "\"" };
		for (int i = 0; i < stringString.length; i++)
			string.add(stringString[i]);
	}

	private static Collection flow = new ArrayList();
	static {
		String[] flowString = { "true", "false", "null" };
		for (int i = 0; i < flowString.length; i++)
			flow.add(flowString[i]);
	}

	private static Collection resWords = new ArrayList();
	static {
		String[] reswString = { "import", "private", "static", "new", "public", "final", "class", "this",
				"synchronized", "native", "package", "if", "then", "else", "finally", "switch", "do", "while", "case",
				"default", "return", "break", "continue", "throw", "throws", "catch", "try", "for", "protected" };
		for (int i = 0; i < reswString.length; i++)
			resWords.add(reswString[i]);
	}

	private static void java2html(String fileName) {
	}

	public String CreateHtml() {

		StreamTokenizer st;
		String htmlFile = "d:/" + SourceFile.name + ".html";
		boolean isVar = false; // 判断是否为变量
		Java2HtmlRule jr = new Java2HtmlRule();
		jr.htmlCode.setLength(0);
		boolean firstOfComment = false; // 判断是否为注释

		try {
			FileInputStream file1;
			file1 = new FileInputStream(SourceFile.PATH);
			InputStreamReader file = new InputStreamReader(file1, Property.codeType);
			Reader r = new BufferedReader(file);
			st = new StreamTokenizer(r);
			st.eolIsSignificant(true);
			st.ordinaryChar('\"');
			st.ordinaryChar('/');
			st.ordinaryChar('\\');
			st.ordinaryChar('.');
			st.ordinaryChar('\'');
			// st.ordinaryChar('#');
			st.ordinaryChar(' ');
			jr.prHtml(jr.START);
			jr.prHead(jr.START);
			jr.prTitle(SourceFile.name + ".html");
			jr.prHead(jr.END);
			jr.prBody(jr.START);
			// nextToken方法读取下一个Token.
			// TT_EOF指示已读到流末尾的常量。
			while (st.nextToken() != StreamTokenizer.TT_EOF) {
				// 在调用 nextToken 方法之后，ttype字段将包含刚读取的标记的类型
				switch (st.ttype) {
				// TT_WORD指示已读到一个文字标记的常量
				// 字母类型
				case StreamTokenizer.TT_WORD:
					if (firstOfComment) // 单斜杠，判断是否为注释
					{
						if ((!jr.comment && !jr.lineComment))
							jr.prOperator("/");
					}
					firstOfComment = false;
					// 对该字进行判断在哪个部分

					// 是否在变量类型
					if (primitives.contains(st.sval)) {
						jr.prPrimitive(st.sval);
						isVar = true;
						jr.backslash = false;
						break;
					}
					// 是否在flow里
					if (flow.contains(st.sval)) {
						isVar = false;
						jr.prFlow(st.sval);
						jr.backslash = false;
						break;
					}
					// 是否在关键字里
					if (resWords.contains(st.sval)) {
						isVar = false;
						jr.prResWord(st.sval);
						jr.backslash = false;
						break;
					}
					// 变量列表
					if (jr.varList.contains(st.sval)) {
						isVar = false;
						jr.prVar(st.sval);
						jr.backslash = false;
						break;
					}
					if (isVar && !(jr.varList.contains(st.sval))) {
						jr.varList.add(st.sval);
						jr.prVar(st.sval);
					} else {
						jr.prNormal(st.sval);
					}
					jr.backslash = false;
					break;
				// 结尾
				// TT_EOL指示已读到行末尾的常量
				case StreamTokenizer.TT_EOL:
					isVar = false;
					if (firstOfComment) {
						jr.prOperator("/");
						firstOfComment = false;
					}
					firstOfComment = false;
					jr.lineComment = false;
					jr.prBr(jr.START);
					jr.backslash = false;
					break;
				// TT_NUMBER指示已读到一个数字标记的常量
				// 数字处理
				case StreamTokenizer.TT_NUMBER:
					isVar = false;
					if (firstOfComment) {
						jr.prOperator("/");
						firstOfComment = false;
					}
					firstOfComment = false;

					if (String.valueOf((char) st.ttype).equals(".")) {
						System.out.println("1223\n");
						jr.prFlow(".");
						jr.backslash = false;
						break;
					}
					if (st.nval - (int) st.nval == 0) {
						jr.prNormal(new Integer((int) st.nval).toString());
					} else
						// 如果当前标记是一个数字，nval字段将包含该数字的值
						jr.prNumber(new Double(st.nval).toString());
					jr.backslash = false;
					break;

				// 如果以上3中类型都不是，则为英文的标点符号
				default:

					isVar = false;
					// 运算符判断
					if (operators.contains(String.valueOf((char) st.ttype))) {
						if (firstOfComment) {
							if (jr.comment) {
								// there has been a star, waiting for slash
								if (String.valueOf((char) st.ttype).equals("/")) {
									firstOfComment = false;
									jr.prJComment("*/");
									jr.comment = false;
									jr.backslash = false;
									firstOfComment = false;
									break;
								} else
									firstOfComment = false;
							} else {
								// there has been a slash, waiting for slash or
								// star
								if (String.valueOf((char) st.ttype).equals("/")) {
									if (!jr.stringlit) {
										jr.lineComment = true;
										jr.prJComment("//");
									} else {
										jr.lineComment = false;
										jr.prNormal("//");
									}
									firstOfComment = false;
									jr.comment = false;
									jr.backslash = false;
									break;
								} else if (String.valueOf((char) st.ttype).equals("*")) {
									if (!jr.stringlit) {
										jr.comment = true;
										jr.prJComment("/*");
									} else {
										jr.comment = false;
										jr.prNormal("/*");
									}
									firstOfComment = false;
									jr.lineComment = false;
									jr.backslash = false;
									break;
								} else {
									firstOfComment = false;
									if (!jr.lineComment)
										jr.prOperator("/");
									jr.prOperator(String.valueOf((char) st.ttype));
									jr.backslash = false;
									break;
								}
							}
						} else {
							if (jr.comment) {
								// waiting for star
								if (String.valueOf((char) st.ttype).equals("*")) {
									firstOfComment = true;
									jr.backslash = false;
									break;
								} else
									firstOfComment = false;
							} else {
								// waiting for slash
								if (String.valueOf((char) st.ttype).equals("/")) {
									firstOfComment = true;
									jr.backslash = false;
									break;
								} else {
									// 操作符设置颜色
									firstOfComment = false;
									jr.prOperator(String.valueOf((char) st.ttype));
									jr.backslash = false;
									break;
								}
							}
						}
					}
					// 字符串判断
					if (String.valueOf((char) st.ttype).equals("\"")) {
						if (firstOfComment) {
							if (!(jr.comment || jr.lineComment))
								jr.prOperator("/");
							firstOfComment = false;
						}
						if (!jr.backslash)
							jr.prStringTwo(!jr.stringlit);
						else
							jr.prNormal("\"");
						jr.backslash = false;
						break;
					}
					// 注释判断
					if (String.valueOf((char) st.ttype).equals("\\")) {
						if (firstOfComment) {
							jr.prOperator("/");
							firstOfComment = false;
						}
						jr.prNormal("\\");
						jr.backslash = !jr.backslash;
						break;
					}
					// 字符判断
					if (String.valueOf((char) st.ttype).equals("\'")) {
						if (firstOfComment) {
							jr.prOperator("/");
							firstOfComment = false;
						}
						if (!jr.backslash && !jr.stringlit2)
							jr.prStringOne(!jr.stringlit);
						else
							jr.prNormal("\'");
						jr.backslash = false;
						break;
					}
					// 分号判断
					if (String.valueOf((char) st.ttype).equals(";")) {
						if (firstOfComment) {

							jr.prOperator("/");
							firstOfComment = false;
						}
						jr.prOther(";");
						jr.backslash = false;
						break;
					}
					// 逗号
					if (String.valueOf((char) st.ttype).equals(",")) {
						if (firstOfComment) {
							jr.prOperator("/");
							firstOfComment = false;
						}
						jr.prFlow(",");
						jr.backslash = false;
						break;
					}
					if (String.valueOf((char) st.ttype).equals(".")) {
						if (firstOfComment) {
							jr.prOperator("/");
							firstOfComment = false;
						}
						jr.prOther(".");
						jr.backslash = false;
						break;
					}
					if (String.valueOf((char) st.ttype).equals(" ")) {
						if (firstOfComment) {
							jr.prOperator("/");
							firstOfComment = false;
						}
						jr.htmlCode.append(" ");
						jr.backslash = false;
						break;
					}
					if (String.valueOf((char) st.ttype).equals(")")) {
						if (firstOfComment) {
							jr.prOperator("/");
							firstOfComment = false;
						}
						jr.prOther(")");
						jr.backslash = false;
						break;
					}
					if (String.valueOf((char) st.ttype).equals("(")) {
						if (firstOfComment) {
							jr.prOperator("/");
							firstOfComment = false;
						}
						jr.prOther("(");
						jr.backslash = false;
						break;
					}
					if (String.valueOf((char) st.ttype).equals("]")) {
						if (firstOfComment) {
							jr.prOperator("/");
							firstOfComment = false;
						}
						jr.prOther("]");
						jr.backslash = false;
						break;
					}
					if (String.valueOf((char) st.ttype).equals("[")) {
						if (firstOfComment) {
							jr.prOperator("/");
							firstOfComment = false;
						}
						jr.prOther("[");
						jr.backslash = false;
						break;
					}
					if (String.valueOf((char) st.ttype).equals("{")) {
						if (firstOfComment) {
							jr.prOperator("/");
							firstOfComment = false;
						}
						jr.tabIncrease(true);
						jr.prOther("{");
						jr.backslash = false;
						break;
					}
					if (String.valueOf((char) st.ttype).equals("}")) {
						if (firstOfComment) {
							jr.prOperator("/");
							firstOfComment = false;
						}
						jr.tabIncrease(false);
						if (jr.htmlCode.substring(jr.htmlCode.length() - 24).equals("&nbsp;&nbsp;&nbsp;&nbsp;")) {
							jr.htmlCode.delete(jr.htmlCode.length() - 24, jr.htmlCode.length());
						}
						jr.prOther("}");
						jr.backslash = false;
						break;
					}

					jr.prNormal(String.valueOf((char) st.ttype));
					jr.backslash = false;
					break;
				}
			}
			jr.prBody(jr.END);
			jr.prHtml(jr.END);
			r.close();
			file.close();
			file1.close();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find the file: " + SourceFile.path);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("There is no input file");
		}
		return jr.htmlCode.toString();
	}
}
