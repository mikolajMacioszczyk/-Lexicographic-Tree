package Tree;

import java.util.ArrayList;

public interface BTree<T> {
    void Add(T value);
    T get(T value);
    void Remove(T value);
    int Index(T value);
    ArrayList<T> GetLeafs(T value);
    ArrayList<T> GetLeafs();
    ArrayList<T> ValuesWithLength(int length);
    int LeafCount(T value, RepeatPolicy policy);
    int LeafCount(RepeatPolicy policy);
    int NodeCount(T value);
    int NodeCount();
    void Clear();
}
