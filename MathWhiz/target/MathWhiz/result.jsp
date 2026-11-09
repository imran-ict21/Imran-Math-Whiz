<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>IMRAN HOSSEN – MATH WHIZ | Result</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <h1 class="title">IMRAN HOSSEN – MATH WHIZ</h1>
    <p class="subtitle">Final Result</p>

    <div class="card">
        <c:if test="${saved == false}">
            <div class="alert warn">
                Your score could not be saved to the database. Check that MySQL is running,
                that <code>math_whiz_db</code> exists and that <code>db.properties</code> is correct.
                <br><small><c:out value="${saveError}"/></small>
            </div>
        </c:if>

        <div class="score-box">
            <div class="score-sub">Player: <strong><c:out value="${playerName}"/></strong></div>
            <div class="score-big">${totalScore} / ${maxScore}</div>
            <div class="score-sub">
                <c:choose>
                    <c:when test="${totalScore == maxScore}">Perfect score! Excellent work!</c:when>
                    <c:when test="${totalScore >= 7}">Great job!</c:when>
                    <c:when test="${totalScore >= 4}">Good effort &mdash; try again for more!</c:when>
                    <c:otherwise>Keep practising &mdash; you can do better!</c:otherwise>
                </c:choose>
            </div>
        </div>

        <div class="table-wrap">
            <table>
                <tr>
                    <th>#</th><th>Question</th><th>Your answer</th><th>Correct answer</th>
                    <th class="center">Marks</th>
                </tr>
                <c:forEach var="r" items="${results}" varStatus="st">
                    <tr>
                        <td>${st.index + 1}</td>
                        <td><c:out value="${r.question.text}"/></td>
                        <td><c:out value="${r.userAnswer}"/></td>
                        <td><c:out value="${r.correctAnswer}"/></td>
                        <td class="center">
                            <span class="${r.correct ? 'ok' : 'no'}">
                                ${r.marksAwarded} / ${r.question.marks} ${r.correct ? '&#10003;' : '&#10007;'}
                            </span>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <div class="actions">
            <a class="btn" href="${pageContext.request.contextPath}/index.jsp">Play Again</a>
            <a class="btn secondary" href="${pageContext.request.contextPath}/leaderboard">View Leaderboard</a>
        </div>
    </div>
    <div class="footer">Java Servlet &amp; JDBC Lab &bull; Developed by Imran Hossen</div>
</div>
</body>
</html>
