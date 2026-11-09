# 🧮 IMRAN HOSSEN – MATH WHIZ

> A web-based mathematical game built with **Java Servlet, JSP and JDBC (MySQL)**.
> The player answers four math questions (prime number, modulus and two trigonometry questions),
> gets a score out of **10**, and the result is saved to a database and shown in a **Top 10 leaderboard**.

**Author:** Imran Hossen &nbsp;|&nbsp; **ID:** IT24063 &nbsp;|&nbsp; Dept. of ICT, Mawlana Bhashani Science & Technology University (MBSTU)
**Course work:** Java Web Application Development – Lab Practice (Java Servlet & JDBC)

---

## 📑 Table of Contents
1. [Project Description](#-project-description)
2. [Project Objectives](#-project-objectives)
3. [Technologies Used](#-technologies-used)
4. [Software Requirements](#-software-requirements)
5. [Project Structure](#-project-structure)
6. [Database Configuration](#-database-configuration)
7. [Database Creation SQL](#-database-creation-sql)
8. [Steps to Run the Application](#-steps-to-run-the-application)
9. [Game Rules & Scoring System](#-game-rules--scoring-system)
10. [Mathematical Methods](#-mathematical-methods)
11. [Testing & Validation](#-testing--validation)
12. [Application Screenshots](#-application-screenshots)
13. [Troubleshooting](#-troubleshooting)
14. [GitHub Project Directory Link](#-github-project-directory-link)

---

## 📖 Project Description

**IMRAN HOSSEN – MATH WHIZ** is a quiz-style web game. A player enters a name, presses **Start Game**
and receives four randomly generated questions:

| Question | Area | Marks |
|----------|------|-------|
| Q1 | Prime Number (answer 1 = prime, 0 = not prime) | 2 |
| Q2 | Modulus (integer operands only) | 2 |
| Q3 | Trigonometric value (sin / cos / tan) | 3 |
| Q4 | Trigonometric value (sin / cos / tan) | 3 |
| | **Total** | **10** |

**Every answer is calculated by real Java methods** (`isPrime`, `calculateMod`, `calculateSin`,
`calculateCos`, `calculateTan`) – no answers are stored or hard-coded. After submitting, the player
sees the final score together with the correct answers. Each result (player name, score, difficulty,
date/time) is saved in MySQL using JDBC `PreparedStatement`, and the **Top 10** scores are displayed
on a leaderboard.

## 🎯 Project Objectives

- Develop reusable Java methods for mathematical calculations.
- Apply conditional statements and decision-making logic.
- Understand Servlet request and response processing (MVC with Servlet + JSP).
- Implement JDBC connectivity and database operations (INSERT / SELECT).
- Use `PreparedStatement` for secure database access (prevents SQL injection).
- Retrieve, sort and display database records (leaderboard).
- Develop, document and submit a complete Java web application through GitHub.

## 🛠 Technologies Used

| Layer | Technology |
|-------|-----------|
| Language | Java 11+ |
| Web | Java Servlet 4.0, JSP 2.3, JSTL 1.2, HTML5, CSS3 |
| Server | Apache Tomcat 9.x |
| Database | MySQL 8.x with MySQL Connector/J 8.4 |
| Build tool | Apache Maven (WAR packaging) |
| IDE | IntelliJ IDEA |
| Testing | JUnit 5 |
| Version control | Git & GitHub |

## 💻 Software Requirements

- **JDK 11 or newer** (JDK 17 recommended)
- **IntelliJ IDEA** (Ultimate has built-in Tomcat support; Community works with the free *Smart Tomcat* plugin)
- **Apache Tomcat 9.x** (important: version 9 – this project uses the `javax.servlet` package)
- **MySQL Server 8.x** and MySQL Workbench (or the `mysql` command line)
- **Maven** (bundled with IntelliJ IDEA)
- **Git** (for GitHub submission)
- A web browser (Chrome, Edge, Firefox)

## 📂 Project Structure

```
MathWhiz/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── controller/GameServlet.java     # Servlet: start game, submit answers, leaderboard
│   │   │   ├── model/GameScore.java            # Database row (id, name, score, difficulty, time)
│   │   │   ├── model/Question.java             # Question parameters (type, numbers, angle, marks)
│   │   │   ├── model/QuestionResult.java       # Evaluation result of one question
│   │   │   ├── service/MathService.java        # isPrime, calculateMod, calculateSin/Cos/Tan
│   │   │   ├── service/GameService.java        # Question generation, validation, scoring
│   │   │   ├── dao/ScoreDAO.java               # JDBC INSERT / SELECT with PreparedStatement
│   │   │   └── util/DBUtil.java                # JDBC connection helper
│   │   ├── resources/db.properties             # Database URL, user and password
│   │   └── webapp/
│   │       ├── index.jsp                       # Screen 1: Welcome page
│   │       ├── game.jsp                        # Screen 2: Game page (4 questions)
│   │       ├── result.jsp                      # Screen 3: Result page
│   │       ├── leaderboard.jsp                 # Screen 4: Top 10 leaderboard
│   │       ├── css/style.css
│   │       └── WEB-INF/web.xml
│   └── test/java/service/MathServiceTest.java  # JUnit tests for the math methods
├── database/math_whiz_db.sql                   # Database creation script
├── screenshots/                                # Application screenshots (used in this README)
├── pom.xml                                     # Maven configuration
├── README.md
└── SETUP_GUIDE_BN.md                           # Step-by-step setup guide in Bangla
```

### Architecture (MVC)

```
Browser ──► GameServlet (Controller) ──► GameService / MathService (Logic)
                 │                                   │
                 │                                   ▼
                 ├──────────────► ScoreDAO ──► MySQL (math_whiz_db.game_scores)
                 ▼
          JSP pages (View)
```

## 🗄 Database Configuration

Open `src/main/resources/db.properties` and set **your own MySQL password**:

```properties
db.url=jdbc:mysql://localhost:3306/math_whiz_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Dhaka
db.user=root
db.password=your_mysql_password
db.driver=com.mysql.cj.jdbc.Driver
```

Optionally the same values can be supplied as environment variables
(`MATHWHIZ_DB_URL`, `MATHWHIZ_DB_USER`, `MATHWHIZ_DB_PASSWORD`), which override the file.

> ⚠️ Do not commit your real password to a public GitHub repository – keep `your_mysql_password`
> in the committed file.

## 🧾 Database Creation SQL

The same script is available in [`database/math_whiz_db.sql`](database/math_whiz_db.sql).

```sql
CREATE DATABASE IF NOT EXISTS math_whiz_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE math_whiz_db;

CREATE TABLE IF NOT EXISTS game_scores (
    id          INT          NOT NULL AUTO_INCREMENT,
    player_name VARCHAR(100) NOT NULL,
    score       INT          NOT NULL,
    difficulty  VARCHAR(20)  NOT NULL DEFAULT 'NORMAL',
    played_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
```

**Leaderboard query** (used by `ScoreDAO.getTopScores`):

```sql
SELECT id, player_name, score, difficulty, played_at
FROM game_scores
ORDER BY score DESC, played_at ASC   -- equal scores: earlier date/time ranks higher
LIMIT 10;
```

## ▶️ Steps to Run the Application

### 1. Clone / download the project
```bash
git clone https://github.com/<your-username>/<your-repository>.git
cd <your-repository>/MathWhiz
```

### 2. Create the database
```bash
mysql -u root -p < database/math_whiz_db.sql
```
(or open `database/math_whiz_db.sql` in MySQL Workbench and run it).

### 3. Configure the connection
Edit `src/main/resources/db.properties` and put your MySQL password.

### 4. Open in IntelliJ IDEA
`File → Open…` → select the **MathWhiz** folder (the one containing `pom.xml`) →
choose **Open as Project** → wait until Maven downloads the dependencies.

### 5. Configure Tomcat
1. `Run → Edit Configurations… → + → Tomcat Server → Local`
   (*Community edition:* install the **Smart Tomcat** plugin and use `+ → Smart Tomcat`).
2. **Application server:** click *Configure…* and select your Tomcat 9 folder.
3. **Deployment tab:** `+ → Artifact → MathWhiz:war exploded`.
4. Set **Application context** to `/MathWhiz`.
5. Click **Apply → OK**.

### 6. Run
Press the green **Run ▶** button, then open:

```
http://localhost:8080/MathWhiz/
```

### Alternative: build a WAR file manually
```bash
mvn clean package
```
Copy `target/MathWhiz.war` into `<TOMCAT_HOME>/webapps/`, start Tomcat (`bin/startup.bat` or
`bin/startup.sh`) and browse to `http://localhost:8080/MathWhiz/`.

### Run the unit tests
```bash
mvn test
```

## 🎮 Game Rules & Scoring System

| Question | Mathematical area | Marks |
|----------|-------------------|-------|
| Q1 | Prime Number (1 = prime, 0 = not prime) | 2 |
| Q2 | Modulus (integer operands only) | 2 |
| Q3 | Trigonometric value (sin / cos / tan) | 3 |
| Q4 | Trigonometric value (sin / cos / tan) | 3 |
| **Total** | | **10** |

- **Prime:** returns `1` for a prime number, otherwise `0`. Numbers less than 2 are not prime.
- **Modulus:** integer operands only; the divisor can never be zero (validated in `calculateMod`).
- **Trigonometry:** angles are given in **degrees** and converted to radians before using
  `Math.sin()`, `Math.cos()` and `Math.tan()`. Decimal answers are accepted within a
  tolerance of **±0.01**, so `0.87` is accepted for `sin(60°) = 0.866`.
- **Tangent:** angles where tan is undefined (90°, 270°…) are never asked, and the method throws
  an `ArithmeticException` if called with them.
- **Difficulty:** `NORMAL` for all questions (stored in the database in the `difficulty` column).
- **Scoring logic:**

```java
int totalScore = 0;
if (primeCorrect) totalScore += 2;
if (modCorrect)   totalScore += 2;
if (trig1Correct) totalScore += 3;
if (trig2Correct) totalScore += 3;      // maximum = 10
```

- **Leaderboard:** top 10 scores, sorted by score (high → low); equal scores are ordered by the
  earlier date/time.

## 🧠 Mathematical Methods

All methods are `public static` in `service/MathService.java`:

```java
public static int    isPrime(int number)            // 1 = prime, 0 = not prime
public static int    calculateMod(int a, int b)     // a % b, throws if b == 0
public static double calculateSin(double degree)    // Math.sin(Math.toRadians(degree))
public static double calculateCos(double degree)    // Math.cos(Math.toRadians(degree))
public static double calculateTan(double degree)    // Math.tan(Math.toRadians(degree)), undefined → exception
```

Mathematical reference: **sin²θ + cos²θ = 1** (x² + y² = 1 on the unit circle) – this identity is
also checked in the unit tests.

## ✅ Testing & Validation

Automated tests are in `MathServiceTest.java` (`mvn test`).

| Test case | Expected result | Status |
|-----------|-----------------|--------|
| Prime input: 7 | 1 | ✅ |
| Prime input: 8 | 0 | ✅ |
| Prime input: 1 | 0 | ✅ |
| Modulus: 17 % 5 | 2 | ✅ |
| Modulus with negatives (−17 % 5) | −2 | ✅ |
| Sine: 30° | 0.5 | ✅ |
| Cosine: 60° | 0.5 | ✅ |
| Tangent: 45° | 1.0 | ✅ |
| Tangent: 90° | Exception (undefined) | ✅ |
| Divisor: 0 | Validation error (`IllegalArgumentException`) | ✅ |
| All answers correct | Maximum 10 marks | ✅ |
| Incorrect answers | Corresponding marks not awarded | ✅ |

**Form validation (server side):** empty name, name longer than 100 characters, empty answers,
non-numeric answers and prime answers other than 0/1 are rejected with a clear message.

## 📸 Application Screenshots

> 📌 Run the project, take the four screenshots and save them in the `screenshots/` folder with
> exactly these file names, then commit them to GitHub.

### Welcome Page
![Welcome Page](screenshots/home.png)

### Game Page
![Game Page](screenshots/game.png)

### Result Page
![Result Page](screenshots/result.png)

### Leaderboard
![Leaderboard](screenshots/leaderboard.png)

## 🧰 Troubleshooting

| Problem | Solution |
|---------|----------|
| `Communications link failure` / `Access denied for user` | Start MySQL and check `db.url`, `db.user`, `db.password` in `db.properties`. |
| `Unknown database 'math_whiz_db'` | Run `database/math_whiz_db.sql` first. |
| HTTP 404 | Check that the context path is `/MathWhiz` and that the artifact `MathWhiz:war exploded` is deployed. |
| `java.lang.ClassNotFoundException: javax.servlet...` / `jakarta.servlet...` errors | Use **Tomcat 9**. Tomcat 10+ needs the `jakarta.*` packages instead of `javax.*`. |
| JSTL / taglib error | Run *Maven → Reload project*, then redeploy so `jstl-1.2.jar` is copied into `WEB-INF/lib`. |
| Score is shown but not saved | The result page shows a yellow warning with the SQL error – fix the DB settings above. |
| Port 8080 already in use | Stop the other program or change the Tomcat HTTP port in the run configuration. |

## 🔗 GitHub Project Directory Link

```
https://github.com/<your-username>/<your-repository>/tree/main/MathWhiz
```

*(Replace `<your-username>` and `<your-repository>` with your own GitHub details.)*

---

### 🙌 Credits
Developed by **Imran Hossen** (ID: IT24063), Dept. of ICT, MBSTU – as part of the Java Servlet & JDBC
lab practice.
