package stackElement;

import java.util.Map.Entry;

public class Self extends StackBase {

  @Override
  public void setValue(String valueKey, String valueValue) {
    this.value.put(valueKey, valueValue);
  }
  
  public Self(String symbol) {
    this.symbol = symbol;
    
  }

  @Override
  public void popMethod() {
    for(Entry<String, String> entry: value.entrySet()) {
      ValueBuffer.add(entry.getKey(), entry.getValue());
    }
  }

}
