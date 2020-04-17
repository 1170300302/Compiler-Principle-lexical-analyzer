package ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import core.Controller;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class CoreFrame extends JFrame {

  private String charStreamPath = ".\\src\\doc\\CharStream.txt";
  JComboBox<String> fxSetComboBox;
  private JTable tokenTable;
  private JTable setTable;

  public CoreFrame() {
    initial();
  }

  private void initial() {
    setVisible(true);
    setBounds(100, 100, 850, 750);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setTitle("Compiler");
    setFont(new Font("Courier New", Font.PLAIN, 15));

    CorePanel corePanel = new CorePanel();
    corePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    corePanel.setLayout(null);
    setContentPane(corePanel);

    JTextArea charStreamTextArea = new JTextArea();
    charStreamTextArea.setFont(new Font("Courier New", Font.PLAIN, 15));
    JScrollPane charStreamScroll = new JScrollPane(charStreamTextArea);
    charStreamScroll.setSize(250, 280);
    charStreamScroll.setLocation(35, 55);
    charStreamScroll
        .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    charStreamScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    corePanel.add(charStreamScroll);

    JButton readFileButton = new JButton("\u8BFB\u5165\u6587\u4EF6");
    readFileButton.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        JFileChooser jFileChooser = new JFileChooser(charStreamPath);
        jFileChooser.showOpenDialog(corePanel);
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        charStreamPath = jFileChooser.getSelectedFile().getPath();
        try {
          BufferedReader bufferedReader = new BufferedReader(new FileReader(charStreamPath));
          charStreamTextArea.read(bufferedReader, "charStream");
          bufferedReader.close();
        } catch (FileNotFoundException e1) {
          e1.printStackTrace();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    });
    readFileButton.setFont(new Font("����", Font.PLAIN, 14));
    readFileButton.setBounds(35, 20, 100, 25);
    corePanel.add(readFileButton);

    JButton lexicalAnalysisButton = new JButton("\u8BCD\u6CD5\u5206\u6790");
    lexicalAnalysisButton.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        try {
          BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(charStreamPath));
          bufferedWriter.write(charStreamTextArea.getText());
          bufferedWriter.flush();
          bufferedWriter.close();
          Controller.lexicalAnalysis(charStreamPath);
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    });
    lexicalAnalysisButton.setFont(new Font("����", Font.PLAIN, 14));
    lexicalAnalysisButton.setBounds(155, 20, 100, 25);
    corePanel.add(lexicalAnalysisButton);

    tokenTable = new JTable();
    tokenTable
        .setModel(new DefaultTableModel(new Object[][] {}, new String[] {"\u952E", "\u503C"}));
    tokenTable.setFont(new Font("Courier New", Font.PLAIN, 13));
    JScrollPane tokenScrollPane = new JScrollPane();
    tokenScrollPane.setBounds(35, 345, 250, 360);
    corePanel.add(tokenScrollPane);
    tokenScrollPane.setViewportView(tokenTable);

    JButton syntaxAnalysisButton = new JButton("\u8BED\u6CD5\u5206\u6790");
    syntaxAnalysisButton.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        // �﷨�������
      }
    });
    syntaxAnalysisButton.setFont(new Font("����", Font.PLAIN, 14));
    syntaxAnalysisButton.setBounds(275, 20, 100, 25);
    corePanel.add(syntaxAnalysisButton);

    fxSetComboBox = new JComboBox<String>();
    fxSetComboBox.setMaximumRowCount(3);
    fxSetComboBox
        .setModel(new DefaultComboBoxModel<String>(new String[] {"FIRST", "FOLLOW", "FORECAST"}));
    fxSetComboBox.setFont(new Font("Courier New", Font.PLAIN, 14));
    fxSetComboBox.setBounds(395, 20, 100, 25);
    corePanel.add(fxSetComboBox);

    JTree parsingTree = new JTree();
    parsingTree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("P") {
      {
        DefaultMutableTreeNode node_1;
        node_1 = new DefaultMutableTreeNode("a");
        node_1.add(new DefaultMutableTreeNode("c"));
        add(node_1);
        node_1 = new DefaultMutableTreeNode("b");
        node_1.add(new DefaultMutableTreeNode("d"));
        node_1.add(new DefaultMutableTreeNode("e"));
        add(node_1);
      }
    }));
    parsingTree.setFont(new Font("Courier New", Font.PLAIN, 13));
    parsingTree.setBounds(295, 55, 250, 280);
    corePanel.add(parsingTree);

    setTable = new JTable();
    setTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] {"\u952E", "\u503C"}));
    setTable.setFont(new Font("Courier New", Font.PLAIN, 13));
    JScrollPane setScrollPane = new JScrollPane();
    setScrollPane.setBounds(295, 345, 250, 360);
    corePanel.add(setScrollPane);
    setScrollPane.setViewportView(setTable);

    JTextArea errorTextArea = new JTextArea();
    errorTextArea.setFont(new Font("Courier New", Font.PLAIN, 15));
    JScrollPane errorScroll = new JScrollPane(errorTextArea);
    errorScroll.setSize(250, 280);
    errorScroll.setLocation(555, 55);
    errorScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    errorScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    corePanel.add(errorScroll);
  }

  public void setTokenTable(Map<String, String> token) {
    Vector<String> title = new Vector<>();
    title.add("��");
    title.add("ֵ");
    Vector<Vector<Object>> tableData = new Vector<Vector<Object>>();
    for (Map.Entry<String, String> entry : token.entrySet()) {
      Vector<Object> tmpVector = new Vector<>();
      switch (entry.getValue()) {
        case "IDN":
        case "CONST":
        case "NOTES":
        case "CHARCONST":
        case "OCT":
        case "HEX":
//          System.out.println(
//              entry.getKey() + "\t\t\t" + "<" + entry.getValue() + ", " + entry.getKey() + ">");
          tmpVector.add(entry.getKey());
          tmpVector.add(entry.getValue() + "," + entry.getKey());
          break;
        case "KEYWORD":
        case "BOUNDARY":
        case "OPERATOR":
//          System.out.println(entry.getKey() + "\t\t\t" + "<" + entry.getKey() + ", _>");
          tmpVector.add(entry.getKey());
          tmpVector.add(entry.getKey() + "," + "_");
          break;
        default:
          System.out.println("Error");
          break;
      }
      tableData.add(tmpVector);
    }
    TableModel tokenTableModel = new DefaultTableModel(tableData, title);
    tokenTable.setModel(tokenTableModel);
  }

  public void setErrorTextArea() {

  }

  public void setParsingTree() {

  }

}


class CorePanel extends JPanel {

}
