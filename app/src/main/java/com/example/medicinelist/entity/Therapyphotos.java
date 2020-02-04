package com.example.medicinelist.entity;

import android.widget.ImageView;

import com.orm.SugarRecord;

import java.util.List;

public class Therapyphotos extends SugarRecord {
    private String path;
    private Therapies therapies;

    public Therapyphotos() {
    }

    public Therapyphotos(String path, Therapies therapies) {
        this.path = path;
        this.therapies = therapies;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Therapies getTherapies() {
        return therapies;
    }

    public void setTherapies(Therapies therapies) {
        this.therapies = therapies;
    }


}
