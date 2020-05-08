package stackElement;

public class Self extends StackBase {

  public Self(String symbol) {
    this.symbol = symbol;
  }

  public void addSystemLexeme(String key, String value) {
    ValueBuffer.addValueBuffer(key + ".val", value);
  }

}
