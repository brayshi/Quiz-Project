package test;

import model.Answer;
import model.AnswerList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class AnswerListTest {
    private AnswerList testAnswerList;

    @BeforeEach
    void setUp() {
        testAnswerList = new AnswerList();
    }

    @Test
    void testGetAnswer() {
        Answer ans1 = new Answer("Mitochondria");
        testAnswerList.addAnswer(ans1);
        assertEquals(ans1, testAnswerList.getAnswer(0));
    }

    @Test
    void testAddAnswerNotPresent() {
        Answer ans1 = new Answer("Mitochondria");
        Answer ans2 = new Answer("yes");

        testAnswerList.addAnswer(ans1);
        assertEquals(ans1, testAnswerList.getAnswer(0));
        assertEquals(1, testAnswerList.listSize());

        testAnswerList.addAnswer(ans2);
        assertEquals(ans2, testAnswerList.getAnswer(1));
        assertEquals(2, testAnswerList.listSize());
    }

    @Test
    void testAddAnswerPresent() {
        Answer ans = new Answer("Mitochondria");

        testAnswerList.addAnswer(ans);
        assertEquals(ans, testAnswerList.getAnswer(0));
        assertEquals(1, testAnswerList.listSize());

        testAnswerList.addAnswer(ans);
        assertEquals(1, testAnswerList.listSize());
    }

    @Test
    void testRemoveAnswer() {
        Answer ans = new Answer("Mitochondria");

        testAnswerList.addAnswer(ans);
        assertEquals(ans, testAnswerList.getAnswer(0));
        assertEquals(1, testAnswerList.listSize());

        testAnswerList.removeAnswer(ans);
        assertEquals(0, testAnswerList.listSize());

        testAnswerList.removeAnswer(ans);
        assertEquals(0, testAnswerList.listSize());
    }

    @Test
    void testListSize() {
        Answer ans1 = new Answer("Mitochondria");

        assertEquals(0, testAnswerList.listSize());

        testAnswerList.addAnswer(ans1);
        assertEquals(1, testAnswerList.listSize());

        testAnswerList.removeAnswer(ans1);
        assertEquals(0, testAnswerList.listSize());
    }
}
