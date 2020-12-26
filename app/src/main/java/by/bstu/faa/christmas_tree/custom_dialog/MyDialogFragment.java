package by.bstu.faa.christmas_tree.custom_dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import by.bstu.faa.christmas_tree.R;

public class MyDialogFragment extends DialogFragment {

    /*
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return builder.setTitle("Диалоговое окно").setMessage("Для закрытия окна нажмите ОК").create();
    }*/

    /*
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Диалоговое окно")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Для закрытия окна нажмите ОК")
                .setPositiveButton("OK", null)
                .setNegativeButton("Отмена", null)
                .create();
    }*/

/*
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final String[] catNamesArray = {"Васька", "Рыжик", "Мурзик"};

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Выберите любимое имя кота")
                    // добавляем переключатели
                    .setSingleChoiceItems(catNamesArray, -1,
                            (dialog, item) -> Toast.makeText(
                                    getActivity(),
                                    "Любимое имя кота: "
                                            + catNamesArray[item],
                                    Toast.LENGTH_SHORT).show())
                    .setPositiveButton("OK", (dialog, id) -> {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog
                    })
                    .setNegativeButton("Отмена", (dialog, id) -> { });

            return builder.create();
        }
        */

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Ho-ho-ho")
                .setIcon(R.drawable.christmas_bauble_32)
                .setView(R.layout.dialog)
                .setPositiveButton("OK", null)
                .setNegativeButton("Cancel", null)
                .create();
    }
}

