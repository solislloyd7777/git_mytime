package com.example.mytime;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class Choose extends AppCompatActivity {
    Button add_emp,choose,comp,admin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        add_emp=findViewById(R.id.add_emp);
        choose=findViewById(R.id.choose_branch);
        comp=findViewById(R.id.comp);
        admin=findViewById(R.id.change);




        int pass2=Integer.parseInt(getIntent().getStringExtra("count"));

        if(pass2==1){
            add_emp.setEnabled(false);
            choose.setEnabled(false);
            add_emp.setTextColor(Color.parseColor("#FFF0BB6F"));
            choose.setTextColor(Color.parseColor("#FFF0BB6F"));
        }

        add_emp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(Choose.this,AddEmp.class);
                intent.putExtra("name",getIntent().getStringExtra("name"));
                intent.putExtra("count",getIntent().getStringExtra("count"));
                startActivity(intent);

            }
        });

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Choose.this,Branch.class);
                intent.putExtra("name",getIntent().getStringExtra("name"));
                intent.putExtra("count",getIntent().getStringExtra("count"));
                startActivity(intent);

            }
        });

        comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Choose.this,compiler.class);
                intent.putExtra("name",getIntent().getStringExtra("name"));
                intent.putExtra("count",getIntent().getStringExtra("count"));
                startActivity(intent);

            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Choose.this,Admin_change.class);
                intent.putExtra("name",getIntent().getStringExtra("name"));
                intent.putExtra("count",getIntent().getStringExtra("count"));
                startActivity(intent);
            }
        });
    }


    public void logout(View view) {



        Intent in=new Intent(Choose.this,Admin_log.class);
        startActivity(in);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Choose.this,Choose.class);
        intent.putExtra("name",getIntent().getStringExtra("name"));
        intent.putExtra("count",getIntent().getStringExtra("count"));
        startActivity(intent);


    }
}
