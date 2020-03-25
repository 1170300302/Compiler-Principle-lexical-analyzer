package dfa;

import java.util.ArrayList;
import java.util.List;

public class DFA {
  
  private State initialState;// DFA初始状态
  private State currentState;// DFA当前指向的状态
  private List<State> states = new ArrayList<>();// DFA状态集合
  
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
    this.currentState = states.get(currentState.move(currentCharacter));// 改变currentState的值，currentState.move返回一个int
  }
  
  /**
   * @dec 回到初态——该方法已完成，如无需要，无需改动
   */
  public void moveToInitialState() {
    this.currentState = this.initialState;
  }

}
