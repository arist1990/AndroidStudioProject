package com.arist.pathanimator.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.arist.pathanimator.R;
import com.arist.pathanimator.view.CircleLoadingSuccessView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by arist on 16/9/11.
 */
public class CircleLoadingSuccessAdapter extends BaseAdapter {

    public Handler handler;

    private ArrayList<JSONObject> datas;
    private Context context;

    public CircleLoadingSuccessAdapter(Context context, ArrayList<JSONObject> datas){
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
            view = LayoutInflater.from(context).inflate(R.layout.list_item_circle_loading_success, null);
            holder = new ViewHolder();
            holder.tvName = (TextView) view.findViewById(R.id.tvName);
            holder.circleLoadingSuccessView = (CircleLoadingSuccessView) view.findViewById(R.id.progressView);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        JSONObject jsonObject = datas.get(i);
        try {


            holder.tvName.setText(jsonObject.getString("title"));

            holder.circleLoadingSuccessView.setTag(jsonObject.getString("id"));
            holder.circleLoadingSuccessView.setOnCircleLoadingListener(new CircleLoadingSuccessView.OnCircleLoadingListener() {
                @Override
                public void onStart(CircleLoadingSuccessView circleLoadingSuccessView) {
                    // 开始下载
                    String id = circleLoadingSuccessView.getTag().toString();

                    Log.e("info", "onStart:" + id);

                    Message message = handler.obtainMessage(0x101);
                    message.obj = id;
                    handler.sendMessage(message);
                }
                @Override
                public void onStop(CircleLoadingSuccessView circleLoadingSuccessView) {
                    // 结束下载
                    String id = circleLoadingSuccessView.getTag().toString();
                    Log.e("info", "onStop:" + id);

                    Message message = handler.obtainMessage(0x102);
                    message.obj = id;
                    handler.sendMessage(message);
                }
            });

            holder.circleLoadingSuccessView.setProgress(Float.parseFloat(jsonObject.getString("progress")));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

    private class ViewHolder{
        private TextView tvName;
        private CircleLoadingSuccessView circleLoadingSuccessView;
    }


}
