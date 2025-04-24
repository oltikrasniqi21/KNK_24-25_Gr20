package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Faq {
    private int faq_id;
    private String question;
    private String answer;

    protected Faq(int faq_id, String question, String answer) {
        this.faq_id = faq_id;
        this.question = question;
        this.answer = answer;
    }

    public static Faq getInstance(ResultSet rs) throws SQLException {
        int faq_id = rs.getInt("faq_id");
        String question = rs.getString("question");
        String answer = rs.getString("answer");

        return new Faq(faq_id, question, answer);
    }

    public int getFaq_id() {
        return faq_id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
