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

    @Test
    void testGetQuestion() {
        Question question = new Question("how?");
        Question why = new Question("why?");

        quiz.addQuestion(question);
        quiz.addQuestion(why);

        assertEquals(question, quiz.getQuestion(0));
        assertEquals(why, quiz.getQuestion(1));
    }

    @Test
    void testListSize() {
        assertEquals(0, quiz.listSize());

        quiz.addQuestion(new Question("test 1"));
        assertEquals(1, quiz.listSize());

        quiz.addQuestion(new Question("test 2"));
        assertEquals(2, quiz.listSize());
    }

    @Test
    void testAddQuestionNotPresent() {
        Question q1 = new Question("question 1");
        Question q2 = new Question("question 2");

        quiz.addQuestion(q1);
        assertEquals(q1, quiz.getQuestion(0));
        assertEquals(1, quiz.listSize());

        quiz.addQuestion(q2);
        assertEquals(q2, quiz.getQuestion(1));
        assertEquals(2, quiz.listSize());
    }

    @Test
    void testAddQuestionPresent() {
        Question q = new Question("test");

        quiz.addQuestion(q);
        assertEquals(q, quiz.getQuestion(0));
        assertEquals(1, quiz.listSize());

        quiz.addQuestion(q);
        assertEquals(1, quiz.listSize());
    }
}
