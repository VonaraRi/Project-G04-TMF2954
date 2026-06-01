//creator:Rosfanida 106171

// Quiz interface acts as a contract or rule.
// Any class that implements this interface must provide the body for all methods below.
public interface Quiz {

    // This method is used to add a question into the quiz list.
    void addQuestion(Question question);

    // This method is used to check whether the user's answer is correct or wrong.
    boolean evaluateAnswer(Question question, String userAnswer);

    // This method is used to start the quiz.
    void startQuiz();
}