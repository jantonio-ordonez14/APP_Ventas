package com.example.esmail.app_ventas;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.esmail.app_ventas.fragments.InitialFragment;
import com.example.esmail.app_ventas.fragments.MakeSaleFragment1;
import com.example.esmail.app_ventas.fragments.MakeSaleFragment2;
import com.example.esmail.app_ventas.modelos.CabeceraPedido;
import com.example.esmail.app_ventas.scanner.BarcodeScan;
import com.example.esmail.app_ventas.scanner.ScanActivity;
import com.example.esmail.app_ventas.sqlite.DatabaseOperations;

import static com.example.esmail.app_ventas.scanner.ScanActivity.EXTRA;

public class MakeSale extends AppCompatActivity {
    private String unidades;
    private String TAG = "MakeSale";
    private String resultado = null;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_sale);

        mFragmentManager = getFragmentManager();
        Log.e(TAG, "onCreate");
        //obtenemos el resultado de ScanActivity
        resultado = getIntent().getStringExtra(EXTRA);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment fragment = null;
        //si el resultado no es nulo, consultamos si existe y si existe lo enviamos, sino existe
        //volvemos a escanear
        if (resultado != null) {

            DatabaseOperations operations = DatabaseOperations.obtenerInstancia(this);
            if (operations.consultarArticulo(resultado)) {
                editorDialog(resultado);

            } else {
                Toast.makeText(this, "No existe el articulo", Toast.LENGTH_SHORT).show();
                anadirDialog();
            }

        } else {
            fragment = new MakeSaleFragment1();
            fragmentTransaction.replace(R.id.content_frame_make, fragment, TAG);
            fragmentTransaction.commit();
        }


    }



    private AlertDialog editorDialog(final String codBarras) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText editText = new EditText(this);
        builder.setTitle(R.string.dialog_makesale);   // Título
        builder.setView(editText);
        builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String texto = editText.getText().toString();
                unidades=texto;

                // llama al metodo de la clase
                setUnidades(unidades, codBarras);


            }
        });
        builder.create();
        return builder.show();
    }

    private void anadirDialog() {
        android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(this);
        b.setTitle(getResources().getString(R.string.dialog_anadir));
        b.setPositiveButton(getResources().getString(R.string.aceptar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                inicializeScan();
            }
        });
        b.setNegativeButton(getResources().getString(R.string.cancelar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent refresh = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(refresh);
                finish();
            }
        });
        b.show();
    }

    /**
     * Metodo para enviar parametros desde @MakeSaleFragment1 a @MakeSaleFragment2
     *
     * @param fecha
     * @param caja
     * @param cliente
     */
    public void setParametersToFragment(String fecha, String caja, String cliente) {
        DatabaseOperations operations = DatabaseOperations.obtenerInstancia(this);
        String tipo = "C";
        //insertamos la cabecera y obtenmos la id
        String idCabecera = operations.insertarCabecera(new CabeceraPedido(tipo, fecha, caja, cliente));
        System.out.println("id cabecera -> " + idCabecera);
        //iniciar fragment
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        MakeSaleFragment2 fragment = new MakeSaleFragment2();
        Bundle args = new Bundle();
        args.putString("idcabecera", idCabecera);
        fragment.setArguments(args);
        fragmentTransaction.replace(R.id.content_frame_make, fragment, TAG);
        fragmentTransaction.commit();

    }

    public void setUnidades(String unidades, String codBarras) {

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        MakeSaleFragment2 fragment = new MakeSaleFragment2();
        Bundle args = new Bundle();
        args.putString("unidades", unidades);
        args.putString("c-barras", codBarras);
        fragment.setArguments(args);
        fragmentTransaction.replace(R.id.content_frame_make, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void setParametersExport(String idCabecera) {

        Intent intent = new Intent(this, Export.class);
        Log.e("MakeFrame2", idCabecera);
        intent.putExtra("id", idCabecera);
        startActivity(intent);
        finish();
        while (mFragmentManager.getBackStackEntryCount() > 0) {
            mFragmentManager.popBackStackImmediate();
        }
    }


    public void inicializeScan() {
        try {
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setTitle(getResources().getString(R.string.dialog_scanner));

            String[] types = getResources().getStringArray(R.array.opciones_scanner);
            b.setItems(types, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int pos) {

                    dialog.dismiss();
                    switch (pos) {

                        case 0:
                            startActivity(new Intent(getApplicationContext(), ScanActivity.class));
                            break;
                        case 1:
                            try {
                                startActivity(new Intent(getApplicationContext(), BarcodeScan.class));
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                            }

                            break;
                    }

                }

            });

            b.show();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }


    }
}
