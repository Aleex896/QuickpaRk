package com.example.alex.quickpark.conexioneshttp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.alex.quickpark.RecargaActivity;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.StringTokenizer;

import static com.example.alex.quickpark.RecargaActivity.timeusu;

/**
 * Created by Alex on 22/05/2017.
 */

public class consultaRecarga extends AsyncTask<Void,Void,JSONArray> {

    Context mycontext;
    String matricula;
    RecargaActivity myactivity;

    public static String plaza = "";
    public static String vehiculo = "";
    public static String fecha = "";
    public static String tiempo = "";
    public static String selectedhorapref;
    public static String selectedminpref;

    public consultaRecarga(Context xcontext, String xmatricula, RecargaActivity xactivity){
        matricula=xmatricula;
        mycontext = xcontext;
        myactivity = xactivity;
    }

    @Override
    protected JSONArray doInBackground(Void... params) {
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();
        URL url;
        JSONArray jsonAr=null;

        try{
            url = new URL("http://25.103.185.238/quickpark/php/consultaRecarga.php?vehiculo="+matricula+"");
            HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();

            Log.d("consulta plaza URL",url.toString());
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
                Log.d("listCar", sb.toString());
                urlConnection.disconnect();

                jsonAr = new JSONArray(sb.toString());

            }
        }catch (Exception ex)
        {
            Log.d("errorR",ex.toString());
        }
        return jsonAr;
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray){
        super.onPostExecute(jsonArray);


        try{
            for(int i=0;i<jsonArray.length();i++) {

                plaza = jsonArray.getJSONObject(i).getString("idPlaza");
                vehiculo = jsonArray.getJSONObject(i).getString("vehiculo");
                fecha = jsonArray.getJSONObject(i).getString("fecha");
                tiempo = jsonArray.getJSONObject(i).getString("tiempo");
            }


            timeusu = tiempo.toString();
            Toast.makeText(mycontext, "timeusu"+timeusu, Toast.LENGTH_SHORT).show();

            StringTokenizer toka = new StringTokenizer(timeusu,":");
            selectedhorapref = toka.nextToken();
            selectedminpref = toka.nextToken();

            Toast.makeText(mycontext, "hora"+selectedhorapref+"min"+selectedminpref, Toast.LENGTH_SHORT).show();






            /*
            myactivity.tvfecha.setText(fecha.substring(0,10).toString());
            myactivity.texto.setText(plaza.toString());
            myactivity.matricula2.setText(vehiculo.toString());
            myactivity.tVtiempolimite.setText(tiempo.toString());*/


        }
        catch(Exception ex)
        {

        }
    }



}

