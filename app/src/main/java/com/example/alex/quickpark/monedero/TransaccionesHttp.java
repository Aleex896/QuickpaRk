package com.example.alex.quickpark.monedero;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.quickpark.R;
import com.example.alex.quickpark.ajustesusuario.ListaVehiculosActivity;
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
    Context mycontext;
    public TransaccionesHttp (Context xcontext){
        mycontext = xcontext;

    }
    @Override
    protected JSONArray doInBackground(Void... voids) {
        JSONArray json = null;
        URL url;
        try{
            url = new URL("http://25.103.185.238/quickpark/php/listTransactions.php?user="+ MonederoActivity.user);
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

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        try {

            if(jsonArray!=null){
                Log.d("listaCars","Array lleno");

                TableLayout tbLayout = MonederoActivity.table;

                for(int i=0;i<jsonArray.length();i++){
                    String importe = jsonArray.getJSONObject(i).getString("importe").toString();
                    String date = jsonArray.getJSONObject(i).getString("fecha").toString();
                    String tipo = jsonArray.getJSONObject(i).getString("tipo").toString();

                    TableRow tableRow = new TableRow(mycontext);
                    tableRow.setVerticalGravity(1);
                    tbLayout.addView(tableRow);

                    TextView imp = new TextView(mycontext);
                    imp.setGravity(Gravity.CENTER);
                    imp.setText(importe+"€");
                    imp.setTextSize(20);
                    imp.setTypeface(myFont(mycontext));

                    TextView fecha = new TextView(mycontext);
                    fecha.setGravity(Gravity.CENTER);
                    fecha.setText(date);
                    fecha.setTextSize(20);
                    fecha.setTypeface(myFont(mycontext));

                    if(tipo.equals("r")){
                        imp.setTextColor(Color.GREEN);
                        fecha.setTextColor(Color.GREEN);
                    }else{
                        imp.setTextColor(Color.RED);
                        fecha.setTextColor(Color.RED);
                    }


                    tableRow.addView(imp);
                    tableRow.addView(fecha);

                }
                MonederoActivity.llListado.addView(tbLayout);
                Toast toast = Toast.makeText(mycontext.getApplicationContext(),"Transacciones cargadas",Toast.LENGTH_LONG);
                toast.show();

            }else{
                Log.d("listaCars","Array vacio");
                Toast toast1 = Toast.makeText(mycontext.getApplicationContext(),"No hay ninguna transaccion añadido",Toast.LENGTH_LONG);
                toast1.show();
            }
        } catch (Exception ex){
            Log.d("errorI",ex.toString());
        }

    }

    public static Typeface myFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Walkway SemiBold.ttf");
    }

}
