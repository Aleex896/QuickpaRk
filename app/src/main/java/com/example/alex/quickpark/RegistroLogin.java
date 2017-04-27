package com.example.alex.quickpark;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class RegistroLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_login);

        Button botonregistro = (Button) findViewById(R.id.bCrearCuent);
        botonregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo : intent a la otra screen
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        View windowScreen = getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // ESCONDE BARRA DE NAVEGACION INFERIOR
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        int uiOption2 = View.SYSTEM_UI_FLAG_IMMERSIVE;
        //windowScreen.setSystemUiVisibility(uiOption2);
        windowScreen.setSystemUiVisibility(uiOptions);
    }
}


