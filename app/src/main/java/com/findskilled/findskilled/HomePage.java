package com.findskilled.findskilled;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePage extends AppCompatActivity {

    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    private RecyclerView service;
    private String[] category=null;
    GridLayoutManager GridlManager;//gridView manager for RecyclerView
    serviceDetailsAdapter adapter;
    private TextView localityTitle;
    private Button BrowseCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_appbar);

        BrowseCategory=(Button)findViewById(R.id.browseMoreButton);

        service=(RecyclerView)findViewById(R.id.serviceList);

        GridlManager=new GridLayoutManager(HomePage.this,2);//fist parameter for context and second for number of columns

        localityTitle=(TextView)findViewById(R.id.localityTitle);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setting up the toolbar as an actionbar/appbar in android passing the object of the toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter =new serviceDetailsAdapter(this,getData());//setting the adapter for recyclerView

        service.setAdapter(adapter);
        service.setLayoutManager(GridlManager);//setting the layout for recylcerView

        String localityName="clement town";


        localityTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, LocalityList.class);
                intent.replaceExtras(new Bundle());
                intent.setAction("");
                intent.setData(null);
                intent.setFlags(0);
                startActivity(intent);

            }
        });
//       We are receiving intent to set a title from MainPage and LocalityList page as well
        final Intent m = getIntent();
        final String b = m.getStringExtra("autocompleteLocalityName");
//       Toast.makeText(this,"Intent     "+b,Toast.LENGTH_LONG).show();
        if(b!=null) {
            localityTitle.setText("  "+b);

        }
        else
        {

            final  String a = m.getStringExtra("localityName");
            localityTitle.setText("  "+a);

        }

//      Browse category button fore more categories display
        BrowseCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainns=new Intent(HomePage.this,CategoryList.class);
                String a= localityTitle.getText().toString();
                Toast.makeText(HomePage.this,"Title     "+a,Toast.LENGTH_LONG).show();
                mainns.putExtra("Name",a);
                startActivity(mainns);
            }
        });

        category = getResources().getStringArray(R.array.service_name);

//        NavigationDrawerFragment drawerFragment=(NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
//
//       drawerFragment.setUp(R.id.drawer_fragment,(DrawerLayout)findViewById(R.id.drawer_layout), toolbar);

    }
    public static List<ServiceDetails> getData()//collection of data to display in recyclerView
    {
          List<ServiceDetails> data=new ArrayList<>();
//          Context context=null;


        int[] serviceImages={R.drawable.carpenter,R.drawable.civil_works,R.drawable.computer_repair,
                R.drawable.doctor,R.drawable.driver,R.drawable.electrician,R.drawable.fabrication,R.drawable.mechanic,R.drawable.weeding_planner
        };
//        Resources res=context.getResources();
//        String[] serviceName=res.getStringArray(R.array.service_name);
        String[] serviceName={"carpenter","construction","computer repair","doctor","driver","electrical","febrication","mechanic","wedding planner"};
        for(int i=0;i<=8;i++)
        {
            ServiceDetails current=new ServiceDetails(serviceImages[i],serviceName[i]);
            data.add(current);
        }
        return data;


    }


}
//class information as a service details
class ServiceDetails
{
    int serviceImages;
    String serviceName;

    ServiceDetails(int serviceImages,String serviceName)
    {
        this.serviceImages=serviceImages;
        this.serviceName=serviceName;
    }
}
//RecyclerView adapter
class serviceDetailsAdapter extends RecyclerView.Adapter<serviceDetailsAdapter.myViewHolder> {
    private LayoutInflater setLayout;
    Context context;
    List<ServiceDetails> list= Collections.emptyList();//Collections.emptyList() for avoiding the null pointer exception
    serviceDetailsAdapter(Context context,List<ServiceDetails> list)
    {
        this.context=context;
        this.list=list;
        //inflating xml for each row only once (or optimization in RecyclerView )
        setLayout=LayoutInflater.from(context);//inflating single_item_grid_view.xml so as to create a new viewHolder and which should be constructed


    }
//when a new row is displayed onCreateViewHolder() is called
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {//method is called when a recycler view needs a new recyclerView.Viewholder of the given type to represent an item

        View rootView=setLayout.inflate(R.layout.single_item_gridview,parent,false);//view(variable) is a root of single_item_gridview.xml eg relativeLayotu in single_item_gridview.xml
        myViewHolder holder = new myViewHolder(rootView);//rootView is passed to myViewHolder constructor


        return holder;


    }
//     onBindViewHolder() is called for setting the data that should correspond to current row
//     holder for the setting the data for a current position
//     position current position
    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        ServiceDetails current =list.get(position);
        holder.serviceimage.setImageResource(current.serviceImages);
        holder.servicename.setText(current.serviceName);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView serviceimage;
        TextView servicename;
        public myViewHolder(View itemView)//finding the image and text view from single_item_gridview.xml by using rootView which is passed from oncreateViewholder()
        {
            super(itemView);
            serviceimage=(ImageView)itemView.findViewById(R.id.serviceimage);
            servicename=(TextView)itemView.findViewById(R.id.servicename);
        }
        @Override
        public void onClick(View v) {

        }
    }
}


