package model;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private String name;
    private List<Question> questionList;

    public Quiz(String name) {
        this.name = name;
        questionList = new ArrayList<>();
    }

    // EFFECTS: obtains the name of the quiz
    public String getName() {
        return name;
    }

    // EFFECTS: returns a question from the list with index int i
    public Question getQuestion(int i) {
        return questionList.get(i);
    }

    // MODIFIES: this
    // EFFECTS: adds a question to the list that hasn't been added
    public void addQuestion(Question question) {
        if (!questionList.contains(question)) {
            questionList.add(question);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes a question from the list
    public void removeQuestion(Question question) {
        questionList.remove(question);
    }

    // EFFECTS: returns the size of the list
    public int listSize() {
        return questionList.size();
    }
}
