package com.example.medicinelist.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.medicinelist.R;
import com.example.medicinelist.entity.Categories;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    private Context context;
    private List<Categories> categories;

    public CategoryAdapter(Context context, List<Categories> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int i) {
        return categories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = View.inflate(context, R.layout.frame_layout_cat, null);
        }
        TextView ther_title = view.findViewById(R.id.tvCatName);
        TextView ther_memo = view.findViewById(R.id.tvCatMemo);
        Categories category = categories.get(i);
        int len = category.getMemo().length()>75 ? 75 : category.getMemo().length();
        String str_memo = category.getMemo().length()>len ? category.getMemo().substring(0,len)+"..." : category.getMemo();
        String str_title = category.getMemo().length()>len ? category.getName().replace("/",".")+ "\n\n":category.getName().replace("/",".")+ "\n";
        ther_memo.setText(str_memo);
        ther_title.setText(str_title);
        return view;
    }
}
