package test;

import model.Question;
import model.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuizTest {
    private Quiz quiz;

    @BeforeEach
    void setUp() {
        quiz = new Quiz("demo");
    }

    @Test
    void testGetName() {
        assertEquals("demo", quiz.getName());
    }

    @Test
    void testGetQuestionList() {
        Question question = new Question("is this true?");

        assertEquals(0, quiz.listSize());

        quiz.addQuestion(question);
        assertEquals(question, quiz.getQuestion(0));
        assertEquals(1, quiz.listSize());
    }
}
