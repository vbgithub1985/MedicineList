package com.example.medicinelist.support;

import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class TextWatcherP implements TextWatcher {
    public EditText mEditText;
    private final String LOG_TAG = "myLogs_TextWatcherP";
    private final int PHONE_NUMBER_LENGTH = 10;
    private final int PHONE_FORMAT_TRIGGER_LENGTH = PHONE_NUMBER_LENGTH+7;
    private final String PHONE_FORMAT = "+38(0%s)%s-%s-%s";
    private boolean mIsDeleting;
    private int mOnTextChangedCount, mBeforeTextChangedCount;

    //private StringBuilder strOld = new StringBuilder();
    public TextWatcherP(EditText et){
        super();
        mEditText = et;
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        mBeforeTextChangedCount = count;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mOnTextChangedCount = count;
        if (s.length() < PHONE_FORMAT_TRIGGER_LENGTH){
        String regex = "\\d+";
            if (!s.toString().matches(regex)){
                mEditText.removeTextChangedListener(this);
                mEditText.setText(toOneFormat(s.toString()));
                mEditText.setSelection(mEditText.getText().toString().length());
                mEditText.addTextChangedListener(this);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

        int kodbegin, kodend, threebegin, threeend, two1begin, two1end, two2begin, two2end;
        if (!s.toString().startsWith("38")){
            kodbegin = 1; kodend = 3; threebegin = 3; threeend = 6; two1begin = 6; two1end = 8; two2begin = 8; two2end = 10;
        }
        else{
            kodbegin = 3; kodend = 5; threebegin = 5; threeend = 8; two1begin = 8; two1end = 10; two2begin = 10; two2end = 12;
        }
        mIsDeleting = mOnTextChangedCount < mBeforeTextChangedCount;
        if(!mIsDeleting && s.length() == PHONE_NUMBER_LENGTH) {
           mEditText.removeTextChangedListener(this);
           mEditText.setText(toFormatNumbers(s.toString(),kodbegin, kodend, threebegin, threeend, two1begin, two1end, two2begin, two2end));
           mEditText.setSelection(mEditText.getText().toString().length());
           mEditText.addTextChangedListener(this);
        }

        //mEditText.setText(PhoneNumberUtils.formatNumber(s.toString()));

        /*if (s.length()==10 && (!s.toString().contains("+38"))){
            editText.setText("+38"+s.toString());
        }*/
    }
    private String toFormatNumbers(String s, int kodbegin, int kodend, int threebegin, int threeend, int two1begin, int two1end, int two2begin, int two2end ) {
        return String.format(PHONE_FORMAT, s.substring(kodbegin,kodend), s.substring(threebegin,threeend), s.substring(two1begin,two1end), s.substring(two2begin,two2end));
    }

    private String toPlainNumbers(String s) {
        return s.replace("-", "").replace("+38","");
    }

    private String toOneFormat(String s){
        return s.replace("-","").replace("+38","").replace("+","").replace("(","").replace(")","");
    }

}
