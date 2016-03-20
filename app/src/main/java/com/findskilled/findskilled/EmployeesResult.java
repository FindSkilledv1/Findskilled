package com.findskilled.findskilled;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class EmployeesResult extends AppCompatActivity {
    private RecyclerView employeelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees_result);
        employeelist=(RecyclerView)findViewById(R.id.employeelist);

    }



}
