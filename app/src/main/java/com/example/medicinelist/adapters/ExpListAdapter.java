package com.example.medicinelist.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.medicinelist.R;
import com.example.medicinelist.entity.Categories;
import com.example.medicinelist.entity.Patients;
import com.example.medicinelist.models.CategoryEdit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class ExpListAdapter extends BaseExpandableListAdapter  {
    private ArrayList<Categories> expandableListTitle;
    private TreeMap<Categories, List<Patients>> expandableListDetail;
    private Context context;
    public ExpListAdapter (Context context, ArrayList<Categories> expandableListTitle,
                           TreeMap<Categories, List<Patients>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.expandableListTitle.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Categories categories = (Categories) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.group_view, null);
        }

        final int grpPos = listPosition;
        Log.d("MyLogsExpListAdapter", "getGroupView - " + categories.getName());
        Button button = (Button) convertView.findViewById(R.id.btnGroupCat);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int grpPOs = listPosition;
                Categories cat = (Categories) getGroup(grpPOs);
                Log.d("MyLogsExpListAdapter", "getGroupView - " + cat.getName());
                Intent intent = new Intent(v.getContext(), CategoryEdit.class);
                intent.putExtra("cat_Id", cat.getId());
                v.getContext().startActivity(intent);
            }
        });

        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.textGroup);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(categories.getName());


        return convertView;
    }


    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final Patients patient = (Patients) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.child_view, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.textChild);
        expandedListTextView.setText(patient.getName());
        Log.d("MyLogsExpListAdapter", "getChildView - " + patient.getName());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
