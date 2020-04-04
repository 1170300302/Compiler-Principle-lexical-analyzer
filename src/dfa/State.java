package dfa;

import java.util.HashMap;
import java.util.Map;

public class State {
  
  private int stateID;// 索引transformMap
  private Map<Character, Integer> transformMap = new HashMap<>();// 状态转换表
//  private static Map<Integer, String> finalStateOutputMap = new HashMap<>();// 终态对应的输出信息，即Token对应的输出信息

  /**
   * @dec 得到StateID——该方法已完成，如无需要，无需改动
   * @return stateID
   */
  public int getStateID() {
    return this.stateID;
  }
  
  /**
   * @dec 构造函数，初始化isEndState，stateID，transformMap——待完成方法，可以随意改动
   */
  public State(String[] strings) {
    this.stateID = Integer.parseInt(strings[0]);
    String[] transformCharacters = strings[2].split("");
    for(int i = 0; i < transformCharacters.length; i++) {
      this.transformMap.put(transformCharacters[i].charAt(0), Integer.parseInt(strings[1]));
    }
  }
  
  public void setState(String[] strings) {
    String[] transformCharacters = strings[2].split("");
    for(int i = 0; i < transformCharacters.length; i++) {
      this.transformMap.put(transformCharacters[i].charAt(0), Integer.parseInt(strings[1]));
    }
  }
  
  /**
   * @dec 检查是否可以移动，并返回bool结果——该方法已完成，如无需要，无需改动
   * @param currentCharacter 输入字符
   * @return 是否可以移动
   */
  public boolean checkMove(char currentCharacter) {
    try {
      if(this.transformMap.containsKey('@')) {
        return true;
      }
      this.transformMap.get(currentCharacter);
      return true;
    } catch (NullPointerException e) {
      return false;
    }
  }
  
  /**
   * @dec 移动，并返回移动后的stateID——该方法已完成，如无需要，无需改动
   * @param currentCharacter 输入字符
   * @return 移动后的stateID
   */
  public int move(char currentCharacter) {
    if(this.transformMap.containsKey('@')) {
      if(currentCharacter != '*') {
        return this.transformMap.get('@');
      }
      else {
        return this.transformMap.get('*');
      }
    }
    return this.transformMap.get(currentCharacter);
  }
  
  /**
   * @dec 检查是否是终态，并返回bool结果——该方法已完成，如无需要，无需改动
   * @param stateID 待检查的stateID
   * @return 是否是终态
   */
//  public static boolean checkFinalState(int stateID) {
//    return finalStateOutputMap.containsKey(stateID);
//  }
  
  /**
   * @dec 返回终态对应的输出信息，即Token对应的输出信息——该方法已完成，如无需要，无需改动
   * @param stateID 对应终态
   * @return 输出信息
   */
//  public static String finalStateOutput(int stateID) {
//    return finalStateOutput(stateID);
//  }
  
}
