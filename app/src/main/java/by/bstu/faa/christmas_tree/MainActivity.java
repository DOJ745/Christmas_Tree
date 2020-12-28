package by.bstu.faa.christmas_tree;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    private static int attempt_count = 2;

    TextView userScore;
    TextView treeLevel;
    ImageView userTree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DB_Helper(getApplicationContext());
        mainDB = dbHelper.getReadableDatabase();
        initViews();

        //showNameDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainDB.close();
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
        myDialog.setIcon(R.drawable.ic_christmas_bauble);
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

        AlertDialog.Builder answerDialogBuilder = new AlertDialog.Builder(this);
        View answerView = getLayoutInflater().inflate(R.layout.question_dialog, null);
        answerDialogBuilder.setView(answerView);

        Dialog answerQuestionDialog = answerDialogBuilder.create();

        TextView questionText = answerView.findViewById(R.id.question_text);
        TextView questionTheme = answerView.findViewById(R.id.question_theme);
        TextView chosenAnswer = answerView.findViewById(R.id.chosen_answer);

        TextView timerCountdown = answerView.findViewById(R.id.countdown);

        CountDownTimer answerTimer = new CountDownTimer(20000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerCountdown.setText("Осталось: " + millisUntilFinished / 1000);
            }
            public void onFinish() {
                timerCountdown.setText("Время вышло!");
                answerQuestionDialog.dismiss();
            }
        }.start();

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
        });

        variant2.setOnClickListener(v -> {
            answerResult = answer_trueness.get(1);
            chosenAnswer.setText("Выбран 2 вариант");
        });

        variant3.setOnClickListener(v -> {
            answerResult = answer_trueness.get(2);
            chosenAnswer.setText("Выбран 3 вариант");
        });

        answer_btn.setOnClickListener(v -> {

            if(answerResult == 1) {

                answerTimer.cancel();
                AlertDialog.Builder correct_builder = new AlertDialog.Builder(this);
                View correctAnswerView = getLayoutInflater().inflate(R.layout.correct_answer_dialog, null);
                correct_builder.setView(correctAnswerView);

                Button close_btn = correctAnswerView.findViewById(R.id.close_btn);

                final Dialog correctAnswerDialog = correct_builder.create();

                close_btn.setOnClickListener(v1 -> {
                    answerQuestionDialog.dismiss();
                    correctAnswerDialog.dismiss();
                });
                correctAnswerDialog.show();
                correctUserAnswer();
            }
            else if(chosenAnswer.getText().toString().equals("")) { chosenAnswer.setText("Вы не выбрали ответ!"); }

            else {
                answerTimer.cancel();
                AlertDialog.Builder wrong_builder = new AlertDialog.Builder(this);
                View wrongAnswerView = getLayoutInflater().inflate(R.layout.wrong_answer_dialog, null);
                wrong_builder.setView(wrongAnswerView);

                Button again_btn = wrongAnswerView.findViewById(R.id.again_btn);
                Button close_btn = wrongAnswerView.findViewById(R.id.close_btn);

                final Dialog wrongAnswerDialog = wrong_builder.create();

                close_btn.setOnClickListener(v2 -> {
                    answerQuestionDialog.dismiss();
                    wrongAnswerDialog.dismiss();
                });

                again_btn.setOnClickListener(this::answerQuestion);

                wrongAnswerDialog.show();
                wrongUserAnswer();
            }
        });

        answerQuestionDialog.show();
    }

    public void correctUserAnswer() {
        current_user.addLevel();
        current_user.addPoints();
        updateViews();
    }

    public void wrongUserAnswer() {
        current_user.reduceLevel();
        current_user.reducePoints();
        updateViews();
    }

    private void initViews() {
        userScore = findViewById(R.id.user_score);
        treeLevel = findViewById(R.id.tree_level);
        userTree = findViewById(R.id.user_tree);
    }

    private void updateViews() {
        switch (current_user.getTreeLevel()){
            case 0: userTree.setImageResource(R.drawable.ic_tree_level_0);
                break;
            case 1: userTree.setImageResource(R.drawable.ic_tree_level_1);
                break;
            case 2: userTree.setImageResource(R.drawable.ic_tree_level_2);
                break;
            case 3: userTree.setImageResource(R.drawable.ic_tree_level_3);
                break;
            case 4: userTree.setImageResource(R.drawable.ic_tree_level_4);
                break;
            case 5: userTree.setImageResource(R.drawable.ic_tree_level_5);
                break;
        }

        userScore.setText("Score: " + current_user.getScore());
        treeLevel.setText("Tree: " + current_user.getTreeLevel());
    }
}