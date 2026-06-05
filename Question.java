/**
 * Class Name: Question
 * Description: This class stores the details of a quiz question, including
 * the question text, correct answer, and question type.
 *
 * Creator: Rosfanida ak Benjamin 106171
 * Tester: Sabrina Natasha
 */
public class Question {
    
    //Question class is used to store information for each question

    //these artibutes are private to protect the data.
    private String  questionText;
    private String answer;
    private QuestionType questionType;

    //Constructor is used to create a Question object.
    //each question must have questionText, answer and questionType.
    public Question(String questionText, String answer, QuestionType questionType) {
        this.questionText = questionText;
        this.answer = answer;
        this.questionType = questionType;
    }
    //getter method to access the question text
    public String getQuestionText() {
        return questionText;
    }

    //getter method to get the correct answer.
    public String getAnswer(){
        return answer;

    }

    //getter method to get the question type.
    public QuestionType getQuestionType() {
        return questionType;
    }   
}
