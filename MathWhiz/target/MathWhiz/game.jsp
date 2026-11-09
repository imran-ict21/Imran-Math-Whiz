<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>IMRAN HOSSEN – MATH WHIZ | Game</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <h1 class="title">IMRAN HOSSEN – MATH WHIZ</h1>
    <p class="subtitle">Player: <strong><c:out value="${sessionScope.playerName}"/></strong> &bull; Difficulty: NORMAL</p>

    <div class="card">
        <h2>Answer the 4 questions</h2>

        <c:if test="${not empty error}">
            <div class="alert error"><c:out value="${error}"/></div>
        </c:if>

        <form action="${pageContext.request.contextPath}/game" method="post">
            <input type="hidden" name="action" value="submit">

            <c:forEach var="q" items="${sessionScope.questions}" varStatus="st">
                <div class="question">
                    <div class="q-head">
                        <span class="q-no">Question ${st.index + 1}</span>
                        <span class="badge"><c:out value="${q.topic}"/> &bull; ${q.marks} marks</span>
                    </div>
                    <div class="q-text"><c:out value="${q.text}"/></div>
                    <div class="hint"><c:out value="${q.hint}"/></div>

                    <c:choose>
                        <c:when test="${q.type == 'PRIME'}">
                            <input type="number" name="answer${st.index}" min="0" max="1" step="1"
                                   placeholder="1 or 0" value="<c:out value='${answers[st.index]}'/>" required>
                        </c:when>
                        <c:when test="${q.type == 'MOD'}">
                            <input type="number" name="answer${st.index}" step="1"
                                   placeholder="Integer answer" value="<c:out value='${answers[st.index]}'/>" required>
                        </c:when>
                        <c:otherwise>
                            <input type="number" name="answer${st.index}" step="any"
                                   placeholder="e.g. 0.866" value="<c:out value='${answers[st.index]}'/>" required>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:forEach>

            <div class="actions">
                <button type="submit" class="btn">Submit Answers</button>
                <a class="btn secondary" href="${pageContext.request.contextPath}/index.jsp">Cancel</a>
            </div>
        </form>
    </div>
    <div class="footer">Java Servlet &amp; JDBC Lab &bull; Developed by Imran Hossen</div>
</div>
</body>
</html>
