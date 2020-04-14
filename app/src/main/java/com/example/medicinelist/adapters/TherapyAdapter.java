package com.example.medicinelist.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.medicinelist.R;
import com.example.medicinelist.controllers.MainController;
import com.example.medicinelist.entity.Patients;
import com.example.medicinelist.entity.Therapies;
import com.example.medicinelist.support.DateStringFormat;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class TherapyAdapter extends BaseAdapter {
    private Context context;
    private List<Therapies> therapies;

    public TherapyAdapter(Context context, List<Therapies> therapies) {
        this.context = context;
        this.therapies = therapies;
    }

    @Override
    public int getCount() {
        return therapies.size();
    }

    @Override
    public Object getItem(int i) {
        return therapies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = View.inflate(context, R.layout.frame_layout_ther, null);
        }

        TextView ther_title = view.findViewById(R.id.tvTherapyName);
        TextView ther_date = view.findViewById(R.id.tvDateConsult);
        Therapies therapy = therapies.get(i);
        int len = therapy.getTherapy().length()>33 ? 33 : therapy.getTherapy().length();
        Log.d("MyLog","getView - "+ len);
        Log.d("MyLog","therapy.getTherapy().length - "+ therapy.getTherapy().length());
        DateStringFormat dateStringFormat = new DateStringFormat(therapy.getDateConsult().replace("/","."), "dd.mm.yyyy");
        String str_title = "\n"+therapy.getTherapy();
        String str_date = null;
        try {
            str_date = therapy.getTherapy().length()>len ? dateStringFormat.getDateOutput()+ "\n\n":dateStringFormat.getDateOutput()+ "\n";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ther_date.setText(str_date);
        ther_title.setText(str_title);
        return view;
    }
}
