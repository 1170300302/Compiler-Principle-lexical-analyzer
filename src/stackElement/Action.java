package stackElement;

public class Action extends StackBase {

  public Action(String symbol) {
    this.symbol = symbol;
  }

  public void executionMethod() {
    switch (symbol) {
      case "a1":
        executionMethodA1();
        break;
      default:

        break;
    }
  }

  // ���������ķ����м���̳����Ժ��ۺ�����
  // ��Ҫʲô���Ե�ValueBuffer�л��
  // ����õ���ֵ�浽ValueBuffer�У���������StackBase��ȡ
  private void executionMethodA1() {

  }
  
}
