package paser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;
import core.Controller;

public class AnalyzeLL {
  
	protected  ArrayList<Character> charStreams; /*存储输入字符流*/
	protected ArrayList<Integer> lineOrder;     /*记录每个输入字符的行号*/
	protected int pointer; /*输入字符流的指针*/
	protected HashMap<String,ArrayList<ArrayList<String>>> grammer = new HashMap<>();
	protected HashSet<String> vtSet = new HashSet<>(); /*终结符的集合*/
	protected HashSet<String> vnSet = new HashSet<>(); /*非终结符的集合*/
	protected HashMap<String, HashSet<String>> firstSet = new HashMap<>(); /*一个符号的First集*/
	protected HashMap<String, HashSet<String>> firstSetX = new HashMap<>();/*一个符号串的First集*/
	protected HashMap<String, HashSet<String>> followSet = new HashMap<>();/*Follow集*/
	protected String[][] table; /*预测分析表*/
	protected String start = "E"; /*文法开始符号*/
	protected Stack<String> analyzeStack = new Stack<>();/*分析栈*/
	protected String action = "";
	protected HashMap<String, ArrayList<String>> analyzeTree = new HashMap<>();/*分析树所需*/
	
	
	private void getGrammerFile(String file) {
		try {
		    FileReader fr = new FileReader(file);
		    BufferedReader br = new BufferedReader(fr);
		    String line = br.readLine();
		    while(line != null) {
		    	String[] str = line.split("->");
		      	String left = str[0];
		    	ArrayList<ArrayList<String>> right = grammer.containsKey(left) ? grammer.get(left) : new ArrayList<>();
		    	ArrayList<String> list = new ArrayList<>();
		    	for(String s : str[1].split(" ")) {
		    		list.add(s);
		    	}
		    	right.add(list);
		    	grammer.put(left, right);	
		    	line = br.readLine();
		    }
		    br.close();
		}catch(IOException e) {
			System.out.println("没有找到文件"); 
			e.getStackTrace();
		}
	}
	
	public void Init() {
		getGrammerFile("src/paser/grammer1.txt");
		getVnVt();
        //求非终结符的first集
		for (String s1 : vnSet)
            getFirst(s1);
        
		for(String s2 : vtSet) {
			if(!firstSet.containsKey(s2)) {
				HashSet<String> list = new HashSet<>();
				list.add(s2);
				firstSet.put(s2, list);
			}
		}
		
        for (String s3 : vnSet) {
            ArrayList<ArrayList<String>> l = grammer.get(s3);
            for(int i = 0; i < l.size(); i++) {
                getFirstX(l.get(i));
            }    
        }
        System.out.println(firstSet);
        System.out.println(firstSetX);
        //求出follow集
        getFollow(start);
        for (String s4 : vnSet) {
            getFollow(s4);
        }
        System.out.println(followSet);
    }
	
	private void getVnVt() {
		for(String Vn : grammer.keySet()) {
			vnSet.add(Vn);
		}
		for(String Vn : grammer.keySet()) {
			int len = grammer.get(Vn).size();
			for(int i = 0; i < len; i++) {
				for(String Vt : grammer.get(Vn).get(i)) {
					if(!vnSet.contains(Vt)) {
						vtSet.add(Vt);
					}
				}
			}	
		}
	}
	
	private void getFirst(String s) {
		ArrayList<ArrayList<String>> production = grammer.get(s);
		HashSet<String> set = firstSet.containsKey(s)?firstSet.get(s):new HashSet<>();
		//System.out.println(vtSet.contains(s));
		//System.out.println(s);
		if(vtSet.contains(s)) {
			set.add(s);
			firstSet.put(s, set);
			return;
		}
		int num = production.size();
		for(int i = 0; i < num; i++) {
			int len = production.get(i).size();
			for(int j = 0; j < len; j++) {
				String Vn = production.get(i).get(j);
//				System.out.println(Vn);
				if(Vn == "&") {
					set.add(Vn);
				}
				else {
					getFirst(Vn);
					HashSet<String> temp = firstSet.get(Vn);
					for(String key : temp) {
						set.add(key);
					}
				    if(!temp.contains("&")) {
				    	break;
				    }
				}
			}
		}
		firstSet.put(s, set);
	}
	
