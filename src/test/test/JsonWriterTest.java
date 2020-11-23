package test;

import exceptions.MultipleValidException;
import model.Answer;
import model.Question;
import model.Quiz;
import model.QuizList;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            QuizList quizList = new QuizList("My Quiz List");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            QuizList quizList = new QuizList("My Quiz List");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyQuizList.json");
            writer.open();
            writer.write(quizList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyQuizList.json");
            quizList = reader.read();
            assertEquals("My Quiz List", quizList.getName());
            assertEquals(0, quizList.listSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            QuizList quizList = new QuizList("My Quiz List");

            quizList.addQuiz(new Quiz("demo"));
            quizList.getQuiz(0).addQuestion(new Question("choose 'yes'"));
            try {
                quizList.getQuiz(0).getQuestion(0).addAnswer(new Answer("yes"));
            } catch (MultipleValidException e) {
                System.out.println("failed to add new answer: Question already had one valid answer");
            }
            quizList.getQuiz(0).getQuestion(0).getAnswer(0).setValid();
            QuizList oldQuizList = quizList;

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralQuizList.json");
            writer.open();
            writer.write(quizList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralQuizList.json");
            quizList = reader.read();
            assertEquals("My Quiz List", quizList.getName());
            List<Quiz> quizzes = quizList.getUnmodifiedQuizzes();
            assertEquals(1, quizzes.size());
            checkQuiz("demo", oldQuizList.getQuiz(0) , quizzes.get(0));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
