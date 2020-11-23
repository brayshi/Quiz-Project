package test;

import exceptions.MultipleValidException;
import model.Answer;
import model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionTest {
    private Question question;

    @BeforeEach
    void setUp() {
        question = new Question("");
    }

    @Test
    void testGetQues() {
        assertEquals("", question.getQues());
    }

    @Test
    void testGetAnswer() {
        Answer answer = new Answer("42");

        assertEquals(0, question.listSize());

        try {
            question.addAnswer(answer);
        } catch (MultipleValidException e) {
            fail("Expected no exception");
        }
        assertEquals(answer, question.getAnswer(0));
        assertEquals(1, question.listSize());
    }

    @Test
    void testListSize() {
        assertEquals(0, question.listSize());

        try {
            question.addAnswer(new Answer("ans 1"));
        } catch (MultipleValidException e) {
            fail("Expected no exception");
        }
        assertEquals(1, question.listSize());

        try {
            question.addAnswer(new Answer("ans 2"));
        } catch (MultipleValidException e) {
            fail("Expected no exception");
        }
        assertEquals(2, question.listSize());
    }

    @Test
    void testAddQuestionNotPresent() {
        Answer a1 = new Answer("ans 1");
        Answer a2 = new Answer("ans 2");

        try {
            question.addAnswer(a1);
        } catch (MultipleValidException e) {
            fail("Expected no exception");
        }
        assertEquals(a1, question.getAnswer(0));
        assertEquals(1, question.listSize());

        try {
            question.addAnswer(a2);
        } catch (MultipleValidException e) {
            fail("Expected no exception");
        }
        assertEquals(a2, question.getAnswer(1));
        assertEquals(2, question.listSize());
    }

    @Test
    void testAddQuestionPresent() {
        Answer answer = new Answer("answer");

        try {
            question.addAnswer(answer);
        } catch (MultipleValidException e) {
            fail("Expected no exception");
        }
        assertEquals(answer, question.getAnswer(0));
        assertEquals(1, question.listSize());

        try {
            question.addAnswer(answer);
        } catch (MultipleValidException e) {
            fail("Expected no exception");
        }
        assertEquals(1, question.listSize());
    }

    @Test
    void testAddQuestionTwoValid() {
        Answer a1 = new Answer("I'm right!");
        a1.setValid();

        Answer a2 = new Answer("No, I'm right!");
        a2.setValid();

        try {
            question.addAnswer(a1);
        } catch (MultipleValidException e) {
            fail("Expected no exception");
        }

        try {
            question.addAnswer(a2);
            fail("Expected MultipleValidException");
        } catch (MultipleValidException e) {
            // expected
        }
    }
}
