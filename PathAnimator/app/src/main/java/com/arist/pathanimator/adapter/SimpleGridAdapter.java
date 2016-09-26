package com.arist.pathanimator.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arist.pathanimator.R;
import com.arist.pathanimator.view.CircleLoadingSuccessView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by arist on 16/9/11.
 */
public class SimpleGridAdapter extends BaseAdapter {

    public Handler handler;

    private ArrayList<JSONObject> datas;
    private Context context;

    public SimpleGridAdapter(Context context, ArrayList<JSONObject> datas){
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

        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.grid_item_layout, null);
            holder = new ViewHolder();
            holder.iv = (ImageView) view.findViewById(R.id.iv);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        JSONObject jsonObject = datas.get(i);
        try {

            if (handler != null) {
                Message message = handler.obtainMessage(0x10);
                message.obj = holder.iv;
                handler.sendMessage(message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private class ViewHolder{
        private ImageView iv;
    }


}
