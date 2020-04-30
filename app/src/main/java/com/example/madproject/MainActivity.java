package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button nextPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openActivity();

            }
        });
    }
    public void openActivity(){
        Intent intent = new Intent(MainActivity.this, BookDetails.class);
        startActivity(intent);
    }
}
