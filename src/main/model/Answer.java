package model;

public class Answer {
    private String ans;
    private Boolean valid;

    // EFFECTS: sets up an Answer
    public Answer(String ans) {
        this.ans = ans;
        valid = false;
    }

    // EFFECTS: returns the answer string
    public String getStr() {
        return ans;
    }

    // EFFECTS: returns the boolean value of valid
    public Boolean getValid() {
        return valid;
    }

    // MODIFIES: this
    // EFFECTS: sets the validity of the answer to true
    public void setValid() {
        valid = true;
    }
}
