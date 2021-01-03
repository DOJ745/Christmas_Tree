package by.bstu.faa.christmas_tree.DB.local_db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import by.bstu.faa.christmas_tree.model.UserInfo;
import by.bstu.faa.christmas_tree.model.query.QueryContainer;
import by.bstu.faa.christmas_tree.model.query.TableAnswerContainer;
import by.bstu.faa.christmas_tree.model.query.TableQuestionContainer;
import by.bstu.faa.christmas_tree.model.query.TableThemesContainer;
import by.bstu.faa.christmas_tree.model.question.QuestionContainer;

public class DB_Operations {

    private static String TAG = "DB_OPERATIONS";
    public static class MainOperations {

        public static void createAllTables(SQLiteDatabase db) {
            createThemesTable(db);
            createQuestionsTable(db);
            createAnswersTable(db);
            createUsersTable(db);
        }

        public static void upgradeAllTables(SQLiteDatabase db) {

            db.execSQL("drop table if exists Themes");
            db.execSQL("drop table if exists Questions");
            db.execSQL("drop table if exists Answers");
            createAllTables(db);
        }

        public static void createThemesTable(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE Themes (\n" +
                    "ID INTEGER NOT NULL,\n" +
                    "Name TEXT NOT NULL,\n" +
                    "constraint ID_pk PRIMARY KEY(ID))");

            Queries.insertThemes(db);
        }

        public static void createUsersTable(SQLiteDatabase db) {

            db.execSQL("create table Users (\n" +
                    "ID TEXT not null,\n" +
                    "Nickname TEXT,\n" +
                    "Tree_Level INTEGER not null,\n" +
                    "Score INTEGER not null,\n" +
                    "constraint ID_pk primary key(ID))");
        }

        public static void createQuestionsTable(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE Questions (\n" +
                    "Theme_ID INTEGER NOT NULL,\n" +
                    "ID INTEGER NOT NULL,\n" +
                    "Q_Text TEXT NOT NULL,\n" +
                    "FOREIGN KEY(Theme_ID) REFERENCES Themes(ID) on delete cascade,\n" +
                    "constraint ID_pk PRIMARY KEY(ID))");

            Queries.insertQuestions(db);
        }

        public static void createAnswersTable(SQLiteDatabase db) {

            db.execSQL("" +
                    "CREATE TABLE Answers (\n" +
                    "ID INTEGER NOT NULL,\n" +
                    "Q_ID INTEGER,\n" +
                    "A_Text TEXT NOT NULL,\n" +
                    "Trueness INTEGER NOT NULL CHECK(Trueness = 0  OR Trueness = 1),\n" +
                    "FOREIGN KEY(Q_ID) REFERENCES Questions(ID) on delete cascade,\n" +
                    "constraint ID_pk PRIMARY KEY(ID))");

            Queries.insertAnswers(db);
        }
    }





    public static class Queries {

        public static void insertUser(SQLiteDatabase db, UserInfo user) {

            ContentValues contentValues = new ContentValues();
            long rowId;

            contentValues.put("ID", user.getId());
            contentValues.put("Nickname", user.getName());
            contentValues.put("Tree_Level", user.getTreeLevel());
            contentValues.put("Score", user.getScore());

            rowId = db.insert("Users", null, contentValues);
        }

        public static void insertThemes(SQLiteDatabase db) {

            ContentValues contentValues = new ContentValues();
            long rowId;

            contentValues.put("Name", "Новый год в Беларуси");
            rowId = db.insert("Themes", null, contentValues);
            contentValues.put("Name", "Новый год в США");
            rowId = db.insert("Themes", null, contentValues);
            contentValues.put("Name", "Новый год в Японии");
            rowId = db.insert("Themes", null, contentValues);
        }

