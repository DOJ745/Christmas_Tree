package by.bstu.faa.christmas_tree.DB.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import by.bstu.faa.christmas_tree.DB.local_db.DB_Helper;
import by.bstu.faa.christmas_tree.DB.local_db.DB_Operations;
import by.bstu.faa.christmas_tree.R;

public class DbOptionSelectActivity extends AppCompatActivity {

    private DB_Helper dbHelper;
    private SQLiteDatabase mainDB;

    Spinner tableList;
    Button add_btn;
    Button update_btn;
    Button delete_btn;
    Button saveJson_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_option_select);

        dbHelper = new DB_Helper(getApplicationContext());
        mainDB = dbHelper.getReadableDatabase();

        initViews();
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
                        update_btn.setText("Изменить ответ");
                        delete_btn.setText("Удалить ответ");
                        setListenersButtons("Answers");
                        break;

                    case "Questions":
                        add_btn.setText("Добавить вопрос");
                        update_btn.setText("Изменить вопрос");
                        delete_btn.setText("Удалить вопрос");
                        setListenersButtons("Questions");
                        break;

                    case "Themes":
                        add_btn.setText("Добавить тему");
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

        });
    }
}