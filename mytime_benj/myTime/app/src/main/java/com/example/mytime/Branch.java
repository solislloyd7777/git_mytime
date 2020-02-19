package com.example.mytime;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Branch extends AppCompatActivity {

    Button save,log;
    TextView branch;
    Spinner spin;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch);

        spin=(Spinner)findViewById(R.id.spinner);
        save=(Button)findViewById(R.id.save);
        log=(Button)findViewById(R.id.log);

        branch=(TextView)findViewById(R.id.branch_text);



        String branch_name=getIntent().getStringExtra("name");
        branch.setText(branch_name);


        String admin=getIntent().getStringExtra("count");
        int admin1=Integer.parseInt(admin);

        if(admin1<=2){
            log.setEnabled(false);
            log.setTextColor(Color.parseColor("#FFF0BB6F"));
        }

        MyClass myClass=new MyClass(this);

        ArrayList<String> list=myClass.getAll();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.txt,list);
        spin.setAdapter(adapter);





        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyClass myClass=new MyClass(Branch.this);
                myClass.StartWork();
                db=myClass.getWritableDatabase();
                String branch1=spin.getSelectedItem().toString();

                //Cursor cursor=db.rawQuery("select mh_branch_ID from mh_branch where mh_branch_name='"+branch1+"'",null);

                //cursor.moveToFirst();
               // int branchID=cursor.getInt(0);
                int branchID=myClass.getBranchId(branch1);

                try{
                    //db.execSQL("update mh_branch set mh_setBranch=null");
                    myClass.nullBranch();
                    myClass.setBranch(branchID);
                    //db.execSQL("update mh_branch set mh_setBranch=1 where mh_branch_ID='"+branchID+"'");
                    Toast.makeText(getApplicationContext(),"successfully saved changes",Toast.LENGTH_LONG).show();
                    //cursor=db.rawQuery("select mh_branch_name from mh_branch where mh_setBranch =1 ",null);
                    //cursor.moveToFirst();
                    Intent intent = new Intent(Branch.this,Branch.class);
                    intent.putExtra("name",myClass.getBranchName());
                    intent.putExtra("count",getIntent().getStringExtra("count"));
                    startActivity(intent);

                }catch (Exception ex){
                    Toast.makeText(Branch.this,ex.getMessage().toString(),Toast.LENGTH_LONG).show();
                }




            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Branch.this,Addbranch.class);
                in.putExtra("name",getIntent().getStringExtra("name"));
                in.putExtra("count",getIntent().getStringExtra("count"));
                startActivity(in);
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent in=new Intent(Branch.this,Choose.class);
        in.putExtra("name",getIntent().getStringExtra("name"));
        in.putExtra("count",getIntent().getStringExtra("count"));
        startActivity(in);
    }

    public void toadmin(View view) {
        Intent in=new Intent(Branch.this,Admin_log.class);
        startActivity(in);
    }
}

