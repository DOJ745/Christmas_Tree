package by.bstu.faa.christmas_tree.model.question;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class QuestionDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String[] answerVariants = {"Васька", "Рыжик", "Мурзик"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Выберите любимое имя кота")
                // добавляем переключатели
                .setSingleChoiceItems(answerVariants, -1,
                        (dialog, item) -> Toast.makeText(
                                getActivity(),
                                "Любимое имя кота: "
                                        + answerVariants[item],
                                Toast.LENGTH_SHORT).show())
                .setPositiveButton("Answer", (dialog, id) -> {

                });

        return builder.create();
    }
}
