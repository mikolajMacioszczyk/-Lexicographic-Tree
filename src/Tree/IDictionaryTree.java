package Tree;

import java.util.ArrayList;

public interface IDictionaryTree {
    void Load(String filePath);
    ArrayList<String> WordWithPrefix(String prefix);
    String[] MostlyOccurringPrefix(int length, RepeatPolicy policy);
    int FirstPositionInText(String word);
    boolean IsInText(String word);
    int FileWeight(String filePath);
    int WordCount(RepeatPolicy policy);
    int NodeCount();
}
