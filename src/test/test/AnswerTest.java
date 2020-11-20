package test;

import model.Answer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AnswerTest {
    private Answer answer;

    @BeforeEach
    void setUp() {
        answer = new Answer("test.test");
    }

    @Test
    void testGetStr() {
        assertEquals("test.test", answer.getStr());
    }

    @Test
    void testGetValid() {
        assertEquals(false, answer.isValid());
    }

    @Test
    void testSetValid() {
        answer.setValid();
        assertEquals(true, answer.isValid());
    }

    @Test
    void testSetInvalid() {
        answer.setValid();
        assertEquals(true, answer.isValid());
        answer.setInvalid();
        assertEquals(false, answer.isValid());
    }

    @Test
    void testEquals() {
        Answer copyAnswer = new Answer(answer.getStr());
        String ans = answer.getStr();

        assertTrue(copyAnswer.equals(answer) && answer.equals(copyAnswer));
        assertFalse(copyAnswer.equals(ans) && answer.equals(ans));

        assertFalse(answer.equals(null));

        copyAnswer.setValid();
        assertFalse(copyAnswer.equals(answer) && answer.equals(copyAnswer));

        answer.setValid();
        assertTrue(copyAnswer.equals(answer) && answer.equals(copyAnswer));
    }

    @Test
    void testHashcode() {
        Answer copyAnswer = new Answer(answer.getStr());
        assertTrue(copyAnswer.equals(answer) && answer.equals(copyAnswer));
        assertEquals(copyAnswer.hashCode(), answer.hashCode());
    }


}
