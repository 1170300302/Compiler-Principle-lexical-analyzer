package stackElement;

import java.util.Map;

public abstract class StackBase {

  protected String symbol;
  protected Map<String, String> value;

  public abstract void setValue(String valueKey, String valueValue);
  
  public abstract void popMethod();

}
