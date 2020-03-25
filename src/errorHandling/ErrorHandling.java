package errorHandling;

import java.util.List;

import charStream.CharStream;
import dfa.State;

public class ErrorHandling {
  
  //�������������幦����Controller�����Ѿ�˵��
  public static int[] backtrackingFinalState(List<Integer> characterStreamState, int currentPointer) {
    // ��ǰ���������̬���±꣬����Ҫ�ر�ע���Ѿ�������±겻�ܱ����ݣ������Ѿ�������±꣬�ֻŷ���������±�
    for(int i = currentPointer - 1; i >= 0 && (!CharStream.checkProcessedPointer(i)); i--) {
      // �õ���Ӧ��stateID
      int stateID = characterStreamState.get(i);
      // ����Ƿ�����̬�����򷵻��±�Ͷ�ӦstateID���������ѭ��
      if(State.checkFinalState(stateID)) {
        int[] res = {i, stateID};
        return res;
      }
    }
    // û���ҵ���̬�±꣬����-1��-1
    int[] res = {-1, -1};
    return res;
  }
  
}
