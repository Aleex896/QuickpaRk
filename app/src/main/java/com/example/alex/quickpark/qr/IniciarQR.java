package com.example.alex.quickpark.qr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.alex.quickpark.R;
import com.example.alex.quickpark.maps.MapsActivity;
import com.google.android.gms.vision.barcode.Barcode;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

public class IniciarQR extends AppCompatActivity {

    private String textoqr;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_qr);

        Button iniqr = (Button) findViewById(R.id.button2);

        user = getIntent().getStringExtra("user");

        iniqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);

                Intent intent = new Intent(IniciarQR.this, ScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            if(data!= null)
            {
                final Barcode barcode = data.getParcelableExtra("barcode");
                textoqr = barcode.displayValue;
                Toast.makeText(IniciarQR.this, "texto:"+barcode.displayValue, Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onBackPressed() {
        Intent goregistro = new Intent(IniciarQR.this, MapsActivity.class);
        goregistro.putExtra("user",user);
        startActivity(goregistro);
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
