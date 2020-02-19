package com.example.mytime;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Admin_change extends AppCompatActivity {

    Button tl, officer,ho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_change);


        tl=(Button)findViewById(R.id.tl);
        officer=(Button)findViewById(R.id.officer);
        ho=(Button)findViewById(R.id.head);



        final String branch_name=getIntent().getStringExtra("name");
        final String pass=getIntent().getStringExtra("count");
        final int pass1=Integer.parseInt(pass);

        if(pass1==1){

            officer.setEnabled(false);
            ho.setEnabled(false);
            officer.setTextColor(Color.parseColor("#FFF0BB6F"));
            ho.setTextColor(Color.parseColor("#FFF0BB6F"));

        }else if(pass1==2){
            ho.setEnabled(false);
            ho.setTextColor(Color.parseColor("#FFF0BB6F"));
        }

        tl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String count="1";
                Intent in=new Intent(Admin_change.this,admin_pass.class);
                in.putExtra("name",branch_name);
                in.putExtra("count",pass);
                in.putExtra("num",count);
                startActivity(in);


            }
        });
        officer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String count="2";

                Intent in=new Intent(Admin_change.this,admin_pass.class);
                in.putExtra("name",branch_name);
                in.putExtra("count",pass);
                in.putExtra("num",count);
                startActivity(in);
            }
        });
        ho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String count="3";

                Intent in=new Intent(Admin_change.this,admin_pass.class);
                in.putExtra("name",branch_name);
                in.putExtra("count",pass);
                in.putExtra("num",count);
                startActivity(in);
            }
        });


    }

    @Override
    public void onBackPressed() {
        String branch_name=getIntent().getStringExtra("name");
        String pass1=getIntent().getStringExtra("count");
        Intent in=new Intent(Admin_change.this,Choose.class);
        in.putExtra("name",branch_name);
        in.putExtra("count",pass1);
        startActivity(in);


    }
}
