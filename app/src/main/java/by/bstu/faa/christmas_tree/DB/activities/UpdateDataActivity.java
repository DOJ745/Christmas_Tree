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

public class UpdateDataActivity extends AppCompatActivity {

    Button update_data_btn;
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
        setContentView(R.layout.activity_update_data);

        dbHelper = new DB_Helper(getApplicationContext());
        mainDB = dbHelper.getReadableDatabase();

        initViews(getIntent().getStringExtra("OPTION"));
    }

    private void initViews(String tableName) {

        data_container = findViewById(R.id.data_update_list);

        ThemeAdapter themeAdapter = new ThemeAdapter(this, DB_Operations.Queries.getTableThemes(mainDB));
        QuestionAdapter questionAdapter = new QuestionAdapter(this, DB_Operations.Queries.getTableQuestion(mainDB));
        AnswerAdapter answerAdapter = new AnswerAdapter(this, DB_Operations.Queries.getTableAnswer(mainDB));
        UserAdapter userAdapter = new UserAdapter(this, DB_Operations.Queries.getTableUser(mainDB));

        update_data_btn = findViewById(R.id.update_to_db);
        cancel_btn = findViewById(R.id.back);
        instructionView = findViewById(R.id.instruction);

        enterId = findViewById(R.id.set_id);
        enterForeignId = findViewById(R.id.set_foreign_id);
        enterText = findViewById(R.id.set_text);
        enterNumber = findViewById(R.id.set_number_param);

        switch (tableName)
        {
            case "Themes":
                instructionView.setText(R.string.instruction_table_theme_update);
                enterForeignId.setVisibility(View.GONE);
                enterNumber.setVisibility(View.GONE);

                data_container.setAdapter(themeAdapter);
                break;

            case "Questions":
                instructionView.setText(R.string.instruction_table_question_update);
                enterNumber.setVisibility(View.GONE);
                enterForeignId.setHint(R.string.hint_question_table_foreign_key);

                data_container.setAdapter(questionAdapter);
                break;

            case "Answers":
                instructionView.setText(R.string.instruction_table_answer_update);
                enterForeignId.setHint(R.string.hint_answer_table_foreign_key);
                enterNumber.setHint(R.string.hint_answer_table_number);
                data_container.setAdapter(answerAdapter);
                break;

            case "Users":
                instructionView.setText(R.string.instruction_table_user_update);
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
                update_data_btn.setOnClickListener(v -> {
                    if(checkThemeData())
                        Toast.makeText(this, "Вы не ввели данные", Toast.LENGTH_SHORT).show();
                    else {
                        if( DB_Operations.Queries.updateTheme(
                                mainDB,
                                Integer.parseInt(enterId.getText().toString()),
                                enterText.getText().toString()) > 0){
                            updateDataContainer("Themes");
                            Toast.makeText(this, "Обновление прошло успешно", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;

            case "Questions":
                update_data_btn.setOnClickListener(v -> {
                    if(checkQuestionData())
                        Toast.makeText(this, "Вы не ввели данные", Toast.LENGTH_SHORT).show();
                    else {
                        if(DB_Operations.Queries.updateQuestion(
                                mainDB,
                                Integer.parseInt(enterId.getText().toString()),
                                Integer.parseInt(enterForeignId.getText().toString()),
                                enterText.getText().toString()) > 0){
                            updateDataContainer("Questions");
                            Toast.makeText(this, "Обновление прошло успешно", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;

            case "Answers":
                update_data_btn.setOnClickListener(v -> {
                    if(checkAnswerData())
                        Toast.makeText(this, "Вы не ввели данные", Toast.LENGTH_SHORT).show();
                    else {
                        if(DB_Operations.Queries.updateAnswer(
                                mainDB,
                                Integer.parseInt(enterId.getText().toString()),
                                Integer.parseInt(enterForeignId.getText().toString()),
                                enterText.getText().toString(),
                                Integer.parseInt(enterNumber.getText().toString())) > 0){
                            Toast.makeText(this, "Обновление прошло успешно", Toast.LENGTH_SHORT).show();
                            updateDataContainer("Answers");
                        }
                    }
                });
                break;

            case "Users":
                update_data_btn.setOnClickListener(v -> {
                    if(checkUserData())
                        Toast.makeText(this, "Вы не ввели данные", Toast.LENGTH_SHORT).show();
                    else {
                        if(DB_Operations.Queries.updateUserData(
                                mainDB,
                                enterId.getText().toString(),
                                enterText.getText().toString(),
                                Integer.parseInt(enterForeignId.getText().toString()),
                                Integer.parseInt(enterNumber.getText().toString())) > 0){
                            updateDataContainer("Users");
                            Toast.makeText(this, "Обновление прошло успешно", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }

    private boolean checkThemeData(){
        return enterText.getText().toString().equals("") && enterId.getText().toString().equals("");
    }

    private boolean checkQuestionData(){
        return enterId.getText().toString().equals("") && enterText.getText().toString().equals("");
    }

    private boolean checkAnswerData(){
        return enterText.getText().toString().equals("") &&
                enterForeignId.getText().toString().equals("") &&
                enterNumber.getText().toString().equals("") &&
                enterId.getText().toString().equals("");
    }

    private boolean checkUserData(){ return enterId.getText().toString().equals(""); }

    private void updateDataContainer(String tableName){

        ThemeAdapter themeAdapter = new ThemeAdapter(this, DB_Operations.Queries.getTableThemes(mainDB));
        QuestionAdapter questionAdapter = new QuestionAdapter(this, DB_Operations.Queries.getTableQuestion(mainDB));
        AnswerAdapter answerAdapter = new AnswerAdapter(this, DB_Operations.Queries.getTableAnswer(mainDB));
        UserAdapter userAdapter = new UserAdapter(this, DB_Operations.Queries.getTableUser(mainDB));
        switch (tableName){
            case "Themes": data_container.setAdapter(themeAdapter); break;
            case "Questions": data_container.setAdapter(questionAdapter); break;
            case "Answers": data_container.setAdapter(answerAdapter); break;
            case "Users": data_container.setAdapter(userAdapter); break;
        }
    }
}