        public static void insertQuestions(SQLiteDatabase db) {
            ContentValues contentValues = new ContentValues();
            long rowId;

                // ТЕМА 1

            contentValues.put("Theme_ID", 1);
            contentValues.put("Q_Text", "В языческие времена белорусы отмечали наступление Нового года…");
            rowId = db.insert("Questions", null, contentValues);

            contentValues.put("Theme_ID", 1);
            contentValues.put("Q_Text", "В каком году Новый год в Беларуси стал официальным праздником?");
            rowId = db.insert("Questions", null, contentValues);

            contentValues.put("Theme_ID", 1);
            contentValues.put("Q_Text", "Кто приносит подарки под ёлку в Беларуси?");
            rowId = db.insert("Questions", null, contentValues);

            contentValues.put("Theme_ID", 1);
            contentValues.put("Q_Text", "В каком месте находится резиденция Деда Мороза в Беларуси?");
            rowId = db.insert("Questions", null, contentValues);

            contentValues.put("Theme_ID", 1);
            contentValues.put("Q_Text", "Какой принцип обычно истолковывают во время Нового года в Беларуси?");
            rowId = db.insert("Questions", null, contentValues);

                // ТЕМА 2

            contentValues.put("Theme_ID", 2);
            contentValues.put("Q_Text", "Новый год в США отмечают…");
            rowId = db.insert("Questions", null, contentValues);

            contentValues.put("Theme_ID", 2);
            contentValues.put("Q_Text", "Что не принято делать на Новый год в США?");
            rowId = db.insert("Questions", null, contentValues);

            contentValues.put("Theme_ID", 2);
            contentValues.put("Q_Text", "Традиционный новогодний атрибут в США это…");
            rowId = db.insert("Questions", null, contentValues);

            contentValues.put("Theme_ID", 2);
            contentValues.put("Q_Text", "Когда в США начинаются и заканчиваются новогодние каникулы?");
            rowId = db.insert("Questions", null, contentValues);

            contentValues.put("Theme_ID", 2);
            contentValues.put("Q_Text", "Какие парады проводятся во время Нового года в США?");
            rowId = db.insert("Questions", null, contentValues);

                // ТЕМА 3

            contentValues.put("Theme_ID", 3);
            contentValues.put("Q_Text", "Кто является главным волшебником в Новый год в Японии?");
            rowId = db.insert("Questions", null, contentValues);

            contentValues.put("Theme_ID", 3);
            contentValues.put("Q_Text", "Какую новогоднюю традицию соблюдают дети в Японии?");
            rowId = db.insert("Questions", null, contentValues);

            contentValues.put("Theme_ID", 3);
            contentValues.put("Q_Text", "Как японцы называют новогоднюю ёлку?");
            rowId = db.insert("Questions", null, contentValues);

            contentValues.put("Theme_ID", 3);
            contentValues.put("Q_Text", "Сколько раз бьют в колокола в канун Нового года в Японии?");
            rowId = db.insert("Questions", null, contentValues);

            contentValues.put("Theme_ID", 3);
            contentValues.put("Q_Text", "В последние дни уходящего года японцы дома делают…");
            rowId = db.insert("Questions", null, contentValues);
        }

        public static void insertAnswers(SQLiteDatabase db) {
            ContentValues contentValues = new ContentValues();
            long rowId;

            contentValues.put("Q_ID", 1);
            contentValues.put("A_Text", "1 сентября");
            contentValues.put("Trueness", 1);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 1);
            contentValues.put("A_Text", "29 декабря");
            contentValues.put("Trueness", 0);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 1);
            contentValues.put("A_Text", "1 ноября");
            contentValues.put("Trueness", 0);
            rowId = db.insert("Answers", null, contentValues);


