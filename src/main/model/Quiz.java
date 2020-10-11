package model;

public class Quiz {
    private String name;
    private QuestionList questionList;

    public Quiz(String name) {
        this.name = name;
        questionList = new QuestionList();
    }

    // EFFECTS: obtains the name of the quiz
    public String getName() {
        return name;
    }

    // EFFECTS: obtains the answer list residing within the quiz
    public QuestionList getQuestionList() {
        return questionList;
    }
}
