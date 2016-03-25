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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.viewpagerindicator.CirclePageIndicator;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomePage extends AppCompatActivity {
//    private RecyclerView recyclerView;
    private static  ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES={R.drawable.find2,R.drawable.find1,R.drawable.find3};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    private RecyclerView service;
    private String[] category=null;
    GridLayoutManager GridlManager;//gridView manager for RecyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_appbar);
        service=(RecyclerView)findViewById(R.id.serviceList);
        GridlManager=new GridLayoutManager(HomePage.this,2);//fist parameter for context and second for number of columns
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        serviceDetailsAdapter adapter =new serviceDetailsAdapter(this);
        service.setAdapter(adapter);
        service.setLayoutManager(GridlManager);//setting the layout for recylcerView
        service.setHasFixedSize(true);//for fix size
        category = getResources().getStringArray(R.array.service_name);

        SpinnerAdapter spinnerAdapter= ArrayAdapter.createFromResource(getApplicationContext(),R.array.service_name,R.layout.spinner_dropdown_item);
        Spinner navigationSpinner=new Spinner(getSupportActionBar().getThemedContext());
        navigationSpinner.setAdapter(spinnerAdapter);
        toolbar.addView(navigationSpinner, 0);

        navigationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(HomePage.this,
                        "you selected: " + category[position],
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        NavigationDrawerFragment drawerFragment=(NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);

        drawerFragment.setUp(R.id.drawer_fragment,(DrawerLayout)findViewById(R.id.drawer_layout), toolbar);


        init();
    }
    private void init() {
  //    Toast.makeText(HomePage.this, ""+IMAGES.length, Toast.LENGTH_SHORT).show();
        mPager = (ViewPager) findViewById(R.id.pager);
        for(int i=0;i<IMAGES.length;i++) {
            ImagesArray.add(IMAGES[i]);

        }


        mPager.setAdapter(new SlidingImageAdapter(HomePage.this,ImagesArray));


        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

       final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES =IMAGES.length;

        // Auto start of viewpager

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                   // mPager.setCurrentItem(currentPage, true);
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 5000, 3000);
        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });

    }

}
//class information as a service details
class ServiceImages
{
    int serviceImages;
    String serviceName;

    ServiceImages(int serviceImages,String serviceName)
    {
        this.serviceImages=serviceImages;
        this.serviceName=serviceName;
    }
}
//RecyclerView adapter
class serviceDetailsAdapter extends RecyclerView.Adapter<serviceDetailsAdapter.myViewHolder> {
    Context context;
    ArrayList<ServiceImages> list;
    serviceDetailsAdapter(Context context)
    {
        this.context=context;
        list=new ArrayList<ServiceImages>();
        int[] serviceImages={R.drawable.carpenter,R.drawable.civil_works,R.drawable.computer_repair,
                R.drawable.doctor,R.drawable.driver,R.drawable.electrician,R.drawable.fabrication,
                R.drawable.mechanic,R.drawable.party,R.drawable.pathology,R.drawable.plumber,
                R.drawable.tailor,R.drawable.weeding_planner};
        Resources res=context.getResources();
        String[] serviceName=res.getStringArray(R.array.service_name);
        for(int i=0;i<=12;i++)
        {
            ServiceImages tempCountry=new ServiceImages(serviceImages[i],serviceName[i]);
            list.add(tempCountry);
        }


    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_gridview, null);
        myViewHolder rcv = new myViewHolder(layoutView);


        return rcv;


    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        ServiceImages temp =list.get(position);
        holder.serviceimage.setImageResource(temp.serviceImages);
        holder.servicename.setText(temp.serviceName);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView serviceimage;
        TextView servicename;
        public myViewHolder(View itemView)
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


