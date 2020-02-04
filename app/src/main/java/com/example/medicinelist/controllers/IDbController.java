package com.example.medicinelist.controllers;

import androidx.lifecycle.MutableLiveData;

import com.example.medicinelist.entity.Categories;
import com.example.medicinelist.entity.Patients;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public interface IDbController {
    MutableLiveData<ArrayList<Patients>> getAllPatientsLiveData();
    ArrayList<Patients> getAllPatientsList();
    ArrayList<Categories> getAllCategoriesList();
    TreeMap<Categories, List<Patients>> getAllCategoriesWithPatients();
}
