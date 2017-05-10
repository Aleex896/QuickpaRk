package com.example.alex.quickpark.monedero;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;

import com.example.alex.quickpark.R;
import com.example.alex.quickpark.ajustesusuario.AjustesActivity;
import com.example.alex.quickpark.pagos.PagoActivity;
import com.example.alex.quickpark.pagos.RecargarActivity;

public class MonederoActivity extends AppCompatActivity {

    Button btRecargar,bVolver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monedero);

        btRecargar = (Button)findViewById(R.id.btRecargar);
        bVolver = (Button)findViewById(R.id.botonAtras);

        bVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goajustes = new Intent(MonederoActivity.this, AjustesActivity.class);
                //goajustes.putExtra("user",user);
                startActivity(goajustes);
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });

        btRecargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goajustes = new Intent(MonederoActivity.this, RecargarActivity.class);
                //goajustes.putExtra("user",user);
                startActivity(goajustes);
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });
    }


}
