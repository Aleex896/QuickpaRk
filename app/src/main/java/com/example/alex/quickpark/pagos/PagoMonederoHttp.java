package com.example.alex.quickpark.pagos;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.alex.quickpark.gestionplaza.SelectorTiempo;
import com.example.alex.quickpark.monedero.MonederoActivity;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jose Antonio Picazos on 21/05/2017.
 */

public class PagoMonederoHttp extends AsyncTask<Void,Void,String> {

    Context mycontext;

    public PagoMonederoHttp (Context xcontext){
        mycontext = xcontext;

    }

    @Override
    protected String doInBackground(Void... params) {
        String res= null;
        URL url;
        try{
            url = new URL("http://25.103.185.238/quickpark/php/pagoMonedero.php?user="+ SelectorTiempo.user+"&amount="+SelectorTiempo.amount);
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
                case "1": Toast.makeText(mycontext,"Monedero", Toast.LENGTH_SHORT).show();
                    Log.d("pagoM","Error Sevidor");
                    break;
                case "2": Toast.makeText(mycontext,"Saldo del Monedero 0€", Toast.LENGTH_SHORT).show();
                    Log.d("pagoM","Saldo 0€");
                    break;
                case "3": Toast.makeText(mycontext,"No tienes suficiente saldo", Toast.LENGTH_SHORT).show();
                    Log.d("pagoM","Saldo Insuficiente");
                    break;
                case "4": Toast.makeText(mycontext,"Pago Correcto", Toast.LENGTH_SHORT).show();
                    Log.d("pagoM","Pago Correcto");
                    break;
            }
        }catch (Exception ex){
            Log.d("errorI",ex.toString());
        }
    }
}
