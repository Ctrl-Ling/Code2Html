package cn.ui;

import javax.swing.JOptionPane;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import cn.file.*;
import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.DirectoryChooserBuilder;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import cn.file.*;

public class View extends Application {

	private final Desktop desktop = Desktop.getDesktop();

	public static String Address = "NULL"; // �ļ��е�ַ��
	private String address; // �ļ�ȫ��
	private String KeywordsOfColor[] = { "PURPLE", "BLUE", "BLACK", "YELLOW", "GREEN" };
	private String FontOfFamily[] = { "΢���ź�", "����", "����", "����", "����" };
	private String FontOfSize[] = { "15", "17", "19", "21", "23", "24" };
	private String IndentedOfCase[] = { "2", "4", "6", "8" };
	private String codeType[] = { "UTF-8", "GBK" };
	private int Indented[] = { 2, 4, 6, 8 };

	private ComboBox<String> cbo = new ComboBox<>();
	private ComboBox<String> cbo1 = new ComboBox<>();
	private ComboBox<String> cbo2 = new ComboBox<>();
	private ComboBox<String> cbo3 = new ComboBox<>();
	private ComboBox<String> cbo4 = new ComboBox<>();

	private ListView<String> list1 = new ListView<>();
	private ListView<String> list2 = new ListView<>();

	private ArrayList List = new ArrayList();
	private ArrayList List1 = new ArrayList();
	public static ArrayList<FileNameArray> ListOfFile = new ArrayList<FileNameArray>();

