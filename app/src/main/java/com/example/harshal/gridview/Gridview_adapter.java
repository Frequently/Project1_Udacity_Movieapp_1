package com.example.harshal.gridview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Harshal on 21-07-2016.
 */
public class Gridview_adapter extends BaseAdapter {

    ArrayList<String> imageslist;
    Context context;


    public Gridview_adapter(Context context,ArrayList<String> list)
    {
        this.context = context;
        imageslist = list;
    }

    @Override
    public int getCount() {
        return imageslist.size();
    }

    @Override
    public Object getItem(int position) {
        return imageslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder;
        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.custom_row,parent,false);
            holder = new ViewHolder(row);
            row.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) row.getTag();
        }



        Picasso.with(context)
                .load(imageslist.get(position))
                .placeholder(R.mipmap.ic_launcher) // can also be a drawable
                .error(R.drawable.errorimage) // will be displayed if the image cannot be loaded
                .into(holder.poster);


        return row;
    }

    class ViewHolder
    {
        @Bind(R.id.poster)ImageView poster;

        public ViewHolder(View v)
        {
            ButterKnife.bind(this, v);
            //poster = (ImageView) v.findViewById(R.id.poster);
        }
    }
}
