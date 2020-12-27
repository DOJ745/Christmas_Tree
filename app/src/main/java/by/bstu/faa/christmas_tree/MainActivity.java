package by.bstu.faa.christmas_tree;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import by.bstu.faa.christmas_tree.DB.DB_Helper;
import by.bstu.faa.christmas_tree.DB.DB_Operations;
import by.bstu.faa.christmas_tree.model.QuestionDialog;
import by.bstu.faa.christmas_tree.model.UserInfo;

public class MainActivity extends AppCompatActivity {

    DB_Helper dbHelper;
    SQLiteDatabase mainDB;
    private static final UserInfo current_user = new UserInfo();

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

    private void showQuestionDialog() {


    }

    public void answerQuestion(View view){
        /*Toast.makeText(
                MainActivity.this,
                "Hello " + "'" + current_user.getName() + "'",
                Toast.LENGTH_SHORT).show();*/

        /*
        Toast.makeText(
                MainActivity.this,
                "Test queries",
                Toast.LENGTH_SHORT).show();*/

        /*
        QuestionDialog dialog = new QuestionDialog();
        Bundle args = new Bundle();
        //args.putString("phone", selectedPhone);
        //dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "custom");*/

        //Toast.makeText(this, DB_Operations.Queries.getRandomTheme(mainDB), Toast.LENGTH_LONG).show();
        //DB_Operations.Queries.getRandomQuestion(mainDB);
    }
}