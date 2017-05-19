package com.example.alex.quickpark.gestionplaza;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.alex.quickpark.R;
import com.example.alex.quickpark.SelectCarActivity;
import com.example.alex.quickpark.qr.IniciarQR;

import java.util.Calendar;

public class GestionPlaza extends AppCompatActivity {

    private String textoqr;
    private String idPlaza;
    private String calle;
    private String turno;
    private Context context;
    public static String resultado;

    public static String user;

    public static TextView tvZona;
    public static TextView tvpreciomin;
    public static TextView tVPrimeraHora;
    public static TextView tVSegundaHora;
    public static TextView tVPrecioMaximo;
    public static TextView tVcalle;
    public static TextView tVPoblacion2;
    public static TextView tvTurno;
    public static TextView tVidPlaza;

    private String preciomin;
    private String preciomax;
    private String precioprimerahora;
    private String preciosegundahora;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_plaza);

        textoqr = getIntent().getStringExtra("textoqr");
        user = getIntent().getStringExtra("user");

        Button continuar = (Button)findViewById(R.id.bContinuar);
        tvZona = (TextView)findViewById(R.id.tVZona);
        tvpreciomin = (TextView)findViewById(R.id.tVPrecioMinimo2);
        tVPrimeraHora = (TextView)findViewById(R.id.tVPrimeraHora2);
        tVSegundaHora = (TextView)findViewById(R.id.tVSegundaHora2);
        tVPrecioMaximo = (TextView)findViewById(R.id.tVPrecioMaximo2);
        tVcalle = (TextView)findViewById(R.id.tVcalle);
        tVPoblacion2 = (TextView)findViewById(R.id.tVPoblacion2);
        tvTurno = (TextView)findViewById(R.id.tVTurno);
        tVidPlaza = (TextView)findViewById(R.id.tVidplaza);

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goseleccar = new Intent(GestionPlaza.this, SelectCarActivity.class);
                goseleccar.putExtra("user",user);
                startActivity(goseleccar);
                finish();
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });



        idPlaza = textoqr.substring(0,6);
        calle = textoqr.substring(textoqr.lastIndexOf(";")+1);

        Calendar c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR_OF_DAY);

        if(hora<14)
        {
            turno = "M";
        }
        else
        {
            turno = "T";
        }

        try {
            new ConsultaPlazaHttp(context,idPlaza,calle,turno).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onBackPressed() {
        Intent goregistro = new Intent(GestionPlaza.this, IniciarQR.class);
        startActivity(goregistro);
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }






}
