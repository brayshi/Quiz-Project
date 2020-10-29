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
    void testEquals() {
        Quiz quiz1 = new Quiz("test");
        Quiz quiz2 = new Quiz("test");
        Quiz quiz3 = new Quiz("test");
        Question question = new Question("hello?");

        quiz1.addQuestion(question);
        quiz2.addQuestion(question);
        quiz3.addQuestion(new Question("say hello"));
        assertFalse(quiz2.equals(quiz3));
        assertTrue(quiz1.equals(quiz2));
    }

    @Test
    void testHashCode() {
        Quiz quiz1 = new Quiz("test");
        Quiz quiz2 = new Quiz("test");
        assertEquals(quiz2.hashCode(), quiz1.hashCode());
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
