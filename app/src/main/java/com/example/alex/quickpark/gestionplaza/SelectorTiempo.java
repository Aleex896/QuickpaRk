package com.example.alex.quickpark.gestionplaza;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
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
import com.example.alex.quickpark.R;
import com.example.alex.quickpark.conexioneshttp.VehiculoPlazaHttp;
import com.example.alex.quickpark.maps.MapsActivity;
import com.example.alex.quickpark.pagos.ConfirmacionActivity;
import com.example.alex.quickpark.pagos.PagoMonederoHttp;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SelectorTiempo extends AppCompatActivity{

    static final int REQUEST_CODE = 1;
    final String get_token = "http://quickpark.000webhostapp.com/braintreepayment/main.php";
    final String send_payment_details = "http://quickpark.000webhostapp.com/braintreepayment/mycheckout.php";
    public static String token, amount;
    HashMap<String, String> paramHash;

    private String textoqr;
    TextView clock;
    TextView fecha;
    Button btiempo,btpagar;
    TextView tiempousuario;

    public static String preciomin;
    public static String preciomax;
    public static String precioprimerahora;
    public static String preciosegundahora;
    static Context context;
    static Activity activity;

    public static String user,plaza,tiempo;
    static CharSequence[] items={"Monedero","PayPal / CreditCard"};
    public static String matricula;


    private RelativeLayout rtiket;
    private TextView hasta;
    private int totalFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector_tiempo);

        matricula = getIntent().getStringExtra("matricula");
        user = getIntent().getStringExtra("user");
        plaza = getIntent().getStringExtra("plaza");


        clock = (TextView) findViewById(R.id.tVClock);
        fecha = (TextView) findViewById(R.id.tVDate);
        btiempo = (Button) findViewById(R.id.bSelecTime);
        btpagar = (Button)findViewById(R.id.btPagar);

        context = getApplicationContext();
        activity = SelectorTiempo.this;
        new HttpRequest().execute();

        TextView test = (TextView)findViewById(R.id.test);
        hasta = (TextView)findViewById(R.id.tVPuedesAparcar);
        rtiket = (RelativeLayout)findViewById(R.id.rltiquet);
        tiempousuario = (TextView) findViewById(R.id.horausuario);

        hasta.setVisibility(View.INVISIBLE);
        rtiket.setVisibility(View.INVISIBLE);
        tiempousuario.setVisibility(View.INVISIBLE);

        preciomin = "0.2";
        preciomax = ConsultaPlazaHttp.precio;
        precioprimerahora = ConsultaPlazaHttp.precio60;
        preciosegundahora = ConsultaPlazaHttp.precio120;


        btiempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SelectorTiempo.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        if(validarHora(selectedHour,selectedMinute,hour, minute))
                        {
                            if(comprovarHorario(selectedHour,selectedMinute,hour, minute))
                            {
                                //Toast.makeText(SelectorTiempo.this, "Hora seleccionada correctamente", Toast.LENGTH_SHORT).show();
                                if(selectedMinute<10)
                                {
                                    tiempousuario.setVisibility(View.VISIBLE);
                                    hasta.setVisibility(View.VISIBLE);
                                    rtiket.setVisibility(View.VISIBLE);
                                    tiempousuario.setText(selectedHour + ":0" + selectedMinute);
                                    calculartarifa(selectedHour,selectedMinute,hour, minute, preciomin, preciomax, precioprimerahora, preciosegundahora);
                                }
                                else
                                {
                                    tiempousuario.setVisibility(View.VISIBLE);
                                    hasta.setVisibility(View.VISIBLE);
                                    rtiket.setVisibility(View.VISIBLE);
                                    tiempousuario.setText(selectedHour + ":" + selectedMinute);
                                    tiempo = selectedHour +":"+selectedMinute;
                                    calculartarifa(selectedHour,selectedMinute,hour, minute, preciomin, preciomax, precioprimerahora, preciosegundahora);
                                }
                            }
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("¿ Hasta que hora quieres aparcar ?");
                mTimePicker.show();
            }
        });

        btpagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SelectorTiempo.this);
                builder
                        .setTitle("Modo de Pago")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        //Toast.makeText(getActivity(),"Monedero", Toast.LENGTH_SHORT).show();
                                        new PagoMonederoHttp(context,activity).execute();
                                        break;
                                    case 1:
                                        onBraintreeSubmit();
                                        //Toast.makeText(getActivity(),"PayPal", Toast.LENGTH_SHORT).show();
                                        break;

                                }
                            }
                        });
                Dialog dlgF = builder.create();
                dlgF.show();
            }
        });

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
        textoqr = getIntent().getStringExtra("textoqr");
        //Toast.makeText(SelectorTiempo.this, "texto:" + textoqr, Toast.LENGTH_SHORT).show();

    }

    private boolean validarHora(int selectedHour, int selectedMinute, int hour, int minute)
    {
            if(selectedHour<hour)
            {
                Toast.makeText(SelectorTiempo.this, "Lo siento, la hora seleccionada es anterior a la actual", Toast.LENGTH_SHORT).show();
                return false;
            }
            else
            {
                if(selectedHour==hour && selectedMinute<minute)
                {
                    Toast.makeText(SelectorTiempo.this, "Lo siento, la hora seleccionada es anterior a la actual", Toast.LENGTH_SHORT).show();
                    return false;
                }
                else
                {
                    totalFinal = mas2horas(selectedHour, hour,selectedMinute,minute);
                    if(totalFinal>120)
                    {
                        Toast.makeText(SelectorTiempo.this, "Lo siento, no se pueden superar las 2 horas", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    else
                    {
                        if(totalFinal<10)
                        {
                            Toast.makeText(SelectorTiempo.this, "Lo siento, el tiempo minimo son 10 minutos", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        else
                        {
                            return true;
                        }
                    }
                }
            }
    }

    private int mas2horas(int selectedHour, int hour, int selectedMinute, int minute){

        int horas;
        int minutos;
        int total;

        int horaA;
        int minutosA;
        int totalA;

        int totalFinal;

        horas = selectedHour *60;
        minutos = selectedMinute;

        total = horas + minutos;

        horaA = hour * 60;
        minutosA = minute;
        totalA = horaA + minutosA;

        totalFinal = total - totalA;

        return  totalFinal;



    }

    private boolean comprovarHorario(int selectedHour, int selectedMinute, int hour, int minute)
    {

        if(selectedHour>=8 && selectedHour<=14)
        {
            if(selectedHour==14 && selectedMinute>0)
            {
                Toast.makeText(SelectorTiempo.this, "A partir de las 14:00H puedes aparcar gratis hasta las 16:00H", Toast.LENGTH_SHORT).show();
                return false;
            }
            else
            {
                Toast.makeText(SelectorTiempo.this, "Seleccionado horario de mañana", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        else
        {
            if(selectedHour<8)
            {
                Toast.makeText(SelectorTiempo.this, "El horario de mañana empieza a las 8:00H", Toast.LENGTH_SHORT).show();
                return false;
            }
            else
            {
                if(selectedHour>=16 && selectedHour<=20)
                {
                    if(selectedHour==20 && selectedMinute>0)
                    {
                        Toast.makeText(SelectorTiempo.this, "A partir de las 20:00H puedes aparcar gratis hasta las 8:00H de mañana", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    else
                    {
                        Toast.makeText(SelectorTiempo.this, "Seleccionado horario de tarde", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                else
                {
                    if(selectedHour>20)
                    {
                        Toast.makeText(SelectorTiempo.this, "El horario de tarde acaba a las 20:00H", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    else
                    {
                        if(selectedHour>14 && selectedHour<16)
                        {
                            Toast.makeText(SelectorTiempo.this, "A partir de las 14:00H puedes aparcar gratis hasta las 16:00H", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        else
                        {
                            Toast.makeText(SelectorTiempo.this, "La hora seleccionada no coincide con ningun horario", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    }
                }
            }
        }
    }

    private void calculartarifa(int selectedHour, int selectedMinute, int hour, int minute, String preciomin, String preciomax, String precioprimerahora, String preciosegundahora)
    {

        TextView tvFtiempo = (TextView)findViewById(R.id.tVFtiempo);
        TextView tvFtarifa = (TextView)findViewById(R.id.tVFtarifa);
        TextView tvFTotal = (TextView)findViewById(R.id.tVFtotal);

        int horasOcu;
        int minutosOcu;
        float preciousuario;
        float pprimera;
        float psegunda;

        pprimera = Float.parseFloat(precioprimerahora);
        psegunda = Float.parseFloat(preciosegundahora);

        horasOcu = totalFinal;
        tvFtiempo.setText(""+horasOcu+" min");

        if(horasOcu<=60)
        {

            tvFtarifa.setText("1.8€");
            preciousuario= horasOcu * pprimera;
            preciousuario = preciousuario/60;
        }
        else
        {
            tvFtarifa.setText("1.95€");
            preciousuario= horasOcu * psegunda;
            preciousuario = preciousuario/120;
        }

        String.format("%.2f", preciousuario);
        tvFTotal.setText(""+preciousuario+"€");
        amount = String.valueOf(preciousuario);
    }


    private void updateTextView() {
        java.util.Date noteTS = Calendar.getInstance().getTime();

        String time = "HH:mm:ss"; // 12:00
        clock.setText(android.text.format.DateFormat.format(time, noteTS));

        String date = "dd-MM-yyyy"; // 01 January 2013
        fecha.setText(android.text.format.DateFormat.format(date, noteTS));
    }

    public static class PagoDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder
                    .setTitle("Modo de Pago")
                    .setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case 0:
                                    Toast.makeText(getActivity(),"Monedero", Toast.LENGTH_SHORT).show();
                                    new PagoMonederoHttp(context,activity).execute();
                                    break;
                                case 1:
                                    Toast.makeText(getActivity(),"PayPal", Toast.LENGTH_SHORT).show();
                                    DropInRequest dropInRequest = new DropInRequest()
                                            .clientToken(token);
                                    startActivityForResult(dropInRequest.getIntent(context), REQUEST_CODE);
                                    break;

                            }
                        }
                    })
            ;

            // Create the AlertDialog object and return it
            return builder.create();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                String stringNonce = nonce.getNonce();
                Log.d("mylog",amount);
                Log.d("mylog", "Result: " + stringNonce);

                // Send payment price with the nonce
                // use the result to update your UI and send the payment method nonce to your server
                if (amount!=null) {
                    paramHash = new HashMap<>();
                    paramHash.put("amount", amount);
                    paramHash.put("nonce", stringNonce);
                    paramHash.put("user",user);
                    sendPaymentDetails();
                } else
                    Toast.makeText(SelectorTiempo.this, "Please enter a valid amount.", Toast.LENGTH_SHORT).show();

            } else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
                Log.d("mylog", "user canceled");
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.d("mylog", "Error : " + error.toString());
            }
        }
    }

    public void onBraintreeSubmit() {
        DropInRequest dropInRequest = new DropInRequest()
                .clientToken(token);
        startActivityForResult(dropInRequest.getIntent(context), REQUEST_CODE);
    }

    private void sendPaymentDetails() {
        RequestQueue queue = Volley.newRequestQueue(SelectorTiempo.this);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, send_payment_details,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains("Successful"))
                        {
                            Toast.makeText(SelectorTiempo.this, "Transaction successful", Toast.LENGTH_LONG).show();
                            new VehiculoPlazaHttp(context,activity).execute();
                            Intent goconfirm = new Intent(SelectorTiempo.this, ConfirmacionActivity.class);
                            goconfirm.putExtra("user",user);
                            startActivity(goconfirm);
                            finish();
                            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                        }
                        else Toast.makeText(SelectorTiempo.this, "Transaction failed", Toast.LENGTH_LONG).show();
                        Log.d("mylog", "Final Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("mylog", "Volley error : " + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                if (paramHash == null)
                    return null;
                Map<String, String> params = new HashMap<>();
                for (String key : paramHash.keySet()) {
                    params.put(key, paramHash.get(key));
                    Log.d("mylog", "Key : " + key + " Value : " + paramHash.get(key));
                }

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public static Typeface myFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Walkway SemiBold.ttf");
    }

    @Override
    public void onBackPressed() {
        Intent goregistro = new Intent(SelectorTiempo.this, MapsActivity.class);
        startActivity(goregistro);
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    private class HttpRequest extends AsyncTask {
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(SelectorTiempo.this, android.R.style.Theme_DeviceDefault_Dialog);
            progress.setCancelable(false);
            progress.setMessage("We are contacting our servers for token, Please wait");
            progress.setTitle("Getting token");
            progress.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            HttpClient client = new HttpClient();
            client.get(get_token, new HttpResponseCallback() {
                @Override
                public void success(String responseBody) {
                    Log.d("mylog", responseBody);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SelectorTiempo.this, "Successfully got token", Toast.LENGTH_SHORT).show();
                        }
                    });
                    token = responseBody;
                }

                @Override
                public void failure(Exception exception) {
                    final Exception ex = exception;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SelectorTiempo.this, "Failed to get token: " + ex.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progress.dismiss();
        }
    }


}
