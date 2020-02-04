package com.example.medicinelist.controllers;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicinelist.models.PatientEdit;

public class MainController  extends AppCompatActivity {
    private Context context;

    public MainController(Context context) {
        this.context = context;
    }

    public void editPatient(long pat_Id){
        Intent intent = new Intent(context, PatientEdit.class);
        intent.putExtra("pat_Id", pat_Id);
        startActivity(intent);
    }
}
