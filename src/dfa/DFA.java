package dfa;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
      String line = "";
      String lastStateID = "";
      while((line = bufferedReader.readLine()) != null) {
        String[] tmpStrings = line.split(" ");
        if(tmpStrings[0].equals(lastStateID)) {
          states.get(states.size() - 1).setState(tmpStrings);
        }
        else {
          this.states.add(new State(tmpStrings));
          if(tmpStrings[0].equals("10")) {
            this.initialState = states.get(states.size() - 1);
            this.currentState = initialState;
          }
        }
        lastStateID = tmpStrings[0];
      }
      bufferedReader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
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
