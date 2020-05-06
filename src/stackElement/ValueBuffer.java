package stackElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValueBuffer {
  
  
  private static Map<String, String> valueBufferMap = new HashMap<>();
  
  public static void add(String valueKey, String valueValue) {
    valueBufferMap.put(valueKey, valueValue);
  }

}
