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

	private String Str; // �ļ���
	private Container contentPane;
	private JTextField addressTex; // �ı���
	private JScrollPane centerPane; // ��ʼ��������
	private JEditorPane textArea; // �༭��ʾ����

	public Http(String str) {
		// *******************�����*************************************
		super(str);
		Str = str;
		setBounds(120, 80, 750, 500);
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		contentPane = this.getContentPane();

		contentPane.setLayout(new BorderLayout());

		addressTex = new JTextField(40); // ��ַ��
		// ������岿
		centerPane = new JScrollPane();
		// ��ʾ����
		JViewport view = centerPane.getViewport();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		// ������ʾ
		textArea = new JEditorPane();
		addressTex.setText(Str);
		textArea.setEditable(false);
		try {
			textArea.setPage(addressTex.getText().trim());
		} catch (IOException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}
		view.add(textArea);
		contentPane.add(centerPane);

	}
}
