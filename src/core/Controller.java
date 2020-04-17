package core;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.UIManager;
import charStream.CharStream;
import dfa.DFA;
import errorHandling.ErrorHandling;
import ui.CoreFrame;

public class Controller {

  private static CoreFrame coreFrame;

  public static void main(String[] args) {
    startGUI();
  }

  private static void startGUI() {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          JFrame.setDefaultLookAndFeelDecorated(true);
          // UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
          UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
          coreFrame = new CoreFrame();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  public static void lexicalAnalysis(String charStreamPath) {
    String dfaPath = ".\\src\\doc\\DFA.txt";
    CharStream charStream = new CharStream(charStreamPath);
    DFA dfa = new DFA(dfaPath);

    while (charStream.checkEnd()) {
//      System.out.printf("state = %d, char = %c\n", dfa.getCurrentStateID(),
//          charStream.getCurrentCharacter());
      if (charStream.checkNull() && dfa.checkMove(charStream.getCurrentCharacter())) {
        lexicalAnalysisTransform(charStream, dfa);// 转移方法
      } else {
        lexicalAnalysisErrorHandling(charStream, dfa);// 错误处理方法
      }
    }
    charStream.tokenIdn2Keyword();
    charStream.setToken(coreFrame.setTokenTable(charStream.getToken()));
//    output(charStream);
  }

  private static void lexicalAnalysisTransform(CharStream charStream, DFA dfa) {
    dfa.move(charStream.getCurrentCharacter());
    charStream.move(dfa.getCurrentStateID());
//    if (charStream.checkEnd()) System.out.printf("state -> %d, char -> %c\n",
//        dfa.getCurrentStateID(), charStream.getCurrentCharacter());
//    System.out.println("characterStreamState = " + charStream.getCharacterStreamState());
  }

  private static void lexicalAnalysisErrorHandling(CharStream charStream, DFA dfa) {
    // 让错误处理类处理错误，调用回溯方法，如果回溯成功会得到找到的字符流下标，stateID，如果回溯失败只得到-1，-1
    int[] errorHandlingRes = ErrorHandling.backtrackingFinalState(
        charStream.getCharacterStreamState(), charStream.getCurrentPointer());
    if (errorHandlingRes[0] != -1) {
      charStream.moveTo(errorHandlingRes[0]);
      charStream.setCharacterStreamState(errorHandlingRes[0]);
//      System.out.printf("char -> %c\n", charStream.characterStream.get(errorHandlingRes[0]));
      charStream.outputToken(errorHandlingRes[1]);
      dfa.moveToInitialState();
//      System.out.printf("state -> %d\n", dfa.getCurrentStateID());
      charStream.moveTo(errorHandlingRes[0] + 1);
//      if (charStream.checkEnd())
//        System.out.printf("cahr -> %c\n", charStream.characterStream.get(errorHandlingRes[0] + 1));
    } else {
      charStream.outputErrorMessage();
      charStream.move(dfa.getCurrentStateID());
    }
  }

//  private static void output(CharStream charStream) {
//    for (Map.Entry<String, String> entry : charStream.getToken().entrySet()) {
//      switch (entry.getValue()) {
//        case "IDN":
//        case "CONST":
//        case "NOTES":
//        case "CHARCONST":
//        case "OCT":
//        case "HEX":
//          System.out.println(
//              entry.getKey() + "\t\t\t" + "<" + entry.getValue() + ", " + entry.getKey() + ">");
//          break;
//        case "KEYWORD":
//        case "BOUNDARY":
//        case "OPERATOR":
//          System.out.println(entry.getKey() + "\t\t\t" + "<" + entry.getKey() + ", _>");
//          break;
//        default:
//          System.out.println("Error");
//          break;
//      }
//    }
//  }

}
