package core;

import java.util.Map;

import charStream.CharStream;
import dfa.DFA;
import errorHandling.ErrorHandling;

public class Controller {

  public static void main(String[] args) {
    // 本次实验的主控方法
    lexicalAnalysis();
  }
  
  // 词法分析控制方法
  private static void lexicalAnalysis() {
    // 实例化字符流和DFA
    CharStream charStream = new CharStream(".\\src\\doc\\CharStream.txt");
    DFA dfa = new DFA(".\\src\\doc\\DFA.txt");
    System.out.println("charStream = " + charStream.characterStream);
    
    // 一直循环，直到检测到文件结束
    while(charStream.checkEnd()) {// 这里暂且用'0'表示文件结束，以后可以通过其他方法具体实现
      // 检测当前状态遇到当前输入能否转移，如果可以进入转移方法，如果不可以进入错误处理方法
      System.out.printf("state = %d, char = %c\n", dfa.getCurrentStateID(), charStream.getCurrentCharacter());
      if(dfa.checkMove(charStream.getCurrentCharacter())){
        lexicalAnalysisTransform(charStream, dfa);// 转移方法
      }
      else {
        lexicalAnalysisErrorHandling(charStream, dfa);// 错误处理方法
      } 
    }
    charStream.tokenIdn2Keyword();
    output(charStream);
  }
  
  public static void printSeparator() {
    System.out.println("--------------------------------------------------");
  }
  
  // 转移方法
  private static void lexicalAnalysisTransform(CharStream charStream, DFA dfa) {
    // DFA，字符流都向前移动
    dfa.move(charStream.getCurrentCharacter());
    charStream.move(dfa.getCurrentStateID());
    if(charStream.checkEnd())
      System.out.printf("state -> %d, char -> %c\n", dfa.getCurrentStateID(), charStream.getCurrentCharacter());
    System.out.println("characterStreamState = " + charStream.getCharacterStreamState());
  }
  
  // 错误处理方法
  private static void lexicalAnalysisErrorHandling(CharStream charStream, DFA dfa) {
    // 让错误处理类处理错误，调用回溯方法，如果回溯成功会得到找到的字符流下标，stateID，如果回溯失败只得到-1，-1
    int[] errorHandlingRes = ErrorHandling.backtrackingFinalState(charStream.getCharacterStreamState(), charStream.getCurrentPointer());
    // 回溯成功
    if(errorHandlingRes[0] != -1) {
      // 字符流下标转移到对应下标
      charStream.moveTo(errorHandlingRes[0]);
      charStream.setCharacterStreamState(errorHandlingRes[0]);
      System.out.printf("char -> %c\n", charStream.characterStream.get(errorHandlingRes[0]));
      // 输出Token
      charStream.outputToken(errorHandlingRes[1]);
      // DFA回到初态扫描下一个字符
      dfa.moveToInitialState();
      System.out.printf("state -> %d\n", dfa.getCurrentStateID());
      charStream.moveTo(errorHandlingRes[0] + 1);
      if(charStream.checkEnd())
        System.out.printf("cahr -> %c\n", charStream.characterStream.get(errorHandlingRes[0] + 1));
    }
    // 回溯失败
    else {
      // 输出错误信息
      charStream.outputErrorMessage();
      charStream.move(dfa.getCurrentStateID());
    }
    // 无论如何字符流都要向前移动
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
