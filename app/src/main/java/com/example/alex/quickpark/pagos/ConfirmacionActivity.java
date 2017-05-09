package com.example.alex.quickpark.pagos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.alex.quickpark.maps.MapsActivity;
import com.example.alex.quickpark.R;
import com.example.alex.quickpark.ajustesusuario.AjustesActivity;

import java.io.Serializable;

public class ConfirmacionActivity extends AppCompatActivity implements Serializable {

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion);

        Button btMenu = (Button) findViewById(R.id.btVMenu);
        TextView tvRealizado = (TextView)findViewById(R.id.tVRcorrecto);

        tvRealizado.setTypeface(myFont(this));
        btMenu.setTypeface(myFont(this));

        user = getIntent().getStringExtra("user");

        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gomenu = new Intent(ConfirmacionActivity.this,AjustesActivity.class);
                gomenu.putExtra("user",user);
                startActivity(gomenu);
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });
    }

    @Override
    public void onBackPressed() {

        Intent goregistro = new Intent(ConfirmacionActivity.this, MapsActivity.class);
        goregistro.putExtra("user",user);
        startActivity(goregistro);
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    public static Typeface myFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Walkway SemiBold.ttf");
    }
}
