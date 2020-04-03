package errorHandling;

import java.util.List;

import charStream.CharStream;
import dfa.DFA;
import dfa.State;

public class ErrorHandling {
  
  //错误处理方法，具体功能在Controller类中已经说明
  public static int[] backtrackingFinalState(List<Integer> characterStreamState, int currentPointer) {
    // 向前回溯最后终态的下标，这里要特别注意已经处理的下标不能被回溯，比如已经输出的下标，恐慌方法处理的下标
    for(int i = currentPointer - 1; i >= 0 && (!CharStream.checkProcessedPointer(i)); i--) {
      // 得到对应的stateID
      int stateID = characterStreamState.get(i);
      // 检查是否是终态，是则返回下标和对应stateID，否则继续循环
      if(DFA.checkFinalState(stateID)) {
        int[] res = {i, stateID};
        return res;
      }
    }
    // 没有找到终态下标，返回-1，-1
    int[] res = {-1, -1};
    return res;
  }
  
}
