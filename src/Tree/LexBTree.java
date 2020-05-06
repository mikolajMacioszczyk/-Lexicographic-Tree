package Tree;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class LexBTree implements IDictionaryTree {
    private BTree<String> Tree;

    public LexBTree(){
        Tree = new StringBTree();
    }

    @Override
    public void Load(String filePath) {
        Tree.Clear();
        Pattern special = Pattern.compile("[^a-z]", Pattern.CASE_INSENSITIVE);
        try {
            String[] words = Files.readString(Paths.get(filePath)).split(" ");
            for (String word : words){
                if (!special.matcher(word).find()){
                    Tree.Add(word.toLowerCase());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> WordWithPrefix(String prefix) {
        return Tree.GetLeafs(prefix);
    }

    @Override
    public String[] MostlyOccurringPrefix(int length, RepeatPolicy policy) {
        ArrayList<String> prefixes = Tree.ValuesWithLength(length);
        if (prefixes.size() == 0){
            return new String[]{};
        }
        ArrayList<String> toReturn = new ArrayList<>();
        int max = 0;
        for (int i = 0; i<prefixes.size(); i++){
            int leafCount = Tree.LeafCount(prefixes.get(i), policy);
            if (leafCount > max){
                max = leafCount;
                toReturn.clear();
                toReturn.add(prefixes.get(i));
            }
            else if (leafCount == max){
                toReturn.add(prefixes.get(i));
            }
        }
        return toReturn.toArray(new String[0]);
    }

    @Override
    public int FirstPositionInText(String word) {
        return Tree.Index(word);
    }

    @Override
    public boolean IsInText(String word) {
        return Tree.Index(word) != -1;
    }

    @Override
    public int FileWeight(String filePath) {
        File file = new File(filePath);
        if (file.exists()){
            return (int) (file.length()/1024);
        }
        return 0;
    }

    @Override
    public int WordCount(RepeatPolicy policy) {
        return Tree.LeafCount(policy);
    }

    @Override
    public int NodeCount() {
        return Tree.NodeCount();
    }
}
