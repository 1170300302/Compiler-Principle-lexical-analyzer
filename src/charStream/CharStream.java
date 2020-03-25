package charStream;

import java.util.ArrayList;
import java.util.List;

public class CharStream {
  
  private List<Character> characterStream = new ArrayList<>();// �洢���������ļ�
  private List<Integer> characterStreamState = new ArrayList<>();// �洢�ַ�����Ӧ�ľ�������״̬��������ɨ���������Ӧ�ú��ַ������ֳ���һ��
  private int currentPointer;// ��ǰָ��
  private static List<Integer> haveProcessedPointer = new ArrayList<>();// �Ѵ�����±֮꣬��Ļ��ݲ������������������±����
  
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
    
  }
  
  /**
   * @dec ���������Ϣ���������Ժ������Ӵ������ͣ��о�����鷳��������ɷ�������������Ķ�
   * @dec ������Ϣ������������ַ����±꣨��ʵ����currentCharacter����������ʾ������һ���֣�
   * @dec ����һ��Ҫ����haveProcessedPointer������
   */
  public void outputErrorMessage() {
    
  }
  
}
