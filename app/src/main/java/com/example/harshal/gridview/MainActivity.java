package com.example.harshal.gridview;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.string.gridview_xml)GridView mygrid;
    String url;
    RequestQueue requestQueue;
    JSONArray jsonArray;
    String baseurl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        baseurl = getResources().getString(R.string.baseurl);
        url = getResources().getString(R.string.popular_api);

        requestQueue = VolleySingleton.getInstance().getRequestQueue();


        mygrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                try {
                    Intent intent = new Intent(getApplicationContext(),DetailActivity.class);

                    JSONObject object = jsonArray.getJSONObject(position);
                    String title = object.getString("original_title");
                    String release_date = object.getString("release_date");
                    String rating = object.getString("vote_average");
                    String trivia = object.getString("overview");
                    String poster = object.getString("poster_path");
                    poster = baseurl + poster;

                    intent.putExtra("title",title);
                    intent.putExtra("date",release_date);
                    intent.putExtra("rating",rating);
                    intent.putExtra("Info",trivia);
                    intent.putExtra("path",poster);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });

        display_poster();



    }


    public void display_poster()
    {
        if(isOnline())
        {

            StringRequest request = new StringRequest(Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                parseJson(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //Toast.makeText(getApplicationContext(),"Response "+response,Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Error "+error.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });

            requestQueue.add(request);
        }
        else
        {
            Toast.makeText(getApplicationContext(),""+getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }


    }
    public void parseJson(String JSON_STRING) throws JSONException {

        JSONObject reader = new JSONObject(JSON_STRING);

        jsonArray = reader.getJSONArray("results");

        //Toast.makeText(getApplicationContext(),"JSONARRAY "+jsonArray,Toast.LENGTH_SHORT).show();



        ArrayList<String> data = new ArrayList<String>();

        for(int i=0;i<jsonArray.length();i++)
        {
            JSONObject object = jsonArray.getJSONObject(i);
            String url = object.getString("poster_path");
            //Toast.makeText(getApplicationContext(),"StringURL "+url,Toast.LENGTH_SHORT).show();

            url = baseurl + url;

            data.add(url);
        }

        mygrid.setAdapter(new Gridview_adapter(getApplicationContext(),data));


    }


 /*   private ArrayList<Integer> getData()
    {
        ArrayList<String> list = new ArrayList<String>();

        for(int i=0;i<10;i++)
        {
            list.add(R.mipmap.ic_launcher);
        }

        return list;
    }

*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainactivity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.



        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mostpopular) {

            if(isOnline())
            {
                url = getResources().getString(R.string.popular_api);
                display_poster();
            }
            else
            {
                Toast.makeText(getApplicationContext(),""+getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
        }
        else if(id == R.id.highrated)
        {
            if(isOnline())
            {
                url = getResources().getString(R.string.toprated_api);
                display_poster();
            }
            else
            {
                Toast.makeText(getApplicationContext(),""+getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }

        }

        return super.onOptionsItemSelected(item);
    }



    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
