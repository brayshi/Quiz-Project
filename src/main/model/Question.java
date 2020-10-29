package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("question", ques);
        json.put("answers", answersToJson());
        return json;
    }

    // EFFECTS: returns answers in this quizList as JSON array
    private JSONArray answersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Answer answer: answerList) {
            jsonArray.put(answer.toJson());
        }
        return jsonArray;
    }

    public void fromJson(Question question, JSONArray jsonArray) {
        JSONObject answerObject;

        for (int i = 0; i < jsonArray.length(); i++) {

            answerObject = jsonArray.getJSONObject(i);
            String answer = answerObject.getString("answer");
            Answer nextAnswer = new Answer(answer);
            Boolean checkValid = answerObject.getBoolean("boolean");

            if (checkValid) {
                nextAnswer.setValid();
            }
            question.addAnswer(nextAnswer);
        }
    }
}
