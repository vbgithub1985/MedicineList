package com.example.medicinelist.models;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.example.medicinelist.R;
import com.example.medicinelist.adapters.TherapyAdapter;
import com.example.medicinelist.entity.Patients;
import com.example.medicinelist.entity.Therapies;

public class PatientEdit extends AppCompatActivity implements View.OnClickListener{
    final String LOG_TAG = "myLogs";

    private static final String FIRST_THERAPY = "Начало лечения. Осмотр.";
    private static final int ADD_PROCESS = 1;
    private static final int EDIT_PROCESS = 2;
    Button btnSave, btnAddTher, btnFVDate;
    ImageButton imgBtnAddTherapy;
    EditText  etName, etDiagnos, etPhone, etEmail, etBirth;
    ExpandableListView elvMain;
    ListView lvTherapyView;
    Calendar dateAndTime=Calendar.getInstance();
    TherapyAdapter therapyAdapter;
    //TextView etDate;

    int DIALOG_DATE = 1;
    int myYear = 2011;
    int myMonth = 01;
    int myDay = 01;

    int ACTION_PROCESS;
    long pat_id;
    long therapy_id;
    boolean flagNew = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG,"---PatientEdit_onCreate----");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_edit);
        initPatientEdit();
    }

    public void setDateFV(View v) {
        new DatePickerDialog(PatientEdit.this, dFV,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    DatePickerDialog.OnDateSetListener dFV=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myYear = year;
            myMonth = monthOfYear+1;
            myDay = dayOfMonth;
            btnFVDate.setText("" + myDay + "." + myMonth + "." + myYear);
        }
    };

private void initPatientEdit(){
    Log.d(LOG_TAG,"---PatientEdit_initPatientEdit----");
    imgBtnAddTherapy = (ImageButton) findViewById(R.id.imgAddTherapy);
    imgBtnAddTherapy.setOnClickListener(this);
    etBirth = (EditText) findViewById(R.id.etBirth);
    btnFVDate = (Button) findViewById(R.id.btnFVDate);
    etName = (EditText) findViewById(R.id.etName);
    etDiagnos = (EditText) findViewById(R.id.etDiagnos);
    etPhone = (EditText) findViewById(R.id.etPhone);
    etEmail = (EditText) findViewById(R.id.etEmail);

    //controller = new Controller(this);
    Intent intent = getIntent();
    if (!flagNew)
        pat_id = intent.getExtras().getLong("pat_Id");
    ACTION_PROCESS = ADD_PROCESS;
    if (pat_id!=0) {
        ACTION_PROCESS = EDIT_PROCESS;
        onLoadInfo(pat_id);
        setEditTextEnabled(false);
        initTherapy();
    }
    Log.d(LOG_TAG,"PATTTTT_ID = " + pat_id);
    therapy_id = 0;
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }
    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();

        if (id == R.id.menu_save) {
            addOrEditPation(ACTION_PROCESS);
            finish();
        }
        if (id == R.id.menu_edit) {
            setEditTextEnabled(true);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStart() {
        Log.d(LOG_TAG,"---PatientEdit_Start----");
        super.onStart();
        initPatientEdit();
    }

    @Override
    protected void onResume() {
        super.onResume();
       // initTherapy();
    }

    private void initTherapy(){
        Log.d(LOG_TAG,"---PatientEdit_initTherapy----");
        Patients pat = Patients.findById(Patients.class, pat_id);

        List<Therapies> therapiesList = pat.getTherapies();
        therapyAdapter = new TherapyAdapter(this, therapiesList);
        lvTherapyView = (ListView) findViewById(R.id.lvTherapyData);
        lvTherapyView.setAdapter(therapyAdapter);
        lvTherapyView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Therapies therapy = (Therapies)adapterView.getAdapter().getItem(i);
                EditTherapy(therapy.getId(), pat_id);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgAddTherapy:
                if (pat_id==0)
                    addOrEditPation(ACTION_PROCESS);
                if (therapy_id!=0)
                    EditTherapy( therapy_id, pat_id);
                else
                    EditTherapy( 0, pat_id);
                break;
        }
    }

    private void setEditTextEnabled(boolean flag){
        etName.setEnabled(flag);
        etDiagnos.setEnabled(flag);
        etPhone.setEnabled(flag);
        etEmail.setEnabled(flag);
        etBirth.setEnabled(flag);
        btnFVDate.setEnabled(flag);
        imgBtnAddTherapy.setEnabled(flag);
    }

    private void EditTherapy(long therapy_Id, long pat_Id){
        Intent intent = new Intent(this, TherapyEdit.class);
        intent.putExtra("therapy_Id", therapy_Id);
        intent.putExtra("pat_id", pat_Id);
        startActivity(intent);
    }
    private void onLoadInfo(long pat_id){
        Log.d(LOG_TAG,"-------Edit PAtient------------");
        Patients pat = Patients.findById(Patients.class, pat_id);
        //Patient pat = controller.getPatient(pat_id);
        Log.d(LOG_TAG,pat.toString());
        etName.setText(pat.getName());
        etDiagnos.setText(pat.getDiagnosis());
        etPhone.setText(pat.getPhone());
        etEmail.setText(pat.getEmail());
        etBirth.setText(""+pat.getAge());
        btnFVDate.setText(pat.getDateFirstConsult().replace("/","."));
    }

    private void addOrEditPation(int flag){
        String FIO, diagnos, phone, email, dateB, FVDate;
        Patients patient;
        Date dBir;
        FIO = etName.getText().toString();
        diagnos = etDiagnos.getText().toString();
        phone = etPhone.getText().toString();
        email = etEmail.getText().toString();
        dateB = etBirth.getText().toString();
        FVDate = btnFVDate.getText().toString().replace(".","/");

        switch (flag){
            case ADD_PROCESS:
                patient = new Patients(FIO, phone, FVDate, diagnos);
                patient.setEmail(email);
                patient.setAge(Integer.parseInt(dateB));
                patient.save();
                Therapies therapies = new Therapies(FIRST_THERAPY, FVDate, patient);
                therapies.save();
                therapy_id = therapies.getId();
                pat_id = patient.getId();
                Log.d(LOG_TAG,"-------Add PAtient------------");
                flagNew = true;
                break;
            case EDIT_PROCESS:
                patient = Patients.findById(Patients.class, pat_id);
                patient.setName(FIO);
                patient.setDiagnosis(diagnos);
                patient.setDateFirstConsult(FVDate);
                patient.setPhone(phone);
                patient.setEmail(email);
                patient.setAge(Integer.parseInt(dateB));
                patient.save();
                break;
        }

    }

}
