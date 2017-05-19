package com.example.alex.quickpark.conexioneshttp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.alex.quickpark.ajustesusuario.AddCarActivity;
import com.example.alex.quickpark.ajustesusuario.ListaVehiculosActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jose Antonio Picazos on 08/05/2017.
 */

public class AddCarHttp extends AsyncTask<Void,Void,String> {

    private Context mycontext;
    private Activity myActivity;

    public AddCarHttp(Context context, Activity activity){
        mycontext = context;
        myActivity = activity;
    }

    @Override
    protected String doInBackground(Void... params) {
        URL url;
        String estat="1";

        try{
            String mail = AddCarActivity.user;
            String matricula = AddCarActivity.matricula;
            String color = AddCarActivity.colorSelec;

            url = new URL("http://25.103.185.238/quickpark/php/addCar.php?mail="+mail+"&car="+matricula+"&color="+color);
            HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();

            Log.d("addCar",url.toString());
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
                Log.d("addCar", sb.toString());
                urlConnection.disconnect();

                estat = sb.toString();

                Log.d("addCar",estat);

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
                    break;
                case "2": Log.d("registroEstado","Este vehiculo ya existe");
                    Toast toast2 = Toast.makeText(mycontext.getApplicationContext(),"Este vehiculo ya existe",Toast.LENGTH_LONG);
                    toast2.show();
                    break;
                case "3": Log.d("Actu Estado","Insertado Correctamente");
                    Toast toast3 = Toast.makeText(mycontext.getApplicationContext(),"Insertado Correctamente",Toast.LENGTH_LONG);
                    toast3.show();

                    Intent goaju = new Intent(myActivity, ListaVehiculosActivity.class);
                    goaju.putExtra("user",AddCarActivity.user);
                    myActivity.startActivity(goaju);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
