package com.findskilled.findskilled;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class CategoryList extends AppCompatActivity {
    String a;
    ListView categoryListView;
    ArrayList<String> catlist=new ArrayList<>();
    ArrayList<String> catlistid=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
//        getting the intent Extra value from homepage with the name "Name"  m.getStringExtra("Name");
        Intent m =getIntent();

        a= m.getStringExtra("Name");

        Toast.makeText(CategoryList.this,"locality name   "+a,Toast.LENGTH_LONG).show();

//        intializatlistView of categorylistview
        categoryListView=(ListView)findViewById(R.id.categoryList);



    }

//        class to get a Categories list from the server
    class GetCategoryList extends AsyncTask<Void,Void,Void>
{
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        categoryListView.setAdapter(new ArrayAdapter<String>(CategoryList.this,R.layout.single_item_list_category,catlist));

    }

    @Override
    protected Void doInBackground(Void... params) {
//        creating the instance of ServerReq class to call the getCategory method for result
        ServerReq sr=new ServerReq();
        String b;
        try {
            b=sr.getCategory(a);
//            json method call to fetch the json data from server
            JsonResp(b);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }


}

    private void JsonResp(String b) throws JSONException {

        JSONObject jsonObject=new JSONObject(b);
        JSONArray jsonArray=jsonObject.getJSONArray("user");
        for (int i=0;i<=jsonArray.length();i++){

            JSONObject jobj = jsonArray.getJSONObject(i);


            catlistid.add(jobj.getString("id"));
            catlist.add(jobj.getString("name"));

        }



    }


}