	protected HBox getPane(Stage primaryStage) {

		Property Property1 = new Property();
		GridPane pane = new GridPane();
		GridPane pane1 = new GridPane();
		HBox hbox = new HBox(2);
		VBox vbox = new VBox(2);
		vbox.setPadding(new Insets(11.5, 12.5, 0, 0));
		vbox.getChildren().add(0, pane1);
		hbox.getChildren().add(pane);
		hbox.getChildren().add(vbox);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(6);
		pane.setVgap(6);
		pane1.setPadding(new Insets(11.5, 12.5, 0, 0));
		pane1.setHgap(6);
		pane1.setVgap(6);

		// �ؼ�����ɫ��Ͽ�ѡ��
		cbo.setPrefWidth(120);
		cbo.setValue(KeywordsOfColor[0]);
		cbo.getItems().addAll(KeywordsOfColor);
		ObservableList<String> items = FXCollections.observableArrayList(KeywordsOfColor);
		cbo.setOnAction(e -> {
			Property.KeywordsOfColor = (KeywordsOfColor[items.indexOf(cbo.getValue())]);
		});

		// ����������Ͽ�ѡ��
		cbo1.setPrefWidth(120);
		cbo1.setValue(FontOfFamily[0]);
		cbo1.getItems().addAll(FontOfFamily);
		ObservableList<String> items0 = FXCollections.observableArrayList(FontOfFamily);
		cbo1.setOnAction(e -> {
			Property.FontOfFamily = FontOfFamily[items0.indexOf(cbo1.getValue())];
		});

		// �����С��Ͽ�ѡ��
		cbo2.setPrefWidth(120);
		cbo2.setValue(FontOfSize[0]);
		cbo2.getItems().addAll(FontOfSize);
		ObservableList<String> items1 = FXCollections.observableArrayList(FontOfSize);
		cbo2.setOnAction(e -> {
			Property.FontOfSize = FontOfSize[items1.indexOf(cbo2.getValue())];
		});

		// ����������Ͽ�ѡ��
		cbo3.setPrefWidth(120);
		cbo3.setValue(IndentedOfCase[0]);
		cbo3.getItems().addAll(IndentedOfCase);
		ObservableList<String> items2 = FXCollections.observableArrayList(IndentedOfCase);
		cbo3.setOnAction(e -> {
			Property.IndentedOfCase = Indented[items2.indexOf(cbo3.getValue())];

		});

		// �ļ��򿪸�ʽ
		cbo4.setPrefWidth(120);
		cbo4.setValue(codeType[0]);
		cbo4.getItems().addAll(codeType);
		ObservableList<String> items3 = FXCollections.observableArrayList(codeType);
		cbo4.setOnAction(e -> {
			Property.codeType = (codeType[items3.indexOf(cbo4.getValue())]);
		});

		// �ļ���ַѡ����
		final Button SelectButton = new Button("ѡ�񱣴�·��");
		final TextField taNode = new TextField();
		SelectButton.setPrefWidth(170);
		SelectButton.setOnAction((final ActionEvent e) -> {
			DirectoryChooserBuilder builder = DirectoryChooserBuilder.create();
			builder.title("ѡ�񱣴�·��");
			DirectoryChooser chooser = builder.build();
			File chosenDir = chooser.showDialog(primaryStage);
			if (chosenDir != null) {
				Address = chosenDir.getAbsolutePath();
				taNode.setText(Address.replaceAll("\\\\", "\\/"));
			} else {
				System.out.print("no directory chosen");
			}
		});

		// �ļ�ѡ��ת��
		final FileChooser fileChooser = new FileChooser();
		final Button openButton = new Button("ѡ���ļ�");
		openButton.setPrefWidth(170);
		openButton.setOnAction((final ActionEvent e) -> {
			configureFileChooser(fileChooser);
			java.util.List<File> list = fileChooser.showOpenMultipleDialog(primaryStage);
			if (list != null) {
				list.stream().forEach((file) -> {
					ListOfFile.add(new FileNameArray(file.getPath()));

					// �б��ļ���1
					List.add(file.getName());
					// �б��ļ���2
					List1.add(file.getName() + ".html");

					ListView1(List);
					// ListView2(List1);
				});
			}

		});

		// �ļ��б�������
		list1.setPrefWidth(170);
		list1.setPrefHeight(400);
		list2.setPrefWidth(170);
		list2.setPrefHeight(400);

		// �б���
		list2.getSelectionModel().selectedItemProperty().addListener(ov -> {
			int Index = list2.getSelectionModel().getSelectedIndex();
			String str = list2.getItems().get(Index);
			address = "file:/" + Address.replaceAll("\\\\", "\\/") + "/" + str;
			Http bro = new Http(address);
			bro.setVisible(true);
			Http.class.getClass();
			list2.getSelectionModel().clearSelection();
		});

		Button Button1 = new Button("����б�");
		Button1.setOnAction(e -> {
			list1.setItems(null); // �б��ļ���1
			List.clear(); // �ļ�������
			List1.clear(); // �ļ�������
		});
		Button Button2 = new Button("����б�");
		Button2.setOnAction(e -> {
			list2.setItems(null); // �б��ļ���2
			ListOfFile.clear(); // ����ļ���
		});

		Button Button3 = new Button("ת��=>");
		Button3.setOnAction(e -> {
			Address = taNode.getText();
			if (taNode.getText().length() < 1) {
				new Alert(Alert.AlertType.NONE, "��ѡ���ļ����ļ�����·����", ButtonType.CLOSE).show();
			} else {
				ListView2(List1);
				// ת�����ܣ�ѭ��ת��html�ļ����������ļ�����ָ���ļ���(�ļ���ַѡ����)
				for (int i = 0; i < ListOfFile.size(); i++) {
					SourceFile.path = ListOfFile.get(i).getPath();
					SourceFile.name = ListOfFile.get(i).getName();
					SourceFile.PATH = ListOfFile.get(i).getFileName();
					SourceFile.fileAccepted = true;

					try {
						String sourceCode = "";

						sourceCode = new FileToString().fileToString(SourceFile.path);
						Property.NumOfLine = 2;
					} catch (FileNotFoundException e1) {
						// TODO �Զ����ɵ� catch ��
						e1.printStackTrace();
					}
				}
			}
		});
		Text tips = new Text("\n\n\n��ܰ��ʾ��\n����html�ļ��б�Ԥ��\n��������ʾ����\n�볢�Ը����ļ���ʽ");
		// tips.setFont(Font.font(20));
		vbox.getChildren().add(1, tips);
		pane1.add(new Label("�ؼ�����ɫ��"), 0, 1);
		pane1.add(cbo, 1, 1);
		pane1.add(new Label("�������ͣ�"), 0, 2);
		pane1.add(cbo1, 1, 2);
		pane1.add(new Label("�����С��"), 0, 3);
		pane1.add(cbo2, 1, 3);
		pane1.add(new Label("�������С��"), 0, 4);
		pane1.add(cbo3, 1, 4);
		pane1.add(new Label("�ļ���ʽ��"), 0, 5);
		pane1.add(cbo4, 1, 5);

		pane.add(new Label("Դ�ļ��б�"), 0, 1);
		pane.add(new Label("Html�ļ��б�"), 3, 1);

		pane.add(list1, 0, 2);
		pane.add(list2, 3, 2);

		pane.add(openButton, 0, 0);
		pane.add(SelectButton, 3, 0);
		vbox.getChildren().add(0, taNode);
		pane.setHalignment(list1, HPos.LEFT);
		pane.setHalignment(openButton, HPos.RIGHT);

		pane.add(Button1, 0, 7);
		pane.add(Button2, 3, 7);
		pane.add(Button3, 1, 2);// ת��

		return hbox;
	}

	private void configureFileChooser(FileChooser fileChooser) {
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("ALL", "*.*"),
				new FileChooser.ExtensionFilter("java", "*.java"), new FileChooser.ExtensionFilter("c", "*.c"),
				new FileChooser.ExtensionFilter("h", "*.h"));
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	@Override
	public void start(Stage primaryStage) {

		Scene scene = new Scene(getPane(primaryStage), 710, 500);
		primaryStage.setTitle("Code2HtmlԴ����ת������");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void ListView1(ArrayList List) {
		ObservableList<String> item0 = FXCollections.observableArrayList(List);
		list1.setItems(item0);
	}

	private void ListView2(ArrayList List1) {
		ObservableList<String> item0 = FXCollections.observableArrayList(List1);
		list2.setItems(item0);
	}

	private void openFile(File file) {
		EventQueue.invokeLater(() -> {
			try {
				desktop.open(file);
			} catch (IOException ex) {
				Logger.getLogger(Http.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
	}

	public static void main(String[] args) throws FileNotFoundException {

		Application.launch(args);

	}
}
