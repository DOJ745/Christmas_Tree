package by.bstu.faa.christmas_tree.DB.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import by.bstu.faa.christmas_tree.DB.local_db.DB_Helper;
import by.bstu.faa.christmas_tree.DB.local_db.DB_Operations;
import by.bstu.faa.christmas_tree.R;

public class AddDataActivity extends AppCompatActivity {

    Button add_data_btn;
    Button cancel_btn;
    TextView instructionView;
    EditText enterId;
    EditText enterForeignId;
    EditText enterText;
    EditText enterNumber;

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

        add_data_btn = findViewById(R.id.add_to_db);
        cancel_btn = findViewById(R.id.back);
        instructionView = findViewById(R.id.instruction);

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