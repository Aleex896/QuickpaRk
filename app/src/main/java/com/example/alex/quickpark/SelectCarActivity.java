package com.example.alex.quickpark;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.example.alex.quickpark.conexioneshttp.SelectCarHttp;

import java.io.Serializable;

public class SelectCarActivity extends AppCompatActivity implements Serializable {

    public static String user;
    public static LinearLayout llListado;
    public static TableLayout table;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_car);

        user = getIntent().getStringExtra("user");
        llListado = (LinearLayout) findViewById(R.id.llListado);
        table = (TableLayout) findViewById(R.id.tablelayout);

        try {
            context = getApplicationContext();
            new SelectCarHttp(context, SelectCarActivity.this).execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}

