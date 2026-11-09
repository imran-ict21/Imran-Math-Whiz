package controller;

import dao.ScoreDAO;
import model.GameScore;
import model.Question;
import model.QuestionResult;
import service.GameService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Front controller of the game.
 *   GET  /game         -> show the question page
 *   POST /game         -> action=start  (new game)  |  action=submit (evaluate answers)
 *   GET  /leaderboard  -> show the top 10 scores
 */
@WebServlet(name = "GameServlet", urlPatterns = {"/game", "/leaderboard"})
public class GameServlet extends HttpServlet {

    private final GameService gameService = new GameService();
    private final ScoreDAO scoreDAO = new ScoreDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("/leaderboard".equals(req.getServletPath())) {
            showLeaderboard(req, resp);
            return;
        }
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("questions") == null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }
        req.getRequestDispatcher("/game.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if ("start".equals(action)) {
            startGame(req, resp);
        } else if ("submit".equals(action)) {
            submitAnswers(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }

    // ---------------------------------------------------------------- start
    private void startGame(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("playerName");
        name = (name == null) ? "" : name.trim();

        if (name.isEmpty()) {
            req.setAttribute("error", "Please enter your name to start the game.");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }
        if (name.length() > 100) {
            req.setAttribute("error", "Name must be 100 characters or less.");
            req.setAttribute("playerName", name);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession(true);
        session.setAttribute("playerName", name);
        session.setAttribute("questions", gameService.generateQuestions());
        resp.sendRedirect(req.getContextPath() + "/game");
    }

    // --------------------------------------------------------------- submit
    @SuppressWarnings("unchecked")
    private void submitAnswers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        List<Question> questions = (session == null) ? null : (List<Question>) session.getAttribute("questions");
        if (questions == null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        String[] answers = new String[questions.size()];
        for (int i = 0; i < answers.length; i++) {
            String a = req.getParameter("answer" + i);
            answers[i] = (a == null) ? "" : a.trim();
        }

        // Server-side validation (never trust the browser)
        String error = gameService.validateAnswers(questions, answers);
        if (error != null) {
            req.setAttribute("error", error);
            req.setAttribute("answers", answers);
            req.getRequestDispatcher("/game.jsp").forward(req, resp);
            return;
        }

        // Evaluate with the real Java methods and calculate the score
        List<QuestionResult> results = gameService.evaluate(questions, answers);
        int totalScore = gameService.totalScore(results);
        String playerName = (String) session.getAttribute("playerName");

        // Save to MySQL through JDBC
        try {
            scoreDAO.saveScore(new GameScore(playerName, totalScore, GameService.DIFFICULTY));
            req.setAttribute("saved", Boolean.TRUE);
        } catch (SQLException e) {
            log("Could not save score", e);
            req.setAttribute("saved", Boolean.FALSE);
            req.setAttribute("saveError", e.getMessage());
        }

        session.removeAttribute("questions");   // prevents re-submitting the same game
        req.setAttribute("playerName", playerName);
        req.setAttribute("results", results);
        req.setAttribute("totalScore", totalScore);
        req.setAttribute("maxScore", GameService.MAX_SCORE);
        req.getRequestDispatcher("/result.jsp").forward(req, resp);
    }

    // ---------------------------------------------------------- leaderboard
    private void showLeaderboard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<GameScore> scores;
        try {
            scores = scoreDAO.getTopScores(10);
        } catch (SQLException e) {
            log("Could not load leaderboard", e);
            scores = Collections.emptyList();
            req.setAttribute("error", "Could not load the leaderboard: " + e.getMessage());
        }
        req.setAttribute("scores", scores);
        req.getRequestDispatcher("/leaderboard.jsp").forward(req, resp);
    }
}
