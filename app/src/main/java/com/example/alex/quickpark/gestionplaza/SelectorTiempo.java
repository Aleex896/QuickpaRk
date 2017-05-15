package com.example.alex.quickpark.gestionplaza;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.alex.quickpark.R;

import java.util.Calendar;

public class SelectorTiempo extends AppCompatActivity{

    private String textoqr;
    TextView clock;
    TextView fecha;
    Button btiempo;
    TextView tiempousuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector_tiempo);

        clock = (TextView) findViewById(R.id.tVClock);
        fecha = (TextView) findViewById(R.id.tVDate);
        btiempo = (Button) findViewById(R.id.bSelecTime);

        tiempousuario = (TextView) findViewById(R.id.horausuario);


        btiempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SelectorTiempo.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        if(validarHora(selectedHour,selectedMinute,hour, minute))
                        {
                            if(comprovarHorario(selectedHour,selectedMinute,hour, minute))
                            {
                                Toast.makeText(SelectorTiempo.this, "Hora seleccionada correctamente", Toast.LENGTH_LONG).show();
                                if(selectedMinute<10)
                                {
                                    tiempousuario.setText("Puedes aparcar hasta:"+selectedHour + ":0" + selectedMinute);
                                }
                                else
                                {
                                    tiempousuario.setText("Puedes aparcar hasta:"+selectedHour + ":" + selectedMinute);
                                }

                            }

                        }

                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Selecciona hasta que hora quieres aparcar");
                mTimePicker.show();
            }
        });


        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateTextView();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();
        textoqr = getIntent().getStringExtra("textoqr");
        Toast.makeText(SelectorTiempo.this, "texto:" + textoqr, Toast.LENGTH_SHORT).show();

    } // acaba ONCREATE

    private boolean validarHora(int selectedHour, int selectedMinute, int hour, int minute)
    {
        if(selectedHour<hour)
        {
            Toast.makeText(SelectorTiempo.this, "Lo siento, la hora seleccionada es anterior a la actual", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            if(selectedHour==hour && selectedMinute<minute)
            {
                Toast.makeText(SelectorTiempo.this, "Lo siento, la hora seleccionada es anterior a la actual", Toast.LENGTH_SHORT).show();
                return false;
            }
            else
            {
                return true;
            }
        }
    }

    private boolean comprovarHorario(int selectedHour, int selectedMinute, int hour, int minute)
    {

        if(selectedHour>=8 && selectedHour<=14)
        {
            if(selectedHour==14 && selectedMinute>0)
            {
                Toast.makeText(SelectorTiempo.this, "A partir de las 14:00H puedes aparcar gratis hasta las 16:00H", Toast.LENGTH_SHORT).show();
                return false;
            }
            else
            {
                Toast.makeText(SelectorTiempo.this, "Seleccionado horario de mañana", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        else
        {
            if(selectedHour<8)
            {
                Toast.makeText(SelectorTiempo.this, "El horario de mañana empieza a las 8:00H", Toast.LENGTH_SHORT).show();
                return false;
            }
            else
            {
                if(selectedHour>=16 && selectedHour<=20)
                {
                    if(selectedHour==20 && selectedMinute>0)
                    {
                        Toast.makeText(SelectorTiempo.this, "A partir de las 20:00H puedes aparcar gratis hasta las 8:00H de mañana", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    else
                    {
                        Toast.makeText(SelectorTiempo.this, "Seleccionado horario de tarde", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                else
                {
                    if(selectedHour>20)
                    {
                        Toast.makeText(SelectorTiempo.this, "El horario de tarde acaba a las 20:00H", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    else
                    {
                        if(selectedHour>14 && selectedHour<16)
                        {
                            Toast.makeText(SelectorTiempo.this, "A partir de las 14:00H puedes aparcar gratis hasta las 16:00H", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        else
                        {
                            Toast.makeText(SelectorTiempo.this, "La hora seleccionada no coincide con ningun horario", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    }
                }
            }
        }
    }



    private void updateTextView() {
        java.util.Date noteTS = Calendar.getInstance().getTime();

        String time = "HH:mm:ss"; // 12:00
        clock.setText(android.text.format.DateFormat.format(time, noteTS));

        String date = "dd-MM-yyyy"; // 01 January 2013
        fecha.setText(android.text.format.DateFormat.format(date, noteTS));
    }
}
