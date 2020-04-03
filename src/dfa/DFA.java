package dfa;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DFA {
  
  private State initialState;// DFA��ʼ״̬
  private State currentState;// DFA��ǰָ���״̬
  private List<State> states = new ArrayList<>();// DFA״̬����
  
  /**
   * @dec �õ�currentStateID�����÷�������ɣ�������Ҫ������Ķ�
   * @return currentStateID
   */
  public int getCurrentStateID() {
    return this.currentState.getStateID();
  }
  
  /**
   * @dec ���캯������ʼ�������ֶΡ�������ɷ�������������Ķ�
   * @dec �����ʼ�����ݰ�����initialStateΪ��̬��currentStateΪ��̬��statesΪ����״̬�ļ���
   * @param filePath DFAת������ļ�·��
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
   * @dec ����Ƿ�����ƶ���������bool��������÷�������ɣ�������Ҫ������Ķ�
   * @param currentCharacter �����ַ�
   * @return �Ƿ�����ƶ�
   */
  public boolean checkMove(char currentCharacter) {
    return this.currentState.checkMove(currentCharacter);
  }
  
  /**
   * @dec �ƶ������÷�������ɣ�������Ҫ������Ķ�
   * @param currentCharacter �����ַ�
   */
  public void move(char currentCharacter) {
    this.currentState = states.get(currentState.move(currentCharacter));// �ı�currentState��ֵ��currentState.move����һ��int
  }
  
  /**
   * @dec �ص���̬�����÷�������ɣ�������Ҫ������Ķ�
   */
  public void moveToInitialState() {
    this.currentState = this.initialState;
  }

}
