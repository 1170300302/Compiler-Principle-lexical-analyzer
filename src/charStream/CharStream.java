package charStream;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dfa.DFA;

public class CharStream {

  public List<Character> characterStream = new ArrayList<>();
  public List<Integer> characterStreamState = new ArrayList<>();
  private int currentPointer;
  private static List<Integer> haveProcessedPointer = new ArrayList<>();
  private List<String[]> token = new ArrayList<>();
  private List<String> errorList = new ArrayList<>();
  private List<String> keyword = new ArrayList<>();

  /**
   * @dec �õ�currentCharacter�����÷�������ɣ�������Ҫ������Ķ�
   * @return currentCharacter
   */
  public char getCurrentCharacter() {
    return this.characterStream.get(currentPointer);
  }

  /**
   * @dec �õ�CharacterStreamStater�����÷�������ɣ�������Ҫ������Ķ�
   * @return CharacterStreamStater
   */
  public List<Integer> getCharacterStreamState() {
    return new ArrayList<>(characterStreamState);
  }

  /**
   * @dec �õ�CurrentPointer�����÷�������ɣ�������Ҫ������Ķ�
   * @return CurrentPointer
   */
  public int getCurrentPointer() {
    return this.currentPointer;
  }

  public List<String[]> getToken() {
    return new ArrayList<>(token);
  }

  public void setToken(List<String[]> token) {
    this.token = new ArrayList<>(token);
  }

  public void setCharacterStreamState(int index) {
    for (int i = index + 1; i <= (characterStreamState.size() - 1); i++) {
      characterStreamState.remove(i);
    }
  }

  /**
   * @dec ���캯������ʼ�������ֶΡ�������ɷ�������������Ķ�
   * @dec �����ʼ�����ݰ����������ļ����ݵ�characterStream��currentPointer = 0
   * @param filePath �ַ������ļ�·��
   */
  public CharStream(String filePath) {
    haveProcessedPointer = new ArrayList<>();
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
      String line = "";
      while ((line = bufferedReader.readLine()) != null) {
        String[] tmpStrings = line.split("");
        for (int i = 0; i < tmpStrings.length; i++) {
          if (!tmpStrings[i].equals("")) {
            this.characterStream.add(tmpStrings[i].charAt(0));
          }
        }
      }
      this.characterStream.add(null);
      this.currentPointer = 0;
      bufferedReader.close();
      BufferedReader keywordBufferedReader =
          new BufferedReader(new FileReader(".\\src\\doc\\Keyword.txt"));
      line = "";
      while ((line = keywordBufferedReader.readLine()) != null) {
        String[] tmpStrings = line.split(",");
        for (int i = 0; i < tmpStrings.length; i++) {
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
    if (currentPointer >= characterStream.size()) {
      return false;
    }
    return true;
  }

  public boolean checkNull() {
    if ((currentPointer + 1) == characterStream.size()) {
      return false;
    }
    return true;
  }

  /**
   * @dec �ƶ���������ɷ�������������Ķ�
   * @param currentStateID DFA�ƶ����stateID
   * @dec ����Ҫ�ı��ֵ�����У�characterStreamState��currentPointer
   */
  public void move(int currentStateID) {
    this.characterStreamState.add(currentStateID);
    this.currentPointer += 1;
  }

  /**
   * @dec �ƶ���ָ��λ�á�������ɷ�������������Ķ�
   * @param desPointer ���ƶ�λ��
   * @dec ����ֻ��Ҫ�ı�currentPointer������Ҫ�ı�characterStreamState����Ϊ�ַ���ֻ�ǻص����ݴ��������������ص���ȥ
   */
  public void moveTo(int desPointer) {
    this.currentPointer = desPointer;
  }

  /**
   * @dec ����±��Ƿ����Ѵ����±��У�������bool��������÷�������ɣ�������Ҫ������Ķ�
   * @param Pointer ����±�
   * @return �Ƿ����Ѵ����±���
   */
  public static boolean checkProcessedPointer(int Pointer) {
    return haveProcessedPointer.contains(Pointer);
  }

  /**
   * @dec ���Token���������˼���������currentPointer���ڵ�֮ǰ�����е��ʣ�������ΪToken��value��������ɷ�������������Ķ�
   * @dec Token key����ͨ��State�ľ�̬����finalStateOutput���
   * @dec ����һ��Ҫ����haveProcessedPointer������
   * @param stateID ��finalStateOutput��Ϊ��������
   */
  public void outputToken(int stateID) {
    StringBuilder tokenBuilder = new StringBuilder();
    for (int i = 0; i <= currentPointer; i++) {
      if (!haveProcessedPointer.contains(i)) {
        tokenBuilder.append(characterStream.get(i));
        haveProcessedPointer.add(i);
      }
    }
    String tmp[] = new String[3];
    tmp[0] = tokenBuilder.toString();
    tmp[1] = DFA.finalStateOutput(stateID);
    tmp[2] = "";
    token.add(tmp);
//    System.out.printf("token = %s, %s, finalState = %d\n", tokenBuilder.toString(),
//        DFA.finalStateOutput(stateID), stateID);
//    tokenList.add(tokenBuilder.toString());
  }

  /**
   * @dec ���������Ϣ���������Ժ������Ӵ������ͣ��о�����鷳��������ɷ�������������Ķ�
   * @dec ������Ϣ������������ַ����±꣨��ʵ����currentCharacter����������ʾ������һ���֣�
   * @dec ����һ��Ҫ����haveProcessedPointer������
   */
  public void outputErrorMessage() {
    if (checkNull() && getCurrentCharacter() != ' ') {
//      System.out.printf("There's an unexpected error at %c\n", getCurrentCharacter());
      errorList.add(
          "There's an unexpected error at " + getCurrentPointer() + ": " + getCurrentCharacter());
    }
    haveProcessedPointer.add(currentPointer);
  }

  public void tokenIdn2Keyword() {
//    for (Map.Entry<String, String> entry : token.entrySet()) {
//      if (entry.getValue().equals("IDN")) {
//        if (keyword.contains(entry.getKey())) {
//          token.put(entry.getKey(), "KEYWORD");
//        }
//      }
//    }
    for (int i = 0; i < token.size(); i++) {
      if (token.get(i)[1].equals("IDN")) {
        if (keyword.contains(token.get(i)[0])) {
          token.get(i)[1] = "KEYWORD";
        }
      }
    }
  }

}
