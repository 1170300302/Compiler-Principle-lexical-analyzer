package errorHandling;

import java.util.List;
import charStream.CharStream;
import dfa.DFA;

public class ErrorHandling {

  public static int[] backtrackingFinalState(List<Integer> characterStreamState,
      int currentPointer) {
    // ��ǰ���������̬���±꣬����Ҫ�ر�ע���Ѿ�������±겻�ܱ����ݣ������Ѿ�������±꣬�ֻŷ���������±�
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
