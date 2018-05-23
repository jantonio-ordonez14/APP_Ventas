package com.example.esmail.app_ventas.scanner;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.esmail.app_ventas.MakeSale;
import com.example.esmail.app_ventas.R;
import com.honeywell.decodemanager.DecodeManager;
import com.honeywell.decodemanager.barcode.CommonDefine;
import com.honeywell.decodemanager.barcode.DecodeResult;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.esmail.app_ventas.scanner.ScanActivity.EXTRA;

public class BarcodeScan extends AppCompatActivity {

    public final static String EXTRA_NOMBRE = "com.example.esmail.inventario.unovarios.esmail";
    private String name = "0";

    private final int ID_SCANSETTING = 0x12;
    private final int ID_CLEAR_SCREEN = 0x13;

    private DecodeManager mDecodeManager = null;
    private final int SCANKEY = 0x94;
    private final int SCANTIMEOUT = 3000;
    long mScanAccount = 0;
    private boolean mbKeyDown = true;
    private Handler mDoDecodeHandler;

    class DoDecodeThread extends Thread {
        public void run() {
            Looper.prepare();
            mDoDecodeHandler = new Handler();
            Looper.loop();
        }
    }

    private DoDecodeThread mDoDecodeThread;

    // private String strResultM;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_barcode_scan);
        initializeUI();
        mDoDecodeThread = new DoDecodeThread();
        mDoDecodeThread.start();

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                try {
                    if (mbKeyDown) {
                        DoScan();
                        mbKeyDown = false;
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return true;
            case KeyEvent.KEYCODE_BACK:
                this.finish();
                return true;
            case KeyEvent.KEYCODE_UNKNOWN:
                if (event.getScanCode() == SCANKEY || event.getScanCode() == 87 || event.getScanCode() == 88) {
                    try {
                        if (mbKeyDown) {
                            DoScan();
                            mbKeyDown = false;
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                try {
                    mbKeyDown = true;
                    cancleScan();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return true;
            case KeyEvent.KEYCODE_BACK:
                this.finish();
                return true;

            case KeyEvent.KEYCODE_UNKNOWN:
                if (event.getScanCode() == SCANKEY || event.getScanCode() == 87 || event.getScanCode() == 88) {
                    try {
                        mbKeyDown = true;
                        cancleScan();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mDecodeManager == null) {
            try {
                mDecodeManager = new DecodeManager(this, ScanResultHandler);
            } catch (Exception e) {
                e.printStackTrace();
                onStop();
                Toast.makeText(this, "Error. Su dispositivo no dispone de lector", Toast.LENGTH_SHORT).show();
            }
        }

        SoundManager.getInstance();
        SoundManager.initSounds(getBaseContext());
        SoundManager.loadSounds();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();

    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, ID_SCANSETTING, 0, R.string.SymbologySettingMenu);
        menu.add(0, ID_CLEAR_SCREEN, 1, R.string.ClearScreenMenu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case ID_SCANSETTING: {
                mDecodeManager.getSymConfigActivityOpeartor().start();
                return true;
            }
            case ID_CLEAR_SCREEN: {

                return true;
            }
            default:
                return false;
        }
    }*/

    @Override
    protected void onPause() {
        super.onPause();

        if (mDecodeManager != null) {
            try {
                mDecodeManager.release();
                mDecodeManager = null;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        SoundManager.cleanup();
        if (mDecodeManager != null) {
            try {

                mDecodeManager.release();
                mDecodeManager = null;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void doScanInBackground() {
        mDoDecodeHandler.post(new Runnable() {

            @Override
            public void run() {
                if (mDecodeManager != null) {
                    // TODO Auto-generated method stub
                    try {
                        synchronized (mDecodeManager) {
                            mDecodeManager.doDecode(SCANTIMEOUT);
                        }

                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void initializeUI() {
        final ImageButton btn2 =  findViewById(R.id.btn_scanner);
        btn2.setOnTouchListener(new Button.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                final int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:

                        try {
                            DoScan();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        break;
                    case MotionEvent.ACTION_UP:


                        try {
                            cancleScan();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        break;
                }
                return true;
            }
        });

    }

    private void DoScan() throws Exception {
        doScanInBackground();
    }

    private Handler ScanResultHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DecodeManager.MESSAGE_DECODER_COMPLETE:
                    mScanAccount++;
                    String strDecodeResult = "";
                    DecodeResult decodeResult = (DecodeResult) msg.obj;
                    SoundManager.playSound(1, 1);

                    byte codeid = decodeResult.codeId;
                    byte aimid = decodeResult.aimId;
                    int iLength = decodeResult.length;

                    strDecodeResult = "Decode Result::" + decodeResult.barcodeData + "\r\n" + "codeid::" + "(" + String.valueOf((char) codeid) + "/" + String.valueOf((char) aimid) + ")" + "\r\n" + "Length:: " + iLength
                            + "  " + "Count:: " + mScanAccount + "\r\n";
                    setParameters(decodeResult.barcodeData);
                    break;

                case DecodeManager.MESSAGE_DECODER_FAIL: {
                    SoundManager.playSound(2, 1);
                    Toast.makeText(getApplicationContext(), "Scan fail", Toast.LENGTH_LONG).show();
                }
                break;
                case DecodeManager.MESSAGE_DECODER_READY: {
                    ArrayList<Integer> arry = mDecodeManager.getSymConfigActivityOpeartor().getAllSymbologyId();
                    try {
                        mDecodeManager.enableSymbology(CommonDefine.SymbologyID.SYM_ALL);
                    } catch (RemoteException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };

    private void setParameters(String barcodeData) {
        if (barcodeData.length() < 13) {
            barcodeData= obtenerCodigoBarrasCompleto(barcodeData);
        }
        Intent intent=new Intent(this, MakeSale.class);
        intent.putExtra(EXTRA,barcodeData);
        startActivity(intent);
        finish();
    }

    /**
     * Cálculo del código de control
     */
    private int calculadorCodigoControl(String firstTwelveDigits) {
        char[] charDigits = firstTwelveDigits.toCharArray();
        int[] ean13 =
                {
                        1, 3
                };
        int sum = 0;
        for (int i = 0; i < charDigits.length; i++) {
            sum += Character.getNumericValue(charDigits[i]) * ean13[i % 2];
        }
        int checksum = 10 - sum % 10;

        if (checksum == 10)
            checksum = 0;

        return checksum;
    }

    /**
     * Metodo para obtener el codigo de barras completo
     *
     * @param num
     * @return
     */
    private String obtenerCodigoBarrasCompleto(String num) {

        int codigoControl = calculadorCodigoControl(num);
        //suma el codigo de control
        String codigoBarras = num + codigoControl;

        return codigoBarras;
    }

    private void cancleScan() throws Exception {
        mDecodeManager.cancelDecode();
    }


}
