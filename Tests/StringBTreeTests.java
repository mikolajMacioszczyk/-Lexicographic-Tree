import Tree.BTree;
import Tree.RepeatPolicy;
import Tree.StringBTree;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringBTreeTests {
    private BTree<String> tree;
    @BeforeEach
    public void BeforeEach(){
        tree = new StringBTree();
    }
    @Test
    public void InitializeTest(){
        tree = new StringBTree();
        tree.Clear();
    }
    @Test
    public void EmptyTreeTests(){
        Assertions.assertEquals(-1,tree.Index("a"));
        String[] emptyArray = new String[]{};
        Assertions.assertArrayEquals(emptyArray, tree.GetLeafs("").toArray());
        Assertions.assertArrayEquals(emptyArray, tree.ValuesWithLength(1).toArray());
        Assertions.assertEquals(0, tree.LeafCount(RepeatPolicy.Whole));
        Assertions.assertEquals(0, tree.NodeCount());
    }
    @Test
    public void AddLetterTest(){
        tree.Add("a");
        int expected = 0;
        int result = tree.Index("a");
        Assertions.assertEquals(expected, result);
    }
    @Test
    public void AddFewLetterTest(){
        tree.Add("a");
        tree.Add("d");
        tree.Add("b");
        tree.Add("c");
        tree.Add("e");
        tree.Add("a");
        int expected = 0;
        int result = tree.Index("a");
        Assertions.assertEquals(expected, result);
        expected = 1;
        result = tree.Index("d");
        Assertions.assertEquals(expected, result);
        expected = 2;
        result = tree.Index("b");
        Assertions.assertEquals(expected, result);
        expected = 3;
        result = tree.Index("c");
        Assertions.assertEquals(expected, result);
    }
    @Test
    public void AddTwoLetterLengthWordTest(){
        tree.Add("ab");
        int expected = 0;
        int result = tree.Index("ab");
        Assertions.assertEquals(expected, result);
        expected = -1;
        result = tree.Index("a");
        Assertions.assertEquals(expected, result);
        expected = -1;
        result = tree.Index("b");
        Assertions.assertEquals(expected, result);
    }
    @Test
    public void AddTwoLetterLengthWordAndHisPrefixTest(){
        tree.Add("ab");
        tree.Add("a");
        int expected = 0;
        int result = tree.Index("ab");
        Assertions.assertEquals(expected, result);
        expected = 1;
        result = tree.Index("a");
        Assertions.assertEquals(expected, result);
        expected = -1;
        result = tree.Index("b");
        Assertions.assertEquals(expected, result);
    }
    @Test
    public void AddTwoLetterLengthWordHisPrefixAndOtherWordTest(){
        tree.Add("ab");
        tree.Add("cd");
        tree.Add("a");
        int expected = 0;
        int result = tree.Index("ab");
        Assertions.assertEquals(expected, result);
        expected = 2;
        result = tree.Index("a");
        Assertions.assertEquals(expected, result);
        expected = -1;
        result = tree.Index("b");
        Assertions.assertEquals(expected, result);
        expected = 1;
        result = tree.Index("cd");
        Assertions.assertEquals(expected, result);
    }
    @Test
    public void AddThreeLetterLengthWordTest(){
        tree.Add("abb");
        tree.Add("a");
        int expected = 0;
        int result = tree.Index("abb");
        Assertions.assertEquals(expected, result);
        expected = 1;
        result = tree.Index("a");
        Assertions.assertEquals(expected, result);
        expected = -1;
        result = tree.Index("ab");
        Assertions.assertEquals(expected, result);
    }
    @Test
    public void AddLongWordTest(){
        tree.Add("qwertyuiop");
        int expected = 0;
        int result = tree.Index("qwertyuiop");
        Assertions.assertEquals(expected, result);
    }
    @Test
    public void AddFewWordsTest(){
        tree.Add("this");
        tree.Add("is");
        tree.Add("b");
        tree.Add("tree");
        tree.Add("test");
        int expected = 0;
        int result = tree.Index("this");
        Assertions.assertEquals(expected, result);
        expected = 1;
        result = tree.Index("is");
        Assertions.assertEquals(expected, result);
        expected = 3;
        result = tree.Index("tree");
        Assertions.assertEquals(expected, result);
    }
    @Test
    public void AddSameWordMultipleTimesLettersTest(){
        tree.Add("abc");
        tree.Add("abc");
        tree.Add("abc");
        tree.Add("abc");
        tree.Add("abc");
        int expected = 0;
        int result = tree.Index("abc");
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void OneElementTreeRootLeafs(){
        tree.Add("abc");
        String[] expected = new String[]{"abc"};
        var result = tree.GetLeafs();
        Assertions.assertArrayEquals(expected, result.toArray());
    }

    @Test
    public void OneElementTreeButAddedMultipleTimesRootLeafs(){
        tree.Add("abc");
        tree.Add("abc");
        tree.Add("abc");
        tree.Add("abc");
        String[] expected = new String[]{"abc"};
        var result = tree.GetLeafs();
        Assertions.assertArrayEquals(expected, result.toArray());
    }
    @Test
    public void FewElementsInTreeRootLeafsTest(){
        tree.Add("abc");
        tree.Add("def");
        tree.Add("abd");
        tree.Add("agh");
        tree.Add("ag");
        tree.Add("ah");
        String[] expected = new String[]{"abc", "abd", "ag", "agh", "ah", "def"};
        var result = tree.GetLeafs();
        Assertions.assertArrayEquals(expected, result.toArray());
    }
    @Test
    public void FewElementsInTreeSomeOfThemMultipleTimesRootLeafsTest(){
        tree.Add("ah");
        tree.Add("def");
        tree.Add("def");
        tree.Add("abd");
        tree.Add("abd");
        tree.Add("agh");
        tree.Add("abc");
        tree.Add("agh");
        tree.Add("ag");
        tree.Add("ah");
        tree.Add("def");
        String[] expected = new String[]{"abc", "abd", "ag", "agh", "ah", "def"};
        var result = tree.GetLeafs();
        Assertions.assertArrayEquals(expected, result.toArray());
    }
    @Test
    public void OnlyOneWordGetLeafsFromThatWordTest(){
        tree.Add("abc");
        String[] expected = new String[]{};
        var result = tree.GetLeafs("abc");
        Assertions.assertArrayEquals(expected, result.toArray());
    }
    @Test
    public void OnlyOneWordGetLeafsFromPrefixThatWordTest(){
        tree.Add("abc");
        String[] expected = new String[]{"abc"};
        var result = tree.GetLeafs("a");
        Assertions.assertArrayEquals(expected, result.toArray());
    }
    @Test
    public void OnlyOneWordGetLeafsFromOtherWordTest(){
        tree.Add("abc");
        String[] expected = new String[]{};
        var result = tree.GetLeafs("cda");
        Assertions.assertArrayEquals(expected, result.toArray());
    }
    @Test
    public void RootHasOneChildWithChildrenTestLeafsThatChild(){
        tree.Add("abc");
        tree.Add("acds");
        tree.Add("acdw");
        tree.Add("a");
        tree.Add("amn");
        tree.Add("arrre");
        String[] expected = new String[]{"abc","acds","acdw","amn","arrre"};
        var result = tree.GetLeafs("a");
        Assertions.assertArrayEquals(expected, result.toArray());
    }
    @Test
    public void BranchedTreeTestNodesLeafs(){
        tree.Add("a");
        tree.Add("a");
        tree.Add("b");
        tree.Add("c");
        tree.Add("c");
        tree.Add("d");
        tree.Add("e");
        tree.Add("ac");
        tree.Add("aa");
        tree.Add("ad");
        tree.Add("ad");
        tree.Add("ba");
        tree.Add("bb");
        tree.Add("bc");
        tree.Add("ca");
        tree.Add("cb");
        tree.Add("cb");
        tree.Add("cc");
        tree.Add("cd");
        tree.Add("aaa");
        tree.Add("aab");
        tree.Add("aba");
        tree.Add("abb");
        tree.Add("aca");
        tree.Add("acb");
        String[] expected = new String[]{"aaa","aab"};
        var result = tree.GetLeafs("aa");
        Assertions.assertArrayEquals(expected, result.toArray());
        expected = new String[]{"aa","aaa","aab","aba","abb","ac","aca","acb","ad"};
        result = tree.GetLeafs("a");
        Assertions.assertArrayEquals(expected, result.toArray());
    }
    @Test
    public void ValuesWithLength0Test(){
        tree.Add("a");
        String[] expected = new String[]{};
        var result = tree.ValuesWithLength(0);
        Assertions.assertArrayEquals(expected, result.toArray());
    }
    @Test
    public void InTreeAreOnlyOneLetterWordsValuesWithLength1Test(){
        tree.Add("a");
        tree.Add("d");
        tree.Add("b");
        tree.Add("c");
        tree.Add("d");
        String[] expected = new String[]{"a","b","c","d"};
        var result = tree.ValuesWithLength(1);
        Assertions.assertArrayEquals(expected, result.toArray());
    }
    @Test
    public void InTreeAreOnlyOneLetterWordsValuesWithLength2Test(){
        tree.Add("a");
        tree.Add("d");
        tree.Add("b");
        tree.Add("c");
        tree.Add("d");
        String[] expected = new String[]{};
        var result = tree.ValuesWithLength(2);
        Assertions.assertArrayEquals(expected, result.toArray());
    }
    @Test
    public void InTreeAre1And3LetterWorldValuesWithLength2Test(){
        tree.Add("a");
        tree.Add("d");
        tree.Add("d");
        tree.Add("aaa");
        tree.Add("acd");
        tree.Add("bac");
        tree.Add("bcd");
        tree.Add("ddd");
        tree.Add("cac");
        String[] expected = new String[]{"aa","ac","ba","bc","ca","dd"};
        var result = tree.ValuesWithLength(2);
        Assertions.assertArrayEquals(expected, result.toArray());
    }
    @Test
    public void BranchedTreeValuesWithLength2Test(){
        tree.Add("a");
        tree.Add("a");
        tree.Add("d");
        tree.Add("ac");
        tree.Add("aa");
        tree.Add("ad");
        tree.Add("ad");
        tree.Add("ba");
        tree.Add("bb");
        tree.Add("bc");
        tree.Add("ca");
        tree.Add("cb");
        tree.Add("cb");
        tree.Add("cc");
        tree.Add("cd");
        tree.Add("aaa");
        tree.Add("abb");
        tree.Add("acb");
        String[] expected = new String[]{"aa","ab","ac","ad","ba","bb","bc","ca","cb","cc","cd"};
        var result = tree.ValuesWithLength(2);
        Assertions.assertArrayEquals(expected, result.toArray());
    }
    @Test
    public void EmptyTreeLeafCountTest(){
        Assertions.assertEquals(0, tree.LeafCount(RepeatPolicy.Whole));
        Assertions.assertEquals(0, tree.LeafCount(RepeatPolicy.Uniq));
    }
    @Test
    public void SingleElementTreeLeafCountTest(){
        tree.Add("a");
        Assertions.assertEquals(1, tree.LeafCount(RepeatPolicy.Whole));
        Assertions.assertEquals(1, tree.LeafCount(RepeatPolicy.Uniq));
    }
    @Test
    public void SingleElementMultipleTimesAddedLeafCountTestExpectedCountedEachRepetition(){
        tree.Add("a");
        tree.Add("a");
        tree.Add("a");
        Assertions.assertEquals(3, tree.LeafCount(RepeatPolicy.Whole));
        Assertions.assertEquals(1, tree.LeafCount(RepeatPolicy.Uniq));
    }
    @Test
    public void FewWordsWithOnlyOneLetterWithRepetitionLeafCount(){
        tree.Add("a");
        tree.Add("a");
        tree.Add("b");
        tree.Add("c");
        tree.Add("c");
        tree.Add("d");
        Assertions.assertEquals(6, tree.LeafCount(RepeatPolicy.Whole));
        Assertions.assertEquals(4, tree.LeafCount(RepeatPolicy.Uniq));
    }
    @Test
    public void TwoLetterWorldsWithRepetitionLeafCountsTest(){
        tree.Add("aa");
        tree.Add("ab");
        tree.Add("ab");
        tree.Add("ba");
        tree.Add("ca");
        tree.Add("ca");
        tree.Add("cb");
        tree.Add("da");
        Assertions.assertEquals(8, tree.LeafCount(RepeatPolicy.Whole));
        Assertions.assertEquals(6, tree.LeafCount(RepeatPolicy.Uniq));
    }
    @Test
    public void TwoLetterWorldsWithRepetitionAndPrefixesLeafCountsTest(){
        tree.Add("aa");
        tree.Add("ab");
        tree.Add("a");
        tree.Add("ab");
        tree.Add("ba");
        tree.Add("ca");
        tree.Add("b");
        tree.Add("b");
        tree.Add("ca");
        tree.Add("cb");
        tree.Add("da");
        Assertions.assertEquals(11, tree.LeafCount(RepeatPolicy.Whole));
        Assertions.assertEquals(8, tree.LeafCount(RepeatPolicy.Uniq));
    }
    @Test
    public void SingleElemInTreeAndThisElemLeafCountTest(){
        tree.Add("a");
        Assertions.assertEquals(0, tree.LeafCount("a", RepeatPolicy.Whole));
        Assertions.assertEquals(0, tree.LeafCount("a", RepeatPolicy.Uniq));
    }
    @Test
    public void SingleElemInTreeAndThisElemsPrefixLeafCountTest(){
        tree.Add("ab");
        Assertions.assertEquals(1, tree.LeafCount("a", RepeatPolicy.Whole));
        Assertions.assertEquals(1, tree.LeafCount("a", RepeatPolicy.Uniq));
    }
    @Test
    public void SingleElemInTreeAddedTwoTimesAndThisElemsPrefixLeafCountTest(){
        tree.Add("ab");
        tree.Add("ab");
        Assertions.assertEquals(2, tree.LeafCount("a", RepeatPolicy.Whole));
        Assertions.assertEquals(1, tree.LeafCount("a", RepeatPolicy.Uniq));
    }
    @Test
    public void LeafCountWhereTreeIsEmptyTest(){
        Assertions.assertEquals(0, tree.LeafCount("a", RepeatPolicy.Whole));
        Assertions.assertEquals(0, tree.LeafCount("a", RepeatPolicy.Uniq));
    }
    @Test
    public void NonExistingElementLeafCountTest(){
        tree.Add("a");
        Assertions.assertEquals(0, tree.LeafCount("b", RepeatPolicy.Whole));
        Assertions.assertEquals(0, tree.LeafCount("b", RepeatPolicy.Uniq));
    }
    @Test
    public void TreeWithTwoBranchesLeafCountTestByOnOfThisBranch(){
        tree.Add("a");
        tree.Add("ab");
        tree.Add("aa");
        tree.Add("aba");
        tree.Add("aaa");
        tree.Add("aaa");
        tree.Add("acc");
        tree.Add("b");
        tree.Add("ba");
        tree.Add("ba");
        tree.Add("bbb");
        tree.Add("ba");
        Assertions.assertEquals(6, tree.LeafCount("a", RepeatPolicy.Whole));
        Assertions.assertEquals(4, tree.LeafCount("b", RepeatPolicy.Whole));
        Assertions.assertEquals(2, tree.LeafCount("b", RepeatPolicy.Uniq));
        Assertions.assertEquals(2, tree.LeafCount("aa", RepeatPolicy.Whole));
        Assertions.assertEquals(1, tree.LeafCount("aa", RepeatPolicy.Uniq));
    }

    @Test
    public void EmptyTreeNodeCountTest(){
        Assertions.assertEquals(0, tree.NodeCount());
    }
    @Test
    public void SingleElementTreeNodeCountTest(){
        tree.Add("a");
        Assertions.assertEquals(1, tree.NodeCount());
    }
    @Test
    public void SingleElementMultipleTimesAddedNodeCountTestExpectedCountedEachRepetition(){
        tree.Add("a");
        tree.Add("a");
        tree.Add("a");
        Assertions.assertEquals(1, tree.NodeCount());
    }
    @Test
    public void FewWordsWithOnlyOneLetterWithoutRepetitionNodeCount(){
        tree.Add("a");
        tree.Add("b");
        tree.Add("c");
        tree.Add("d");
        Assertions.assertEquals(1, tree.NodeCount());
    }
    @Test
    public void FewWordsWithOnlyOneLetterWithRepetitionNodeCount(){
        tree.Add("a");
        tree.Add("a");
        tree.Add("b");
        tree.Add("c");
        tree.Add("c");
        tree.Add("d");
        Assertions.assertEquals(1, tree.NodeCount());
    }
    @Test
    public void TwoLetterWorldsWithRepetitionNodeCountsTest(){
        tree.Add("aa");
        tree.Add("ab");
        tree.Add("ab");
        tree.Add("ba");
        tree.Add("ca");
        tree.Add("ca");
        tree.Add("cb");
        tree.Add("da");
        Assertions.assertEquals(5, tree.NodeCount());
    }
    @Test
    public void TwoLetterWorldsWithRepetitionAndPrefixesNodeCountsTest(){
        tree.Add("aa");
        tree.Add("ab");
        tree.Add("a");
        tree.Add("ab");
        tree.Add("ba");
        tree.Add("ca");
        tree.Add("b");
        tree.Add("b");
        tree.Add("ca");
        tree.Add("cb");
        tree.Add("da");
        Assertions.assertEquals(5, tree.NodeCount());
    }
    @Test
    public void AtLeastTwoLetterWorldsNodeCountsTest(){
        tree.Add("aa");
        tree.Add("ab");
        tree.Add("ab");
        tree.Add("abc");
        tree.Add("abd");
        tree.Add("ba");
        tree.Add("bad");
        tree.Add("ca");
        tree.Add("cat");
        tree.Add("car");
        tree.Add("cap");
        tree.Add("cba");
        tree.Add("dam");
        tree.Add("dar");
        tree.Add("dap");
        tree.Add("dba");
        tree.Add("dbc");
        Assertions.assertEquals(11, tree.NodeCount());
    }
    @Test
    public void SingleElemInTreeAndThisElemNodeCountTest(){
        tree.Add("a");
        Assertions.assertEquals(0, tree.NodeCount("a"));
    }
    @Test
    public void SingleElemInTreeAddedTwoTimesAndThisElemsPrefixNodeCountTest(){
        tree.Add("ab");
        tree.Add("ab");
        Assertions.assertEquals(1, tree.NodeCount("a"));
    }
    @Test
    public void NodeCountWhereTreeIsEmptyTest(){
        Assertions.assertEquals(0, tree.NodeCount("a"));
    }
    @Test
    public void NonExistingElementNodeCountTest(){
        tree.Add("a");
        Assertions.assertEquals(0, tree.NodeCount("b"));
    }
    @Test
    public void TreeWithTwoBranchesNodeCountTestByOnOfThisBranch(){
        tree.Add("a");
        tree.Add("ab");
        tree.Add("aa");
        tree.Add("aba");
        tree.Add("aaa");
        tree.Add("acc");
        tree.Add("b");
        tree.Add("ba");
        tree.Add("bbb");
        tree.Add("ba");
        Assertions.assertEquals(4, tree.NodeCount("a"));
        Assertions.assertEquals(2, tree.NodeCount("b"));
        Assertions.assertEquals(1, tree.NodeCount("aa"));
    }
    @Test
    public void AtLeastTwoLetterWorldsNodeCountsByValueTest(){
        tree.Add("aa");
        tree.Add("ab");
        tree.Add("ab");
        tree.Add("abc");
        tree.Add("abd");
        tree.Add("abe");
        tree.Add("aca");
        tree.Add("acc");
        tree.Add("acb");
        tree.Add("acba");
        tree.Add("ba");
        tree.Add("bad");
        Assertions.assertEquals(4, tree.NodeCount("a"));
    }

    @Test
    public void ClearTest(){
        tree.Add("a");
        tree.Add("ab");
        tree.Add("ac");
        tree.Add("ad");
        tree.Clear();
        var expected = new String[]{};
        var result = tree.GetLeafs();
        Assertions.assertArrayEquals(expected, result.toArray());
        Assertions.assertEquals(0, tree.LeafCount(RepeatPolicy.Whole));
        Assertions.assertEquals(0, tree.NodeCount());
    }
}
