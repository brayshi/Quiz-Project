package test;

import model.QuestionList;
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
}
