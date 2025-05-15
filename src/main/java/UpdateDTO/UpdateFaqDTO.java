package UpdateDTO;

public class UpdateFaqDTO {
    private int id;
    private String question;
    private String answer;

    public UpdateFaqDTO(int id, String question, String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }

    public int getFaqId() {
        return id;
    }

    public void setFaqId(int faqId) {
        this.id = faqId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}