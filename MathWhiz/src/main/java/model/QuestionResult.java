package model;

/** Evaluation result of a single question (used by result.jsp). */
public class QuestionResult {

    private final Question question;
    private final String userAnswer;
    private final String correctAnswer;
    private final boolean correct;
    private final int marksAwarded;

    public QuestionResult(Question question, String userAnswer, String correctAnswer,
                          boolean correct, int marksAwarded) {
        this.question = question;
        this.userAnswer = userAnswer;
        this.correctAnswer = correctAnswer;
        this.correct = correct;
        this.marksAwarded = marksAwarded;
    }

    public Question getQuestion()   { return question; }
    public String getUserAnswer()   { return userAnswer; }
    public String getCorrectAnswer(){ return correctAnswer; }
    public boolean isCorrect()      { return correct; }
    public int getMarksAwarded()    { return marksAwarded; }
}
