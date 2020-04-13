package dfa;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DFA {

  private State initialState;// DFA初始状态
  private State currentState;// DFA当前指向的状态
//  private List<State> states = new ArrayList<>();// DFA状态集合
  private final int statesNum = 84;
  private State[] states = new State[statesNum];
  private static Map<Integer, String> finalStateAndInfoMap = new HashMap<>();

  /**
   * @dec 得到currentStateID——该方法已完成，如无需要，无需改动
   * @return currentStateID
   */
  public int getCurrentStateID() {
    return this.currentState.getStateID();
  }

  /**
   * @dec 构造函数，初始化各个字段——待完成方法，可以随意改动
   * @dec 具体初始化内容包括：initialState为初态，currentState为初态，states为所有状态的集合
   * @param filePath DFA转换表的文件路径
   */
  public DFA(String filePath) {
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
      String line = "";
      String lastStateID = "";
      while ((line = bufferedReader.readLine()) != null) {
        String[] tmpStrings = line.split(" ");
        if (tmpStrings[0].equals(lastStateID)) {
          states[Integer.parseInt(lastStateID)].setState(tmpStrings);
        } else {
          states[Integer.parseInt(tmpStrings[0])] = new State(tmpStrings);
        }
        lastStateID = tmpStrings[0];
      }
      this.initialState = states[10];
      this.currentState = initialState;
      bufferedReader.close();
      setFinalStateAndInfoMap();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void setFinalStateAndInfoMap() {
    finalStateAndInfoMap.put(20, "IDN");
    finalStateAndInfoMap.put(24, "BOUNDARY");
    finalStateAndInfoMap.put(34, "NOTES");
    finalStateAndInfoMap.put(35, "OPERATOR");
    finalStateAndInfoMap.put(36, "OPERATOR");
    finalStateAndInfoMap.put(37, "OPERATOR");
    finalStateAndInfoMap.put(38, "OPERATOR");
    finalStateAndInfoMap.put(39, "OPERATOR");
    finalStateAndInfoMap.put(40, "OPERATOR");
    finalStateAndInfoMap.put(41, "OPERATOR");
    finalStateAndInfoMap.put(42, "OPERATOR");
    finalStateAndInfoMap.put(43, "OPERATOR");
    finalStateAndInfoMap.put(44, "OPERATOR");
    finalStateAndInfoMap.put(45, "OPERATOR");
    finalStateAndInfoMap.put(46, "OPERATOR");
    finalStateAndInfoMap.put(47, "OPERATOR");
    finalStateAndInfoMap.put(48, "OPERATOR");
    finalStateAndInfoMap.put(49, "OPERATOR");
    finalStateAndInfoMap.put(50, "OPERATOR");
    finalStateAndInfoMap.put(51, "OPERATOR");
    finalStateAndInfoMap.put(52, "OPERATOR");
    finalStateAndInfoMap.put(53, "OPERATOR");
    finalStateAndInfoMap.put(54, "OPERATOR");
    finalStateAndInfoMap.put(55, "OPERATOR");
    finalStateAndInfoMap.put(56, "OPERATOR");
    finalStateAndInfoMap.put(57, "OPERATOR");
    finalStateAndInfoMap.put(62, "CHARCONST");
    finalStateAndInfoMap.put(77, "CONST");
    finalStateAndInfoMap.put(78, "CONST");
    finalStateAndInfoMap.put(79, "CONST");
    finalStateAndInfoMap.put(80, "CONST");
    finalStateAndInfoMap.put(81, "OCT");
    finalStateAndInfoMap.put(83, "HEX");
  }

  /**
   * @dec 检查是否可以移动，并返回bool结果——该方法已完成，如无需要，无需改动
   * @param currentCharacter 输入字符
   * @return 是否可以移动
   */
  public boolean checkMove(char currentCharacter) {
    return this.currentState.checkMove(currentCharacter);
  }

  /**
   * @dec 移动——该方法已完成，如无需要，无需改动
   * @param currentCharacter 输入字符
   */
  public void move(char currentCharacter) {
    this.currentState = states[currentState.move(currentCharacter)];
  }

  /**
   * @dec 回到初态——该方法已完成，如无需要，无需改动
   */
  public void moveToInitialState() {
    this.currentState = this.initialState;
  }

  public static boolean checkFinalState(int stateID) {
    return finalStateAndInfoMap.containsKey(stateID);
  }

  public static String finalStateOutput(int stateID) {
    return finalStateAndInfoMap.get(stateID);
  }

}
