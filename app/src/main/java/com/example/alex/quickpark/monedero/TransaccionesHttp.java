package com.example.alex.quickpark.monedero;

import android.os.AsyncTask;
import android.util.Log;

import com.example.alex.quickpark.pagos.RecargarActivity;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by JoseAntonio on 19/05/2017.
 */

public class TransaccionesHttp  extends AsyncTask<Void,Void,JSONArray> {
    @Override
    protected JSONArray doInBackground(Void... voids) {
        JSONArray json = null;
        URL url;
        try{
            url = new URL("http://25.103.185.238/quickpark/php/updateMonedero.php?user="+ RecargarActivity.user+"&amount="+RecargarActivity.amount+"&type=r");
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

                json = new JSONArray(sb.toString());

            }
        }catch (Exception ex)
        {
            Log.d("errorR",ex.toString());
        }
        return json;
    }
}
