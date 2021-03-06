package com.example.alex.quickpark.monedero;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.quickpark.ajustesusuario.ListaVehiculosActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by JoseAntonio on 19/05/2017.
 */

public class MonederoHttp extends AsyncTask<Void,Void,String> {

    Context mycontext;

    public MonederoHttp(Context xcontext){
        mycontext = xcontext;
    }
    @Override
    protected String doInBackground(Void... voids) {
        URL url;
        String valor=null;

        try{
            url = new URL("http://25.103.185.238/quickpark/php/consultaMonedero.php?user="+MonederoActivity.user);
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

                valor = sb.toString();

            }
        }catch (Exception ex)
        {
            Log.d("errorR",ex.toString());
        }
        return valor;
    }

    @Override
    protected void onPostExecute(String saldo) {
        super.onPostExecute(saldo);

        try {
            if(saldo!=null){
                Log.d("monedero",saldo);
                String valor = saldo.substring(1,saldo.length()-1);
                MonederoActivity.tvSaldo.setText(valor+"€");
            }else{
                Log.d("monedero","Array vacio");
            }
        } catch (Exception ex){
            Log.d("errorI",ex.toString());
        }
    }
}
