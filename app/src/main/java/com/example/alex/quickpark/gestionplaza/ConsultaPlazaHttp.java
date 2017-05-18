package com.example.alex.quickpark.gestionplaza;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Alex on 15/05/2017.
 */

public class ConsultaPlazaHttp extends AsyncTask<Void,Void,JSONArray> {

    private String idplaza;
    private String calle;
    private String turno;
    public static String estat;
    public static String resultado;
    JSONObject json = null;


    public static String id = "";
    public static String poblacion = "";
    public static String tarifa = "";
    public static String turnof = "";
    public static String precio = "";
    public static String precio60 = "";
    public static String precio120 = "";





    GestionPlaza gp;

    Context mycontext;


    public ConsultaPlazaHttp(Context xcontext, String xidplaza, String xcalle, String xturno){
        idplaza = xidplaza;
        calle = xcalle;
        turno = xturno;
        mycontext = xcontext;
    }

    @Override
    protected JSONArray doInBackground(Void... params) {
        URL url;
        JSONArray jsonAr=null;

        try{
            url = new URL("http://25.103.185.238/quickpark/php/consultaPlaza.php?plaza="+idplaza+"&turno="+turno+"");
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
    protected void onPostExecute(JSONArray jsonArray) {
        super.onPostExecute(jsonArray);
        id = "";
        poblacion = "";
        tarifa = "";
        turnof = "";
        precio = "";
        precio60 = "";
        precio120 = "";

        try{
            for(int i=0;i<jsonArray.length();i++) {

                id = jsonArray.getJSONObject(i).getString("id");
                poblacion = jsonArray.getJSONObject(i).getString("poblacion");
                tarifa = jsonArray.getJSONObject(i).getString("tarifa");
                turnof = jsonArray.getJSONObject(i).getString("turno");
                precio = jsonArray.getJSONObject(i).getString("precio");
                precio60 = jsonArray.getJSONObject(i).getString("precio60");
                precio120 = jsonArray.getJSONObject(i).getString("precio120");
            }

            gp = new GestionPlaza();

            gp.tVcalle.setText(calle.toString());
            gp.tVidPlaza.setText("Estas en la plaza: "+id);
            gp.tvZona.setText(tarifa);
            gp.tvpreciomin.setText("0.2 €");
            gp.tVPrimeraHora.setText(precio60 + " €");
            gp.tVSegundaHora.setText(precio120 + " €");
            gp.tVPrecioMaximo.setText(precio + " €");
            gp.tVPoblacion2.setText(poblacion);

            SelectorTiempo sp = new SelectorTiempo();

            sp.preciomax = gp.tVPrecioMaximo.getText().toString();
            sp.preciomin = gp.tvpreciomin.getText().toString();
            sp.precioprimerahora = gp.tVPrimeraHora.getText().toString();
            sp.preciosegundahora = gp.tVSegundaHora.getText().toString();

            switch (turnof){
                case "M": gp.tvTurno.setText("Horario de Mañana - 08:00H a 14:00H");
                    break;
                case "T": gp.tvTurno.setText("Horario de Tarde - 16:00H a 20:00H");
            }
        }
        catch(Exception ex)
        {

        }
    }
}

