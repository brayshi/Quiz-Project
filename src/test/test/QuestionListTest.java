package test;

import model.Question;
import model.QuestionList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionListTest {
    private QuestionList questionList;

    @BeforeEach
    void setUp() {
        questionList = new QuestionList();
    }

    @Test
    void testGetQuestion() {
        Question question = new Question("Is this working?");

        questionList.addQuestion(question);
        assertEquals(question, questionList.getQuestion(0));
    }

    @Test
    void testAddQuestionNotPresent() {
        Question question1 = new Question("What created a symbiotic relationship with eukaryotic cells?");
        Question question2 = new Question("Whoops this isn't a question");

        questionList.addQuestion(question1);
        assertEquals(question1, questionList.getQuestion(0));
        assertEquals(1, questionList.listSize());

        questionList.addQuestion(question2);
        assertEquals(question2, questionList.getQuestion(1));
        assertEquals(2, questionList.listSize());
    }

    @Test
    void testAddQuestionPresent() {
        Question question = new Question("What created a symbiotic relationship with eukaryotic cells?");

        questionList.addQuestion(question);
        assertEquals(question, questionList.getQuestion(0));
        assertEquals(1, questionList.listSize());

        questionList.addQuestion(question);
        assertEquals(1, questionList.listSize());
    }

    @Test
    void testRemoveQuestion() {
        Question question1 = new Question("What created a symbiotic relationship with eukaryotic cells?");
        Question question2 = new Question("Whoops this shouldn't have been added");

        questionList.addQuestion(question2);
        assertEquals(1, questionList.listSize());
        assertEquals(question2, questionList.getQuestion(0));

        questionList.removeQuestion(question1);
        assertEquals(question2, questionList.getQuestion(0));
        assertEquals(1, questionList.listSize());
    }

    @Test
    void testListSize() {
        Question question1 = new Question("What created a symbiotic relationship with eukaryotic cells?");

        assertEquals(0, questionList.listSize());

        questionList.addQuestion(question1);
        assertEquals(1, questionList.listSize());

        questionList.removeQuestion(question1);
        assertEquals(0, questionList.listSize());
    }
}

