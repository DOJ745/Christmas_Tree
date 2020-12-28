package by.bstu.faa.christmas_tree;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

import by.bstu.faa.christmas_tree.DB.DB_Helper;
import by.bstu.faa.christmas_tree.DB.DB_Operations;
import by.bstu.faa.christmas_tree.model.question.QuestionContainer;
import by.bstu.faa.christmas_tree.model.UserInfo;

public class MainActivity extends AppCompatActivity {

    DB_Helper dbHelper;
    SQLiteDatabase mainDB;
    private static final UserInfo current_user = new UserInfo();
    private static final String TAG = "MainActivity";
    private static int answerResult = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DB_Helper(getApplicationContext());
        mainDB = dbHelper.getReadableDatabase();

        //showNameDialog();
    }

    private void showNameDialog() {

        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog, null);
        EditText entered_name = view.findViewById(R.id.edit_name);

        entered_name.setError("Required field!");
        entered_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (entered_name.getText().length() != 0) {
                    entered_name.setError(null);
                } else {
                    entered_name.setError("Required!");
                }
            }
        });

        myDialog.setTitle("Ho-ho-ho");
        myDialog.setIcon(R.drawable.christmas_bauble_32);
        myDialog.setView(view);

        myDialog.setPositiveButton("OK", (dialog, which) -> {
            current_user.setName(entered_name.getText().toString());
            Toast.makeText(
                    MainActivity.this,
                    "Hello " + "'" + current_user.getName() + "'",
                    Toast.LENGTH_SHORT).show();
        });
        myDialog.show();
    }

    public void answerQuestion(View view) {

        QuestionContainer currentQ = DB_Operations.Queries.getRandomQuestion(mainDB);
        ArrayList<String> answer_variants = new ArrayList<>();
        ArrayList<Integer> answer_trueness = new ArrayList<>();

        //AtomicInteger isTrue = new AtomicInteger();

        AlertDialog.Builder answerDialog = new AlertDialog.Builder(this);
        View answerView = getLayoutInflater().inflate(R.layout.question_dialog, null);
        answerDialog.setView(answerView);

        TextView questionText = answerView.findViewById(R.id.question_text);
        TextView questionTheme = answerView.findViewById(R.id.question_theme);
        TextView chosenAnswer = answerView.findViewById(R.id.chosen_answer);
        TextView timerCountdown = answerView.findViewById(R.id.countdown);

        Button variant1 = answerView.findViewById(R.id.variant1);
        Button variant2 = answerView.findViewById(R.id.variant2);
        Button variant3 = answerView.findViewById(R.id.variant3);
        Button answer_btn = answerView.findViewById(R.id.answer_btn);

        questionTheme.setText("Тема - " + currentQ.getQuestionTheme());
        questionText.setText(currentQ.getQuestionText());

        for(Map.Entry<String, Integer> item :currentQ.getVariants().entrySet()){
            answer_variants.add(item.getKey());
            answer_trueness.add(item.getValue());
        }

        variant1.setText(answer_variants.get(0));
        variant2.setText(answer_variants.get(1));
        variant3.setText(answer_variants.get(2));

        variant1.setOnClickListener(v -> {
            answerResult = answer_trueness.get(0);
            chosenAnswer.setText("Выбран 1 вариант");
            //Toast.makeText(this, "Result - " + answerResult, Toast.LENGTH_LONG).show();
        });

        variant2.setOnClickListener(v -> {
            answerResult = answer_trueness.get(1);
            chosenAnswer.setText("Выбран 2 вариант");
            //Toast.makeText(this, "Result - " + answerResult, Toast.LENGTH_LONG).show();
        });

        variant3.setOnClickListener(v -> {
            answerResult = answer_trueness.get(2);
            chosenAnswer.setText("Выбран 3 вариант");
            //Toast.makeText(this, "Result - " + answerResult, Toast.LENGTH_LONG).show();
        });

        answer_btn.setOnClickListener(v -> {
            if(answerResult == 1){
                Toast.makeText(this, "YAY - " + answerResult, Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this, "OOF - " + answerResult, Toast.LENGTH_LONG).show();
            }
        });

        new CountDownTimer(20000, 1000) {
            // Действие в интервал времени
            public void onTick(long millisUntilFinished) {
                timerCountdown.setText("Осталось: "
                        + millisUntilFinished / 1000);
            }
            // Запуск действия после завершения отсчета
            public void onFinish() {
                timerCountdown.setText("Время вышло!");
            }
        }.start();

        answerDialog.show();
    }
}