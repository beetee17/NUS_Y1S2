import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class MarkovModelWordsTest {

    @Test
    public void standardTest() {
        String text = "Hello my name is test. Is test my name? test is my name.";
        int order = 1;
        int seed = 100;
        List<String> kgram = List.of("test");
        int expected = 3;

        MarkovModelWords testModel = new MarkovModelWords(order, seed);
        testModel.initializeText(MarkovModelWords.getWords(text));
        int freq = testModel.getFrequency(kgram);

        assertEquals(expected, freq);
    }


    @Test
    public void standardTest2() {
        String text = "is, test is test is is test is test test";
        int order = 2;
        int seed = 100;
        List<String> kgram = List.of("is", "test");
        int expected = 3;

        MarkovModelWords testModel = new MarkovModelWords(order, seed);
        testModel.initializeText(MarkovModelWords.getWords(text));
        int freq = testModel.getFrequency(kgram);

        assertEquals(expected, freq);
    }

    @Test
    public void standardTest3() {
        String text = "is test is test is is test is test test";
        int order = 2;
        int seed = 100;
        List<String> kgram = List.of("test", "is");
        int expected = 2;
        String wordAfter = "test";

        MarkovModelWords testModel = new MarkovModelWords(order, seed);
        testModel.initializeText(MarkovModelWords.getWords(text));
        int freq = testModel.getFrequency(kgram, wordAfter);

        assertEquals(expected, freq);
    }

    @Test
    public void standardTest4() {
        String text = "is test is test is is test is test test";
        int order = 2;
        int seed = 100;
        List<String> kgram = List.of("is", "test");
        int expected = 3;
        String wordAfter = "is";

        MarkovModelWords testModel = new MarkovModelWords(order, seed);
        testModel.initializeText(MarkovModelWords.getWords(text));
        int freq = testModel.getFrequency(kgram, wordAfter);

        assertEquals(expected, freq);
    }
}
