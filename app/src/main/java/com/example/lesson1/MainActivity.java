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
//    private EditText editText;
//    private View.OnClickListener onClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            editText.setText("");
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_cities);
//        setContentView(R.layout.activity_settings);
//        setContentView(R.layout.activity_cities_add);

//        Button button = findViewById(R.id.button);
//        editText = findViewById(R.id.addCity);
//        button.setOnClickListener(onClickListener);
    }
}