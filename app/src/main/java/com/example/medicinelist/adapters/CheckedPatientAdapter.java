package com.example.medicinelist.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.medicinelist.R;
import com.example.medicinelist.entity.Patients;

import java.util.ArrayList;

public class CheckedPatientAdapter extends PatientsAdapter {
    ArrayList<Patients> objects;
    Context context;
    ArrayList<Patients> result = new ArrayList<>();
    public CheckedPatientAdapter(Context context, ArrayList<Patients> patients) {
        super(context, patients);
        this.objects = patients;
        this.context = context;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = View.inflate(context, R.layout.frame_layout_check, null);
        }
        TextView pat_title = view.findViewById(R.id.tvPatNameCheck);
        TextView pat_diag = view.findViewById(R.id.tvPatDiagnosCheck);
        CheckBox cbBuy = (CheckBox) view.findViewById(R.id.cbBox);
        // присваиваем чекбоксу обработчик
        cbBuy.setOnCheckedChangeListener(myCheckChangeList);
        // пишем позицию

        Patients patient = objects.get(i);
        cbBuy.setTag(i);
        cbBuy.setChecked(false);
        if (patient.getDiagnosis()!=null)
            pat_diag.setText(patient.getDiagnosis());
        if (patient.getName()!=null)
            pat_title.setText(patient.getName() + "\n");

        return view;
    }
    public ArrayList<Patients> getResult() {
        return result;
    }
    OnCheckedChangeListener myCheckChangeList = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            result.add((Patients) getItem((Integer) compoundButton.getTag()));
        }
    };



}
