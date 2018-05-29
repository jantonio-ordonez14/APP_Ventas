package com.example.esmail.app_ventas.makesale;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.esmail.app_ventas.Export;
import com.example.esmail.app_ventas.MainActivity;
import com.example.esmail.app_ventas.R;
import com.example.esmail.app_ventas.makesale.MakeSaleFragmentDetails;
import com.example.esmail.app_ventas.makesale.MakeSaleFragmentHeader;
import com.example.esmail.app_ventas.modelos.CabeceraPedido;
import com.example.esmail.app_ventas.modelos.DetallePedido;
import com.example.esmail.app_ventas.modelos.Pedido;
import com.example.esmail.app_ventas.scanner.BarcodeScan;
import com.example.esmail.app_ventas.scanner.ScanActivity;
import com.example.esmail.app_ventas.sqlite.DatabaseOperations;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.esmail.app_ventas.scanner.ScanActivity.EXTRA;

public class MakeSale extends AppCompatActivity {
    private String unidades;
    private String TAG = "MakeSale";
    private String resultado = null;
    private FragmentManager mFragmentManager;
    private FragmentTransaction fragmentTransaction;
    private MakeSaleFragmentHeader makeSaleFragmentHeader;
    private MakeSaleFragmentDetails makeSaleFragmentDetails;

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

        fragmentTransaction = mFragmentManager.beginTransaction();

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
            makeSaleFragmentHeader = new MakeSaleFragmentHeader();
            fragmentTransaction.replace(R.id.content_frame_make, makeSaleFragmentHeader);
            fragmentTransaction.commit();
        }

    }

    /**
     * AlertDialog para añadir unidades
     *
     * @param codBarras
     * @return
     */
    private AlertDialog editorDialog(final String codBarras) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText editText = new EditText(this);
        builder.setTitle(R.string.dialog_makesale);   // Título
        builder.setView(editText);
        builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String texto = editText.getText().toString();
                unidades = texto;

                // llama al metodo de la clase
                setUnidades(unidades, codBarras);

            }
        });
        builder.create();
        return builder.show();
    }

    /**
     * AlertDialog sino encuentra codigoBarras
     */
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
     * Metodo para enviar parametros desde @MakeSaleFragmentHeader a @MakeSaleFragmentDetails
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
        fragmentTransaction = mFragmentManager.beginTransaction();
        makeSaleFragmentDetails = new MakeSaleFragmentDetails();
        Bundle args = new Bundle();
        args.putString("idcabecera", idCabecera);
        makeSaleFragmentDetails.setArguments(args);
        fragmentTransaction.replace(R.id.content_frame_make, makeSaleFragmentDetails, TAG);
        fragmentTransaction.commit();

    }

    /**
     * Metodo para enviar unidades a @MakeSaleFragmentDetails
     *
     * @param unidades
     * @param codBarras
     */
    public void setUnidades(String unidades, String codBarras) {

        fragmentTransaction = mFragmentManager.beginTransaction();
        makeSaleFragmentDetails = new MakeSaleFragmentDetails();
        Bundle args = new Bundle();
        args.putString("unidades", unidades);
        args.putString("c-barras", codBarras);
        makeSaleFragmentDetails.setArguments(args);
        fragmentTransaction.replace(R.id.content_frame_make, makeSaleFragmentDetails);
        fragmentTransaction.commit();
    }

    /**
     * Metodo para iniciar @Export
     *
     * @param idCabecera
     */
    public void setParametersExport(String idCabecera) {

        hacerPedido(idCabecera);
        DatabaseOperations operations = DatabaseOperations.obtenerInstancia(this);
        operations.eliminarDetalle();
        operations.eliminarCabecera();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    /**
     * Metodo para elegir entre camara y escaner
     */
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

    private void hacerPedido(String idCabecera) {
        Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        String creacion=String.valueOf(hourdateFormat.format(date));
        ArrayList<Pedido> pedido = new ArrayList<>();
        ArrayList<CabeceraPedido> cabecera = obtenerDetallesCabecera(idCabecera);
        ArrayList<DetallePedido> detalle = obtenerDetallesPedido(idCabecera);

        for (int i = 0; i < cabecera.size(); i++) {
            pedido.add(new Pedido(cabecera.get(i).getTipo(), cabecera.get(i).getFecha(), cabecera.get(i).getCaja(),
                    cabecera.get(i).getFk_id_cliente(), "", "", idCabecera, creacion));
            for (int j = 0; j < detalle.size(); j++) {
                pedido.add(new Pedido(detalle.get(j).getTipo(), "", "", "",
                        detalle.get(j).getArticulo(), detalle.get(j).getUnidades(), idCabecera,""));
            }
        }

        for (Pedido pd :
                pedido) {
            System.out.println(pd.getTipo() + "\t" + pd.getFecha() + "\t" + pd.getCaja() + "\t" + pd.getCliente() + "\t" +
                    pd.getArticulo() + "\t" + pd.getUnidades() +"\t" + pd.getCreacion());

            DatabaseOperations operations = DatabaseOperations.obtenerInstancia(this);
            operations.insertarPedidos(new Pedido(pd.getTipo(), pd.getFecha(), pd.getCaja(), pd.getCliente(),
                    pd.getArticulo(), pd.getUnidades(), pd.getFk_id_cabecera(), pd.getCreacion()));
        }


    }

    private ArrayList<CabeceraPedido> obtenerDetallesCabecera(String idCabecera) {
        DatabaseOperations operations = DatabaseOperations.obtenerInstancia(this);
        Cursor c = operations.obtenerCabeceraId(idCabecera);

        ArrayList<CabeceraPedido> alCabecera = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                String tipo = c.getString(1);
                String fecha = c.getString(2);
                String caja = c.getString(3);
                String fk_id_cliente = c.getString(4);

                alCabecera.add(new CabeceraPedido(tipo, fecha, caja, fk_id_cliente));
            } while (c.moveToNext());
        }

        System.out.println("****************CABECERA**********************");
        for (CabeceraPedido pedido :
                alCabecera) {
            System.out.println("TIPO->" + pedido.getTipo() + "\tFECHA->" + pedido.getFecha()
                    + "\tCAJA->" + pedido.getCaja() + "\tIDCLIENTE->" + pedido.getFk_id_cliente());
        }
        System.out.println("*******************FIN************************");

        return alCabecera;
    }

    private ArrayList<DetallePedido> obtenerDetallesPedido(String idCabecera) {
        DatabaseOperations operations = DatabaseOperations.obtenerInstancia(this);
        Cursor c = operations.obtenerDetallesId(idCabecera);

        ArrayList<DetallePedido> alDetalle = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                String id = c.getString(0);
                String tipo = c.getString(1);
                String articulo = c.getString(2);
                String unidades = c.getString(3);
                String fk_id_cabecera = c.getString(4);

                alDetalle.add(new DetallePedido(tipo, articulo, unidades, fk_id_cabecera));
            } while (c.moveToNext());
        }
        System.out.println("****************DETALLE**********************");

        for (DetallePedido pedido :
                alDetalle) {
            System.out.println("TIPO->" + pedido.getTipo() + "\tARTICULO->" + pedido.getArticulo() + "\tUNIDADES->" +
                    pedido.getUnidades() + "\tIDCABECERA->" + pedido.getFk_id_cabecera());
        }
        System.out.println("*******************FIN************************");

        return alDetalle;
    }


}
