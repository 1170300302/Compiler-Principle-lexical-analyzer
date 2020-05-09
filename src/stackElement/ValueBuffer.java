package stackElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValueBuffer {

//  private static Map<String, List<String>> inhe = new HashMap<>();
//  private static List<String> syne = new ArrayList<>();
  private static Map<String, String> valueBuffer = new HashMap<>();
  private static Map<String, String[]> symbolTable = new HashMap<>();
  private static List<String[]> quaternion = new ArrayList<>();
  private static int addressCount = 1;

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

  public static void addSymbolTable(String valueKey, String valueType, String valueOffset) {
    symbolTable.put(valueKey, new String[] {valueType, valueOffset});
  }

  public static String getSymbolTableType(String valueKey) {
    return symbolTable.get(valueKey)[0];
  }

  public static String getSymbolTableOffset(String valueKey) {
    return symbolTable.get(valueKey)[1];
  }

  public static void addQuaternion(String q1, String q2, String q3, String q4) {
    quaternion.add(new String[] {q1, q2, q3, q4});
  }

  public static List<String[]> getQuaternion() {
    return new ArrayList<>(quaternion);
  }

  public static String newTemp() {
    String res = "t" + addressCount;
    addressCount += 1;
    return res;
  }

}
