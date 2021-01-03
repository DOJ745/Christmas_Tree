package by.bstu.faa.christmas_tree.DB.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import by.bstu.faa.christmas_tree.DB.local_db.DB_Helper;
import by.bstu.faa.christmas_tree.DB.local_db.DB_Operations;
import by.bstu.faa.christmas_tree.DB.local_db.JsonOperations;
import by.bstu.faa.christmas_tree.R;

public class DbOptionSelectActivity extends AppCompatActivity {

    private DB_Helper dbHelper;
    private SQLiteDatabase mainDB;

    Spinner tableList;
    Button add_btn;
    Button update_btn;
    Button delete_btn;
    Button saveJson_btn;

    private static String THEMES_JSON = "themes_table.json";
    private static String QUESTIONS_JSON = "questions_table.json";
    private static String ANSWERS_JSON = "answers_table.json";
    private static String USERS_JSON = "users_table.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_option_select);

        dbHelper = new DB_Helper(getApplicationContext());
        mainDB = dbHelper.getReadableDatabase();

        initViews();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
    }

    private void initViews() {

        tableList = findViewById(R.id.table_list);
        add_btn = findViewById(R.id.add_button);
        update_btn = findViewById(R.id.update_button);
        delete_btn = findViewById(R.id.delete_button);
        saveJson_btn = findViewById(R.id.saveToJson_button);

        ArrayAdapter<String> tableListAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                DB_Operations.Queries.getTables(mainDB));
        tableListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tableList.setAdapter(tableListAdapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = (String)parent.getItemAtPosition(position);
                switch (item)
                {
                    case "Answers":
                        add_btn.setText("Добавить ответ");
                        add_btn.setEnabled(true);
                        update_btn.setText("Изменить ответ");
                        delete_btn.setText("Удалить ответ");
                        setListenersButtons("Answers");
                        break;

                    case "Questions":
                        add_btn.setText("Добавить вопрос");
                        add_btn.setEnabled(true);
                        update_btn.setText("Изменить вопрос");
                        delete_btn.setText("Удалить вопрос");
                        setListenersButtons("Questions");
                        break;

                    case "Themes":
                        add_btn.setText("Добавить тему");
                        add_btn.setEnabled(true);
                        update_btn.setText("Изменить тему");
                        delete_btn.setText("Удалить тему");
                        setListenersButtons("Themes");
                        break;

                    case "Users":
                        add_btn.setText("Добавить пользователя");
                        add_btn.setEnabled(false);
                        update_btn.setText("Изменить пользователя");
                        delete_btn.setText("Удалить пользователя");
                        setListenersButtons("Users");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        };
        tableList.setOnItemSelectedListener(itemSelectedListener);
    }

    private void setListenersButtons(String tableName) {

        add_btn.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddDataActivity.class);
            intent.putExtra("OPTION", tableName);
            startActivity(intent);
        });
        update_btn.setOnClickListener(v -> {
            Intent intent = new Intent(this, UpdateDataActivity.class);
            intent.putExtra("OPTION", tableName);
            startActivity(intent);
        });
        delete_btn.setOnClickListener(v -> {
            Intent intent = new Intent(this, DeleteDataActivity.class);
            intent.putExtra("OPTION", tableName);
            startActivity(intent);
        });

        saveJson_btn.setOnClickListener(v -> {
                switch (tableName){
                    case "Themes":
                        File saveThemesFile = new File(
                                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                                THEMES_JSON);
                        try {
                            JsonOperations.saveThemesJson(
                                    DB_Operations.Queries.getTableThemes(mainDB),
                                    saveThemesFile.getPath());
                            Toast.makeText(
                                    this,
                                    "Таблица Themes сохранена в  " + saveThemesFile.getPath(),
                                    Toast.LENGTH_LONG).show();
                        }
                        catch (IOException e) { e.printStackTrace(); }
                        break;

                    case "Questions":
                        File saveQuestionsFile = new File(
                                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                                QUESTIONS_JSON);
                        try {
                            JsonOperations.saveQuestionsJson(
                                    DB_Operations.Queries.getTableQuestion(mainDB),
                                    saveQuestionsFile.getPath());
                            Toast.makeText(
                                    this,
                                    "Таблица Questions сохранена в  " + saveQuestionsFile.getPath(),
                                    Toast.LENGTH_LONG).show();
                        }
                        catch (IOException e) { e.printStackTrace(); }
                        break;

                    case "Answers":
                        File saveAnswersFile = new File(
                                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                                ANSWERS_JSON);
                        try {
                            JsonOperations.saveAnswersJson(
                                    DB_Operations.Queries.getTableAnswer(mainDB),
                                    saveAnswersFile.getPath());
                            Toast.makeText(
                                    this,
                                    "Таблица Answers сохранена в  " + saveAnswersFile.getPath(),
                                    Toast.LENGTH_LONG).show();
                        }
                        catch (IOException e) { e.printStackTrace(); }
                        break;

                    case "Users":
                        File saveUsersFile = new File(
                                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                                USERS_JSON);
                        try {
                            JsonOperations.saveUsersJson(
                                    DB_Operations.Queries.getTableUser(mainDB),
                                    saveUsersFile.getPath());
                            Toast.makeText(
                                    this,
                                    "Таблица Users сохранена в  " + saveUsersFile.getPath(),
                                    Toast.LENGTH_LONG).show();
                        }
                        catch (IOException e) { e.printStackTrace(); }
                        break;
                }
        });
    }
}