package com.example.medicinelist.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.example.medicinelist.R;
import com.example.medicinelist.adapters.CategoryAdapter;
import com.example.medicinelist.adapters.ExpListAdapter;
import com.example.medicinelist.controllers.DbController;
import com.example.medicinelist.entity.Categories;
import com.example.medicinelist.entity.Patients;
import com.example.medicinelist.models.CategoryEdit;
import com.example.medicinelist.models.PatientEdit;
import com.example.medicinelist.repository.ExpandableListDataPump;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class FragmentCategory extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private DbController dbController;
    private PageViewModel pageViewModel;
    public CategoryAdapter categoryAdapter;
    public ListView lvList;
    public ExpandableListView listView;
    public ExpListAdapter adapter;
    public ArrayList<Categories> expandableListTitle;
    public TreeMap<Categories, List<Patients>> expandableListDetail;
    public Button btn;

    public static FragmentCategory newInstance(int index) {
        Log.d("MyLogs", "FragmentCategory newInstance: Hello " + index);
        FragmentCategory fragment = new FragmentCategory();
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


        final View root = inflater.inflate(R.layout.fragment_second, container, false);
        listView  = (ExpandableListView)root.findViewById(R.id.exListView);
        expandableListDetail = dbController.getAllCategoriesWithPatients();
        expandableListTitle = new ArrayList<Categories>(expandableListDetail.keySet());
        adapter = new ExpListAdapter(root.getContext(), expandableListTitle, expandableListDetail);
        listView.setAdapter(adapter);
        Log.d("MyLosdfsdfgs", "listView.getSelectedPosition()" + listView.getSelectedPosition());
        //listView.setIndicatorBounds(0, 50);

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Patients pat = (Patients)parent.getExpandableListAdapter().getChild(groupPosition, childPosition);
                Intent intent = new Intent(root.getContext(), PatientEdit.class);
                intent.putExtra("pat_Id", pat.getId());
                startActivity(intent);
                return false;
            }
        });


        /*lvList = root.findViewById(R.id.lvDataSec);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        LiveData<ArrayList<Patients>> data = pageViewModel.getData();
        categoryAdapter = new CategoryAdapter(root.getContext(), dbController.getAllCategoriesList());
        lvList.setAdapter(categoryAdapter);
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Categories cat = (Categories) adapterView.getAdapter().getItem(i);
                Intent intent = new Intent(root.getContext(), CategoryEdit.class);
                intent.putExtra("cat_Id", cat.getId());
                startActivity(intent);
                //mainController.editPatient(pat.getId());
            }
        });*/
        return root;
    }
}
