package by.bstu.faa.christmas_tree.DB.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import by.bstu.faa.christmas_tree.DB.local_db.DB_Helper;
import by.bstu.faa.christmas_tree.DB.local_db.DB_Operations;
import by.bstu.faa.christmas_tree.R;
import by.bstu.faa.christmas_tree.adapters.AnswerAdapter;
import by.bstu.faa.christmas_tree.adapters.QuestionAdapter;
import by.bstu.faa.christmas_tree.adapters.ThemeAdapter;
import by.bstu.faa.christmas_tree.adapters.UserAdapter;
import by.bstu.faa.christmas_tree.model.UserInfo;
import by.bstu.faa.christmas_tree.model.query.TableAnswerContainer;
import by.bstu.faa.christmas_tree.model.query.TableQuestionContainer;
import by.bstu.faa.christmas_tree.model.query.TableThemesContainer;

public class DeleteDataActivity extends AppCompatActivity {

    Button delete_data_btn;
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
        setContentView(R.layout.activity_delete_data);

        dbHelper = new DB_Helper(getApplicationContext());
        mainDB = dbHelper.getReadableDatabase();

        initViews(getIntent().getStringExtra("OPTION"));
    }

    private void initViews(String tableName) {

        ArrayList<TableThemesContainer> themes_data;
        themes_data = DB_Operations.Queries.getTableThemes(mainDB);

        ArrayList<TableQuestionContainer> questions_data;
        questions_data = DB_Operations.Queries.getTableQuestion(mainDB);

        ArrayList<TableAnswerContainer> answers_data;
        answers_data = DB_Operations.Queries.getTableAnswer(mainDB);

        ArrayList<UserInfo> users_data;
        users_data = DB_Operations.Queries.getTableUser(mainDB);

        data_container = findViewById(R.id.data_delete_list);
        ThemeAdapter themeAdapter = new ThemeAdapter(this, themes_data);
        QuestionAdapter questionAdapter = new QuestionAdapter(this, questions_data);
        AnswerAdapter answerAdapter = new AnswerAdapter(this, answers_data);
        UserAdapter userAdapter = new UserAdapter(this, users_data);

        delete_data_btn = findViewById(R.id.delete_to_db);
        cancel_btn = findViewById(R.id.back);
        instructionView = findViewById(R.id.instruction);

        enterId = findViewById(R.id.set_id);
        enterForeignId = findViewById(R.id.set_foreign_id);
        enterText = findViewById(R.id.set_text);
        enterNumber = findViewById(R.id.set_number_param);

        switch (tableName)
        {
            case "Themes":
                instructionView.setText(R.string.instruction_table_theme_delete);
                enterForeignId.setVisibility(View.GONE);
                enterNumber.setVisibility(View.GONE);
                enterText.setVisibility(View.GONE);

                data_container.setAdapter(themeAdapter);
                break;

            case "Questions":
                instructionView.setText(R.string.instruction_table_question_delete);
                enterNumber.setVisibility(View.GONE);
                enterText.setVisibility(View.GONE);
                enterForeignId.setVisibility(View.GONE);

                data_container.setAdapter(answerAdapter);
                break;

            case "Answers":
                instructionView.setText(R.string.instruction_table_answer_delete);
                enterNumber.setVisibility(View.GONE);
                enterText.setVisibility(View.GONE);
                enterForeignId.setVisibility(View.GONE);

                data_container.setAdapter(questionAdapter);
                break;

            case "Users":
                instructionView.setText(R.string.instruction_table_user_delete);
                enterNumber.setVisibility(View.GONE);
                enterText.setVisibility(View.GONE);
                enterForeignId.setVisibility(View.GONE);

                data_container.setAdapter(userAdapter);
                break;
        }

        setAddBtnListener(tableName);
        cancel_btn.setOnClickListener(v -> finish());
    }

    private void setAddBtnListener(String tableName) {
        switch (tableName) {

            case "Themes":
                delete_data_btn.setOnClickListener(v -> {
                    if(checkThemeData())
                        Toast.makeText(this, "Вы не ввели данные", Toast.LENGTH_SHORT).show();
                    else {
                        if( DB_Operations.Queries.deleteTheme(
                                mainDB,
                                enterText.getText().toString()) > 0){
                            updateDataContainer("Themes");
                            Toast.makeText(this, "Добавление прошло успешно", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;

            case "Questions":
                delete_data_btn.setOnClickListener(v -> {
                    if(checkQuestionData())
                        Toast.makeText(this, "Вы не ввели данные", Toast.LENGTH_SHORT).show();
                    else {
                        if(DB_Operations.Queries.deleteQuestion(
                                mainDB,
                                Integer.parseInt(enterForeignId.getText().toString()),
                                enterText.getText().toString()) > 0){
                            updateDataContainer("Questions");
                            Toast.makeText(this, "Добавление прошло успешно", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;

            case "Answers":
                delete_data_btn.setOnClickListener(v -> {
                    if(checkAnswerData())
                        Toast.makeText(this, "Вы не ввели данные", Toast.LENGTH_SHORT).show();
                    else {
                        if(DB_Operations.Queries.deleteAnswer(
                                mainDB,
                                Integer.parseInt(enterForeignId.getText().toString()),
                                enterText.getText().toString(),
                                Integer.parseInt(enterNumber.getText().toString())) > 0){
                            Toast.makeText(this, "Добавление прошло успешно", Toast.LENGTH_SHORT).show();
                            updateDataContainer("Answers");
                        }

                    }
                });
                break;
        }
    }

    private boolean checkThemeData(){
        return enterText.getText().toString().equals("");
    }

    private boolean checkQuestionData(){
        return enterText.getText().toString().equals("") && enterForeignId.getText().toString().equals("");
    }

    private boolean checkAnswerData(){
        return enterText.getText().toString().equals("") &&
                enterForeignId.getText().toString().equals("") &&
                enterNumber.getText().toString().equals("");
    }

    private void updateDataContainer(String tableName){

        ThemeAdapter themeAdapter = new ThemeAdapter(this, DB_Operations.Queries.getTableThemes(mainDB));
        QuestionAdapter questionAdapter = new QuestionAdapter(this, DB_Operations.Queries.getTableQuestion(mainDB));
        AnswerAdapter answerAdapter = new AnswerAdapter(this, DB_Operations.Queries.getTableAnswer(mainDB));
        switch (tableName){
            case "Themes": data_container.setAdapter(themeAdapter); break;
            case "Questions": data_container.setAdapter(questionAdapter); break;
            case "Answers": data_container.setAdapter(answerAdapter); break;
        }
    }
}