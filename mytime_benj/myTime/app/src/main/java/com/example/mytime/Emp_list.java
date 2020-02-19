package com.example.mytime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.List;

public class Emp_list extends AppCompatActivity {

    ListView list_emp;
    List<employee> list;
    ArrayAdapter<employee> arrayAdapter;
    MyClass myClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_list);


        list_emp=findViewById(R.id.list_emp);
        myClass=new MyClass(this);



        list=myClass.getAllemployee();
        arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
        list_emp.setAdapter(arrayAdapter);

        list_emp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                employee emp= list.get(position);



                //String branch_name=getIntent().getStringExtra("name");
                //String pass1=getIntent().getStringExtra("count");
                Intent in=new Intent(Emp_list.this,Updata_delete.class);
                in.putExtra("name",getIntent().getStringExtra("name"));
                in.putExtra("count",getIntent().getStringExtra("count"));
                in.putExtra("EMPLOYEE", emp);
                startActivity(in);
            }
        });
    }

    @Override
    public void onBackPressed() {

        //String branch_name=getIntent().getStringExtra("name");
        //String pass1=getIntent().getStringExtra("count");
        Intent in=new Intent(Emp_list.this,AddEmp.class);
        in.putExtra("name",getIntent().getStringExtra("name"));
        in.putExtra("count",getIntent().getStringExtra("count"));
        startActivity(in);
    }


}
