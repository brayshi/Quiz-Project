package test;

import exceptions.MultipleValidException;
import model.Answer;
import model.Question;
import model.Quiz;
import model.QuizList;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// NOTE: heavily based on test within JsonSerializationDemo
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            QuizList quizList = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyQuizList.json");
        try {
            QuizList quizList = reader.read();
            assertEquals("My Quiz List", quizList.getName());
            assertEquals(0, quizList.listSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralQuizList.json");
        try {
            QuizList quizList = reader.read();
            assertEquals("My Quiz List", quizList.getName());
            List<Quiz> quizzes = quizList.getUnmodifiedQuizzes();
            assertEquals(2, quizzes.size());
            checkQuiz("demo", createQuiz1(), quizzes.get(0));
            checkQuiz("test", createQuiz2(), quizzes.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    private Quiz createQuiz1() {
        Quiz quiz1 = new Quiz("demo");
        quiz1.addQuestion(new Question("Choose 'yes'"));
        try {
            quiz1.getQuestion(0).addAnswer(new Answer("no"));
        } catch (MultipleValidException e) {
            System.out.println("failed to add new answer: Question already had one valid answer");
        }
        try {
            quiz1.getQuestion(0).addAnswer(new Answer("yes"));
        } catch (MultipleValidException e) {
            System.out.println("failed to add new answer: Question already had one valid answer");
        }
        quiz1.getQuestion(0).getAnswer(1).setValid();

        return quiz1;
    }

    private Quiz createQuiz2() {
        Quiz quiz2 = new Quiz("test");
        quiz2.addQuestion(new Question("Choose 'false'"));
        try {
            quiz2.getQuestion(0).addAnswer(new Answer("false"));
        } catch (MultipleValidException e) {
            System.out.println("failed to add new answer: Question already had one valid answer");
        }
        try {
            quiz2.getQuestion(0).addAnswer(new Answer("true-ish?"));
        } catch (MultipleValidException e) {
            System.out.println("failed to add new answer: Question already had one valid answer");
        }
        try {
            quiz2.getQuestion(0).addAnswer(new Answer("false-ish?"));
        } catch (MultipleValidException e) {
            System.out.println("failed to add new answer: Question already had one valid answer");
        }
        quiz2.getQuestion(0).getAnswer(0).setValid();

        return quiz2;
    }
}
