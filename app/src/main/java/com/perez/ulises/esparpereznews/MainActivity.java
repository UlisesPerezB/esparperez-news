package com.perez.ulises.esparpereznews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView test;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        test = findViewById(R.id.sp_tv);
        test.setText("Aquí irán todas las pantallas adicionales");
    }
}
