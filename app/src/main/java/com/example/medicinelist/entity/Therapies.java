package com.example.medicinelist.entity;

import com.orm.SugarRecord;
import com.orm.*;
import com.orm.dsl.Column;

import java.util.List;


public class Therapies extends SugarRecord {

    private String therapy;
    private String Photo;
    private String DateConsult;
    private String memo;
    private Patients patients;

    public Therapies() {
    }

    public Therapies(String therapy, String dateConsult, Patients patients) {
        this.therapy = therapy;
        DateConsult = dateConsult;
        this.patients = patients;
    }

    public String getTherapy() {
        return therapy;
    }

    public void setTherapy(String therapy) {
        this.therapy = therapy;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getDateConsult() {
        return DateConsult;
    }

    public void setDateConsult(String dateConsult) {
        DateConsult = dateConsult;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Patients getPatients() {
        return patients;
    }

    public void setPatients(Patients patients) {
        this.patients = patients;
    }

    public List<Therapyphotos> getTherapyphotos() {
        return Therapyphotos.findWithQuery(Therapyphotos.class, "select * from Therapyphotos where therapies = ? ", getId().toString());
    }

    @Override
    public String toString() {
        return "Therapy{" +
                "therapy_Id=" + getId() +
                ", dateConsult=" + getDateConsult() +
                ", threatment='" + getTherapy() + '\'' +
                ", analysys='" + getPhoto() + '\'' +
                ", memo='" + getMemo() + '\'' +
                ", patient='" + getPatients().toString() + '\'' +
                '}';
    }
}
