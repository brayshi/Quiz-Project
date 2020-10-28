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
    void testGetAnswerList() {
        Answer answer = new Answer("42");

        assertEquals(0, question.listSize());

        question.addAnswer(answer);
        assertEquals(answer, question.getAnswer(0));
        assertEquals(1, question.listSize());
    }
}
