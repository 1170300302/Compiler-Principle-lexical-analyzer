package paser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

import charStream.CharStream;
import core.Controller;

public class AnalyzeLL {

  protected ArrayList<Character> charStreams; /* 存储输入字符流 */
  protected ArrayList<Integer> lineOrder; /* 记录每个输入字符的行号 */
  protected int pointer; /* 输入字符流的指针 */
  protected static HashMap<String, ArrayList<ArrayList<String>>> grammer = new HashMap<>();
  protected static HashSet<String> vtSet = new HashSet<>(); /* 终结符的集合 */
  protected static HashSet<String> vnSet = new HashSet<>(); /* 非终结符的集合 */
  protected static String start = "Start"; /* 文法开始符号 */
  protected static String[][] table; /* 预测分析表 */
  protected Stack<String> analyzeStack = new Stack<>();/* 分析栈 */
  protected String action = "";
  protected static HashMap<String, ArrayList<String>> analyzeTree = new HashMap<>();/* 分析树所需 */

  public void getGrammerFile(String file) {
    try {
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);
      String line = br.readLine();
      while (line != null) {
        String[] str = line.split("->");
        String left = str[0];
        ArrayList<ArrayList<String>> right =
            grammer.containsKey(left) ? grammer.get(left) : new ArrayList<>();
        ArrayList<String> list = new ArrayList<>();
        for (String s : str[1].split(" ")) {
          list.add(s);
        }
        right.add(list);
        grammer.put(left, right);
        line = br.readLine();
      }
      br.close();
    } catch (IOException e) {
      System.out.println("没有找到文件");
      e.getStackTrace();
    }
  }

  public void Init(FirstAndFollow f) {
    // 求非终结符的first集
    for (String s1 : vnSet)
      f.setFirstSet(s1);

    for (String s2 : vtSet) {
      if (!f.contains(s2)) {
        f.setFirstSet(s2);
      }
    }

    for (String s3 : vnSet) {
      ArrayList<ArrayList<String>> l = grammer.get(s3);
      for (int i = 0; i < l.size(); i++) {
        f.setFirstSet(l.get(i));
      }
    }
    f.setFollowSet(start);
    for (String s4 : vnSet) {
      f.setFollowSet(s4);
    }
  }

  @SuppressWarnings("static-method")
  private void getVnVt() {
    for (String Vn : grammer.keySet()) {
      vnSet.add(Vn);
    }
    for (String Vn : grammer.keySet()) {
      int len = grammer.get(Vn).size();
      for (int i = 0; i < len; i++) {
        for (String Vt : grammer.get(Vn).get(i)) {
          if (!vnSet.contains(Vt)) {
            vtSet.add(Vt);
          }
        }
      }
    }
  }

  public void createTable(FirstAndFollow f) {
    Object[] vtArray = vtSet.toArray();
    Object[] vnArray = vnSet.toArray();
    table = new String[vnArray.length + 1][vtArray.length + 1];
    table[0][0] = "Vn/Vt";
    for (int i = 0; i < vtArray.length; i++) {
      table[0][i + 1] = ((vtArray[i] + "").equals("epsilon")) ? "$" : vtArray[i] + "";
    }
    for (int i = 0; i < vnArray.length; i++) {
      table[i + 1][0] = vnArray[i] + "";
    }
    for (int i = 0; i < vnArray.length; i++) {
      for (int j = 0; j < vtArray.length; j++) {
        table[i + 1][j + 1] = "error";
      }
    }
    for (String A : vnSet) {
      ArrayList<ArrayList<String>> production = grammer.get(A);
      for (int i = 0; i < production.size(); i++) {
        HashSet<String> set = f.firstSetX.get(production.get(i));
        for (String str : set) {
          insert(A, str, production.get(i).toString());
        }
        if (set.contains("epsilon")) {
          HashSet<String> setFollow = f.followSet.get(A);
          if (setFollow.contains("epsilon")) {
            insert(A, "epsilon", production.get(i).toString());
          }
          for (String str : setFollow) {
            insert(A, str, production.get(i).toString());
          }
        }
      }
    }
    for (int i = 0; i < vnArray.length; i++) {
      for (int j = 0; j < vtArray.length; j++) {
        if (f.followSet.get(table[i + 1][0]).contains(table[0][j + 1])
            && (!table[i + 1][j + 1].equals("epsilon")))
          table[i + 1][j + 1] = "synch";
      }
    }

  }

  public void insert(String X, String a, String s) {
    String S = s.substring(1, s.length() - 1).replaceAll(",", " ");
    if (a.equals("epsilon")) {
      a = "$";
    }
    for (int i = 0; i < vnSet.size() + 1; i++) {
      if (table[i][0].equals(X)) {
        for (int j = 0; j < vtSet.size() + 1; j++) {
          if (table[0][j].equals(a)) {
            table[i][j] = S;
            return;
          }
        }
      }
    }
  }

  public String find(String X, String a) {
    for (int i = 0; i < vnSet.size() + 1; i++) {
      if (table[i][0].equals(X)) {
        for (int j = 0; j < vtSet.size() + 1; j++) {
          if (table[0][j].equals(a)) {
            return table[i][j];
          }
        }
      }
    }
    return "";
  }

  public void analyzeLL(List<String[]> token) {
    analyzeStack.push("$");
    analyzeStack.push(start);
    pointer = 0;
    String X = analyzeStack.peek();
    while (!X.equals("$")) {
      String c = token.get(pointer)[1];
      String father = X;
      ArrayList<String> son;
      if (X.equals(c)) { //
        son = null;
        analyzeStack.pop();
        pointer++;
        analyzeTree.put(father, son);
      } else if (find(X, c).equals("error")) {
        pointer++;
      } else if (find(X, c).equals("synch")) {
        analyzeStack.pop();
      } else if (find(X, c).equals("epsilon")) {
        analyzeStack.pop();
        son = new ArrayList<String>();
        son.add("epsilon");
        analyzeTree.put(father, son);
      } else {
        String str = find(X, c);
        if (str != "") {
          analyzeStack.pop();
          String[] strings = str.split("\\s+");
          int len = strings.length;
          son = new ArrayList<>();
          for (int i = len - 1; i >= 0; i--) {
            analyzeStack.push(strings[i]);
            son.add(strings[i]);
          }
          analyzeTree.put(father, son);
        } else {
          System.out.println("error at" + token.get(pointer)[0] + "in" + pointer);
          return;
        }
      }
      X = analyzeStack.peek();
    }
  }

  public static void syntaxAnalysis() {
    AnalyzeLL ll = new AnalyzeLL();
    ll.getGrammerFile("src/grammer/grammer2.txt");
    ll.getVnVt();
    FirstAndFollow f = new FirstAndFollow();
    ll.Init(f);
    ll.createTable(f);
    ll.analyzeLL(CharStream.getToken());
    Controller.getCoreFrame().setSet(f.firstSet, f.followSet, table);
    Controller.getCoreFrame().setParsingTree(analyzeTree);
  }

  public static void main(String[] argc) {
    syntaxAnalysis();
  }

}
