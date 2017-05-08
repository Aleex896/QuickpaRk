package com.example.alex.quickpark;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by JoseAntonio on 03/05/2017.
 */

public class UpdateHttp extends AsyncTask<Void,Void,String> {

    private Context mycontext;
    private Activity myActivity;

    public UpdateHttp(Context context, Activity activity){
        mycontext = context;
        myActivity = activity;
    }

    @Override
    protected String doInBackground(Void... voids) {
        URL url;
        String estat="1";

        try{
            String mail = AjustesUsActivity.email;
            String name = AjustesUsActivity.nombre;
            String apellido = AjustesUsActivity.apellido;
            String pass = AjustesUsActivity.pass;

            url = new URL("http://quickpark.000webhostapp.com/php/updateUsuario.php?mail="+mail+"&name="+name+"&ape="+apellido+"&pasw="+pass);
            HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();

            Log.d("registro",url.toString());
            int status = urlConnection.getResponseCode();

            Log.d("estatUrl","estat:"+status);

            if (status == 200) // if response code = 200 ok
            {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader br = new BufferedReader(new InputStreamReader(in));

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                Log.d("registro", sb.toString());
                urlConnection.disconnect();

                estat = sb.toString();

                Log.d("registro",estat);

            }
        }catch (Exception ex){
            Log.d("errorR",ex.toString());
        }
        return estat;
    }

    @Override
    protected void onPostExecute(String string) {
        try {
            String estatFinal = string;
            switch (estatFinal){
                case "1": Log.d("registroEstado","Error Servidor");
                    Toast toast1 = Toast.makeText(mycontext.getApplicationContext(),"Error Servidor",Toast.LENGTH_LONG);
                    toast1.show();
                    AjustesUsActivity.eTContra.setText(null);
                    AjustesUsActivity.eTContraR.setText(null);
                    break;
                case "2": Log.d("registroEstado","No Existe usuario!");
                    Toast toast2 = Toast.makeText(mycontext.getApplicationContext(),"No existe el usuario",Toast.LENGTH_LONG);
                    toast2.show();
                    AjustesUsActivity.eTContra.setText(null);
                    AjustesUsActivity.eTContraR.setText(null);
                    break;
                case "3": Log.d("Actu Estado","Actualizado Correctamente");
                    Toast toast3 = Toast.makeText(mycontext.getApplicationContext(),"Datos Actualizados Correctamente",Toast.LENGTH_LONG);
                    toast3.show();

                    AjustesUsActivity.eTNombre.setText(null);
                    AjustesUsActivity.eTApellidos.setText(null);
                    AjustesUsActivity.eTContra.setText(null);
                    AjustesUsActivity.eTContraR.setText(null);
                    AjustesUsActivity.eTCorreo.setText(null);

                    Intent goaju = new Intent(myActivity, AjustesActivity.class);
                    goaju.putExtra("user",AjustesUsActivity.email);
                    myActivity.startActivity(goaju);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
