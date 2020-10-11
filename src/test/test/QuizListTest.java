package test;

import model.Quiz;
import model.QuizList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuizListTest {
    private QuizList quizList;

    @BeforeEach
    void setUp() {
        quizList = new QuizList();
    }

    @Test
    void testGetQuiz() {
        Quiz quiz = new Quiz("demo 1");
        quizList.addQuiz(quiz);
        assertEquals(quiz, quizList.getQuiz(0));
    }

    @Test
    void testAddQuizNotPresent() {
        Quiz q1 = new Quiz("demo 1");
        Quiz q2 = new Quiz("demo 2");

        quizList.addQuiz(q1);
        assertEquals(q1, quizList.getQuiz(0));
        assertEquals(1, quizList.listSize());

        quizList.addQuiz(q2);
        assertEquals(q2, quizList.getQuiz(1));
        assertEquals(2, quizList.listSize());
    }

    @Test
    void testAddQuizPresent() {
        Quiz q = new Quiz("demo 1");

        quizList.addQuiz(q);
        assertEquals(q, quizList.getQuiz(0));
        assertEquals(1, quizList.listSize());

        quizList.addQuiz(q);
        assertEquals(1, quizList.listSize());
    }

    @Test
    void testRemoveQuiz() {
        Quiz q1 = new Quiz("demo 1");
        Quiz q2 = new Quiz("demo 2");

        quizList.addQuiz(q1);
        quizList.addQuiz(q2);
        assertEquals(q1, quizList.getQuiz(0));
        assertEquals(q2, quizList.getQuiz(1));

        quizList.removeQuiz(q1);
        assertEquals(q2, quizList.getQuiz(0));
        assertEquals(1, quizList.listSize());

        quizList.removeQuiz(q1);
        assertEquals(1, quizList.listSize());

        quizList.removeQuiz(q2);
        assertEquals(0, quizList.listSize());
    }

    @Test
    void testListSize() {
        Quiz q = new Quiz("demo 1");

        assertEquals(0, quizList.listSize());

        quizList.addQuiz(q);
        assertEquals(1, quizList.listSize());

        quizList.removeQuiz(q);
        assertEquals(0, quizList.listSize());
    }
}
