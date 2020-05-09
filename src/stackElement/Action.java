package stackElement;

public class Action extends StackBase {

  public Action(String symbol) {
    this.symbol = symbol;
  }

  public void executionMethod() {
    switch (symbol) {
      case "a1":
        executionA1();
        break;
      case "a2":
        executionA2();
        break;
      case "a3":
        executionA3();
        break;
      case "a4":
        executionA4();
        break;
      case "a5":
        executionA5();
        break;
      case "a6":
        executionA6();
        break;
      case "a7":
        executionA7();
        break;
      case "a8":
        executionA8();
        break;
      case "a9":
        executionA9();
        break;
      case "a10":
        executionA10();
        break;
      case "a11":
        executionA11();
        break;
      case "a12":
        executionA12();
        break;
      case "a13":
        executionA13();
        break;
      case "a14":
        executionA14();
        break;
      case "a15":
        executionA15();
        break;
      case "a16":
        executionA16();
        break;
      case "a17":
        executionA17();
        break;
      case "a18":
        executionA18();
        break;
      case "a19":
        executionA19();
        break;
      case "a20":
        executionA20();
        break;
      default:

        break;
    }
  }

  private static void executionA1() {
    ValueBuffer.addValueBuffer("offset", "0");
  }

  private static void executionA2() {
    String res;
    res = (Integer.valueOf(ValueBuffer.getValueBufferValue("offset"))
        + Integer.valueOf(ValueBuffer.getValueBufferValue("T.width"))) + "";
    ValueBuffer.addValueBuffer("offset", res);
    ValueBuffer.addSymbolTable(ValueBuffer.getValueBufferValue("id.val"),
        ValueBuffer.getValueBufferValue("T.type"), ValueBuffer.getValueBufferValue("offset"));
  }

  private static void executionA3() {
    ValueBuffer.addValueBuffer("t", "X.type");
    ValueBuffer.addValueBuffer("w", "X.width");
  }

  private static void executionA4() {
    ValueBuffer.addValueBuffer("T.type", "C.type");
    ValueBuffer.addValueBuffer("T.width", "C.width");
  }

  private static void executionA5() {
    ValueBuffer.addValueBuffer("X.type", "int");
    ValueBuffer.addValueBuffer("X.width", "4");
  }

  private static void executionA6() {
    ValueBuffer.addValueBuffer("X.type", "float");
    ValueBuffer.addValueBuffer("X.width", "8");
  }

  private static void executionA7() {
    ValueBuffer.addValueBuffer("X.type", "char");
    ValueBuffer.addValueBuffer("X.width", "2");
  }

  private static void executionA8() {
    ValueBuffer.addValueBuffer("C.type", "t");
    ValueBuffer.addValueBuffer("C.width", "w");
  }

  private static void executionA9() {
    ValueBuffer.addValueBuffer("C.type", "array(num.val, C.type)");
    String res;
    res = (Integer.valueOf(ValueBuffer.getValueBufferValue("num.val"))
        * Integer.valueOf(ValueBuffer.getValueBufferValue("C.type"))) + "";
    ValueBuffer.addValueBuffer("C.width", res);
  }

  private static void executionA10() {
    ValueBuffer.addValueBuffer("p", ValueBuffer.getValueBufferValue("id.val"));
    ValueBuffer.addQuaternion("_", ValueBuffer.getValueBufferValue("E.addr"), "_",
        ValueBuffer.getValueBufferValue("P"));
  }

  private static void executionA11() {
    ValueBuffer.addValueBuffer("F.addr", ValueBuffer.getValueBufferValue("id.val"));
  }

  private static void executionA12() {
    ValueBuffer.addValueBuffer("F.addr", ValueBuffer.getValueBufferValue("E.addr"));
  }

  private static void executionA13() {
    ValueBuffer.addValueBuffer("F.addr", ValueBuffer.newTemp());
    ValueBuffer.addQuaternion("-", "_", ValueBuffer.getValueBufferValue("E.addr"),
        ValueBuffer.getValueBufferValue("F.addr"));
  }

  private static void executionA14() {
    ValueBuffer.addValueBuffer("F.addr", ValueBuffer.getValueBufferValue("num.val"));
  }

  private static void executionA15() {
    ValueBuffer.addValueBuffer("G.addr", ValueBuffer.newTemp());
    ValueBuffer.addQuaternion("*", ValueBuffer.getValueBufferValue("F.addr"),
        ValueBuffer.getValueBufferValue("G_.addr"), ValueBuffer.getValueBufferValue("G.addr"));
  }

  private static void executionA16() {
    if (ValueBuffer.getValueBufferValue("G.flag") != null
        && ValueBuffer.getValueBufferValue("G.flag").equals("true")) {
      ValueBuffer.addValueBuffer("G_.addr", ValueBuffer.getValueBufferValue("F.addr"));
    } else {
      String tmp = ValueBuffer.getValueBufferValue("G_addr");
      ValueBuffer.addValueBuffer("G_.addr", ValueBuffer.newTemp());
      ValueBuffer.addQuaternion("*", ValueBuffer.getValueBufferValue("F.addr"),
          ValueBuffer.getValueBufferValue(tmp), ValueBuffer.getValueBufferValue("G_.addr"));
    }
  }

  private static void executionA17() {
    ValueBuffer.addValueBuffer("G.flag", "true");
  }

  private static void executionA18() {
    ValueBuffer.addValueBuffer("E.addr", ValueBuffer.newTemp());
    ValueBuffer.addQuaternion("+", ValueBuffer.getValueBufferValue("G.addr"),
        ValueBuffer.getValueBufferValue("E_.addr"), ValueBuffer.getValueBufferValue("E.addr"));
  }

  private static void executionA19() {
    if (ValueBuffer.getValueBufferValue("E_.flag") != null
        && ValueBuffer.getValueBufferValue("E_.flag").equals("true")) {
      ValueBuffer.addValueBuffer("E_.addr", ValueBuffer.getValueBufferValue("G.addr"));
    } else {
      String tmp = ValueBuffer.getValueBufferValue("E_.addr");
      ValueBuffer.addValueBuffer("E_.addr", ValueBuffer.newTemp());
      ValueBuffer.addQuaternion("+", ValueBuffer.getValueBufferValue("G.addr"), tmp,
          ValueBuffer.getValueBufferValue("E_.addr"));
    }
  }
  
  private static void executionA20() {
    ValueBuffer.addValueBuffer("E_flag", "true");
  }

}
