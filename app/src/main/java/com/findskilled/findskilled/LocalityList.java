package com.findskilled.findskilled;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LocalityList extends AppCompatActivity {
    ArrayList<String> localityListing,listid;
    ListView localityList;
    SweetAlertDialog pDialog;
    //bydefault keep the value as false
    Boolean isInternetPresent = false;
    //to check whether internet is available or not
    InternetConnectionDetector icd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Locality");
        Intent m = getIntent();
        Toast.makeText(this,"intennnnnt   "+m,Toast.LENGTH_LONG).show();
        localityList=(ListView)findViewById(R.id.localityListing);

        localityListing=new ArrayList<>();

        listid=new ArrayList<>();

        icd=new InternetConnectionDetector(getApplicationContext());
        isInternetPresent=icd.isConnectingToInternet();
        if(isInternetPresent) {
            ConnectToServer con = new ConnectToServer();
            con.execute();
        }
        else{
            pDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("check your internet connectivity!")
                    .setConfirmText("Ok.")
                    .showCancelButton(true)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.cancel();
                            finish();
                        }
                    });

            pDialog.show();
        }
        localityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(LocalityList.this,""+listid.get(position),Toast.LENGTH_LONG).show();
                String name=localityListing.get(position);
                Intent main=new Intent(LocalityList.this,HomePage.class);
                main.putExtra("localityName",name);
                startActivity(main);
            }
        });

    }
    class ConnectToServer extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new SweetAlertDialog(LocalityList.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        localityList.setAdapter(new ArrayAdapter<String>(LocalityList.this, android.R.layout.simple_list_item_1, localityListing));
            pDialog.dismiss();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ServerReq req= new ServerReq();
            String cityName="Dehradun";

            String a;
            try {
                a = req.getLocalityName(cityName);
                JsonResponce(a);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
        public void JsonResponce(String s) throws JSONException {


            JSONObject json = new JSONObject(s);
            JSONArray jarray = json.getJSONArray("user");


            for (int i = 0; i <= jarray.length(); i++) {

                JSONObject jobj = jarray.getJSONObject(i);


                listid.add(jobj.getString("id"));
                localityListing.add(jobj.getString("name"));


            }

        }
    }
}
