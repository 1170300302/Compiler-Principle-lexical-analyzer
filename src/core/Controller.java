package core;

import java.util.Map;

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
    CharStream charStream = new CharStream(".\\src\\doc\\CharStream.txt");
    DFA dfa = new DFA(".\\src\\doc\\DFA.txt");
    System.out.println("charStream = " + charStream.characterStream);
    
    // һֱѭ����ֱ����⵽�ļ�����
    while(charStream.checkEnd()) {// ����������'0'��ʾ�ļ��������Ժ����ͨ��������������ʵ��
      // ��⵱ǰ״̬������ǰ�����ܷ�ת�ƣ�������Խ���ת�Ʒ�������������Խ����������
      System.out.printf("state = %d, char = %c\n", dfa.getCurrentStateID(), charStream.getCurrentCharacter());
      if(dfa.checkMove(charStream.getCurrentCharacter())){
        lexicalAnalysisTransform(charStream, dfa);// ת�Ʒ���
      }
      else {
        lexicalAnalysisErrorHandling(charStream, dfa);// ��������
      } 
    }
    charStream.tokenIdn2Keyword();
    output(charStream);
  }
  
  public static void printSeparator() {
    System.out.println("--------------------------------------------------");
  }
  
  // ת�Ʒ���
  private static void lexicalAnalysisTransform(CharStream charStream, DFA dfa) {
    // DFA���ַ�������ǰ�ƶ�
    dfa.move(charStream.getCurrentCharacter());
    charStream.move(dfa.getCurrentStateID());
    if(charStream.checkEnd())
      System.out.printf("state -> %d, char -> %c\n", dfa.getCurrentStateID(), charStream.getCurrentCharacter());
    System.out.println("characterStreamState = " + charStream.getCharacterStreamState());
  }
  
  // ��������
  private static void lexicalAnalysisErrorHandling(CharStream charStream, DFA dfa) {
    // �ô������ദ����󣬵��û��ݷ�����������ݳɹ���õ��ҵ����ַ����±꣬stateID���������ʧ��ֻ�õ�-1��-1
    int[] errorHandlingRes = ErrorHandling.backtrackingFinalState(charStream.getCharacterStreamState(), charStream.getCurrentPointer());
    // ���ݳɹ�
    if(errorHandlingRes[0] != -1) {
      // �ַ����±�ת�Ƶ���Ӧ�±�
      charStream.moveTo(errorHandlingRes[0]);
      charStream.setCharacterStreamState(errorHandlingRes[0]);
      System.out.printf("char -> %c\n", charStream.characterStream.get(errorHandlingRes[0]));
      // ���Token
      charStream.outputToken(errorHandlingRes[1]);
      // DFA�ص���̬ɨ����һ���ַ�
      dfa.moveToInitialState();
      System.out.printf("state -> %d\n", dfa.getCurrentStateID());
      charStream.moveTo(errorHandlingRes[0] + 1);
      if(charStream.checkEnd())
        System.out.printf("cahr -> %c\n", charStream.characterStream.get(errorHandlingRes[0] + 1));
    }
    // ����ʧ��
    else {
      // ���������Ϣ
      charStream.outputErrorMessage();
      charStream.move(dfa.getCurrentStateID());
    }
    // ��������ַ�����Ҫ��ǰ�ƶ�
  }
  
  private static void output(CharStream charStream) {
    System.out.println("Over");
    printSeparator();
    for(Map.Entry<String, String> entry : charStream.getToken().entrySet()) {
      switch (entry.getValue()) {
        case "IDN":
        case "CONST":
        case "NOTES":
        case "CHARCONST":
        case "OCT":
        case "HEX":
          System.out.println(entry.getKey() + "-----" + "<" + entry.getValue() + ", " + entry.getKey() + ">");
          break;
        case "KEYWORD":
        case "BOUNDARY":
        case "OPERATOR":
          System.out.println(entry.getKey() + "-----" + "<" + entry.getKey() + ", _>");
          break;
        default:
          System.out.println("Error");
          break;
      }
    }
  }

}
