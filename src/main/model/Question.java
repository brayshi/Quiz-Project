package model;

public class Question {
    private String ques;
    private AnswerList answerList;

    public Question(String q) {
        this.ques = q;
        answerList = new AnswerList();
    }

    // EFFECTS: returns the question string
    public String getQues() {
        return ques;
    }

    // EFFECTS: returns the list of answers
    public AnswerList getAnswerList() {
        return answerList;
    }
}
