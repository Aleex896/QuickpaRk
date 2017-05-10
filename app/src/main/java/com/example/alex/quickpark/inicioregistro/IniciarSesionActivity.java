package com.example.alex.quickpark.inicioregistro;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.quickpark.screeninicial.FirstScreen;
import com.example.alex.quickpark.R;
import com.example.alex.quickpark.conexioneshttp.InicioSessionHttp;

import org.xmlpull.v1.XmlSerializer;

import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;
import java.io.Serializable;

public class IniciarSesionActivity extends AppCompatActivity implements Serializable{

    public static String userMail;
    public static String password;
    private Context context;
    public static boolean passok;
    public static EditText correo;
    public static EditText eTContra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciarses);

        Button bVolver = (Button) findViewById(R.id.bVolver);
        TextView tvIni = (TextView) findViewById(R.id.tVIniciar);
        TextView tvInici = (TextView) findViewById(R.id.tVIniciaSesiondatos);
        correo = (EditText) findViewById(R.id.edTCorreoElectronico);
        eTContra = (EditText) findViewById(R.id.edTContraseña);
        Button biniciar = (Button) findViewById(R.id.bIniciar);

        eTContra.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);

        tvIni.setTypeface(myFont(this));
        tvInici.setTypeface(myFont(this));
        correo.setTypeface(myFont(this));
        eTContra.setTypeface(myFont(this));
        biniciar.setTypeface(myFont(this));

        biniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userMail = correo.getText().toString();
                password = eTContra.getText().toString();

                if(!userMail.equals("") && !password.equals("")){
                    context = getApplicationContext();
                    generarXML();
                    new InicioSessionHttp(context, IniciarSesionActivity.this).execute();

                }
                else
                {
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Contraseñas Incorrectas", Toast.LENGTH_LONG);
                    toast1.show();
                }
            }
        });


        bVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goregistro = new Intent(IniciarSesionActivity.this, FirstScreen.class);
                startActivity(goregistro);
                finish();
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent goregistro = new Intent(IniciarSesionActivity.this, FirstScreen.class);
        startActivity(goregistro);
        finish();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }



    // FUNCION PARA ASIGNAR LA FUENTE
    public static Typeface myFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Walkway SemiBold.ttf");
    }

    public void generarXML(){

        try {
            //Creamos el serializer
            XmlSerializer ser = Xml.newSerializer();

            //Creamos un fichero en memoria interna
            OutputStreamWriter fout =
                    new OutputStreamWriter(
                            openFileOutput("prueba_pull.xml",
                                    Context.MODE_PRIVATE));
            //Asignamos el resultado del serializer al fichero
            ser.setOutput(fout);
            //Construimos el XML
            ser.startTag("", "usuario");

            ser.startTag("", "mail");
            ser.text(userMail);
            ser.endTag("", "mail");

            ser.endTag("", "usuario");

            ser.endDocument();

            fout.close();

            Log.d("xml","creado");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
