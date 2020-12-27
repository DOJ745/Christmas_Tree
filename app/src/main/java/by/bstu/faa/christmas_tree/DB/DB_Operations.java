package by.bstu.faa.christmas_tree.DB;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class DB_Operations {
    public static class MainOperations {

        public static void createAllTables(SQLiteDatabase db) {
            createThemesTable(db);
            createQuestionsTable(db);
            createAnswersTable(db);
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

            contentValues.put("Theme_ID", 1);
            contentValues.put("Q_Text", "В языческие времена белорусы отмечали наступление Нового года…");
            rowId = db.insert("Questions", null, contentValues);

            contentValues.put("Theme_ID", 1);
            contentValues.put("Q_Text", "В каком году Новый год в Беларуси стал официальным праздником?");
            rowId = db.insert("Questions", null, contentValues);

            contentValues.put("Theme_ID", 1);
            contentValues.put("Q_Text", "Кто приносит подарки под ёлку в Беларуси?");
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
        }

        public static void getRandomQuestion() {

        }
    }
}
