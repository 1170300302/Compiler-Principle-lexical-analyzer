package core;

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
    CharStream charStream = new CharStream("测试文件路径");
    DFA dfa = new DFA("转换表文件路径");
    
    // 一直循环，直到检测到文件结束
    while(charStream.getCurrentCharacter() == '0') {// 这里暂且用'0'表示文件结束，以后可以通过其他方法具体实现
      // 检测当前状态遇到当前输入能否转移，如果可以进入转移方法，如果不可以进入错误处理方法
      if(dfa.checkMove(charStream.getCurrentCharacter())){
        lexicalAnalysisTransform(charStream, dfa);// 转移方法
      }
      else {
        lexicalAnalysisErrorHandling(charStream, dfa);// 错误处理方法
      } 
    }
  }
  
  // 转移方法
  private static void lexicalAnalysisTransform(CharStream charStream, DFA dfa) {
    // DFA，字符流都向前移动
    char tmpChar = charStream.getCurrentCharacter();
    int tmpStateID = dfa.getCurrentStateID();
    dfa.move(tmpChar);
    charStream.move(tmpStateID);
  }
  
  // 错误处理方法
  private static void lexicalAnalysisErrorHandling(CharStream charStream, DFA dfa) {
    // 让错误处理类处理错误，调用回溯方法，如果回溯成功会得到找到的字符流下标，stateID，如果回溯失败只得到-1，-1
    int[] errorHandlingRes = ErrorHandling.backtrackingFinalState(charStream.getCharacterStreamState(), charStream.getCurrentPointer());
    // 回溯成功
    int tmpStateID = dfa.getCurrentStateID();
    if(errorHandlingRes[0] != -1) {
      // 字符流下标转移到对应下标
      charStream.moveTo(errorHandlingRes[0]);
      // 输出Token
      charStream.outputToken(errorHandlingRes[1]);
      // DFA回到初态扫描下一个字符
      dfa.moveToInitialState();
    }
    // 回溯失败
    else {
      // 输出错误信息
      charStream.outputErrorMessage();
    }
    // 无论如何字符流都要向前移动
    charStream.move(tmpStateID);
  }

}