	private void getFirstX(ArrayList<String> X) {
		String strX = X.toString();
		HashSet<String> set = firstSetX.containsKey(strX)?firstSetX.get(strX):new HashSet<>();
		for(int i = 0; i < X.size(); i++) {
			String Vn = X.get(i);
			HashSet<String> temp = firstSet.get(Vn);
			for(String key : temp) {
				if(key != "&") {
					set.add(key);
				}
				if(key == "&" && i == X.size()-1) {
					set.add(key);
				}
			}
			if(!temp.contains("&")) {
				break;
			}
		}
		firstSetX.put(strX, set);
	}
	
	private void getFollow(String s) {
		ArrayList<ArrayList<String>> production = grammer.get(s);
		HashSet<String> setA = followSet.containsKey(s)?followSet.get(s):new HashSet<>();
		if(s == start) {
			setA.add("$");
		}
		for(String Vn : vnSet) {
			ArrayList<ArrayList<String>> production1 = grammer.get(Vn);
			for(int i = 0; i < production1.size(); i++) {
				for(int j = 0; j < production1.get(i).size(); j++) {
					if(production1.get(i).get(j).equals(s) && j+1 < production1.get(i).size() && vtSet.contains(production1.get(i).get(j+1))) {
						setA.add(production1.get(i).get(j+1));
					}
				}
			}	
		}
		followSet.put(s, setA);
		for(int i = 0; i < production.size(); i++) {
			int j = production.get(i).size() - 1;
			while(j >= 0) {
				String tmp = production.get(i).get(j);
				//System.out.println(tmp + vnSet.contains(tmp));
				if(vnSet.contains(tmp)) {
					//System.out.println("ppp");
					if(production.get(i).size() - j - 1 > 0) {//标识不是右部第一个
						//System.out.println("xxx");
						ArrayList<String> right = new ArrayList<>();
						for(int k = j + 1; k < production.get(i).size(); k++) {
							right.add(production.get(i).get(k));
						}
						HashSet<String> setF = null;
//						System.out.println(right.get(0));
//						System.out.println(firstSet.containsKey(right.get(0)));
						if(right.size() == 1 && firstSet.containsKey(right.get(0))) {
							setF = firstSet.get(right.get(0));
						}
						else {
							if(!firstSet.containsKey(right.toString())) {
								HashSet<String> set = new HashSet<>();
								firstSet.put(right.toString(), set);
							}
							setF = firstSetX.get(right.toString());
						}
						HashSet<String> setX = followSet.containsKey(tmp)?followSet.get(tmp):new HashSet<>();
//						System.out.println(setF);
						for(String str : setF) {
//							System.out.println(str);
							if(!str.equals("&")) {
								setX.add(str);
							}
						}
						followSet.put(tmp, setX);
						if(setF.contains("&")) {
							if(!tmp.equals(s)) {
								HashSet<String> setB = followSet.containsKey(tmp)?followSet.get(tmp):new HashSet<>();
								for(String str : setA) {
									setB.add(str);
								}
								followSet.put(tmp, setB);
							}
						}
					}
					else {
						if(!tmp.equals(s)) {
							HashSet<String> setB = followSet.containsKey(tmp)?followSet.get(tmp):new HashSet<>();
							for(String str : setA) {
								setB.add(str);
							}
							followSet.put(tmp, setB);
						}
					}
					j--;
				}
				else j--;
			}
			
		}
	}
	
	public void createTable() {
		Object[] vtArray = vtSet.toArray();
		Object[] vnArray = vnSet.toArray();
		table = new String[vnArray.length+1][vtArray.length+1];
		table[0][0] = "Vn/Vt";
		for(int i = 0; i < vtArray.length; i++) {
			table[0][i+1] = ((vtArray[i]+"").equals("&"))?"$":vtArray[i]+"";
		}
		for(int i = 0; i < vnArray.length; i++) {
			table[i+1][0] = vnArray[i] + "";
		}
		for(int i = 0; i < vnArray.length; i++) {
			for(int j = 0; j < vtArray.length; j++) {
				table[i+1][j+1] = "error";
			}
		}
		for(String A : vnSet) {
			ArrayList<ArrayList<String>> production = grammer.get(A);
			for(int i = 0; i < production.size(); i++) {
				HashSet<String> set = firstSetX.get(production.get(i).toString());
				for(String str : set) {
					insert(A, str, production.get(i).toString());
				}
				if(set.contains("&")) {
					HashSet<String> setFollow = followSet.get(A);
					if(setFollow.contains("$")) {
						insert(A, "$", production.get(i).toString());
					}
					for(String str : setFollow) {
						insert(A, str, production.get(i).toString());
					}
				}
			} 
		}
		for(int i = 0; i < vnArray.length; i++) {
			for(int j = 0; j < vtArray.length; j++) {
				if(followSet.get(table[i+1][0]).contains(table[0][j+1]) && (!table[i+1][j+1].equals("&")))
				table[i+1][j+1] = "synch";
			}
		}
		
	}
	
