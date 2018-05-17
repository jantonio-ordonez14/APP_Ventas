package com.example.esmail.app_ventas.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.esmail.app_ventas.R;

public class MakeSaleFragment extends Fragment {


    public MakeSaleFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_make_sale_1, container, false);


        return v;
    }
}
