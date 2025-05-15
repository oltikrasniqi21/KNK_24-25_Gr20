package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Faq {
    private int id;
    private String question;
    private String answer;

    protected Faq(int id, String question, String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }

    public static Faq getInstance(ResultSet rs) throws SQLException {
        int faq_id = rs.getInt("id");
        String question = rs.getString("question");
        String answer = rs.getString("answer");

        return new Faq(faq_id, question, answer);
    }

    public int getFaq_id() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
