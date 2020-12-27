package by.bstu.faa.christmas_tree;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import by.bstu.faa.christmas_tree.model.UserInfo;

public class MainActivity extends AppCompatActivity {

    private static String user_name = "";
    private static UserInfo current_user = new UserInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showDialog();
    }

    private void showDialog() {
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
            user_name = entered_name.getText().toString();

            Toast.makeText(MainActivity.this, "Hello " + user_name,  Toast.LENGTH_SHORT).show();
        });
        myDialog.show();
    }
}