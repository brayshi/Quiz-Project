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


}
