package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    // EFFECTS: obtains the question list
    public List<Question> getQuestionList() {
        return questionList;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quiz quiz = (Quiz) o;
        return name.equals(quiz.name) &&
                questionList.equals(quiz.questionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, questionList);
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
