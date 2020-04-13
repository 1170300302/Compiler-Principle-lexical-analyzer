package errorHandling;

import java.util.List;
import charStream.CharStream;
import dfa.DFA;

public class ErrorHandling {

  public static int[] backtrackingFinalState(List<Integer> characterStreamState,
      int currentPointer) {
    // 向前回溯最后终态的下标，这里要特别注意已经处理的下标不能被回溯，比如已经输出的下标，恐慌方法处理的下标
    for (int i = currentPointer - 1; i >= 0 && (!CharStream.checkProcessedPointer(i)); i--) {
      int stateID = characterStreamState.get(i);
      if (DFA.checkFinalState(stateID)) {
        int[] res = {i, stateID};
        return res;
      }
    }
    int[] res = {-1, -1};
    return res;
  }

}
