package com.example.medicinelist.controllers;

import androidx.lifecycle.MutableLiveData;

import com.example.medicinelist.entity.Categories;
import com.example.medicinelist.entity.Patients;
import com.example.medicinelist.repository.DataRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class DbController implements IDbController {
    private DataRepository dataRepository;
    public DbController() {
        this.dataRepository = new DataRepository();
    }

    @Override
    public MutableLiveData<ArrayList<Patients>> getAllPatientsLiveData() {
        return dataRepository.getAllPatientsLiveData();
    }

    @Override
    public ArrayList<Patients> getAllPatientsList() {
        return dataRepository.getAllPatientsList();
    }

    @Override
    public ArrayList<Categories> getAllCategoriesList() {
        return dataRepository.getAllCategoriesList();
    }

    @Override
    public TreeMap<Categories, List<Patients>> getAllCategoriesWithPatients() {
        return dataRepository.getAllCategoriesWithPatients();
    }
}
