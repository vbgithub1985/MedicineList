package com.example.medicinelist.support;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class TextWatcherP implements TextWatcher {
    public EditText editText;
    private final String LOG_TAG = "myLogs_TextWatcherP";
    private int selPos;
    private String oldString, newString;
    private StringBuilder strNew = new StringBuilder();
    private StringBuilder strOld = new StringBuilder();
    public TextWatcherP(EditText et){
        super();
        editText = et;
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        /*//oldString = s.toString();
        StringBuilder stringBuilder = new StringBuilder(strOld);
        if (s.charAt(start)=='_'){
            strNew.append(stringBuilder.substring(0,start));
        }
        Log.d(LOG_TAG,"beforeTextChanged_oldString-"+oldString);
        Log.d(LOG_TAG,"beforeTextChanged_newString-"+strNew.toString());*/
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        /*strOld.append(s.toString());
        strNew.append(s.charAt(start));
        strNew.append(s.subSequence(start+2,s.length()));
       // editText.setText(strNew.toString());
        //selPos = start+2;
        //strNew.append(s.subSequence(start+1,s.length()));
        Log.d(LOG_TAG,"onTextChanged_newString-"+strNew.toString());*/
    }

    @Override
    public void afterTextChanged(Editable s) {
        /*if (!strNew.toString().contains("_")){
            editText.setText(strNew.toString());
        }*/
        if (s.length()==10 && (!s.toString().contains("+38"))){
            editText.setText("+38"+s.toString());
        }
    }


}