	public void insert(String X, String a, String s) {
		String S = s.substring(1, s.length()-1).replaceAll(",", " ");
		if(a.equals("&")) {
			a = "$";
		}
		for(int i = 0; i < vnSet.size()+1; i++) {
			if(table[i][0].equals(X)) {
				for(int j = 0; j < vtSet.size()+1; j++) {
					if(table[0][j].equals(a)) {
						table[i][j] = S;
						return;
					}
				}
			}
		}
	}
	
	public String find(String  X, String a) {
      for(int i = 0; i < vnSet.size() + 1; i++) {
          if(table[i][0].equals(X)) {
              for(int j = 0; j < vtSet.size() + 1; j++) {
                  if(table[0][j].equals(a)) {
                      return table[i][j];
                  }
              }
          }
      }
      return "";
  }
	
	public void analyzeLL() {
      System.out.println("---------------LL1分析过程----------------");
      analyzeStack.push("$");
      analyzeStack.push(start);
      String[] s = {"+","id","*","+","id","$"};
      pointer = 0;
      String X = analyzeStack.peek();
      while(!X.equals("$")) {
//        String c = CharStream.token.get(pointer)[1]; //读取一个一个的输入
          String c = s[pointer];
          System.out.println(X);
          System.out.println(c);
          String father = X;
          ArrayList<String> son;
          if(X.equals(c)) { //
              action = "match" + analyzeStack.peek();
              son = null;
              analyzeStack.pop();
              pointer++;
              analyzeTree.put(father, son);
          }
          else if(find(X, c).equals("error")) {
              pointer++;
//            System.out.println("xxx");
          }
          else if(find(X, c).equals("synch")) {
              analyzeStack.pop();
//            System.out.println("ppp");
          }
          else if(find(X, c).equals("&")){
//            System.out.println("lll");
              analyzeStack.pop();
              action = X + "->&";
              son = new ArrayList<String>();
              son.add("&");
              analyzeTree.put(father, son);
          }
          else {
              String str = find(X, c);
              if (str != "") {
//                System.out.println("kkk");
                  action = X + "->" + str;
                  analyzeStack.pop();
                  String[] strings = str.split("\\s+");
                  int len = strings.length;
                  son = new ArrayList<>();
                  for(int i = len - 1; i >= 0; i--) {
                      analyzeStack.push(strings[i]);
                      son.add(strings[i]);
                  }
                  analyzeTree.put(father, son);
              }
              else {
                  System.out.println("非法字符:error at"+ s[pointer]+ "in" + pointer);//这里改成行号
                  return;
              }
          }
          X = analyzeStack.peek();
      } 
      System.out.println("分析完成!");
      Controller.getCoreFrame().setSet(firstSet, followSet, table);
      Controller.getCoreFrame().setParsingTree(analyzeTree);
  }
	
	public static void syntaxAnalysis() {
	  AnalyzeLL ll = new AnalyzeLL();
      ll.Init();
      ll.createTable();
      ll.analyzeLL();
	}
	
	
	
	
	public static void main(String[] argc){
		AnalyzeLL ll = new AnalyzeLL();
		ll.Init();
		ll.createTable();
		System.out.println(ll.vnSet);
		System.out.println(ll.vtSet);
		for(int i = 0;i <= ll.vnSet.size();i++) {
			for(int j = 0;j <= ll.vtSet.size();j++) {
				System.out.print(ll.table[i][j]+" ");
			}
			System.out.println();
		}
		ll.analyzeLL();
	}

}
