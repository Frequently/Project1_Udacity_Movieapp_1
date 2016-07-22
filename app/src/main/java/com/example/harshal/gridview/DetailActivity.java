package com.example.harshal.gridview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @Bind(R.string.title)TextView title_movie;
    @Bind(R.string.releasedate)TextView realease_date;
    @Bind(R.string.rating)TextView user_rating;
    @Bind(R.string.information)TextView additional_info;

    @Bind(R.string.imageView)ImageView poster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);
        Intent intent = getIntent();

        title_movie.setText(intent.getStringExtra("title"));
        realease_date.setText(intent.getStringExtra("date"));
        user_rating.setText(intent.getStringExtra("rating"));
        additional_info.setText(intent.getStringExtra("Info"));


        Picasso.with(getApplicationContext())
                .load(intent.getStringExtra("path"))
                .placeholder(R.mipmap.ic_launcher) // can also be a drawable
                .error(R.drawable.errorimage) // will be displayed if the image cannot be loaded
                .into(poster);

    }
}