            contentValues.put("Q_ID", 2);
            contentValues.put("A_Text", "1956");
            contentValues.put("Trueness", 0);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 2);
            contentValues.put("A_Text", "1922");
            contentValues.put("Trueness", 0);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 2);
            contentValues.put("A_Text", "1948");
            contentValues.put("Trueness", 1);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 3);
            contentValues.put("A_Text", "Бабайка");
            contentValues.put("Trueness", 0);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 3);
            contentValues.put("A_Text", "Вайнахтсман");
            contentValues.put("Trueness", 0);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 3);
            contentValues.put("A_Text", "Дед Мороз");
            contentValues.put("Trueness", 1);
            rowId = db.insert("Answers", null, contentValues);


            contentValues.put("Q_ID", 4);
            contentValues.put("A_Text", "В Бобруйске");
            contentValues.put("Trueness", 0);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 4);
            contentValues.put("A_Text", "В Беловежской Пуще");
            contentValues.put("Trueness", 1);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 4);
            contentValues.put("A_Text", "В Гомеле");
            contentValues.put("Trueness", 0);
            rowId = db.insert("Answers", null, contentValues);


            contentValues.put("Q_ID", 5);
            contentValues.put("A_Text", "Как Новый год встретишь, так его и проведёшь");
            contentValues.put("Trueness", 1);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 5);
            contentValues.put("A_Text", "Как Новый год встретишь, так и старый вернётся");
            contentValues.put("Trueness", 0);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 5);
            contentValues.put("A_Text", "Как Новый год встретишь, так и оставшиеся проведёшь");
            contentValues.put("Trueness", 0);
            rowId = db.insert("Answers", null, contentValues);


            contentValues.put("Q_ID", 6);
            contentValues.put("A_Text", "В кругу семьи и близких");
            contentValues.put("Trueness", 0);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 6);
            contentValues.put("A_Text", "В публичных местах (на вечеринках, карнавалах)");
            contentValues.put("Trueness", 1);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 6);
            contentValues.put("A_Text", "В гордом одиночестве");
            contentValues.put("Trueness", 0);
            rowId = db.insert("Answers", null, contentValues);


            contentValues.put("Q_ID", 7);
            contentValues.put("A_Text", "Веселиться");
            contentValues.put("Trueness", 0);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 7);
            contentValues.put("A_Text", "Находиться в семейном кругу");
            contentValues.put("Trueness", 0);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 7);
            contentValues.put("A_Text", "Дарить подарки");
            contentValues.put("Trueness", 1);
            rowId = db.insert("Answers", null, contentValues);



            contentValues.put("Q_ID", 8);
            contentValues.put("A_Text", "Бэби (растёт и стареет в течении года)");
            contentValues.put("Trueness", 1);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 8);
            contentValues.put("A_Text", "Грузовик с Coca-Cola");
            contentValues.put("Trueness", 0);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 8);
            contentValues.put("A_Text", "Ёлка на Тайм-Сквер");
            contentValues.put("Trueness", 0);
            rowId = db.insert("Answers", null, contentValues);


            contentValues.put("Q_ID", 9);
            contentValues.put("A_Text", "С 23 декабря до 5 января");
            contentValues.put("Trueness", 0);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 9);
            contentValues.put("A_Text", "С 23 декабря до 7 января");
            contentValues.put("Trueness", 0);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 9);
            contentValues.put("A_Text", "С 23 декабря до 3 января");
            contentValues.put("Trueness", 1);
            rowId = db.insert("Answers", null, contentValues);


            contentValues.put("Q_ID", 10);
            contentValues.put("A_Text", "Парад Санта-Клаусов и Парад Краусов");
            contentValues.put("Trueness", 0);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 10);
            contentValues.put("A_Text", "Парад Роз и Парад Санта-Клаусов");
            contentValues.put("Trueness", 0);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 10);
            contentValues.put("A_Text", "Парад Роз и Парад Пантомимы");
            contentValues.put("Trueness", 1);
            rowId = db.insert("Answers", null, contentValues);


            contentValues.put("Q_ID", 11);
            contentValues.put("A_Text", "Ширане-сан");
            contentValues.put("Trueness", 0);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 11);
            contentValues.put("A_Text", "Санта-Клаус");
            contentValues.put("Trueness", 0);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 11);
            contentValues.put("A_Text", "Одзи-сан(Сегацу-сан)");
            contentValues.put("Trueness", 1);
            rowId = db.insert("Answers", null, contentValues);



            contentValues.put("Q_ID", 12);
            contentValues.put("A_Text", "Пишут письмо и отправляют его Одзи-сану");
            contentValues.put("Trueness", 0);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 12);
            contentValues.put("A_Text", "Прячутся под стол в канун праздника и загадывают там желания там громко, как только они могут");
            contentValues.put("Trueness", 0);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 12);
            contentValues.put("A_Text", "Рисуют на бумаге свою мечту и перед сном кладут рисунок под подушку");
            contentValues.put("Trueness", 1);
            rowId = db.insert("Answers", null, contentValues);



            contentValues.put("Q_ID", 13);
            contentValues.put("A_Text", "Кадомацу");
            contentValues.put("Trueness", 1);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 13);
            contentValues.put("A_Text", "Саримацу");
            contentValues.put("Trueness", 0);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 13);
            contentValues.put("A_Text", "Лидарё");
            contentValues.put("Trueness", 0);
            rowId = db.insert("Answers", null, contentValues);



            contentValues.put("Q_ID", 14);
            contentValues.put("A_Text", "108");
            contentValues.put("Trueness", 1);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 14);
            contentValues.put("A_Text", "12");
            contentValues.put("Trueness", 0);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 14);
            contentValues.put("A_Text", "24");
            contentValues.put("Trueness", 0);
            rowId = db.insert("Answers", null, contentValues);



            contentValues.put("Q_ID", 15);
            contentValues.put("A_Text", "Алтарь с Одзи-саном");
            contentValues.put("Trueness", 0);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 15);
            contentValues.put("A_Text", "Генеральную уборку с выбрасыванием хлама");
            contentValues.put("Trueness", 1);
            rowId = db.insert("Answers", null, contentValues);

            contentValues.put("Q_ID", 15);
            contentValues.put("A_Text", "Свитки с пожеланиями для близких");
            contentValues.put("Trueness", 0);
            rowId = db.insert("Answers", null, contentValues);
        }

        public static void updateUser(SQLiteDatabase db, UserInfo user){
            db.execSQL("update Users set Nickname = '" + user.getName() + "'" +
                    ", Tree_Level = " + user.getTreeLevel() + ", Score = " +
                    user.getScore() + " where ID like '" + user.getId() + "'");
        }

        public static boolean checkUser(SQLiteDatabase db, UserInfo user){
            Cursor queryCursor = db.rawQuery(
                    "select * from Users where ID like '" + user.getId() + "'",
                    null);
            if(queryCursor.moveToFirst()){
                do{
                    user.setId(queryCursor.getString(0));
                    user.setName(queryCursor.getString(1));
                    user.setTreeLevel(queryCursor.getInt(2));
                    user.setScore(queryCursor.getInt(3));
                }while (queryCursor.moveToNext());
            }
            queryCursor.close();

            if(user.getId().equals("") || user.getName().equals("")) { return false; }
            else return true;
        }


                // ------------------------- INSERT QUERIES -------------------------

        public static long insertTheme(SQLiteDatabase db, String themeName){

            ContentValues contentValues = new ContentValues();
            long rowId;

            contentValues.put("Name", themeName);
            rowId = db.insert("Themes", null, contentValues);

            return rowId;
        }

        public static long insertQuestion(SQLiteDatabase db, int themeId, String questionText) {
            ContentValues contentValues = new ContentValues();
            long rowId;

            contentValues.put("Theme_ID", themeId);
            contentValues.put("Q_Text", questionText);
            rowId = db.insert("Questions", null, contentValues);

            return rowId;
        }

        public static long insertAnswer(SQLiteDatabase db, int questionId, String answerText, int trueness){
            ContentValues contentValues = new ContentValues();
            long rowId;

            contentValues.put("Q_ID", questionId);
            contentValues.put("A_Text", answerText);
            contentValues.put("Trueness", trueness);
            rowId = db.insert("Answers", null, contentValues);

            return rowId;
        }


                // ------------------------- UPDATE QUERIES -------------------------

        public static int updateTheme(SQLiteDatabase db, int themeId, String themeName){
            ContentValues contentValues = new ContentValues();
            int rowId;

            contentValues.put("ID", themeId);
            contentValues.put("Name", themeName);

            rowId = db.update(
                    "Themes",
                    contentValues,
                    "where ID = ?",
                    new String[] {String.valueOf(themeId)});
            return rowId;
        }

        public static int updateQuestion(
                SQLiteDatabase db,
                int questionId,
                int questionThemeId,
                String questionText){

            ContentValues contentValues = new ContentValues();
            int rowId;

            if(questionThemeId != 0) {
                contentValues.put("Theme_ID", questionThemeId);
                contentValues.put("ID", questionId);
                contentValues.put("Q_Text", questionText);
            }
            else{
                contentValues.put("ID", questionId);
                contentValues.put("Theme_ID", questionThemeId);
            }
            rowId = db.update(
                    "Questions",
                    contentValues,
                    "where ID = ?",
                    new String[] {String.valueOf(questionId)});
            return rowId;
        }

        public static int updateAnswer(
                SQLiteDatabase db,
                int answerId,
                int answerQuestionId,
                String answerText,
                int answerTrueness) {

            ContentValues contentValues = new ContentValues();
            int rowId;

            contentValues.put("ID", answerId);
            contentValues.put("Q_ID", answerQuestionId);
            contentValues.put("A_Text", answerText);
            contentValues.put("Trueness", answerTrueness);

            rowId = db.update(
                    "Answers",
                    contentValues,
                    "where ID = ?",
                    new String[] {String.valueOf(answerId)});
            return rowId;
        }

        public static int updateUserData(
                SQLiteDatabase db,
                String userId,
                String username,
                int treeLevel,
                int score){
            ContentValues contentValues = new ContentValues();
            int rowId = 0;
            return rowId;
        }



                // ------------------------- DELETE QUERIES -------------------------

        public static void deleteTheme(SQLiteDatabase db){

        }

        public static void deleteQuestion(SQLiteDatabase db){

        }

        public static void deleteAnswer(SQLiteDatabase db){

        }

        public static void deleteUser(SQLiteDatabase db){

        }


        public static QuestionContainer getRandomQuestion(SQLiteDatabase db) {

            int themeId = randomNumber(1, 3);
            int questionId = 0;

            if(themeId == 1) { questionId = randomNumber(1, 5); }
            if(themeId == 2) { questionId = randomNumber(6, 10); }
            if(themeId == 3) { questionId = randomNumber(11, 15); }

            ArrayList<QueryContainer> queryResult = new ArrayList<>();
            Cursor queryCursor = db.rawQuery(
                    "select Themes.Name as 'theme',\n" +
                            "Questions.ID as 'q_id',\n" +
                            "Questions.Q_Text,\n" +
                            "Answers.ID as 'answer_id', \n" +
                            "Answers.A_Text, \n" +
                            "Answers.Trueness\n" +
                            "\n" +
                            "from Answers\n" +
                            "inner join Questions on Answers.Q_ID = Questions.ID\n" +
                            "inner join Themes on Questions.Theme_ID = Themes.ID\n" +
                            "where Theme_ID = " + themeId + " AND Questions.ID = " + questionId, null
            );

            if(queryCursor.moveToFirst()){
                do{
                    QueryContainer container = new QueryContainer();
                    container.setThemeName(queryCursor.getString(0));
                    container.setQ_id(queryCursor.getInt(1));
                    container.setQ_text(queryCursor.getString(2));
                    container.setA_id(queryCursor.getInt(3));
                    container.setA_text(queryCursor.getString(4));
                    container.setTrueness(queryCursor.getInt(5));

                    queryResult.add(container);
                }while (queryCursor.moveToNext());
            }
            queryCursor.close();

            QuestionContainer randomQuestion = new QuestionContainer();

            randomQuestion.setQuestionTheme(queryResult.get(0).getThemeName());
            randomQuestion.setQuestionText(queryResult.get(0).getQ_text());
            Map<String, Integer> variants = new HashMap<>();

            for(int i = 0; i < queryResult.size(); i++){
                variants.put(queryResult.get(i).getA_text(), queryResult.get(i).getTrueness());
            }

            randomQuestion.setVariants(variants);

            for (QueryContainer elem: queryResult) {
                Log.d("QUERIES", elem.toString());
            }
            Log.d("QUERIES", randomQuestion.getQuestionText() + "---" +
                    randomQuestion.getQuestionTheme());

            return randomQuestion;
        }

        public static Map<String, Integer> getRating(SQLiteDatabase db) {

            Map<String, Integer> rating = new HashMap<>();
            Cursor queryCursor = db.rawQuery("select * from Users order by Score desc", null);
            if(queryCursor.moveToFirst()){
                do {
                    rating.put(queryCursor.getString(1), queryCursor.getInt(3));
                }while (queryCursor.moveToNext());
            }
            queryCursor.close();
            return rating;
        }

        public static ArrayList<String> getTables(SQLiteDatabase db) {
            ArrayList<String> tableList = new ArrayList<>();
            Cursor queryCursor = db.rawQuery(
                    "SELECT name FROM sqlite_master WHERE type='table' ORDER BY name limit 4;",
                    null);
            if(queryCursor.moveToFirst()){
                do {
                    tableList.add(queryCursor.getString(0));
                }while (queryCursor.moveToNext());
            }
            queryCursor.close();
            return tableList;
        }



        public static ArrayList<TableThemesContainer> getTableThemes(SQLiteDatabase db) {

            ArrayList<TableThemesContainer> themeList = new ArrayList<>();
            Cursor queryCursor = db.rawQuery(
                    "SELECT * FROM Themes",
                    null);
            if(queryCursor.moveToFirst()){
                do {
                    TableThemesContainer theme = new TableThemesContainer();
                    theme.setThemeId(queryCursor.getInt(0));
                    theme.setThemeName(queryCursor.getString(1));
                    themeList.add(theme);
                }while (queryCursor.moveToNext());
            }
            queryCursor.close();
            return themeList;
        }

        public static ArrayList<TableQuestionContainer> getTableQuestion(SQLiteDatabase db){

            ArrayList<TableQuestionContainer> questionList = new ArrayList<>();
            Cursor queryCursor = db.rawQuery(
                    "SELECT * FROM Questions",
                    null);
            if(queryCursor.moveToFirst()){
                do {
                    TableQuestionContainer question = new TableQuestionContainer();
                    question.setThemeId(queryCursor.getInt(0));
                    question.setQuestionId(queryCursor.getInt(1));
                    question.setQuestionText(queryCursor.getString(2));
                    questionList.add(question);
                }while (queryCursor.moveToNext());
            }
            queryCursor.close();
            return questionList;
        }

        public static ArrayList<TableAnswerContainer> getTableAnswer(SQLiteDatabase db) {

            ArrayList<TableAnswerContainer> answerList = new ArrayList<>();
            Cursor queryCursor = db.rawQuery(
                    "SELECT * FROM Answers",
                    null);
            if(queryCursor.moveToFirst()){
                do {
                    TableAnswerContainer answer = new TableAnswerContainer();
                    answer.setAnswerId(queryCursor.getInt(0));
                    answer.setQuestionId(queryCursor.getInt(1));
                    answer.setAnswerText(queryCursor.getString(2));
                    answer.setTrueness(queryCursor.getInt(3));
                    answerList.add(answer);
                }while (queryCursor.moveToNext());
            }
            queryCursor.close();
            return answerList;
        }

        public static ArrayList<UserInfo> getTableUser(SQLiteDatabase db){

            ArrayList<UserInfo> userList = new ArrayList<>();
            Cursor queryCursor = db.rawQuery(
                    "select * from Users",
                    null);

            if(queryCursor.moveToFirst()){
                do {
                    UserInfo user = new UserInfo();
                    user.setId(queryCursor.getString(0));
                    user.setName(queryCursor.getString(1));
                    user.setTreeLevel(queryCursor.getInt(2));
                    user.setScore(queryCursor.getInt(3));

                    userList.add(user);
                }while (queryCursor.moveToNext());
            }
            queryCursor.close();
            return userList;
        }

        private static int randomNumber(int min, int max)
        {
            max -= min;
            return (int) (Math.random() * ++max) + min;
        }
    }
}
