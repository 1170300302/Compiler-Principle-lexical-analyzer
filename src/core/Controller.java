package core;

import charStream.CharStream;
import dfa.DFA;
import errorHandling.ErrorHandling;

public class Controller {

  public static void main(String[] args) {
    // ����ʵ������ط���
    lexicalAnalysis();
  }
  
  // �ʷ��������Ʒ���
  private static void lexicalAnalysis() {
    // ʵ�����ַ�����DFA
    CharStream charStream = new CharStream("�����ļ�·��");
    DFA dfa = new DFA("ת�����ļ�·��");
    
    // һֱѭ����ֱ����⵽�ļ�����
    while(charStream.getCurrentCharacter() == '0') {// ����������'0'��ʾ�ļ��������Ժ����ͨ��������������ʵ��
      // ��⵱ǰ״̬������ǰ�����ܷ�ת�ƣ�������Խ���ת�Ʒ�������������Խ����������
      if(dfa.checkMove(charStream.getCurrentCharacter())){
        lexicalAnalysisTransform(charStream, dfa);// ת�Ʒ���
      }
      else {
        lexicalAnalysisErrorHandling(charStream, dfa);// ��������
      } 
    }
  }
  
  // ת�Ʒ���
  private static void lexicalAnalysisTransform(CharStream charStream, DFA dfa) {
    // DFA���ַ�������ǰ�ƶ�
    char tmpChar = charStream.getCurrentCharacter();
    int tmpStateID = dfa.getCurrentStateID();
    dfa.move(tmpChar);
    charStream.move(tmpStateID);
  }
  
  // ��������
  private static void lexicalAnalysisErrorHandling(CharStream charStream, DFA dfa) {
    // �ô������ദ����󣬵��û��ݷ�����������ݳɹ���õ��ҵ����ַ����±꣬stateID���������ʧ��ֻ�õ�-1��-1
    int[] errorHandlingRes = ErrorHandling.backtrackingFinalState(charStream.getCharacterStreamState(), charStream.getCurrentPointer());
    // ���ݳɹ�
    int tmpStateID = dfa.getCurrentStateID();
    if(errorHandlingRes[0] != -1) {
      // �ַ����±�ת�Ƶ���Ӧ�±�
      charStream.moveTo(errorHandlingRes[0]);
      // ���Token
      charStream.outputToken(errorHandlingRes[1]);
      // DFA�ص���̬ɨ����һ���ַ�
      dfa.moveToInitialState();
    }
    // ����ʧ��
    else {
      // ���������Ϣ
      charStream.outputErrorMessage();
    }
    // ��������ַ�����Ҫ��ǰ�ƶ�
    charStream.move(tmpStateID);
  }

}
