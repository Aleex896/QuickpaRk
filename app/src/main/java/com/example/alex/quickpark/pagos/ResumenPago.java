package com.example.alex.quickpark.pagos;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.example.alex.quickpark.R;
import com.example.alex.quickpark.ajustesusuario.AjustesActivity;
import com.example.alex.quickpark.gestionplaza.SelectorTiempo;
import com.example.alex.quickpark.monedero.MonederoActivity;

import java.io.Serializable;

public class ResumenPago extends AppCompatActivity implements Serializable{

    Button bVolver;
    String user;
    static Context context;
    static CharSequence[] items={"Monedero","PayPal / Tarjeta Crédit"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_pago);

        context.getApplicationContext();

        bVolver = (Button)findViewById(R.id.btVMenu);

        bVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goajustes = new Intent(ResumenPago.this, AjustesActivity.class);
                goajustes.putExtra("user",user);
                startActivity(goajustes);
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);

            }
        });
    }


    public static Typeface myFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Walkway SemiBold.ttf");
    }


}
