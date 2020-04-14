package com.example.medicinelist.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateStringFormat {
    private String dateInput;
    private String dateOutput;
    private String format;

    public DateStringFormat(String dateInput, String format) {
        this.dateInput = dateInput;
        this.format = format;
    }

    public String getDateOutput() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = simpleDateFormat.parse(dateInput);
        dateOutput = simpleDateFormat.format(date);
        return dateOutput;
    }
}
