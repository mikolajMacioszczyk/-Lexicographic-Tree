import Tree.LexBTree;
import Tree.RepeatPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LexBTreeTest {
    private LexBTree tree;
    private String smallFile = "smallSizeText";
    private String mediumFile = "mediumSizeText";
    private String hugeFile = "hugeSizeText";
    private String testFile = "testFile";

    @BeforeEach
    public void BeforeEach(){
        tree = new LexBTree();
    }
    @Test
    public void InitializeTest(){
        tree.Load(smallFile);
        tree.Load(mediumFile);
    }
    @Test
    public void SizeTest(){
        System.out.println(tree.FileWeight(smallFile));
        System.out.println(tree.FileWeight(mediumFile));
        System.out.println(tree.FileWeight(hugeFile));
    }
    @Test
    public void WordWithPrefixEmptyTreeTest(){
        var expected = new String[]{};
        var result = tree.WordWithPrefix("a");
        Assertions.assertArrayEquals(expected, result.toArray());
    }

    @Test
    public void TestFilePrefixFi(){
        tree.Load(testFile);
        var expected = new String[]{"file","fill","finalize"};
        var result = tree.WordWithPrefix("fi");
        Assertions.assertArrayEquals(expected, result.toArray());
    }
    @Test
    public void WordWithPrefixWithTreeTest(){
        tree.Load(smallFile);
        var expected = new String[]{"temper","ten","tended","terminated","terms"};
        var result = tree.WordWithPrefix("te");
        Assertions.assertArrayEquals(expected, result.toArray());
    }

    @Test
    public void WordWithPrefixWithTreeTest2(){
        tree.Load(smallFile);
        var expected = new String[]{"valley","very","via","views","village","visited"};
        var result = tree.WordWithPrefix("v");
        Assertions.assertArrayEquals(expected, result.toArray());
    }

    @Test
    public void NotExistingPrefixWordsTest(){
        tree.Load(testFile);
        var expected = new String[]{};
        var result = tree.WordWithPrefix("notAtAll");
        Assertions.assertArrayEquals(expected, result.toArray());
    }

    @Test
    public void MostOccuringPrefixOfEmptyLexTest(){
        var expected = new String[]{};
        var result = tree.MostlyOccurringPrefix(1, RepeatPolicy.Whole);
        Assertions.assertArrayEquals(expected,result);
        result = tree.MostlyOccurringPrefix(1, RepeatPolicy.Uniq);
        Assertions.assertArrayEquals(expected,result);
    }

    @Test
    public void MostOccuringPrefixOfTestLexWithLength0Test(){
        tree.Load(testFile);
        var expected = new String[]{};
        var result = tree.MostlyOccurringPrefix(0, RepeatPolicy.Whole);
        Assertions.assertArrayEquals(expected,result);
        result = tree.MostlyOccurringPrefix(0, RepeatPolicy.Uniq);
        Assertions.assertArrayEquals(expected,result);
    }

    @Test
    public void MostOccuringPrefixOfTestLexWithLength1ThereAreTwoSuchPrefix(){
        tree.Load(testFile);
        var expected = new String[]{"f","w"};
        var result = tree.MostlyOccurringPrefix(1, RepeatPolicy.Whole);
        Assertions.assertArrayEquals(expected,result);
    }

    @Test
    public void MostOccuringPrefixOfTestLexWithLength2Test(){
        tree.Load(testFile);
        var expected = new String[]{"fi"};
        var result = tree.MostlyOccurringPrefix(2, RepeatPolicy.Whole);
        Assertions.assertArrayEquals(expected,result);
    }

    @Test
    public void MostOccuringPrefixOfTestLexWithLength3Test(){
        tree.Load(testFile);
        var expected = new String[]{"con","fil", "tes"};
        var result = tree.MostlyOccurringPrefix(3, RepeatPolicy.Whole);
        Assertions.assertArrayEquals(expected,result);
    }

    @Test
    public void MostOccuringPrefixOfTestLexWithLength3ByUniqWordTest(){
        tree.Load(testFile);
        var expected = new String[]{"fil", "tes"};
        var result = tree.MostlyOccurringPrefix(3, RepeatPolicy.Uniq);
        Assertions.assertArrayEquals(expected,result);
    }

    @Test
    public void MostOccuringPrefixOfTestLexWithLength6Test(){
        tree.Load(testFile);
        var expected = new String[]{"contai"};
        var result = tree.MostlyOccurringPrefix(6,RepeatPolicy.Whole);
        Assertions.assertArrayEquals(expected,result);
    }

    @Test
    public void MostOccuringPrefixOfTestLexWithLength6ByUniqWordTest(){
        tree.Load(testFile);
        var expected = new String[]{"contai","finali","projec"};
        var result = tree.MostlyOccurringPrefix(6,RepeatPolicy.Uniq);
        Assertions.assertArrayEquals(expected,result);
    }

    @Test
    public void FirstPositionTestOnEmptyList(){
        var expected = -1;
        var result = tree.FirstPositionInText("a");
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void FirstPositionTestNotExistingElement(){
        tree.Load(testFile);
        var expected = -1;
        var result = tree.FirstPositionInText("bentley");
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void FirstPositionTestFirstWordInTree(){
        tree.Load(testFile);
        var expected = 0;
        var result = tree.FirstPositionInText("wery");
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void FirstPositionTestLastWordInTree(){
        tree.Load(testFile);
        var expected = 16;
        var result = tree.FirstPositionInText("tests");
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void FirstPositionTestSomeWordTest(){
        tree.Load(testFile);
        var expected = 4;
        var result = tree.FirstPositionInText("only");
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void FirstPositionTestRedundantWordTestExpectedFirstAppearIndex(){
        tree.Load(testFile);
        var expected = 7;
        var result = tree.FirstPositionInText("contains");
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void IsWordInTextTestOnEmptyList(){
        Assertions.assertFalse(tree.IsInText("a"));
    }

    @Test
    public void IsWordInTextTestExpectedFalse(){
        tree.Load(testFile);
        Assertions.assertFalse(tree.IsInText("rockybalboa"));
    }

    @Test
    public void IsWordInTextTestExpectedFalseButThisWordIsPrefixForOtherWord(){
        tree.Load(testFile);
        Assertions.assertFalse(tree.IsInText("on"));
    }

    @Test
    public void IsWordInTextTestFirstWordInFile(){
        tree.Load(testFile);
        Assertions.assertTrue(tree.IsInText("wery"));
    }

    @Test
    public void IsWordInTextTestLastWordInFile(){
        tree.Load(testFile);
        Assertions.assertTrue(tree.IsInText("tests"));
    }

    @Test
    public void IsWordInTextTestRandomWordInFile(){
        tree.Load(testFile);
        Assertions.assertTrue(tree.IsInText("words"));
    }

    @Test
    public void WordCountUniqEmptyTreeText(){
        Assertions.assertEquals(0, tree.WordCount(RepeatPolicy.Uniq));
    }

    @Test
    public void WordCountUniqTestFileText(){
        tree.Load(testFile);
        Assertions.assertEquals(16, tree.WordCount(RepeatPolicy.Uniq));
    }

    @Test
    public void WordCountWholeEmptyTreeText(){
        Assertions.assertEquals(0, tree.WordCount(RepeatPolicy.Whole));
    }

    @Test
    public void WordCountWholeTestFileText(){
        tree.Load(testFile);
        Assertions.assertEquals(17, tree.WordCount(RepeatPolicy.Whole));
    }

    @Test
    public void NodeCountEmptyTreeText(){
        Assertions.assertEquals(0, tree.NodeCount());
    }

    @Test
    public void NodeCountTestFileText(){
        tree.Load(testFile);
        Assertions.assertEquals(46, tree.NodeCount());
    }
}
