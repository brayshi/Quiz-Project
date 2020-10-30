package test;

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

        question.addAnswer(answer);
        assertEquals(answer, question.getAnswer(0));
        assertEquals(1, question.listSize());
    }

    @Test
    void testListSize() {
        assertEquals(0, question.listSize());

        question.addAnswer(new Answer("ans 1"));
        assertEquals(1, question.listSize());

        question.addAnswer(new Answer("ans 2"));
        assertEquals(2, question.listSize());
    }

    @Test
    void testAddQuestionNotPresent() {
        Answer a1 = new Answer("ans 1");
        Answer a2 = new Answer("ans 2");

        question.addAnswer(a1);
        assertEquals(a1, question.getAnswer(0));
        assertEquals(1, question.listSize());

        question.addAnswer(a2);
        assertEquals(a2, question.getAnswer(1));
        assertEquals(2, question.listSize());
    }

    @Test
    void testAddQuestionPresent() {
        Answer answer = new Answer("answer");

        question.addAnswer(answer);
        assertEquals(answer, question.getAnswer(0));
        assertEquals(1, question.listSize());

        question.addAnswer(answer);
        assertEquals(1, question.listSize());
    }
}
