package model;

import java.util.ArrayList;
import java.util.List;

public class QuizList {
    private List<Quiz> quizzes;

    public QuizList() {
        quizzes = new ArrayList<>();
    }

    public Quiz getQuiz(int i) {
        return quizzes.get(i);
    }

    // MODIFIES: this
    // EFFECTS: adds a quiz to the list of quizzes if quiz isn't already present within the list
    public void addQuiz(Quiz q) {
        if (!quizzes.contains(q)) {
            quizzes.add(q);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes a quiz from the list of quizzes
    public void removeQuiz(Quiz q) {
        quizzes.remove(q);
    }

    // EFFECTS: returns the quiz list size
    public int listSize() {
        return quizzes.size();
    }
}
