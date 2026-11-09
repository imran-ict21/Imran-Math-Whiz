<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>IMRAN HOSSEN – MATH WHIZ | Leaderboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <h1 class="title">IMRAN HOSSEN – MATH WHIZ</h1>
    <p class="subtitle">Top 10 Leaderboard</p>

    <div class="card">
        <c:if test="${not empty error}">
            <div class="alert error"><c:out value="${error}"/></div>
        </c:if>

        <div class="table-wrap">
            <table>
                <tr>
                    <th class="center">Rank</th><th>Player</th><th class="center">Score</th>
                    <th>Difficulty</th><th>Date / Time</th>
                </tr>
                <c:forEach var="s" items="${scores}" varStatus="st">
                    <tr<c:if test="${st.count le 3}"> class="rank-${st.count}"</c:if>>
                        <td class="center">${st.count}</td>
                        <td><c:out value="${s.playerName}"/></td>
                        <td class="center"><strong>${s.score}</strong> / 10</td>
                        <td><c:out value="${s.difficulty}"/></td>
                        <td><fmt:formatDate value="${s.playedAt}" pattern="dd MMM yyyy, hh:mm a"/></td>
                    </tr>
                </c:forEach>
                <c:if test="${empty scores}">
                    <tr><td colspan="5" class="center">No scores yet &mdash; be the first to play!</td></tr>
                </c:if>
            </table>
        </div>

        <div class="actions">
            <a class="btn" href="${pageContext.request.contextPath}/index.jsp">Play Game</a>
        </div>
    </div>
    <div class="footer">Equal scores are ranked by earlier date/time &bull; Developed by Imran Hossen</div>
</div>
</body>
</html>
