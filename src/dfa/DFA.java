package dfa;

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
