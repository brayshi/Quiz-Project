package test;

import model.QuestionList;
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

        assertEquals(0, quiz.getQuestionList().listSize());

        quiz.getQuestionList().addQuestion(question);
        assertEquals(question, quiz.getQuestionList().getQuestion(0));
        assertEquals(1, quiz.getQuestionList().listSize());
    }
}
