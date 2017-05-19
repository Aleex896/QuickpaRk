package com.example.alex.quickpark.ajustesusuario;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.quickpark.R;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by JoseAntonio on 09/05/2017.
 */

public class ListaCarsHttp extends AsyncTask<Void,Void,JSONArray> {

    private Context mycontext;
    private Activity myActivity;

    public ListaCarsHttp(Context context, Activity activity){
        mycontext = context;
        myActivity = activity;
    }

    @Override
    protected JSONArray doInBackground(Void... params) {
        URL url;
        JSONArray jsonAr=null;

        try{
            String mail = ListaVehiculosActivity.user;

            url = new URL("http://25.103.185.238/quickpark/php/listaCars.php?mail="+mail);
            HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();

            Log.d("listCar",url.toString());
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
        }catch (Exception ex){
            Log.d("errorR",ex.toString());
        }
        return jsonAr;
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        try {

            if(jsonArray!=null){
                Log.d("listaCars","Array lleno");

                TableLayout tbLayout = ListaVehiculosActivity.table;

                for(int i=0;i<jsonArray.length();i++){
                    String matricula = jsonArray.getJSONObject(i).getString("matricula").toString();
                    String color = jsonArray.getJSONObject(i).getString("color").toString();

                    TableRow tableRow = new TableRow(mycontext);
                    tableRow.setVerticalGravity(1);
                    tbLayout.addView(tableRow);

                    TextView matri = new TextView(mycontext);
                    matri.setGravity(Gravity.CENTER);
                    matri.setText(matricula);
                    matri.setTextSize(20);
                    matri.setTypeface(myFont(mycontext));
                    tableRow.addView(matri);

                    TextView colo = new TextView(mycontext);
                    colo.setGravity(Gravity.CENTER);
                    colo.setText(color);
                    colo.setTextSize(20);
                    colo.setTypeface(myFont(mycontext));
                    tableRow.addView(colo);

                }
                ListaVehiculosActivity.llListado.addView(tbLayout);
                Toast toast = Toast.makeText(mycontext.getApplicationContext(),"Vehiculos cargados",Toast.LENGTH_LONG);
                toast.show();

            }else{
                Log.d("listaCars","Array vacio");
                Toast toast1 = Toast.makeText(mycontext.getApplicationContext(),"No hay ningun vehiculo añadido",Toast.LENGTH_LONG);
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
