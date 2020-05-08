package stackElement;

import java.util.HashMap;
import java.util.Map;

public class ValueBuffer {

//  private static Map<String, List<String>> inhe = new HashMap<>();
//  private static List<String> syne = new ArrayList<>();
  private static Map<String, String> valueBuffer = new HashMap<>();

//  public static void initInhe(String pathName) {
//    try {
//      BufferedReader bufferedReader = new BufferedReader(new FileReader(pathName));
//      String line = "";
//      while ((line = bufferedReader.readLine()) != null) {
//        String[] splitStrings = line.split("->");
//        if (inhe.containsKey(splitStrings[0])) {
//          inhe.get(splitStrings[0]).add(splitStrings[1]);
//        } else {
//          inhe.put(splitStrings[0], Arrays.asList(splitStrings[1]));
//        }
//      }
//      bufferedReader.close();
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//  }
//
//  public static void initSyne(String pathName) {
//
//  }
//
//  public static boolean hasInhe(String symbol) {
//    return inhe.containsKey(symbol);
//  }
//
//  public static boolean hasSyne(String symbol) {
//    return syne.contains(symbol);
//  }
//
//  public static String getInheSingleValue(String symbol) {
//    if (inhe.get(symbol).size() != 1) {
//      System.out.println("getInheSingleValue error");
//    }
//    return inhe.get(symbol).get(0);
//  }

  public static void addValueBuffer(String valueKey, String valueValue) {
    valueBuffer.put(valueKey, valueValue);
  }

  public static String getValueBufferValue(String valueKey) {
    return valueBuffer.get(valueKey);
  }

}
