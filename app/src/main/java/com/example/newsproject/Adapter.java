package com.example.newsproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {

    Context context;
    ArrayList<String> titleS;
    ArrayList<String> imageS;
    ArrayList<String> authorS;
    LayoutInflater layoutInflater;

    public Adapter(Context context, ArrayList<String> titleS, ArrayList<String> imageS, ArrayList<String> authorS) {
        this.context = context;
        this.titleS = titleS;
        this.imageS = imageS;
        this.authorS = authorS;

        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return titleS.toArray().length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.customlistview,null);
        TextView txtViewTitle = convertView.findViewById(R.id.textView2);
        TextView txtViewAuthors = convertView.findViewById(R.id.textView1);
        ImageView imageView = convertView.findViewById(R.id.imageView237);

        txtViewAuthors.setText(authorS.get(position));
        txtViewTitle.setText(titleS.get(position));
        Picasso.get().load(imageS.get(position)).into(imageView);
        return convertView;
    }
}
