package com.example.medicinelist.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.medicinelist.entity.Categories;
import com.example.medicinelist.entity.Patients;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class DataRepository {


    public MutableLiveData<ArrayList<Patients>> getAllPatientsLiveData(){
        List<Patients> patientsList = Patients.listAll(Patients.class);
        MutableLiveData<ArrayList<Patients>> dataPatient = new MutableLiveData<>();
        Patients patients= new Patients();
        ArrayList<Patients> patientsArrayList = new ArrayList<>();
        for (int i = 0; i < patientsList.size(); i++) {
            patients = patientsList.get(i);
            patientsArrayList.add(patients);
        }
        dataPatient.postValue(patientsArrayList);
        return dataPatient;
    }

    public ArrayList<Patients> getAllPatientsList(){
        List<Patients> objList = Patients.listAll(Patients.class, "name");
        Patients patients = new Patients();
        ArrayList<Patients> resultArrayList = new ArrayList<>();
        for (int i = 0; i < objList.size(); i++) {
            patients = objList.get(i);
            resultArrayList.add(patients);
        }
        return resultArrayList;
    }

    public TreeMap<Categories, List<Patients>> getAllCategoriesWithPatients(){
        TreeMap<Categories, List<Patients>> result = new TreeMap<>();
        List<Categories> objList = Categories.listAll(Categories.class);
        for(Categories cat:objList){
            result.put(cat,cat.getPatients());
        }
        return result;
    }

    public ArrayList<Categories> getAllCategoriesList(){
        List<Categories> objList = Categories.listAll(Categories.class, "name");
        Categories category = new Categories();
        ArrayList<Categories> resultArrayList = new ArrayList<>();
        for (int i = 0; i < objList.size(); i++) {
            category = objList.get(i);
            resultArrayList.add(category);
        }
        return resultArrayList;
    }



}
