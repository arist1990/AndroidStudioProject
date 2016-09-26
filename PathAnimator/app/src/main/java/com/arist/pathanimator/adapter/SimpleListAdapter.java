package com.arist.pathanimator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.arist.pathanimator.R;

import java.util.ArrayList;

/**
 * Created by arist on 16/9/1.
 */
public class SimpleListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> datas;

    public SimpleListAdapter(Context context, ArrayList<String> datas) {

        this.context = context;
        this.datas = datas;

    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.simple_list_item, null);

            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.tv);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.textView.setText(datas.get(i));

        return view;
    }

    private static class ViewHolder{
        TextView textView;
    }

}
