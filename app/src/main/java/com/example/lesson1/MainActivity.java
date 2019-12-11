package com.example.lesson1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = findViewById(R.id.editText);
        Button button = findViewById(R.id.button);

        editText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Resources r = getResources();
                String user = r.getString(R.string.city, s);
                TextView textView = findViewById(R.id.textView);

                if (editText.getText().toString().isEmpty()) {
                    textView.setText("");
                } else {
                    textView.setText(user);
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources r = getResources();
                String city = editText.getText().toString();
                TextView textWelcome = findViewById(R.id.city);

                if (city.isEmpty()) {
                    city = r.getString(R.string.default_city);
                }

                textWelcome.setText(city);
                editText.setText("");
            }
        });
    }
}