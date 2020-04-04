package charStream;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dfa.DFA;

public class CharStream {
  
  public List<Character> characterStream = new ArrayList<>();// 存储整个输入文件
  public List<Integer> characterStreamState = new ArrayList<>();// 存储字符流对应的经历过的状态，对于已扫描过的区域应该和字符流保持长度一致
  private int currentPointer;// 当前指针
  private static List<Integer> haveProcessedPointer = new ArrayList<>();// 已处理的下标，之后的回溯操作不允许对这里面的下标操作
  private Map<String, String> token = new HashMap<>();
  private List<String> keyword = new ArrayList<>();
  
  /**
   * @dec 得到currentCharacter——该方法已完成，如无需要，无需改动
   * @return currentCharacter
   */
  public char getCurrentCharacter() {
    return this.characterStream.get(currentPointer);
  }
  
  /**
   * @dec 得到CharacterStreamStater——该方法已完成，如无需要，无需改动
   * @return CharacterStreamStater
   */
  public List<Integer> getCharacterStreamState() {
    return new ArrayList<>(characterStreamState);
  }
  
  /**
   * @dec 得到CurrentPointer——该方法已完成，如无需要，无需改动
   * @return CurrentPointer
   */
  public int getCurrentPointer() {
    return this.currentPointer;
  }
  
  public Map<String, String> getToken(){
    return new HashMap<>(token);
  }
  
  public void setCharacterStreamState(int index) {
    for(int i = index + 1; i <= (characterStreamState.size() - 1); i++) {
      characterStreamState.remove(i);
    }
  }
  
  /**
   * @dec 构造函数，初始化各个字段——待完成方法，可以随意改动
   * @dec 具体初始化内容包括：测试文件内容到characterStream，currentPointer = 0
   * @param filePath 字符流的文件路径
   */
  public CharStream(String filePath) {
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
      String line = "";
      while((line = bufferedReader.readLine()) != null) {
        String[] tmpStrings = line.split("");
        for(int i = 0; i < tmpStrings.length; i++) {
          if(!tmpStrings[i].equals("")) {
            this.characterStream.add(tmpStrings[i].charAt(0));
          }
        }
      }
      this.characterStream.add(null);
      this.currentPointer = 0;
      bufferedReader.close();
      BufferedReader keywordBufferedReader = new BufferedReader(new FileReader(".\\src\\doc\\Keyword.txt"));
      line = "";
      while((line = keywordBufferedReader.readLine()) != null) {
        String[] tmpStrings = line.split(",");
        for(int i = 0; i < tmpStrings.length; i++) {
          this.keyword.add(tmpStrings[i]);
        }
      }
      keywordBufferedReader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public boolean checkEnd() {
    if((characterStream.size() <= currentPointer) || characterStream.get(currentPointer) == null) {
      return false;
    }
    return true;
  }
  
  /**
   * @dec 移动——待完成方法，可以随意改动
   * @param currentStateID DFA移动后的stateID
   * @dec 这里要改变的值具体有：characterStreamState，currentPointer
   */
  public void move(int currentStateID) {
    this.characterStreamState.add(currentStateID);
    this.currentPointer += 1;
  }
  
  /**
   * @dec 移动到指定位置——待完成方法，可以随意改动
   * @param desPointer 待移动位置
   * @dec 这里只需要改变currentPointer，不需要改变characterStreamState，因为字符流只是回到回溯处，并不是真正回到过去
   */
  public void moveTo(int desPointer) {
    this.currentPointer = desPointer;
  }
  
  /**
   * @dec 检查下标是否在已处理下标中，并返回bool结果——该方法已完成，如无需要，无需改动
   * @param Pointer 检查下标
   * @return 是否在已处理下标中
   */
  public static boolean checkProcessedPointer(int Pointer) {
    return haveProcessedPointer.contains(Pointer);
  }
  
  /**
   * @dec 输出Token，这里的意思是输出包括currentPointer在内的之前的所有单词，可能作为Token的value——待完成方法，可以随意改动
   * @dec Token key可以通过State的静态方法finalStateOutput获得
   * @dec 这里一定要更新haveProcessedPointer！！！
   * @param stateID 在finalStateOutput作为参数传递
   */
  public void outputToken(int stateID) {
    StringBuilder tokenBuilder = new StringBuilder();
    for(int i = 0; i <= currentPointer; i++) {
      if(!haveProcessedPointer.contains(i)) {
        tokenBuilder.append(characterStream.get(i));
        haveProcessedPointer.add(i);
      }
    }
    token.put(tokenBuilder.toString(), DFA.finalStateOutput(stateID));
    System.out.printf("token = %s, %s, finalState = %d\n", tokenBuilder.toString(), DFA.finalStateOutput(stateID), stateID);
  }
  
  /**
   * @dec 输出错误信息，可以在以后具体添加错误类型，感觉会很麻烦——待完成方法，可以随意改动
   * @dec 错误信息包括：错误的字符串下标（其实就是currentCharacter），错误提示（随便打一行字）
   * @dec 这里一定要更新haveProcessedPointer！！！
   */
  public void outputErrorMessage() {
    if(getCurrentCharacter() != ' ')
      System.out.printf("There's an unexpected error at %c\n", getCurrentCharacter());
    haveProcessedPointer.add(currentPointer);
  }
  
  public void tokenIdn2Keyword() {
    for(Map.Entry<String, String> entry : token.entrySet()) {
      if(entry.getValue().equals("IDN")) {
        if(keyword.contains(entry.getKey())) {
          token.put(entry.getKey(), "KEYWORD");
        }
      }
    }
  }
  
}
