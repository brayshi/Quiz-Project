package model;

import org.json.JSONObject;

import java.util.Objects;

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
    public Boolean isValid() {
        return valid;
    }

    // MODIFIES: this
    // EFFECTS: sets the validity of the answer to true
    public void setValid() {
        valid = true;
    }

    // MODIFIES: this
    // EFFECTS: sets the validity of the answer to false
    public void setInvalid() {
        valid = false;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("answer", ans);
        json.put("boolean", valid);
        return json;
    }
}
