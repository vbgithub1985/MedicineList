package com.example.medicinelist.entity;

import com.orm.SugarRecord;

import java.util.List;

public class Categories extends SugarRecord implements Comparable<Categories> {
    private String name;
    private String memo;

    public Categories() {
    }

    public Categories(String name, String memo) {
        this.name = name;
        this.memo = memo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public List<Patients> getPatients() {
        return Patients.findWithQuery(Patients.class, "select * from Patients p where category = ? order by p.name", getId().toString());
    }

    @Override
    public String toString() {
        return "Categories{" +
                "id='" + getId() + '\'' +
                "name='" + name + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }

    @Override
    public int compareTo(Categories o) {
        return this.name.compareTo(o.getName());
    }
}
