package model;

import org.json.JSONObject;

import java.util.Objects;
// Represents an answer within a question
public class Answer {
    private String ans;
    private Boolean valid;

    // EFFECTS: constructs an Answer initially false for a question
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

    // EFFECTS: converts answer into JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("answer", ans);
        json.put("boolean", valid);
        return json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Answer answer = (Answer) o;
        return ans.equals(answer.ans)
                && valid.equals(answer.valid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ans, valid);
    }
}
