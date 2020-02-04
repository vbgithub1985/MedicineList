package com.example.medicinelist;

import android.os.Bundle;
import android.content.Intent;

import com.example.medicinelist.models.CategoryEdit;
import com.example.medicinelist.models.PatientEdit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.example.medicinelist.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {
    final String LOG_TAG = "myLogs";
    final int OPEN_PAT = 1;
    final int OPEN_CAT = 2;
    Intent intent;
    SectionsPagerAdapter sectionsPagerAdapter;
    ViewPager viewPager;
    TabLayout tabs;
    private int OpenTab = 1;

    private void firstInit(){
        if (sectionsPagerAdapter==null)
            sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        viewPager= findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "OnCreate: Hello ");
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart: Hello ");
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               switch (OpenTab){
                   case OPEN_PAT:
                       editPatient(0);
                       break;
                   case OPEN_CAT:
                       editCategory(0);
                       break;
               }

            }
        });
        firstInit();

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        OpenTab = tab.getPosition()+1;
                        break;
                    case 1:
                        OpenTab = tab.getPosition()+1;
                        break;
                        default:
                            OpenTab = 1;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

    }

    private void editPatient(long pat_Id){
        Intent intent = new Intent(this, PatientEdit.class);
        intent.putExtra("pat_Id", pat_Id);
        startActivity(intent);
    }

    private void editCategory(long cat_Id){
        Log.d(LOG_TAG, "editCategory Hello id = " + cat_Id);
        Intent intent = new Intent(this, CategoryEdit.class);
        intent.putExtra("cat_Id", cat_Id);
        startActivity(intent);
    }
}