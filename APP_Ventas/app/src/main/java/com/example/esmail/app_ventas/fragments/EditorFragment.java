package com.example.esmail.app_ventas.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.esmail.app_ventas.MakeSale;
import com.example.esmail.app_ventas.R;

public class EditorFragment extends DialogFragment {

    private String TAG = "EF";
    private String unidades;
    private String codBarras;


    public EditorFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");
        //inicia el dialogo
        onCreateDialog(savedInstanceState);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editor, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        codBarras=getArguments().getString("c-barras");

        Log.e(TAG, "onCreateDialog");
        return createSingleListDialog();

    }

    /**
     * Crea un Diálogo con una lista de selección simple
     *
     * @return La instancia del diálogo
     */
    public AlertDialog createSingleListDialog() {
        Log.e(TAG, "createSingleListDialog");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final EditText editText = new EditText(getActivity());
        builder.setTitle(R.string.dialog_makesale);   // Título
        builder.setView(editText);
        builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String texto = editText.getText().toString();
                unidades=texto;

                // llama al metodo de la clase
                ((MakeSale) getActivity()).setUnidades(unidades, codBarras);


            }
        });
        builder.create();
        return builder.show();
    }


}
