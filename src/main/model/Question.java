package model;

import exceptions.MultipleValidException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Represents a Question that is part of a Quiz
public class Question {
    private String ques;
    private List<Answer> answerList;

    // EFFECTS: constructs a question with a list of answers
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
    public void addAnswer(Answer ans) throws MultipleValidException {
        if (!answerList.contains(ans)) {
            for (Answer answer : answerList) {
                if (answer.isValid() && ans.isValid()) {
                    throw new MultipleValidException();
                }
            }
            answerList.add(ans);
        }
    }

    // EFFECTS: returns the size of the list
    public int listSize() {
        return answerList.size();
    }

    // EFFECTS: converts question into JSON object
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

    // MODIFIES: quizList
    // EFFECTS: converts Json array's objects into answers
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
            try {
                question.addAnswer(nextAnswer);
            } catch (MultipleValidException e) {
                System.out.println("ERROR: question's answers have too many valid choices");
            }
        }
    }
}
