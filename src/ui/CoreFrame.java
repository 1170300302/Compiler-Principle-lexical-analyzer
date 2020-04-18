package ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import core.Controller;
import paser.AnalyzeLL;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class CoreFrame extends JFrame {

  private String charStreamPath = ".\\src\\doc\\CharStream.txt";
  private JTable tokenTable;
  private JTable setTable;
  private Map<String, Set<String>> first = new HashMap<>();
  private Map<String, Set<String>> follow = new HashMap<>();
  private String[][] forecast = new String[0][0];
  private JTextArea errorTextArea;
  private JTree parsingTree;

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
    readFileButton.setFont(new Font("宋体", Font.PLAIN, 14));
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
    lexicalAnalysisButton.setFont(new Font("宋体", Font.PLAIN, 14));
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
        AnalyzeLL.syntaxAnalysis();
      }
    });
    syntaxAnalysisButton.setFont(new Font("宋体", Font.PLAIN, 14));
    syntaxAnalysisButton.setBounds(275, 20, 100, 25);
    corePanel.add(syntaxAnalysisButton);

    JComboBox<String> fxSetComboBox = new JComboBox<String>();
    fxSetComboBox.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        switch (fxSetComboBox.getSelectedItem().toString()) {
          case "FIRST":
            setFirstOrFollow(0);
            break;
          case "FOLLOW":
            setFirstOrFollow(1);
            break;
          case "FORECAST":
            setForecast();
            break;
          default:
            break;
        }
      }
    });
    fxSetComboBox.setMaximumRowCount(3);
    fxSetComboBox
        .setModel(new DefaultComboBoxModel<String>(new String[] {"FIRST", "FOLLOW", "FORECAST"}));
    fxSetComboBox.setFont(new Font("Courier New", Font.PLAIN, 14));
    fxSetComboBox.setBounds(395, 20, 100, 25);
    corePanel.add(fxSetComboBox);

    parsingTree = new JTree();
    parsingTree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("P") {
      {}
    }));
    parsingTree.setFont(new Font("Courier New", Font.PLAIN, 13));
    parsingTree.setBounds(295, 55, 250, 280);
    DefaultTreeCellRenderer render = new DefaultTreeCellRenderer();
    render.setOpenIcon(null);
    render.setClosedIcon(null);
    render.setLeafIcon(null);
    parsingTree.setCellRenderer(render);
    corePanel.add(parsingTree);

    setTable = new JTable();
    setTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] {"\u952E", "\u503C"}));
    setTable.setFont(new Font("Courier New", Font.PLAIN, 13));
    JScrollPane setScrollPane = new JScrollPane();
    setScrollPane.setBounds(295, 345, 510, 360);
    corePanel.add(setScrollPane);
    setScrollPane.setViewportView(setTable);

    errorTextArea = new JTextArea();
    errorTextArea.setFont(new Font("Courier New", Font.PLAIN, 15));
    JScrollPane errorScroll = new JScrollPane(errorTextArea);
    errorScroll.setSize(250, 280);
    errorScroll.setLocation(555, 55);
    errorScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    errorScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    corePanel.add(errorScroll);
  }

  public List<String[]> setTokenTable(List<String[]> token) {
    List<String[]> resToken = new ArrayList<>();
    Vector<String> title = new Vector<>();
    title.add("键");
    title.add("值");
    Vector<Vector<Object>> tableData = new Vector<Vector<Object>>();
    for (int i = 0; i < token.size(); i++) {
      Vector<Object> tmpVector = new Vector<>();
      String[] tmpStrings = new String[3];
      switch (token.get(i)[1]) {
        case "id":
        case "CONST":
        case "NOTES":
        case "CHARCONST":
        case "OCT":
        case "HEX":
//          System.out.println(
//              entry.getKey() + "\t\t\t" + "<" + entry.getValue() + ", " + entry.getKey() + ">");
//          tmpVector.add(entry.getKey());
//          tmpVector.add(entry.getValue() + "," + entry.getKey());
          tmpVector.add(token.get(i)[0]);
          tmpVector.add(token.get(i)[1] + "," + token.get(i)[0]);
          tmpStrings[0] = token.get(i)[0];
          tmpStrings[1] = token.get(i)[1];
          tmpStrings[2] = token.get(i)[0];
          break;
        case "KEYWORD":
        case "BOUNDARY":
        case "OPERATOR":
//          System.out.println(entry.getKey() + "\t\t\t" + "<" + entry.getKey() + ", _>");
//          tmpVector.add(entry.getKey());
//          tmpVector.add(entry.getKey() + "," + "_");
          tmpVector.add(token.get(i)[0]);
          tmpVector.add(token.get(i)[0] + "," + "_");
          tmpStrings[0] = token.get(i)[0];
          tmpStrings[1] = token.get(i)[0];
          tmpStrings[2] = "_";
          break;
        default:
          System.out.println("Error");
          break;
      }
      tableData.add(tmpVector);
      resToken.add(tmpStrings);
    }
    TableModel tokenTableModel = new DefaultTableModel(tableData, title);
    tokenTable.setModel(tokenTableModel);
    return new ArrayList<>(resToken);
  }

  public void setSet(HashMap<String, HashSet<String>> firstSet, HashMap<String, HashSet<String>> followSet,
      String[][] forecast) {
    this.first = new HashMap<>(firstSet);
    this.follow = new HashMap<>(followSet);
    this.forecast = forecast;// 此处没有防止表示暴露
  }

  private void setFirstOrFollow(int flag) {
    Map<String, Set<String>> set;
    if (flag == 0) {
      set = first;
    } else {
      set = follow;
    }
    Vector<String> title = new Vector<>();
    title.add("键");
    title.add("值");
    Vector<Vector<Object>> tableData = new Vector<Vector<Object>>();
    for (Entry<String, Set<String>> entry : set.entrySet()) {
      Vector<Object> tmpVector = new Vector<>();
      tmpVector.add(entry.getKey());
      tmpVector.add(entry.getValue());
      tableData.add(tmpVector);
    }
    TableModel setTableModel = new DefaultTableModel(tableData, title);
    setTable.setModel(setTableModel);
  }

  private void setForecast() {
    Vector<String> title = new Vector<>();
    Vector<Vector<Object>> tableData = new Vector<Vector<Object>>();
    for(int i = 0; i < forecast[0].length; i++) {
      title.add(forecast[0][i]);
    }
//    title.add("");
//    title.add("$");
//    title.add("(");
//    title.add(")");
//    title.add("*");
//    title.add("id");
//    title.add("+");
    for (int i = 1; i < forecast.length; i++) {
      Vector<Object> tmpVector = new Vector<>();
//      switch (i) {
//        case 0:
//          tmpVector.add("T");
//          break;
//        case 1:
//          tmpVector.add("E");
//          break;
//        case 2:
//          tmpVector.add("F");
//          break;
//        case 3:
//          tmpVector.add("E1");
//          break;
//        case 4:
//          tmpVector.add("T1");
//          break;
//        default:
//          break;
//      }
      for (int j = 0; j < forecast[i].length; j++) {
        tmpVector.add(forecast[i][j]);
      }
      tableData.add(tmpVector);
    }
    TableModel setTableModel = new DefaultTableModel(tableData, title);
    setTable.setModel(setTableModel);
  }

  public void setErrorTextArea(List<String> errorText) {
    errorTextArea.setText("");
    for (int i = 0; i < errorText.size(); i++) {
      errorTextArea.append(errorText.get(i) + "\n");
    }
  }

  public void setParsingTree(HashMap<String, ArrayList<String>> analyzeTree) {
    String root = "E";
    Map<String, DefaultMutableTreeNode> parsingTreeNode = new HashMap<>();
    for (Entry<String, ArrayList<String>> entry : analyzeTree.entrySet()) {
      parsingTreeNode.put(entry.getKey(), new DefaultMutableTreeNode(entry.getKey()));
    }
    Queue<String> treeQueue = new LinkedList<>();
    treeQueue.offer(root);
    String currentNode;
    while (!treeQueue.isEmpty()) {
      currentNode = treeQueue.poll();
      if (analyzeTree.get(currentNode) != null) {
        for (int i = 0; i < analyzeTree.get(currentNode).size(); i++) {
          treeQueue.offer(analyzeTree.get(currentNode).get(i));
          parsingTreeNode.get(currentNode)
              .add(parsingTreeNode.get(analyzeTree.get(currentNode).get(i)));
        }
      }
    }
    TreeModel parsingTreeModel = new DefaultTreeModel(parsingTreeNode.get(root));
    parsingTree.setModel(parsingTreeModel);
  }

}


class CorePanel extends JPanel {

}
