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
import com.example.medicinelist.support.DateStringFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    public View getView(int i, View view, ViewGroup viewGroup)  {
        if (view == null){
            view = View.inflate(context, R.layout.frame_layout, null);
        }
        Patients patient = patients.get(i);
        try {
            TextView pat_title = view.findViewById(R.id.tvName);
            TextView pat_diag = view.findViewById(R.id.tvDiagnos);
            TextView pat_fdate = view.findViewById(R.id.tvFVDate);
            DateStringFormat dateStringFormat = new DateStringFormat(patient.getDateFirstConsult().replace("/","."), "dd.mm.yyyy");
            pat_diag.setText(patient.getDiagnosis());
            pat_fdate.setText(dateStringFormat.getDateOutput());
            pat_title.setText(patient.getName() + "\n"+"\n");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return view;
    }


}
