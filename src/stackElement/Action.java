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

  // 类似这样的方法中计算继承属性和综合属性
  // 需要什么可以到ValueBuffer中获得
  // 计算得到的值存到ValueBuffer中，方便其他StackBase获取
  private void executionMethodA1() {

  }
  
}
