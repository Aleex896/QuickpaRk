package com.example.alex.quickpark;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.suitebuilder.TestMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class AddCarActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageView ivColor;
    Spinner spColor;
    String colorSelec="";
    String matricula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        TextView tvAddCar = (TextView)findViewById(R.id.tVTusVehiculos);
        TextView tvDatos = (TextView)findViewById(R.id.tVListado);
        TextView tvMatricula = (TextView)findViewById(R.id.tVVehiculo);
        TextView tvColor = (TextView)findViewById(R.id.tVColor);
        EditText etMatricula = (EditText)findViewById(R.id.etMatricula);
        Button btAnadir = (Button)findViewById(R.id.btAdd);
        spColor = (Spinner)findViewById(R.id.spColor);
        ivColor = (ImageView)findViewById(R.id.iVCarColor);


        tvColor.setTypeface(myFont(this));
        tvMatricula.setTypeface(myFont(this));
        tvDatos.setTypeface(myFont(this));
        tvAddCar.setTypeface(myFont(this));
        etMatricula.setTypeface(myFont(this));
        btAnadir.setTypeface(myFont(this));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.lista_colores,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spColor.setAdapter(adapter);
        spColor.setOnItemSelectedListener(this);
    }

    public static Typeface myFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Walkway SemiBold.ttf");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        int colorAzul = getResources().getColor(R.color.Azul);

        switch (position) {
            case 0:((TextView)parent.getChildAt(0)).setTypeface(myFont(this));
                ((TextView)parent.getChildAt(0)).setTextColor(colorAzul);
                colorSelec = "";
                break;
            case 1:ivColor.setImageResource(R.drawable.coche_deportivo_red);
                ((TextView)parent.getChildAt(0)).setTextColor(colorAzul);
                ((TextView)parent.getChildAt(0)).setTypeface(myFont(this));
                colorSelec = ((TextView)parent.getChildAt(0)).getText().toString();
                break;
            case 2: ivColor.setImageResource(R.drawable.coche_deportivo_white);
                ((TextView)parent.getChildAt(0)).setTextColor(colorAzul);
                ((TextView)parent.getChildAt(0)).setTypeface(myFont(this));
                colorSelec = ((TextView)parent.getChildAt(0)).getText().toString();
                break;
            case 3:ivColor.setImageResource(R.drawable.coche_deportivo_blue);
                ((TextView)parent.getChildAt(0)).setTextColor(colorAzul);
                ((TextView)parent.getChildAt(0)).setTypeface(myFont(this));
                colorSelec = ((TextView)parent.getChildAt(0)).getText().toString();
                break;
            case 4:ivColor.setImageResource(R.drawable.coche_deportivo_green);
                ((TextView)parent.getChildAt(0)).setTextColor(colorAzul);
                ((TextView)parent.getChildAt(0)).setTypeface(myFont(this));
                colorSelec = ((TextView)parent.getChildAt(0)).getText().toString();
                break;
            case 5:ivColor.setImageResource(R.drawable.coche_deportivo_grey);
                ((TextView)parent.getChildAt(0)).setTextColor(colorAzul);
                ((TextView)parent.getChildAt(0)).setTypeface(myFont(this));
                colorSelec = ((TextView)parent.getChildAt(0)).getText().toString();
                break;
            case 6:ivColor.setImageResource(R.drawable.coche_deportivo_black);
                ((TextView)parent.getChildAt(0)).setTextColor(colorAzul);
                ((TextView)parent.getChildAt(0)).setTypeface(myFont(this));
                colorSelec = ((TextView)parent.getChildAt(0)).getText().toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        ivColor.setVisibility(View.INVISIBLE);
    }
}
