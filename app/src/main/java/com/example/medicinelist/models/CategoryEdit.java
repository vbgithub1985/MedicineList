package com.example.medicinelist.models;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.medicinelist.controllers.DbController;
import com.example.medicinelist.entity.Categories;
import com.example.medicinelist.entity.Patients;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicinelist.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CategoryEdit extends AppCompatActivity implements View.OnClickListener {

    private long cat_id;
    private EditText etCatName;
    private EditText etCatMemo;
    private Button btnAddPatToCat;
    private DbController dbController;
    final int DIALOG_ITEMS = 1;
    private Map<Long, String> paTreeMap;
    private String[] arr;
    private boolean[] chkd;
    int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
    TableLayout tableLayout;
    private LinearLayout llMain;
    private boolean flagNew = false;
    private Intent intent;
    AlertDialog.Builder alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MyLog", "onCreate_CAteg1=" + cat_id);
        setContentView(R.layout.activity_category_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        llMain = (LinearLayout) findViewById(R.id.patFilterlinLayout);

        etCatName = (EditText) findViewById(R.id.etCatName);
        etCatMemo = (EditText) findViewById(R.id.etCatMemo);

        btnAddPatToCat = (Button) findViewById(R.id.btnAddPatToCat);
        btnAddPatToCat.setOnClickListener(this);
        intent = getIntent();

        if (!flagNew) {
            cat_id = intent.getExtras().getLong("cat_Id");
            if (cat_id!=0)
                setEditTextEnabled(false);
            else
                setEditTextEnabled(true);
        }

        Log.d("MyLog", "onCreate_CAteg2=" + cat_id);
        load_info();
        dbController = new DbController();

        //alertDialog = new AlertDialog.Builder(this);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("MyLog", "onStart_CAteg=" + cat_id);
        load_info();
        initListPat();
    }

    private void initListPat() {
        Log.d("MyLogInitListPat", "initListPat = " + cat_id);
        if (cat_id==0)
            return;
        llMain.removeAllViews();
        Categories category = Categories.findById(Categories.class, cat_id);
        //Log.d("MyLog", "pat=" + category.toString());
        List<Patients> selectedPat = category.getPatients();
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                wrapContent, wrapContent);
        int btnGravity = Gravity.LEFT;
        lParams.gravity = btnGravity;
        //final LinearLayout  llMain = (LinearLayout) findViewById(R.id.patFilterlinLayout);
        Log.d("MyLog_initListPat", "selectedPat=" + selectedPat.toString());
        for(Patients pat:selectedPat){
            Button btnNew = new Button(this);
            btnNew.setText(pat.getName()+" * ");
            long id = pat.getId();
            btnNew.setId((int) id);
            btnNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int id = v.getId();
                    if (id!=0) {
                        Patients pat = Patients.findById(Patients.class, id);
                        pat.setCategory(null);
                        pat.save();
                        //v.setVisibility(View.INVISIBLE);
                        llMain.removeView(v);
                    }
                }
            });
            llMain.addView(btnNew, lParams);
        }
    }


    private void load_info() {

        //setEditTextEnabled(false);
        Log.d("MyLog", "load_info=");
        if (cat_id!=0){
            setEditTextEnabled(false);
            Categories category = Categories.findById(Categories.class, cat_id);
            Log.d("MyLog", "load_info=" + category.getName());
            etCatName.setText(category.getName());
            etCatMemo.setText(category.getMemo());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddPatToCat:
                AddPatients();//AddListPatients( cat_id);
                break;
                default:
                    int id = v.getId();
                    Patients pat = Patients.findById(Patients.class, id);
                    Log.d("MyLog", "pat=" + pat.toString());
                    break;
        }
    }
    private void AddPatients(){
        long catId=0;
        if (cat_id == 0){
            catId = saveData(cat_id);
            cat_id = catId;
        }
        Log.d("MyLogAddPatients", "AddPatients = " + cat_id);
        Intent intent = new Intent(this, PatientsForAdd.class);
        intent.putExtra("cat_id", cat_id);
        startActivity(intent);
    }


    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        if (id == DIALOG_ITEMS) {
                adb.setTitle(R.string.info_del_cat);
                adb.setMessage(R.string.del_cat);
                adb.setIcon(android.R.drawable.ic_dialog_info);
                adb.setPositiveButton(R.string.yes, myClickListener);
                // кнопка отрицательного ответа
                adb.setNegativeButton(R.string.no, myClickListener);
                // кнопка нейтрального ответа
                adb.setNeutralButton(R.string.cancel, myClickListener);
            return adb.create();
        }
        return super.onCreateDialog(id);

    }
    DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                // положительная кнопка
                case Dialog.BUTTON_POSITIVE:
                    deleteObj(cat_id);
                    //finish();
                    break;
                // негативная кнопка
                case Dialog.BUTTON_NEGATIVE:
                    //finish();
                    break;
                // нейтральная кнопка
                case Dialog.BUTTON_NEUTRAL:
                    break;
            }
        }
    };
    // обработчик нажатия на кнопку

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }
    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();

        if (id == R.id.menu_save) {
            cat_id = saveData(cat_id);
            finish();
        }
        if (id == R.id.menu_edit) {
            setEditTextEnabled(true);
        }
        if (id == R.id.menu_del_obj) {
            showDialog(DIALOG_ITEMS);
            //finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteObj(long cat_id) {
        if (cat_id==0)
            return;
        Log.d("MyLog", "deleteObj=" + cat_id);

        Categories cat = Categories.findById(Categories.class, cat_id);
        cat.delete();
        Toast.makeText(getApplicationContext(), "Удалено", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void setEditTextEnabled(boolean flag){
        etCatName.setEnabled(flag);
        etCatMemo.setEnabled(flag);
    }

    private long saveData(long cat_id) {
        String catName = etCatName.getText().toString();
        String catMemo = etCatMemo.getText().toString();
        Categories category;
        if (cat_id==0){
            category = new Categories(catName, catMemo);
            category.save();
            flagNew = true;
        }else{
            category = Categories.findById(Categories.class, cat_id);
            category.setName(catName);
            category.setMemo(catMemo);
            category.save();
        }
        return category.getId();
    }

}
