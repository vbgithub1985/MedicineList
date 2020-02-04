package com.example.medicinelist.ui.main;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.medicinelist.R;
import com.example.medicinelist.adapters.PatientsAdapter;
import com.example.medicinelist.controllers.DbController;
import com.example.medicinelist.entity.Patients;
import com.example.medicinelist.repository.DataRepository;

import java.util.ArrayList;
import java.util.List;

public class PageViewModel extends ViewModel {
    //private DataRepository dataRepository = new DataRepository();
    private DbController dbController = new DbController();
    private MutableLiveData<ArrayList<Patients>> arrayListMutableLiveData;

    public LiveData<ArrayList<Patients>> getData(){

        if (arrayListMutableLiveData == null){
            arrayListMutableLiveData = dbController.getAllPatientsLiveData();//new MutableLiveData<>();
        }
        //Log.d("MyLogs", "LiveData<ArrayList<Patients>> getData(): Hello " + dbController.getAllPatientsList().size());
        return arrayListMutableLiveData;
    }
}