package service;

import model.Question;
import model.QuestionResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/** TASK 06 - Game logic: question generation, answer validation, scoring. */
public class GameService {

    public static final String DIFFICULTY = "NORMAL";
    public static final int MAX_SCORE = 10;

    // Angles for sin/cos and for tan (90 and 270 are excluded because tan is undefined there)
    private static final int[] SIN_COS_ANGLES = {0, 30, 45, 60, 90, 120, 135, 150, 180, 210, 225, 240, 270, 300, 315, 330, 360};
    private static final int[] TAN_ANGLES     = {0, 30, 45, 60, 120, 135, 150, 180, 210, 225, 240, 300, 315, 330, 360};

    private final Random random = new Random();

    /** Generates 4 questions: 1 prime, 1 modulus and 2 different trigonometric ones. */
    public List<Question> generateQuestions() {
        List<Question> list = new ArrayList<>();

        // Q1 - prime (1..60, so both primes and non-primes appear, 1 is possible too)
        list.add(Question.prime(1 + random.nextInt(60)));

        // Q2 - modulus (integers only, divisor 2..15 -> never zero)
        list.add(Question.mod(10 + random.nextInt(90), 2 + random.nextInt(14)));

        // Q3 & Q4 - two different trigonometric functions
        List<Question.Type> trig = new ArrayList<>(Arrays.asList(Question.Type.SIN, Question.Type.COS, Question.Type.TAN));
        Collections.shuffle(trig, random);
        for (int i = 0; i < 2; i++) {
            Question.Type t = trig.get(i);
            int[] angles = (t == Question.Type.TAN) ? TAN_ANGLES : SIN_COS_ANGLES;
            list.add(Question.trig(t, angles[random.nextInt(angles.length)]));
        }
        return list;
    }

    /** @return null if all answers are valid, otherwise an error message. */
    public String validateAnswers(List<Question> questions, String[] answers) {
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            String a = (answers[i] == null) ? "" : answers[i].trim();
            int n = i + 1;
            if (a.isEmpty()) {
                return "Question " + n + ": please enter an answer.";
            }
            try {
                if (q.getType() == Question.Type.PRIME) {
                    int v = Integer.parseInt(a);
                    if (v != 0 && v != 1) {
                        return "Question " + n + ": enter 1 (prime) or 0 (not prime).";
                    }
                } else if (q.getType() == Question.Type.MOD) {
                    Integer.parseInt(a);
                } else {
                    double d = parseDecimal(a);
                    if (Double.isNaN(d) || Double.isInfinite(d)) {
                        return "Question " + n + ": enter a valid number.";
                    }
                }
            } catch (NumberFormatException e) {
                return "Question " + n + ": '" + a + "' is not a valid "
                        + (q.isTrig() ? "number." : "integer.");
            }
        }
        return null;
    }

    /** Evaluates every answer using the Java methods of MathService. Call validateAnswers() first. */
    public List<QuestionResult> evaluate(List<Question> questions, String[] answers) {
        List<QuestionResult> results = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            String user = answers[i].trim();
            boolean correct;
            String expectedText;

            switch (q.getType()) {
                case PRIME: {
                    int expected = MathService.isPrime(q.getA());
                    correct = Integer.parseInt(user) == expected;
                    expectedText = String.valueOf(expected);
                    break;
                }
                case MOD: {
                    int expected = MathService.calculateMod(q.getA(), q.getB());
                    correct = Integer.parseInt(user) == expected;
                    expectedText = String.valueOf(expected);
                    break;
                }
                default: {
                    double expected;
                    if (q.getType() == Question.Type.SIN)      expected = MathService.calculateSin(q.getDegree());
                    else if (q.getType() == Question.Type.COS) expected = MathService.calculateCos(q.getDegree());
                    else                                        expected = MathService.calculateTan(q.getDegree());
                    correct = MathService.isClose(expected, parseDecimal(user));
                    expectedText = String.format(Locale.US, "%.3f", expected + 0.0); // +0.0 avoids "-0.000"
                    break;
                }
            }
            results.add(new QuestionResult(q, user, expectedText, correct, correct ? q.getMarks() : 0));
        }
        return results;
    }

    /** Total score = sum of marks of the correct answers (maximum 10). */
    public int totalScore(List<QuestionResult> results) {
        int totalScore = 0;
        for (QuestionResult r : results) {
            totalScore += r.getMarksAwarded();
        }
        return totalScore;
    }

    private static double parseDecimal(String s) {
        return Double.parseDouble(s.replace(',', '.'));
    }
}
