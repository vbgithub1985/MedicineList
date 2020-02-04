package com.example.medicinelist.entity;

import com.orm.SugarRecord;

import java.util.List;

public class Patients extends SugarRecord {
    private String name;
    private String email;
    private String phone;
    private int age;
    private String diagnosis;
    private String DateFirstConsult;
    private String memo;
    private Categories category;

    public Patients() {
    }

    public Patients(String name, String phone, String dateFirstConsult, String diagnosis) {
        this.name = name;
        this.phone = phone;
        this.DateFirstConsult = dateFirstConsult;
        this.diagnosis = diagnosis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getDateFirstConsult() {
        return DateFirstConsult;
    }

    public void setDateFirstConsult(String dateFirstConsult) {
        DateFirstConsult = dateFirstConsult;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public List<Therapies> getTherapies() {
        return Therapies.findWithQuery(Therapies.class, "select * from Therapies where patients = ? order by replace(Date_consult,'/','') desc", getId().toString());
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "ID = " + getId() + '\'' +
                "name='" + getName() + '\'' +
                "phone='" + getPhone() + '\'' +
                "age='" + getAge() + '\'' +
                "diagnosis='" + getDiagnosis() + '\'' +
                '}';
    }
}
