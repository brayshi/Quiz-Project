package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Represents a quiz to be added to the quiz list in the application
public class Quiz {
    private String name;
    private List<Question> questionList;

    // EFFECTS: constructs a quiz with a name and list of question
    public Quiz(String name) {
        this.name = name;
        questionList = new ArrayList<>();
    }

    // EFFECTS: obtains the name of the quiz
    public String getName() {
        return name;
    }

    // EFFECTS: sets the name of the quiz
    public void setName(String name) {
        this.name = name;
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

    // EFFECTS: returns the size of the list
    public int listSize() {
        return questionList.size();
    }

    // MODIFIES: quizList
    // EFFECTS: converts JSON array's objects into questions
    public void fromJson(Quiz quiz, JSONArray jsonArray) {
        JSONObject questionObject;
        for (int i = 0; i < jsonArray.length(); i++) {
            questionObject = jsonArray.getJSONObject(i);
            String question = questionObject.getString("question");
            JSONArray nextArray = questionObject.getJSONArray("answers");
            Question nextQuestion = new Question(question);
            nextQuestion.fromJson(nextQuestion, nextArray);
            quiz.addQuestion(nextQuestion);
        }
    }

    // EFFECTS: converts quiz into JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("quiz", name);
        json.put("questions", questionsToJson());
        return json;
    }

    // EFFECTS: returns questions in this quiz List as a JSON array
    private JSONArray questionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Question ques: questionList) {
            jsonArray.put(ques.toJson());
        }

        return jsonArray;
    }
}
