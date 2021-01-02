package by.bstu.faa.christmas_tree.DB.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import by.bstu.faa.christmas_tree.DB.local_db.DB_Helper;
import by.bstu.faa.christmas_tree.DB.local_db.DB_Operations;
import by.bstu.faa.christmas_tree.R;
import by.bstu.faa.christmas_tree.adapters.ThemeAdapter;
import by.bstu.faa.christmas_tree.model.query.TableAnswerContainer;
import by.bstu.faa.christmas_tree.model.query.TableQuestionContainer;
import by.bstu.faa.christmas_tree.model.query.TableThemesContainer;

public class AddDataActivity extends AppCompatActivity {

    Button add_data_btn;
    Button cancel_btn;
    TextView instructionView;
    EditText enterId;
    EditText enterForeignId;
    EditText enterText;
    EditText enterNumber;
    RecyclerView data_container;

    private DB_Helper dbHelper;
    private SQLiteDatabase mainDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        dbHelper = new DB_Helper(getApplicationContext());
        mainDB = dbHelper.getReadableDatabase();

        initViews(getIntent().getStringExtra("OPTION"));
    }

    private void initViews(String tableName) {

        ArrayList<TableThemesContainer> themes_data = new ArrayList<>();
        themes_data = DB_Operations.Queries.getTableThemes(mainDB);

        ArrayList<TableQuestionContainer> questions_data = new ArrayList<>();
        questions_data = DB_Operations.Queries.getTableQuestion(mainDB);

        ArrayList<TableAnswerContainer> answers_data = new ArrayList<>();
        answers_data = DB_Operations.Queries.getTableAnswer(mainDB);

        add_data_btn = findViewById(R.id.add_to_db);
        cancel_btn = findViewById(R.id.back);
        instructionView = findViewById(R.id.instruction);

        data_container = findViewById(R.id.data_add_list);

        ThemeAdapter themeAdapter = new ThemeAdapter(this, themes_data);

        // устанавливаем для списка адаптер
        //recyclerView.setAdapter(adapter);

        enterId = findViewById(R.id.set_id);
        enterForeignId = findViewById(R.id.set_foreign_id);
        enterText = findViewById(R.id.set_text);
        enterNumber = findViewById(R.id.set_number_param);

        switch (tableName)
        {
            case "Themes":
                instructionView.setText(R.string.instruction_table_theme_add);
                enterId.setVisibility(View.GONE);
                enterForeignId.setVisibility(View.GONE);
                enterNumber.setVisibility(View.GONE);
                data_container.setAdapter(themeAdapter);
                break;

            case "Questions":
                instructionView.setText(R.string.instruction_table_question_add);
                enterId.setVisibility(View.GONE);
                enterNumber.setVisibility(View.GONE);
                break;

            case "Answers":
                instructionView.setText(R.string.instruction_table_answer_add);
                enterId.setVisibility(View.GONE);
                break;
        }

        setAddBtnListener(tableName);
        cancel_btn.setOnClickListener(v -> finish());
    }

    private void setAddBtnListener(String tableName) {
        switch (tableName) {
            case "Themes":
                add_data_btn.setOnClickListener(v -> DB_Operations.Queries.insertTheme(mainDB));
                break;
            case "Questions":
                add_data_btn.setOnClickListener(v -> DB_Operations.Queries.insertQuestion(mainDB));
                break;
            case "Answers":
                add_data_btn.setOnClickListener(v -> DB_Operations.Queries.insertAnswer(mainDB));
                break;
        }

    }
}