package paser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class FirstAndFollow {
  protected HashMap<String, HashSet<String>> firstSet;
  protected HashMapX firstSetX;
  protected HashMap<String, HashSet<String>> followSet;/* Follow集 */

  public FirstAndFollow() {
    firstSet = new HashMap<>();
    firstSetX = new HashMapX();
    followSet = new HashMap<>();
  }

  public void setFirstSet(String s) {
    ArrayList<ArrayList<String>> production = AnalyzeLL.grammer.get(s);
    HashSet<String> vtSet = AnalyzeLL.vtSet;
    HashSet<String> set = firstSet.containsKey(s) ? firstSet.get(s) : new HashSet<>();
    if (vtSet.contains(s)) {
      set.add(s);
      firstSet.put(s, set);
      return;
    }
    int num = production.size();
    for (int i = 0; i < num; i++) {
      int len = production.get(i).size();
      for (int j = 0; j < len; j++) {
        String Vn = production.get(i).get(j);
        if (Vn == "epsilon") {
          set.add(Vn);
        } else {
          setFirstSet(Vn);
          HashSet<String> temp = firstSet.get(Vn);
          for (String key : temp) {
            set.add(key);
          }
          if (!temp.contains("epsilon")) {
            break;
          }
        }
      }
    }
    firstSet.put(s, set);
  }

  public void setFirstSet(ArrayList<String> X) {
    HashSet<String> set = firstSetX.containsKey(X) != -1 ? firstSetX.get(X) : new HashSet<>();
    for (int i = 0; i < X.size(); i++) {
      String Vn = X.get(i);
      HashSet<String> temp = firstSet.get(Vn);
      for (String key : temp) {
        if (key != "epsilon") {
          set.add(key);
        }
        if (key == "epsilon" && i == X.size() - 1) {
          set.add(key);
        }
      }
      if (!temp.contains("epsilon")) {
        break;
      }
    }
//		System.out.println(X);
//		System.out.println(set);
    firstSetX.put(X, set);
  }

  public void setFollowSet(String s) {
//		System.out.println(AnalyzeLL.grammer.toString());
//		System.out.println(s);
    ArrayList<ArrayList<String>> production = AnalyzeLL.grammer.get(s);
    HashSet<String> setA = followSet.containsKey(s) ? followSet.get(s) : new HashSet<>();
    if (s == AnalyzeLL.start) {
//			System.out.println("xxx");
      setA.add("$");
    }
    for (String Vn : AnalyzeLL.vnSet) {
      ArrayList<ArrayList<String>> production1 = AnalyzeLL.grammer.get(Vn);
      for (int i = 0; i < production1.size(); i++) {
        for (int j = 0; j < production1.get(i).size(); j++) {
          if (production1.get(i).get(j).equals(s) && j + 1 < production1.get(i).size()
              && AnalyzeLL.vtSet.contains(production1.get(i).get(j + 1))) {
            setA.add(production1.get(i).get(j + 1));
          }
        }
      }
    }
    followSet.put(s, setA);
//		System.out.println(production.size());
    for (int i = 0; i < production.size(); i++) {
      int j = production.get(i).size() - 1;
      while (j >= 0) {
        String tmp = production.get(i).get(j);
        if (AnalyzeLL.vnSet.contains(tmp)) {
          if (production.get(i).size() - j - 1 > 0) {// 标识不是右部最后一个
            ArrayList<String> right = new ArrayList<>();
            for (int k = j + 1; k < production.get(i).size(); k++) {
              right.add(production.get(i).get(k));
            }
            HashSet<String> setF = null;
            if (right.size() == 1 && firstSet.containsKey(right.get(0))) {
              setF = firstSet.get(right.get(0));
//							System.out.println("yyy");
            } else {
//							System.out.println("xxx");
//							System.out.println(right);
              if (firstSetX.containsKey(right) == -1) {
//								System.out.println("zzz");
                setFirstSet(right);
              }
//							System.out.println(firstSetX.toString());
//							System.out.println(firstSetX.containsKey(right));
              setF = firstSetX.get(right);
//							System.out.println(setF);
            }
            HashSet<String> setX =
                followSet.containsKey(tmp) ? followSet.get(tmp) : new HashSet<>();
//						System.out.println(setF);
            for (String str : setF) {
              if (!str.equals("epsilon")) {
                setX.add(str);
              }
            }
            followSet.put(tmp, setX);
            if (setF.contains("epsilon")) {
              if (!tmp.equals(s)) {
                HashSet<String> setB =
                    followSet.containsKey(tmp) ? followSet.get(tmp) : new HashSet<>();
                for (String str : setA) {
                  setB.add(str);
                }
                followSet.put(tmp, setB);
              }
            }
          } else {
            if (!tmp.equals(s)) {
              HashSet<String> setB =
                  followSet.containsKey(tmp) ? followSet.get(tmp) : new HashSet<>();
              for (String str : setA) {
                setB.add(str);
              }
              followSet.put(tmp, setB);
            }
          }
          j--;
        } else
          j--;
      }

    }
  }

  public boolean contains(String s) {
    return firstSet.containsKey(s);
  }

  public HashSet<String> getFirstSet(String s) {
    return firstSet.get(s);
  }

  public HashSet<String> getFirstSet(ArrayList<String> X) {
    return firstSetX.get(X);
  }

  public HashSet<String> getFollowSet(String s) {
    return followSet.get(s);
  }

  public String toString() {
    return "FirstSet:" + firstSet.toString() + '\n' + "FirstSetX:" + firstSetX.toString() + '\n'
        + "FollowSet:" + followSet.toString();
  }
}


class HashMapX {
  ArrayList<ArrayList<String>> keys;
  ArrayList<HashSet<String>> values;
  int size;

  public HashMapX() {
    keys = new ArrayList<>();
    values = new ArrayList<>();
    size = 0;
  }

  public void put(ArrayList<String> key, HashSet<String> value) {
    int index = containsKey(key);
    if (index != -1) {
      values.get(index).clear();
      for (String s : value) {
        values.get(index).add(s);
      }
    } else {
      keys.add(key);
      values.add(value);
      size++;
    }
  }

  public int containsKey(ArrayList<String> key) {
    int index = -1;
    ArrayList<String> tmp;
    if (size != 0) {
      for (int i = 0; i < size; i++) {
        tmp = keys.get(i);
        if (tmp.size() == key.size()) {
          int j = 0;
          while (j < tmp.size()) {
            if (!tmp.get(j).equals(key.get(j))) {
              break;
            }
            j++;
          }
          if (j == tmp.size()) {
            index = i;
            break;
          }
        }
      }
    }
    return index;
  }

  public HashSet<String> get(ArrayList<String> key) {
    HashSet<String> result = new HashSet<>();
    int index = containsKey(key);
    if (index == -1) {
      return null;
    } else {
      for (String s : values.get(index)) {
        result.add(s);
      }
    }
    return result;
  }

  public String toString() {
    String result = "";
    for (int i = 0; i < size; i++) {
      result = result + "{" + keys.get(i).toString() + "," + values.get(i).toString() + "}";
    }
    return result;
  }
}
