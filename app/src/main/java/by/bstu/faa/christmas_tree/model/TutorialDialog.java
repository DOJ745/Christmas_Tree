package by.bstu.faa.christmas_tree.model;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import by.bstu.faa.christmas_tree.R;

public class TutorialDialog extends DialogFragment {

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.tutorial_dialog, null);
        builder.setView(view);
        Dialog tutorialDialog = builder.create();

        Button close_btn = view.findViewById(R.id.close_btn);

        close_btn.setOnClickListener(v1 -> {
            tutorialDialog.dismiss();
        });
        return  tutorialDialog;
    }
}
