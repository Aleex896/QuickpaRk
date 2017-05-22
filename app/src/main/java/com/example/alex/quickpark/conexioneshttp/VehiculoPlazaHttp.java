package com.example.alex.quickpark.conexioneshttp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.alex.quickpark.SelectCarActivity;
import com.example.alex.quickpark.gestionplaza.SelectorTiempo;
import com.example.alex.quickpark.pagos.ConfirmacionActivity;
import com.example.alex.quickpark.pagos.ResumenPago;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by JoseAntonio on 22/05/2017.
 */

public class VehiculoPlazaHttp extends AsyncTask<Void,Void,String> {

    Context mycontext;
    Activity myactivity;

    public VehiculoPlazaHttp(Context xcontext, Activity xactivity){
        mycontext = xcontext;
        myactivity = xactivity;
    }
    @Override
    protected String doInBackground(Void... voids) {
        String res= null;
        URL url;
        try{
            url = new URL("http://25.103.185.238/quickpark/php/insertVehiculoPlaza.php?matricula="+ SelectorTiempo.matricula+"&plaza="+SelectorTiempo.plaza+"&tiempo="+ SelectorTiempo.tiempo +"&amount="+SelectorTiempo.amount +"&user="+SelectorTiempo.user);
            HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();

            Log.d("pagoM URL",url.toString());
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
                Log.d("pagoM", sb.toString());
                urlConnection.disconnect();

                res = sb.toString();

            }
        }catch (Exception ex)
        {
            Log.d("errorPago",ex.toString());
        }
        return res;
    }

    @Override
    protected void onPostExecute(String string) {
        try {
            String resultado = string;

            switch (resultado){
                case "1":Log.d("pagoM","Error Sevidor");
                    break;
                case "2":  Log.d("pagoM","Vehiculo Plaza Correcto");
                    Intent goresumen = new Intent(myactivity, ConfirmacionActivity.class);
                    goresumen.putExtra("user",SelectorTiempo.user);
                    myactivity.startActivity(goresumen);
                    break;
            }
        }catch (Exception ex){
            Log.d("errorI",ex.toString());
        }
    }
}
