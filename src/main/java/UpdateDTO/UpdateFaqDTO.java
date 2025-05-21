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

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

}