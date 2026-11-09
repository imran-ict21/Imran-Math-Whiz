package dao;

import model.GameScore;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/** TASK 07 & 08 - JDBC data access for the game_scores table (PreparedStatement only). */
public class ScoreDAO {

    private static final String INSERT_SQL =
            "INSERT INTO game_scores (player_name, score, difficulty, played_at) VALUES (?, ?, ?, ?)";

    private static final String TOP_SQL =
            "SELECT id, player_name, score, difficulty, played_at FROM game_scores " +
            "ORDER BY score DESC, played_at ASC LIMIT ?";

    /** INSERT one score (player name, score, difficulty and current date/time). */
    public void saveScore(GameScore gs) throws SQLException {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {
            ps.setString(1, gs.getPlayerName());
            ps.setInt(2, gs.getScore());
            ps.setString(3, gs.getDifficulty());
            ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            ps.executeUpdate();
        }
    }

    /** SELECT the highest scores: score DESC, then earlier date/time first for equal scores. */
    public List<GameScore> getTopScores(int limit) throws SQLException {
        List<GameScore> list = new ArrayList<>();
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(TOP_SQL)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    GameScore gs = new GameScore();
                    gs.setId(rs.getInt("id"));
                    gs.setPlayerName(rs.getString("player_name"));
                    gs.setScore(rs.getInt("score"));
                    gs.setDifficulty(rs.getString("difficulty"));
                    gs.setPlayedAt(rs.getTimestamp("played_at"));
                    list.add(gs);
                }
            }
        }
        return list;
    }
}
