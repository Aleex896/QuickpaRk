package com.example.alex.quickpark;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class FirstScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_login);

        Button botonregistro = (Button) findViewById(R.id.bCrearCuent);
        botonregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goregistro = new Intent(FirstScreen.this, RegistroActivity.class);
                startActivity(goregistro);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}


