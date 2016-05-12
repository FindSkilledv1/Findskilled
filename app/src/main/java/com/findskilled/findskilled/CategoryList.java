package com.findskilled.findskilled;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Network.VolleySingleton;

public class CategoryList extends AppCompatActivity {
    String a;
    RecyclerView categoryListView;

    VolleySingleton volleySingleton;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
//        getting the intent Extra value from homepage with the name "Name"  m.getStringExtra("Name");
        Intent m =getIntent();

        a= m.getStringExtra("Name");

        Toast.makeText(CategoryList.this,"locality name   "+a,Toast.LENGTH_LONG).show();

//      intializatlistView of categorylistview
        categoryListView=(RecyclerView)findViewById(R.id.categoryList);

        volleySingleton =VolleySingleton.getInstance();

        requestQueue=volleySingleton.getRequestQueue();

        sendJsonRequest();




    }

    private void sendJsonRequest() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getRequestUrl(10), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        });


    }


//    private void JsonResp(String b) throws JSONException {
//
//        JSONObject jsonObject=new JSONObject(b);
//        JSONArray jsonArray=jsonObject.getJSONArray("user");
//        for (int i=0;i<=jsonArray.length();i++){
//
//            JSONObject jobj = jsonArray.getJSONObject(i);
//
//
////            catlistid.add(jobj.getString("id"));
////            catlist.add(jobj.getString("name"));
//
//
//        }
//
//
//
//    }




}
//class information as a service details
class CategoryDetails
{
    int CategoryImages;
    String CategoryName;

    CategoryDetails(int CategoryImages,String CategoryName)
    {
        this.CategoryImages=CategoryImages;
        this.CategoryName=CategoryName;
    }
}
//RecyclerView adapter
class categoryDetailsAdapter extends RecyclerView.Adapter<categoryDetailsAdapter.myViewHolders> {
    private LayoutInflater setLayout;
    Context context;
    List<CategoryDetails> list= Collections.emptyList();//Collections.emptyList() for avoiding the null pointer exception
    categoryDetailsAdapter(Context context,List<CategoryDetails> list)
    {
        this.context=context;
        this.list=list;
//        inflating xml for each row only once (or optimization in RecyclerView )
        setLayout= LayoutInflater.from(context);//inflating single_item_grid_view.xml so as to create a new viewHolder and which should be constructed


    }
    //when a new row is displayed onCreateViewHolder() is called
    @Override
    public myViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {//method is called when a recycler view needs a new recyclerView.Viewholder of the given type to represent an item

        View rootView=setLayout.inflate(R.layout.single_item_list_category,parent,false);//view(variable) is a root of single_item_gridview.xml eg relativeLayotu in single_item_gridview.xml
        myViewHolders holder = new myViewHolders(rootView);//rootView is passed to myViewHolder constructor


        return holder;


    }

//     onBindViewHolder() is called for setting the data that should correspond to current row
//     holder for the setting the data for a current position
//     position current position
    @Override
    public void onBindViewHolder(myViewHolders holder, int position) {

//        holder.serviceimage.setImageResource(list.serviceImages);
            holder.servicename.setText(list.);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView serviceimage;
        TextView servicename;
        public myViewHolders(View itemView)//finding the image and text view from single_item_gridview.xml by using rootView which is passed from oncreateViewholder()
        {
            super(itemView);
            serviceimage=(ImageView)itemView.findViewById(R.id.imageView2);
            servicename=(TextView)itemView.findViewById(R.id.categoryName);
        }
        @Override
        public void onClick(View v) {

        }
    }
}