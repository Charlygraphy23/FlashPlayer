public class FlashCard {
    private String question;
    private String answar;

    public FlashCard(String question, String answar) {
        this.question = question;
        this.answar = answar;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswar() {
        return answar;
    }

    public void setAnswar(String answar) {
        this.answar = answar;
    }
}
