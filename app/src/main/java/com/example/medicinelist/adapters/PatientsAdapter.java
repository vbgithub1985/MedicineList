package com.example.medicinelist.adapters;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.medicinelist.R;
import com.example.medicinelist.controllers.MainController;
import com.example.medicinelist.entity.Patients;

import java.util.ArrayList;

public class PatientsAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Patients> patients;

    public PatientsAdapter(Context context, ArrayList<Patients> patients) {
        this.context = context;
        this.patients = patients;
    }

    @Override
    public int getCount() {
        return patients.size();
    }

    @Override
    public Object getItem(int i) {
        return patients.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = View.inflate(context, R.layout.frame_layout, null);
        }
        TextView pat_title = view.findViewById(R.id.tvName);
        TextView pat_diag = view.findViewById(R.id.tvDiagnos);
        TextView pat_fdate = view.findViewById(R.id.tvFVDate);
        Patients patient = patients.get(i);
        pat_diag.setText(patient.getDiagnosis());
        pat_fdate.setText(patient.getDateFirstConsult().replace("/","."));
        pat_title.setText(patient.getName() + "\n");

        return view;
    }


}
