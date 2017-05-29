package com.example.alex.quickpark.conexioneshttp;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.alex.quickpark.R;
import com.example.alex.quickpark.RecargaActivity;
import com.example.alex.quickpark.gestionplaza.SelectorTiempo;
import com.example.alex.quickpark.pagos.ConfirmacionActivity;

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

    public static final int NOTIFICACION_ID=1;

    public VehiculoPlazaHttp(Context xcontext, Activity xactivity){
        mycontext = xcontext;
        myactivity = xactivity;
    }
    @Override
    protected String doInBackground(Void... voids) {
        String res= null;
        URL url;
        try{
            url = new URL("http://25.103.185.238/quickpark/php/insertVehiculoPlaza.php?matricula="+ SelectorTiempo.matricula+"&plaza="+SelectorTiempo.plaza+"&tiempo="+ SelectorTiempo.tiempo +"&amount="+SelectorTiempo.amount +"&user="+SelectorTiempo.user +"&type="+SelectorTiempo.type);
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

                    generarnotificacion();

                    guardardatos();


                    Intent goresumen = new Intent(myactivity, ConfirmacionActivity.class);
                    goresumen.putExtra("user",SelectorTiempo.user);
                    SharedPreferences sharedPreferences = mycontext.getSharedPreferences("preferencias",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user", SelectorTiempo.user);
                    editor.commit();
                    myactivity.startActivity(goresumen);
                    break;
            }
        }catch (Exception ex){
            Log.d("errorI",ex.toString());
        }
    }

    private void guardardatos(){
        SharedPreferences sharedPreferences = mycontext.getSharedPreferences("preferencias",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("matricula", SelectorTiempo.matricula);
        editor.commit();
    }

    private void generarnotificacion()
    {
        Notification.Builder builder = new Notification.Builder(mycontext);

        builder.setContentTitle("Quick Park");
        builder.setContentText("Pulsa para recargar tu tiempo");
        builder.setSmallIcon(R.drawable.icono_app);

        Intent gorecarga = new Intent(myactivity, RecargaActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mycontext, 0, gorecarga,0);

        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(false);

        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_NO_CLEAR;

        NotificationManager manager = (NotificationManager) mycontext.getSystemService(mycontext.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICACION_ID,notification);
    }
}
