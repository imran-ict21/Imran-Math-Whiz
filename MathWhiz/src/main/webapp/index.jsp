<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>IMRAN HOSSEN – MATH WHIZ</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <h1 class="title">IMRAN HOSSEN – MATH WHIZ</h1>
    <p class="subtitle">Prime Numbers &bull; Modulus &bull; Trigonometry</p>

    <div class="card">
        <h2>Welcome, challenger!</h2>

        <c:if test="${not empty error}">
            <div class="alert error"><c:out value="${error}"/></div>
        </c:if>

        <form action="${pageContext.request.contextPath}/game" method="post">
            <input type="hidden" name="action" value="start">
            <label for="playerName">Player Name</label>
            <input type="text" id="playerName" name="playerName" maxlength="100"
                   placeholder="Enter your name" value="<c:out value='${playerName}'/>" required autofocus>
            <div class="actions">
                <button type="submit" class="btn">Start Game</button>
                <a class="btn secondary" href="${pageContext.request.contextPath}/leaderboard">View Leaderboard</a>
            </div>
        </form>

        <div class="rules">
            <strong>Game rules</strong>
            <ul>
                <li>4 questions &mdash; maximum score <strong>10</strong> (Q1 = 2, Q2 = 2, Q3 = 3, Q4 = 3)</li>
                <li>Difficulty: <strong>NORMAL</strong></li>
                <li>Trigonometry angles are in <strong>degrees</strong>; decimal answers are accepted with a small tolerance (&plusmn;0.01)</li>
            </ul>
        </div>
    </div>
    <div class="footer">Java Servlet &amp; JDBC Lab &bull; Developed by Imran Hossen</div>
</div>
</body>
</html>
