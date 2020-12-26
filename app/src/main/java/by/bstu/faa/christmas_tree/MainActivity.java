package by.bstu.faa.christmas_tree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import by.bstu.faa.christmas_tree.custom_dialog.MyDialogFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FragmentManager manager = getSupportFragmentManager();
        MyDialogFragment myDialogFragment = new MyDialogFragment();
        myDialogFragment.show(manager, "myDialog");
    }

    public void onClick(View view) {
        /*FragmentManager manager = getSupportFragmentManager();
        MyDialogFragment myDialogFragment = new MyDialogFragment();
        myDialogFragment.show(manager, "myDialog");*/
    }

}