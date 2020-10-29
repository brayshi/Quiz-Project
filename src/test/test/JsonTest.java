package test;

import model.Question;
import model.Quiz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class JsonTest {
    protected void checkQuiz(String name, Quiz createdQuiz, Quiz quiz) {
        assertEquals(name, quiz.getName());
        assertTrue(createdQuiz.equals(quiz));
    }
}
