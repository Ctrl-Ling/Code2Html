package cn.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.IOException;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.WindowConstants;

public class Http extends JFrame {

	private String Str; // 文件名
	private Container contentPane;
	private JTextField addressTex; // 文本框
	private JScrollPane centerPane; // 初始化滚动条
	private JEditorPane textArea; // 编辑显示区域

	public Http(String str) {
		// *******************浏览器*************************************
		super(str);
		Str = str;
		setBounds(120, 80, 750, 500);
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		contentPane = this.getContentPane();

		contentPane.setLayout(new BorderLayout());

		addressTex = new JTextField(40); // 地址栏
		// 浏览器体部
		centerPane = new JScrollPane();
		// 显示区域
		JViewport view = centerPane.getViewport();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		// 内容显示
		textArea = new JEditorPane();
		addressTex.setText(Str);
		textArea.setEditable(false);
		try {
			textArea.setPage(addressTex.getText().trim());
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		view.add(textArea);
		contentPane.add(centerPane);

	}
}
