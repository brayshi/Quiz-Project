package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Writable implementation mostly from JsonSerializationDemo

// Represents a list of quizzes within the application
public class QuizList implements Writable {
    private String name;
    private List<Quiz> quizzes;

    // EFFECTS: constructs a list of quizzes with a name
    public QuizList(String name) {
        this.name = name;
        quizzes = new ArrayList<>();
    }

    // EFFECTS: getter for quiz list name
    public String getName() {
        return name;
    }

    // EFFECTS: getter for quizzes
    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    // EFFECTS: returns an unmodifiable list of quizzes from this quizList
    public List<Quiz> getUnmodifiedQuizzes() {
        return Collections.unmodifiableList(quizzes);
    }

    // EFFECT: getter for quiz within quiz list
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

    // EFFECTS: converts quiz list to json file
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("quizzes", quizzesToJson());
        return json;
    }

    // EFFECT: returns quizzes in this quiz list as a JSON array
    private JSONArray quizzesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Quiz quiz: quizzes) {
            jsonArray.put(quiz.toJson());
        }

        return jsonArray;
    }

    // MODIFIES: quizList
    // EFFECTS: Converts JSON array's objects into quizzes
    public void fromJson(QuizList quizList, JSONArray jsonArray) {
        JSONObject quizObject;
        for (int i = 0; i < jsonArray.length(); i++) {
            quizObject = jsonArray.getJSONObject(i);
            String quiz = quizObject.getString("quiz");
            JSONArray nextArray = quizObject.getJSONArray("questions");
            Quiz nextQuiz = new Quiz(quiz);
            nextQuiz.fromJson(nextQuiz, nextArray);
            quizList.addQuiz(nextQuiz);
        }
    }
}
