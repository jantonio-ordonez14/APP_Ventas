package com.example.esmail.app_ventas.scanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.esmail.app_ventas.MakeSale;
import com.example.esmail.app_ventas.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    public static final String EXTRA = "package com.example.esmail.app_ventas.scanner";
    private ZXingScannerView mScannerView;
    private String unidades;

    @Override
    public void onCreate(Bundle state) {

        super.onCreate(state);

        try {
            mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
            setContentView(mScannerView);                // Set the scanner view as the content view
        } catch (Exception e) {
            e.printStackTrace();
        }

        //setupActionBar();
    }
/*
    //flecha atras
    private void setupActionBar() {
        android.app.ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getResources().getString(R.string.scanner));
        }
    }*/

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {

        Log.i("QRCode", rawResult.getText());

        Toast.makeText(this, rawResult.getText(), Toast.LENGTH_SHORT).show();

        mScannerView.resumeCameraPreview(this);
        //recoge el resultado
        String resultado = rawResult.getText();
        Intent intent=new Intent(this, MakeSale.class);
        intent.putExtra(EXTRA,resultado);
        startActivity(intent);
        finish();


    }

}