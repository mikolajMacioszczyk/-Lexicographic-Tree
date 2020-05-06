package Tree;

import java.util.ArrayList;

public class StringBTree implements BTree<String> {
    private class Node{
        private Key[] Keys;
        private Node[] Children;

        private Node(){
            Keys = new Key[26];
            Children = new Node[26];
        }
    }
    private class Key{
        String Value;
        int TextIndex;
        int Occurrences;

        private Key(String value, int indexInText){
            Value = value;
            TextIndex = indexInText;
        }

    }
    private Node Root;
    int upcomingIndex;

    @Override
    public void Add(String value) {
        if (value.length() == 0){return;}
        if (Root == null){
            Root = new Node();
        }
        Add(value, Root, 1);
        upcomingIndex++;
    }

    private void Add(String value, Node position, int stringLength){
        int index = value.charAt(stringLength-1) - 97;
        if (stringLength < value.length()){
            AddDeeper(value, position, stringLength, index);
        }
        else {
            AddPermanently(value, position, index);
        }
    }
    private void AddPermanently(String value, Node position, int index){
        if (position.Keys[index] == null){
            position.Keys[index] = new Key(value, upcomingIndex);
            position.Children[index] = new Node();
        }else {
            if (position.Keys[index].TextIndex == -1){
                position.Keys[index].TextIndex = upcomingIndex;
            }
        }
        position.Keys[index].Occurrences++;
    }
    private void AddDeeper(String value, Node position, int stringLength, int index){
        if (position.Keys[index] == null) {
            position.Keys[index] = new Key(value.substring(0, stringLength), -1);
            position.Children[index] = new Node();
        }
        Add(value, position.Children[index], stringLength+1);
    }

    @Override
    public int Index(String value) {
        if (Root == null){
            return -1;
        }
        return Index(value, Root, 0);
    }

    private int Index(String value, Node position, int keyLength){
        int index = value.charAt(keyLength) - 97;
        if (value.length()-1 > keyLength && position.Children[index] != null){
            return Index(value, position.Children[index], keyLength+1);
        }
        if (position.Keys[index] != null){
            return position.Keys[index].TextIndex;
        }
        return -1;
    }

    private Node GetNode(String value, Node position, int keyLength){
        int index = value.charAt(keyLength) - 97;
        if (value.length()-1 > keyLength && position.Children[index] != null){
            return GetNode(value, position.Children[index], keyLength+1);
        }
        if (position.Keys[index] != null){
            return position.Children[index];
        }
        return null;
    }

    @Override
    public ArrayList<String> GetLeafs(String value) {
        if (Root == null){return new ArrayList<>(); }
        Node investigatedNode = GetNode(value, Root, 0);
        if (investigatedNode == null || Root == null){ return new ArrayList<>(); }
        return GetLeafs(investigatedNode);
    }

    @Override
    public ArrayList<String> GetLeafs() {
        if (Root == null){
            return new ArrayList<>();
        }
        return GetLeafs(Root);
    }

    private ArrayList<String> GetLeafs(Node node){
        ArrayList<String> SumList = new ArrayList<>();
        for (int i = 0; i< 26; i++){
            if (node.Keys[i] != null){
                if (node.Keys[i].Occurrences>0){
                    SumList.add(node.Keys[i].Value);
                }
                SumList.addAll(GetLeafs(node.Children[i]));
            }
        }
        return SumList;
    }

    @Override
    public ArrayList<String> ValuesWithLength(int length) {
        if (Root == null){
            return new ArrayList<String>();
        }
        return ValuesWithLength(length, Root,1);
    }

    private ArrayList<String> ValuesWithLength(int length, Node node, int currentDeep){
        ArrayList<String> toReturn = new ArrayList<>();
        if (currentDeep == length){
            for (Key key : node.Keys){
                if (key != null){
                    toReturn.add(key.Value);
                }
            }
        }
        else {
            for (Node child : node.Children){
                if (child != null){
                    toReturn.addAll(ValuesWithLength(length, child, currentDeep+1));
                }
            }
        }
        return toReturn;
    }

    @Override
    public int LeafCount(String value, RepeatPolicy policy) {
        if (Root == null){return 0;}
        Node investigatedNode = GetNode(value, Root, 0);
        if (investigatedNode == null){return 0;}
        return GetLeafsCount(investigatedNode, policy);
    }

    @Override
    public int LeafCount(RepeatPolicy policy) {
        if (Root == null){
            return 0;
        }
        return GetLeafsCount(Root, policy);
    }

    private int GetLeafsCount(Node node, RepeatPolicy policy){
        int sum = 0;
        for (int i = 0; i< 26; i++){
            if (node.Keys[i] != null){
                switch (policy){
                    case Uniq:
                        sum += node.Keys[i].Occurrences > 0 ? 1 : 0;
                        break;
                    case Whole:
                        sum += node.Keys[i].Occurrences;
                        break;
                    default:
                        throw new IndexOutOfBoundsException();
                }
                sum += GetLeafsCount(node.Children[i], policy);
            }
        }
        return sum;
    }

    @Override
    public int NodeCount(String value) {
        if (Root == null){return 0; }
        Node investigatedNode = GetNode(value, Root, 0);
        if (investigatedNode == null){return 0; }
        return NodeCount(investigatedNode);
    }

    @Override
    public int NodeCount() {
        if (Root == null){
            return 0;
        }
        return NodeCount(Root);
    }

    private int NodeCount(Node node){
        int sum = 0;
        boolean isEmpty = true;
        for (Node child : node.Children){
            if (child != null){
                isEmpty = false;
                sum += NodeCount(child);
            }
        }
        if (!isEmpty){
            sum++;
        }
        return sum;
    }

    @Override
    public void Clear() {
        Root = null;
        upcomingIndex = 0;
    }

    @Override
    public String get(String value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void Remove(String value) {
        throw new UnsupportedOperationException();
    }

}
