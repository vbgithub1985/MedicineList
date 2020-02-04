package com.example.medicinelist.models;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.medicinelist.R;
import com.example.medicinelist.adapters.CheckedPatientAdapter;
import com.example.medicinelist.adapters.PatientsAdapter;
import com.example.medicinelist.controllers.DbController;
import com.example.medicinelist.entity.Categories;
import com.example.medicinelist.entity.Patients;

import java.util.ArrayList;

public class PatientsForAdd extends AppCompatActivity {

    private CheckedPatientAdapter patientsAdapter;
    private DbController dbController;
    public ListView lvList;
    private ArrayList<Patients> arrPatients;
    private long cat_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_for_add);
        dbController = new DbController();
        initList();
        Intent intent = getIntent();
        cat_id = intent.getExtras().getLong("cat_id");
        Log.d("MyLogPatientsForAdd", "onCreate = " + cat_id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_select, menu);
        return true;
    }
    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();

        if (id == R.id.menu_select) {
            //deletePatients();
            Categories category = Categories.findById(Categories.class, cat_id);
            for (int i = 0; i < arrPatients.size(); i++) {
                Log.d("MyLog", "arrPatients = " + arrPatients.get(i).toString());
                arrPatients.get(i).setCategory(category);
                arrPatients.get(i).save();
            }
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initList() {
        //dbController = new DbController();
        lvList = findViewById(R.id.lvDataAdd);
        lvList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        patientsAdapter = new CheckedPatientAdapter(this, dbController.getAllPatientsList());
        lvList.setAdapter(patientsAdapter);
        arrPatients = patientsAdapter.getResult();
    }
}
