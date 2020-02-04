package com.example.medicinelist.ui.main;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.example.medicinelist.R;
import com.example.medicinelist.adapters.CategoryAdapter;
import com.example.medicinelist.adapters.PatientsAdapter;
import com.example.medicinelist.controllers.DbController;
import com.example.medicinelist.controllers.MainController;
import com.example.medicinelist.entity.Patients;
import com.example.medicinelist.models.PatientEdit;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private DbController dbController;
    private PageViewModel pageViewModel;
    public PatientsAdapter patientsAdapter;
    public ListView lvList;
    private int index;
    //private DataRepository dataRepository = new DataRepository();



    public static PlaceholderFragment newInstance(int index) {
        Log.d("MyLogs", "PlaceholderFragment newInstance: Hello " + index);
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbController = new DbController();

    }
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        Log.d("MyLogs", "PlaceholderFragment onCreateView: Hello ");
        final View root = inflater.inflate(R.layout.fragment_main, container, false);
        lvList = root.findViewById(R.id.lvData);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        LiveData<ArrayList<Patients>> data = pageViewModel.getData();
        patientsAdapter = new PatientsAdapter(root.getContext(), dbController.getAllPatientsList());
        lvList.setAdapter(patientsAdapter);
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Patients pat = (Patients)adapterView.getAdapter().getItem(i);
                Intent intent = new Intent(root.getContext(), PatientEdit.class);
                intent.putExtra("pat_Id", pat.getId());
                startActivity(intent);
                //mainController.editPatient(pat.getId());
            }
        });
        return root;
    }
}