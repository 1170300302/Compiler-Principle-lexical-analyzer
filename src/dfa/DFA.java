package dfa;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DFA {
  
  private State initialState;// DFA��ʼ״̬
  private State currentState;// DFA��ǰָ���״̬
  private List<State> states = new ArrayList<>();// DFA״̬����
  private static Map<Integer, String> finalStateAndInfoMap = new HashMap<>();
  
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
      setFinalStateAndInfoMap();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private static void setFinalStateAndInfoMap() {
    finalStateAndInfoMap.put(21,"��ʶ��");
    finalStateAndInfoMap.put(34,"ע��");
    finalStateAndInfoMap.put(35,"�����");
    finalStateAndInfoMap.put(36,"�����");
    finalStateAndInfoMap.put(37,"�����");
    finalStateAndInfoMap.put(38,"�����");
    finalStateAndInfoMap.put(39,"�����");
    finalStateAndInfoMap.put(40,"�����");
    finalStateAndInfoMap.put(41,"�����");
    finalStateAndInfoMap.put(42,"�����");
    finalStateAndInfoMap.put(43,"�����");
    finalStateAndInfoMap.put(44,"�����");
    finalStateAndInfoMap.put(45,"�����");
    finalStateAndInfoMap.put(46,"�����");
    finalStateAndInfoMap.put(47,"�����");
    finalStateAndInfoMap.put(48,"�����");
    finalStateAndInfoMap.put(49,"�����");
    finalStateAndInfoMap.put(50,"�����");
    finalStateAndInfoMap.put(51,"�����");
    finalStateAndInfoMap.put(52,"�����");
    finalStateAndInfoMap.put(53,"�����");
    finalStateAndInfoMap.put(54,"�����");
    finalStateAndInfoMap.put(55,"�����");
    finalStateAndInfoMap.put(56,"�����");
    finalStateAndInfoMap.put(24,"���");
    finalStateAndInfoMap.put(81,"�˽��Ƴ���");
    finalStateAndInfoMap.put(83,"ʮ�����Ƴ���");
    finalStateAndInfoMap.put(77,"����");
    finalStateAndInfoMap.put(78,"����");
    finalStateAndInfoMap.put(79,"����");
    finalStateAndInfoMap.put(80,"����");
    finalStateAndInfoMap.put(62,"�ַ�����");

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
  
  public static boolean checkFinalState(int stateID) {
    return finalStateAndInfoMap.containsKey(stateID);
  }
  
  public static String finalStateOutput(int stateID) {
    return finalStateAndInfoMap.get(stateID);
  }

}
