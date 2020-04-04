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
  
  public List<Character> characterStream = new ArrayList<>();// �洢���������ļ�
  private List<Integer> characterStreamState = new ArrayList<>();// �洢�ַ�����Ӧ�ľ�������״̬��������ɨ���������Ӧ�ú��ַ������ֳ���һ��
  private int currentPointer;// ��ǰָ��
  private static List<Integer> haveProcessedPointer = new ArrayList<>();// �Ѵ�����±֮꣬��Ļ��ݲ������������������±����
  private Map<String, String> token = new HashMap<>();
  
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
  

  /**
   * @dec ���캯������ʼ�������ֶΡ�������ɷ�������������Ķ�
   * @dec �����ʼ�����ݰ����������ļ����ݵ�characterStream��currentPointer = 0
   * @param filePath �ַ������ļ�·��
   */
  public CharStream(String filePath) {
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
      String line = "";
      while((line = bufferedReader.readLine()) != null) {
//        System.out.println("line is " + line);
        String[] tmpStrings = line.split("");
        for(int i = 0; i < tmpStrings.length; i++) {
//          System.out.println("char is " + tmpStrings[i]);
          if(!tmpStrings[i].equals("") && !tmpStrings[i].equals(" ")) {
//            System.out.println("addbef is " + tmpStrings[i]);
//            System.out.println("add is " + tmpStrings[i].charAt(0));
            this.characterStream.add(tmpStrings[i].charAt(0));
          }
        }
      }
      this.characterStream.add(null);
      this.currentPointer = 0;
      bufferedReader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public boolean checkEnd() {
    try {
      characterStream.get(currentPointer);
      return true;
    } catch (Exception e) {
      return false;
    }
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
    for(int i = 0; i <= currentPointer; i++) {
      if(!haveProcessedPointer.contains(i)) {
        tokenBuilder.append(characterStream.get(i));
        haveProcessedPointer.add(i);
      }
    }
    token.put(tokenBuilder.toString(), DFA.finalStateOutput(stateID));
  }
  
  /**
   * @dec ���������Ϣ���������Ժ������Ӵ������ͣ��о�����鷳��������ɷ�������������Ķ�
   * @dec ������Ϣ������������ַ����±꣨��ʵ����currentCharacter����������ʾ������һ���֣�
   * @dec ����һ��Ҫ����haveProcessedPointer������
   */
  public void outputErrorMessage() {
    System.out.printf("There's an unexpected error at %d\n", currentPointer);
    haveProcessedPointer.add(currentPointer);
  }
  
}
