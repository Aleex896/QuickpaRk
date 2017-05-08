package com.example.alex.quickpark;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by JoseAntonio on 03/05/2017.
 */

public class InicioSessionHttp extends AsyncTask<Void,Void,JSONArray> {

    private Activity myActivity;
    private Context mycontext;
    private String passU;
    private byte[] passBy;

    //private CifrarDescifrar descifrar = new CifrarDescifrar();

    public InicioSessionHttp(Context context, Activity activity){
        mycontext = context;
        myActivity = activity;
    }

    @Override
    protected JSONArray doInBackground(Void... voids) {
        URL url;

        JSONArray jsonAr=null;


        try{
            String mail = IniciarSesionActivity.userMail;
            passU =IniciarSesionActivity.password;


            url = new URL("http://quickpark.000webhostapp.com/php/inicioSession.php?user="+mail);
            HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();

            Log.d("inicio",url.toString());
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
                Log.d("inicio", sb.toString());
                urlConnection.disconnect();

                jsonAr =new JSONArray( sb.toString());

            }
        }catch (Exception ex){
            Log.d("errorI",ex.toString());
        }
        return jsonAr;
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        try {
            String passw = jsonArray.getJSONObject(0).getString("passbd").toString();
            /*String passw;
            passBy = jsonArray.getJSONObject(0).getString("passbd").getBytes("UTF-8");
            Log.d("inicioEstado",passBy.toString());
            passw = descifrar.descifra(passBy);
            Log.d("inicioEstado",passw);*/

            if(passw.equals(passU)){
                Log.d("inicioEstado","Contraseñas correctas");
                Toast toast = Toast.makeText(mycontext.getApplicationContext(),"Session Iniciada",Toast.LENGTH_LONG);
                toast.show();
                IniciarSesionActivity.eTContra.setText(null);
                IniciarSesionActivity.correo.setText(null);

                Intent gomap = new Intent(myActivity, MapsActivity.class);
                gomap.putExtra("user",IniciarSesionActivity.userMail);
                myActivity.startActivity(gomap);

            }else{
                Log.d("inicioEstado","Datos incorrectos");
                Toast toast1 = Toast.makeText(mycontext.getApplicationContext(),"Correo o Contraseña Incorrectos",Toast.LENGTH_LONG);
                toast1.show();
                IniciarSesionActivity.eTContra.setText(null);
                IniciarSesionActivity.correo.setText(null);
            }
        } catch (Exception ex){
            Log.d("errorI",ex.toString());
        }

    }
}
