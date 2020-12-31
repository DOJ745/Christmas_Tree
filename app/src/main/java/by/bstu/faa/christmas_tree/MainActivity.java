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

import java.util.ArrayList;
import java.util.Map;

import by.bstu.faa.christmas_tree.DB.DB_Helper;
import by.bstu.faa.christmas_tree.DB.DB_Operations;
import by.bstu.faa.christmas_tree.model.question.QuestionContainer;
import by.bstu.faa.christmas_tree.model.UserInfo;


public class MainActivity extends AppCompatActivity {

    DB_Helper dbHelper;
    SQLiteDatabase mainDB;

    private static UserInfo current_user = new UserInfo();
    private static final String TAG = "MainActivity";
    private static int answerResult = 0;
    private static int attempt_count = 3;

    TextView userScore;
    TextView userName;
    TextView treeLevel;
    ImageView userTree;
    Button growTreeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DB_Helper(getApplicationContext());
        mainDB = dbHelper.getReadableDatabase();
        initViews();

        current_user = DB_Operations.Queries.getUser(mainDB);
        userName.setText("Hello, " + current_user.getName());
        if(current_user.getName().equals("player"))
            showNameDialog();
    }

    @Override
    protected void onStart(){
        super.onStart();
        updateViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DB_Operations.Queries.updateUser(mainDB, current_user);
        attempt_count = 3;
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
            DB_Operations.Queries.updateUser(mainDB, current_user);
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

        CountDownTimer answerTimer = new CountDownTimer(30000, 1000) {
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

                startBlockBtnTimer(30);
            }
            else if(chosenAnswer.getText().toString().equals("Выберите ответ"))
            {
                chosenAnswer.setText("Вы не выбрали ответ!");
                wrongUserAnswer();
                startBlockBtnTimer(30);
            }

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

                if(attempt_count > 0)
                    again_btn.setOnClickListener(this::answerQuestion);
                else
                {
                    again_btn.setText("Попытки кончились...");
                    again_btn.setEnabled(false);
                }

                wrongAnswerDialog.show();
                wrongUserAnswer();
                startBlockBtnTimer(30);
            }
        });

        answerQuestionDialog.show();
    }

    public void howToPlay(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View tutorial_view = getLayoutInflater().inflate(R.layout.tutorial_dialog, null);
        builder.setView(tutorial_view);
        Dialog tutorialDialog = builder.create();
        Button close_btn = tutorial_view.findViewById(R.id.close_btn);

        close_btn.setOnClickListener(v1 -> tutorialDialog.dismiss());

        tutorialDialog.show();
    }

    private void correctUserAnswer() {
        current_user.correctAnswer();
        DB_Operations.Queries.updateUser(mainDB, current_user);
        updateViews();
    }

    private void wrongUserAnswer() {
        current_user.wrongAnswer();
        attempt_count--;
        DB_Operations.Queries.updateUser(mainDB, current_user);
        updateViews();
    }

    private void initViews() {
        userName = findViewById(R.id.user_name);
        userScore = findViewById(R.id.user_score);
        treeLevel = findViewById(R.id.tree_level);
        userTree = findViewById(R.id.user_tree);
        growTreeButton = findViewById(R.id.grow_btn);
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

    private void startBlockBtnTimer(int sec) {

        new CountDownTimer(sec * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                growTreeButton.setEnabled(false);
                growTreeButton.setText("Wait little bit...");
            }
            public void onFinish() {
                growTreeButton.setText("Grow your tree!");
                growTreeButton.setEnabled(true);
            }
        }.start();
    }
}