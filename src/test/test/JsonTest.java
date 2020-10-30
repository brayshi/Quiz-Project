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
        assertTrue(checkQuestions(createdQuiz, quiz));
    }

    private boolean checkQuestions(Quiz quiz1, Quiz quiz2) {
        if (quiz1.listSize() == quiz2.listSize()) {
            for (int i = 0; i < quiz1.listSize(); i++) {
                if (!quiz1.getQuestion(i).getQues().equals(quiz2.getQuestion(i).getQues())) {
                    return false;
                }
            }
        }
        return true;
    }
}
