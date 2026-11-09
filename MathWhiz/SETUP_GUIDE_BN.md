# 🧮 IMRAN HOSSEN – MATH WHIZ : সেটআপ গাইড (বাংলা)

এই গাইড ধাপে ধাপে দেখাবে কীভাবে IntelliJ IDEA + Tomcat + MySQL দিয়ে প্রজেক্টটি চালাবে।

## ধাপ ১: প্রয়োজনীয় সফটওয়্যার (Software Requirements)
- JDK 11 বা তার উপরে (JDK 17 হলে ভালো)
- IntelliJ IDEA
- **Apache Tomcat 9** (অবশ্যই ৯ — ১০ বা ১১ নয়, কারণ প্রজেক্টে `javax.servlet` ব্যবহার হয়েছে)
- MySQL Server 8 + MySQL Workbench

## ধাপ ২: ডাটাবেস তৈরি (Create Database)
1. MySQL Workbench খোলো এবং connect করো।
2. `database/math_whiz_db.sql` ফাইলটি খুলে **Run (⚡)** দাও।
3. এতে `math_whiz_db` ডাটাবেস এবং `game_scores` টেবিল তৈরি হবে।

## ধাপ ৩: পাসওয়ার্ড বসাও
`src/main/resources/db.properties` ফাইলে `db.password=` এর পরে **তোমার MySQL পাসওয়ার্ড** লেখো।

## ধাপ ৪: IntelliJ-তে প্রজেক্ট ওপেন
`File → Open` → `MathWhiz` ফোল্ডার (যেখানে `pom.xml` আছে) সিলেক্ট করো → **Open as Project**।
Maven নিজে থেকে dependency (MySQL driver, JSTL) ডাউনলোড করবে — একটু অপেক্ষা করো।

## ধাপ ৫: Tomcat কনফিগার
1. উপরে ডানে `Add Configuration…` → `+` → **Tomcat Server → Local**
2. **Application server → Configure…** থেকে তোমার Tomcat 9 ফোল্ডার দাও।
3. **Deployment** ট্যাব → `+` → **Artifact → MathWhiz:war exploded**
4. **Application context** = `/MathWhiz`
5. Apply → OK

> IntelliJ **Community** হলে Tomcat অপশন থাকে না — Settings → Plugins থেকে **Smart Tomcat** ইনস্টল করো।

## ধাপ ৬: রান করো
▶ Run চাপো, তারপর ব্রাউজারে যাও: **http://localhost:8080/MathWhiz/**

## ধাপ ৭: স্ক্রিনশট নাও (README-র জন্য)
চারটি পেজের স্ক্রিনশট নিয়ে `screenshots/` ফোল্ডারে এই নামে সেভ করো:
`home.png`, `game.png`, `result.png`, `leaderboard.png`

## ধাপ ৮: GitHub-এ আপলোড
```bash
git init
git add .
git commit -m "Add IMRAN HOSSEN - MATH WHIZ servlet jdbc project"
git branch -M main
git remote add origin https://github.com/<username>/<repository>.git
git push -u origin main
```
সাবমিশন লিংক (সাবডিরেক্টরি হলে):
`https://github.com/<username>/<repository>/tree/main/MathWhiz`

## কোড কীভাবে কাজ করে (সংক্ষেপে)
| ফাইল | কাজ |
|------|-----|
| `MathService.java` | `isPrime`, `calculateMod`, `calculateSin/Cos/Tan` — সব উত্তর এখানে হিসাব হয় |
| `GameService.java` | ৪টি প্রশ্ন বানানো, ইনপুট যাচাই, স্কোর হিসাব (২+২+৩+৩ = ১০) |
| `GameServlet.java` | Start / Submit / Leaderboard রিকুয়েস্ট হ্যান্ডেল করে |
| `ScoreDAO.java` | JDBC `PreparedStatement` দিয়ে INSERT ও Top-10 SELECT |
| `DBUtil.java` | ডাটাবেস কানেকশন তৈরি করে |
| `*.jsp` | ৪টি স্ক্রিন: Welcome, Game, Result, Leaderboard |

## সমস্যা হলে
- **Access denied / Communications link failure** → MySQL চালু আছে কিনা এবং `db.properties`-এর পাসওয়ার্ড ঠিক আছে কিনা দেখো।
- **404** → context path `/MathWhiz` এবং artifact `war exploded` ঠিক আছে কিনা দেখো।
- **javax/jakarta error** → Tomcat 9 ব্যবহার করো।
- **Taglib/JSTL error** → Maven → Reload project, তারপর আবার রান করো।
