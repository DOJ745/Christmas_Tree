package by.bstu.faa.christmas_tree.model.question;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Map;

import by.bstu.faa.christmas_tree.DB.DB_Operations;
import by.bstu.faa.christmas_tree.R;

public class QuestionDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        QuestionContainer currentQ = (QuestionContainer) getArguments().getSerializable("QUESTION");
        final int[] answerResult = { getArguments().getInt("ANSWER_RESULT") };
        ArrayList<String> answer_variants = new ArrayList<>();
        ArrayList<Integer> answer_trueness = new ArrayList<>();

        //AtomicInteger isTrue = new AtomicInteger();

        AlertDialog.Builder answerDialog = new AlertDialog.Builder(getActivity());
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
            answerResult[0] = answer_trueness.get(0);
            chosenAnswer.setText("Выбран 1 вариант");
            //Toast.makeText(this, "Result - " + answerResult, Toast.LENGTH_LONG).show();
        });

        variant2.setOnClickListener(v -> {
            answerResult[0] = answer_trueness.get(1);
            chosenAnswer.setText("Выбран 2 вариант");
            //Toast.makeText(this, "Result - " + answerResult, Toast.LENGTH_LONG).show();
        });

        variant3.setOnClickListener(v -> {
            answerResult[0] = answer_trueness.get(2);
            chosenAnswer.setText("Выбран 3 вариант");
            //Toast.makeText(this, "Result - " + answerResult, Toast.LENGTH_LONG).show();
        });

        answer_btn.setOnClickListener(v -> {
            if(answerResult[0] == 1){
                //Toast.makeText(getContext(), "YAY - " + 1, Toast.LENGTH_LONG).show();
            }
            else if(chosenAnswer.getText().toString().equals("")) {
                //Toast.makeText(getContext(), "Вы не выбрали ответ!", Toast.LENGTH_SHORT).show();
            }
            else{
                //Toast.makeText(getContext(), "OOF - " + 0, Toast.LENGTH_LONG).show();
            }
        });

        new CountDownTimer(20000, 1000) {
            // Действие в интервал времени
            public void onTick(long millisUntilFinished) {
                timerCountdown.setText("Осталось: " + millisUntilFinished / 1000);
            }
            // Запуск действия после завершения отсчета
            public void onFinish() { timerCountdown.setText("Время вышло!"); }
        }.start();

        return answerDialog.create();
    }
}
