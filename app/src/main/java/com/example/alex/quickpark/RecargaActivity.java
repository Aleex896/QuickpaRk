package com.example.alex.quickpark;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.internal.HttpClient;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.example.alex.quickpark.gestionplaza.SelectorTiempo;
import com.example.alex.quickpark.monedero.UpdateMonedero;
import com.example.alex.quickpark.pagos.ConfirmacionActivity;
import com.example.alex.quickpark.pagos.RecargarActivity;
import com.google.android.gms.vision.text.TextRecognizer;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class RecargaActivity extends AppCompatActivity {

    String matriculapref;
    SharedPreferences sharedPreferences;

    private String usuario;

    public static String timeusu;

    public static TextView texto;
    public static TextView nusuario;
    public static TextView titulovigi;
    public static TextView matricula;
    public static TextView matricula2;
    public static TextView tVtiempolimite;

    public String selectedhorapref;
    public String selectedminpref;

    public static RelativeLayout relativeLayout;
    private String textoqr;
    private Context context;
    private TextRecognizer detector;
    private String idPlaza;

    public static final int NOTIFICACION_ID=1;


    public static TextView tvfecha;

    int tiempofinal;


    String tiempoUsuario;

    TextView clock;
    TextView fecha;

    private int horasfinales;
    private int minutosfinales;

    Button bPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recarga);
        context = this.getApplicationContext();

        bPay = (Button)findViewById(R.id.btRecargar);

        sharedPreferences = getSharedPreferences("preferencias", MODE_PRIVATE);
        matriculapref = sharedPreferences.getString("matricula", String.valueOf(matricula));

        TextView tvmatricula = (TextView)findViewById(R.id.matricula);
        tvmatricula.setText(matriculapref);


        // ----------------- CONTADOR HORA SUPERIOR --------------------------
                clock = (TextView) findViewById(R.id.tVClock);
                fecha = (TextView) findViewById(R.id.tVDate);
        // -------------------------------------------------------------------


        texto = (TextView) findViewById(R.id.textoqr); // NOMBRE DE LA PLAZA
        tvfecha = (TextView) findViewById(R.id.fechausuario); // TV FECHA ABAJO IZQUIERDA
        matricula2 = (TextView) findViewById(R.id.matricula2); // MATRICULA CUADRO INFERIOR
        tVtiempolimite = (TextView) findViewById(R.id.tVtiempolimite); // TV TIEMPO ABAJO DERECHA

        // quita la notificacion inicial
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(1);

        SharedPreferences sharedPreferences = this.getSharedPreferences("preferencias",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("plaza", texto.getText().toString());
        editor.commit();


        // THREAD ACTUALIZA EL TIMER DE LA HORA SUPERIOR
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateTextView();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();

        try{
            new consultaRecarga(context, matriculapref, RecargaActivity.this).execute();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        bPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecargaActivity.this, SelectorTiempo.class);

                startActivity(intent);

            }
        });

    }


    private void updateTextView() {
        java.util.Date noteTS = Calendar.getInstance().getTime();

        String time = "HH:mm:ss"; // 12:00
        clock.setText(android.text.format.DateFormat.format(time, noteTS));

        String date = "dd-MM-yyyy"; // 01 January 2013
        fecha.setText(android.text.format.DateFormat.format(date, noteTS));
    }


    public class consultaRecarga extends AsyncTask<Void,Void,JSONArray> {

        Context mycontext;
        String matricula;
        RecargaActivity myactivity;

        public String plaza = "";
        public String vehiculo = "";
        public String fecha = "";
        public String tiempo = "";

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

            if(android.os.Debug.isDebuggerConnected())
                android.os.Debug.waitForDebugger();


            try{
                for(int i=0;i<jsonArray.length();i++) {

                    plaza = jsonArray.getJSONObject(i).getString("idPlaza");
                    vehiculo = jsonArray.getJSONObject(i).getString("vehiculo");
                    fecha = jsonArray.getJSONObject(i).getString("fecha");
                    tiempo = jsonArray.getJSONObject(i).getString("tiempo");
                }


                timeusu = tiempo.toString();
                //Toast.makeText(mycontext, "timeusu"+timeusu, Toast.LENGTH_SHORT).show();

                StringTokenizer toka = new StringTokenizer(timeusu,":");
                selectedhorapref = toka.nextToken();
                selectedminpref = toka.nextToken();

                //Toast.makeText(mycontext, "hora"+selectedhorapref+"min"+selectedminpref, Toast.LENGTH_SHORT).show();


            tvfecha.setText(fecha.substring(0,10).toString());
            texto.setText(plaza.toString());
            matricula2.setText(vehiculo.toString());
            tVtiempolimite.setText(tiempo.toString());

                horasfinales = calcularminutos(selectedhorapref, selectedminpref);
                Log.d("tiempofinal", String.valueOf(horasfinales));

                generarnotificacion();

                // THREAD PARA COMPROBAR CADA MINUTO EL TIEMPO QUE QUEDA
                Thread comp = new Thread() {
                    @Override
                    public void run() {
                        try {
                            while (!isInterrupted()) {
                                Thread.sleep(60000);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        comprovarHora(horasfinales);
                                    }
                                });
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                };
                comp.start();

            }
            catch(Exception ex)
            {

            }
        }

        private void comprovarHora(int xtiempo){
            int tiempo = xtiempo;

            tiempo = tiempo - 1;

            if(tiempo<=5)
            {
                NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(1);

                generarnotificacion();

                MediaPlayer mp = MediaPlayer.create(RecargaActivity.this, R.raw.vehicle039);
                mp.start();
            }
            else
            {
                if(tiempo==0)
                {
                    NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.cancel(1);
                }
            }
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

        private int calcularminutos(String xselectedhorapref, String xselectedminpref){
            java.util.Date noteTS = Calendar.getInstance().getTime();
            String horasis;
            String minutossis;

            String selectedhora;
            String selectedmin;
            selectedhora = xselectedhorapref;

            if(xselectedminpref.substring(0,1)=="0")
            {
                selectedmin= xselectedminpref.substring(1,2);
            }
            else
            {
                selectedmin= xselectedminpref;
            }


            String time = "HH:mm:ss"; // 12:00
            horasis = android.text.format.DateFormat.format(time, noteTS).toString().substring(0,2);
            if(android.text.format.DateFormat.format(time, noteTS).toString().substring(3,4)=="0")
            {
                minutossis = android.text.format.DateFormat.format(time, noteTS).toString().substring(4,5);
            }
            else
            {
                minutossis = android.text.format.DateFormat.format(time, noteTS).toString().substring(3,5);
            }

            int horas;
            int minutos;
            int total;

            horas = Integer.parseInt(selectedhora) * 60;
            minutos = Integer.parseInt(selectedmin);
            total = horas + minutos;

            int horaA;
            int minutosA;
            int totalA;

            horaA = Integer.parseInt(horasis) * 60;
            minutosA = Integer.parseInt(minutossis);
            totalA = horaA + minutosA;

            tiempofinal = total - totalA;

            return tiempofinal;
        }


    }
}





