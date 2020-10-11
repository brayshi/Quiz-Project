package model;

import java.util.ArrayList;
import java.util.List;

public class AnswerList {
    private List<Answer> ansList;

    public AnswerList() {
        ansList = new ArrayList<>();
    }

    //EFFECTS: gets an answer with index i from the answer list
    public Answer getAnswer(int i) {
        return ansList.get(i);
    }

    // MODIFIES: this
    // EFFECTS: adds an answer to the list that hasn't been added
    public void addAnswer(Answer ans) {
        if (!ansList.contains(ans)) {
            ansList.add(ans);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes an answer from the list
    public void removeAnswer(Answer ans) {
        ansList.remove(ans);
    }

    // EFFECTS: returns the size of the list
    public int listSize() {
        return ansList.size();
    }
}
