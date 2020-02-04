package com.example.medicinelist.models;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import com.example.medicinelist.R;
//import com.example.medicinelist.entities.Therapy;
//import com.example.medicinelist.adapters.MySimpleCursorTreeAdapter;
//import com.example.medicinelist.controller.Controller;
//import com.example.medicinelist.controller.DBController;
//import com.example.medicinelist.entities.Patient;
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
        //setInitialDateTime();
    }

    // отображаем диалоговое окно для выбора даты
    /*public void setDate(View v) {
        new DatePickerDialog(PatientEdit.this, dBirth,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }
*/
    public void setDateFV(View v) {
        new DatePickerDialog(PatientEdit.this, dFV,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

   /* private void setInitialDateTime() {

        etDate.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
    }*/

    /*DatePickerDialog.OnDateSetListener dBirth=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myYear = year;
            myMonth = monthOfYear+1;
            myDay = dayOfMonth;
            btnBirth.setText("" + myDay + "/" + myMonth + "/" + myYear);
            //setInitialDateTime();
        }
    };*/

    DatePickerDialog.OnDateSetListener dFV=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            /*dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);*/
            myYear = year;
            myMonth = monthOfYear+1;
            myDay = dayOfMonth;
            btnFVDate.setText("" + myDay + "." + myMonth + "." + myYear);
            //setInitialDateTime();
        }
    };

private void initPatientEdit(){
    Log.d(LOG_TAG,"---PatientEdit_initPatientEdit----");
    btnAddTher = (Button) findViewById(R.id.btnAddTher);
    btnAddTher.setOnClickListener(this);
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

        /*


        Map<String, String> map;

        Patients pat = Patients.findById(Patients.class, pat_id);
        String id = ""+pat_id;
        // коллекция для групп
        ArrayList<Map<String, String>> groupDataList = new ArrayList<>();
       // List<Therapies> groupName = Therapies.listAll(Therapies.class, "");
        List<Therapies> groupName = pat.getTherapies();//Therapies.find(Therapies.class, "patients = ?", pat.getId().toString(),"","");
        Log.d(LOG_TAG,"---groupName----" + groupName.toString());
        for (int i = 0; i < groupName.size(); i++) {
            map = new HashMap<>();
            map.put("groupName", groupName.get(i).getDateConsult()); // время года
            groupDataList.add(map);
        }

        // список атрибутов групп для чтения
        String groupFrom[] = new String[] { "groupName" };
        // список ID view-элементов, в которые будет помещены атрибуты групп
        int groupTo[] = new int[] { android.R.id.text1 };

        // создаем общую коллекцию для коллекций элементов
        ArrayList<ArrayList<Map<String, String>>> сhildDataList = new ArrayList<>();

        // в итоге получится сhildDataList = ArrayList<сhildDataItemList>

        // создаем коллекцию элементов для первой группы
        ArrayList<Map<String, String>> сhildDataItemList = new ArrayList<>();
        String dateConst = groupName.get(0).getDateConsult();
        String str = "";//groupName.get(0).getTherapy();
        for (int i = 0; i < groupName.size(); i++) {
            if (!dateConst.equals(groupName.get(i).getDateConsult())){
                сhildDataList.add(сhildDataItemList);
                сhildDataItemList = new ArrayList<>();
            }else{
                str = str + "\n" + groupName.get(i).getTherapy();
            }
            map = new HashMap<>();
            map.put("therapyName", str); // название месяца
            сhildDataItemList.add(map);
            dateConst = groupName.get(i).getDateConsult();
            str = groupName.get(i).getTherapy();
        }
        сhildDataList.add(сhildDataItemList);
        // список атрибутов элементов для чтения
        String childFrom[] = new String[] { "therapyName" };
        // список ID view-элементов, в которые будет помещены атрибуты
        // элементов
        int childTo[] = new int[] { android.R.id.text1 };
        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                this, groupDataList,
                android.R.layout.simple_expandable_list_item_1, groupFrom,
                groupTo, сhildDataList, android.R.layout.simple_list_item_1,
                childFrom, childTo);

        elvMain = (ExpandableListView) findViewById(R.id.elvMain);
        elvMain.setAdapter(adapter);
        elvMain.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View view,
                                        int groupPosition, int childPosition, long id) {
                    //Therapies therapies = (Therapies)parent.getExpandableListAdapter().getChildId(groupPosition, childPosition);
                    Long TherId = parent.getExpandableListAdapter().getChildId(groupPosition, childPosition);
                    //Therapies therapies = Therapies.findById(Therapies.class, TherId);
                //Cursor cur = (Cursor)parent.getExpandableListAdapter().getChild(groupPosition, childPosition);
                    Log.d(LOG_TAG,"GetFrom List TherId - " + TherId);
                    Log.d(LOG_TAG,"GetFrom List groupPosition - " + groupPosition);
                    Log.d(LOG_TAG,"GetFrom List childPosition- " + childPosition);
                    Log.d(LOG_TAG,"GetFrom List id - " + id);
                    //Log.d(LOG_TAG,"GetFrom List - " + therapies.getTherapy() + " - " + therapies.getId());
                    return false;
                }
            });
        //}
*/
        /*Cursor cursor = controller.getAllDatesTherapy(pat_id);
        startManagingCursor(cursor);
        String[] groupFrom = { DBController.THERCOLUMN_DATECONSULT };
        int[] groupTo = { android.R.id.text1 };

        String[] childFrom = { DBController.THERCOLUMN_NAME };
        int[] childTo = { android.R.id.text1 };

        // создаем адаптер и настраиваем список
         sctAdapter = new MySimpleCursorTreeAdapter(this, cursor,
                android.R.layout.simple_expandable_list_item_1, groupFrom,
                grouSimpleCursorTreeAdapterpTo, android.R.layout.simple_list_item_1, childFrom,
                childTo);
        elvMain = (ExpandableListView) findViewById(R.id.elvMain);
        elvMain.setAdapter(sctAdapter);

        elvMain.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition,   int childPosition, long id) {
                Cursor cur = (Cursor)parent.getExpandableListAdapter().getChild(groupPosition, childPosition);
                Log.d(LOG_TAG,cur.getString(0));
                EditTherapy(getApplicationContext(), Long.parseLong(cur.getString(0)), (long)pat_id);
                /*Intent intent = new Intent(getApplicationContext(), TherapyEdit.class);
                intent.putExtra("therapy_Id", Integer.parseInt(cur.getString(0)));
                intent.putExtra("pat_id", pat_id);
                startActivity(intent);
                return false;
            }
        });*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddTher:
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
        btnAddTher.setEnabled(flag);
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
