package model;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private String ques;
    private List<Answer> answerList;

    public Question(String q) {
        this.ques = q;
        answerList = new ArrayList<>();
    }

    // EFFECTS: returns the question string
    public String getQues() {
        return ques;
    }

    //EFFECTS: gets an answer with index i from the answer list
    public Answer getAnswer(int i) {
        return answerList.get(i);
    }

    // MODIFIES: this
    // EFFECTS: adds an answer to the list that hasn't been added
    public void addAnswer(Answer ans) {
        if (!answerList.contains(ans)) {
            answerList.add(ans);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes an answer from the list
    public void removeAnswer(Answer ans) {
        answerList.remove(ans);
    }

    // EFFECTS: returns the size of the list
    public int listSize() {
        return answerList.size();
    }
}
