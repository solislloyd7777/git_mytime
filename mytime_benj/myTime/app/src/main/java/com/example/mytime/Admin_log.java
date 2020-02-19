package com.example.mytime;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class Admin_log extends AppCompatActivity {

    EditText ad_pass;
    Button signin;
    MyClass myClass;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_log);


        Thread t=new Thread() {
            @Override
            public void run() {
                try {

                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                long date = System.currentTimeMillis();
                                SimpleDateFormat format = new SimpleDateFormat("HH");
                                String dateString= format.format(date);
                                SimpleDateFormat format1 = new SimpleDateFormat("mm");
                                String dateString1= format1.format(date);
                                SimpleDateFormat format2 = new SimpleDateFormat("ss");
                                String dateString2= format2.format(date);
                                int time=Integer.parseInt(dateString);
                                int time1=Integer.parseInt(dateString1);
                                int time2=Integer.parseInt(dateString2);
                                if(time==0 && time1==0 && time2==0){
                                    finishAffinity();
                                    System.exit(0);
                                }

                            }
                        });


                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };t.start();



        ad_pass=(EditText)findViewById(R.id.password);
        signin=(Button)findViewById(R.id.login_btn);
        myClass=new MyClass(this);
        myClass.StartWork();
        db=myClass.getWritableDatabase();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String pass=ad_pass.getText().toString();
                Boolean check1=myClass.AdminPass(pass);

                if(check1==true){

                   /* Cursor curs=db.rawQuery("select admin_ID from mh_admin where admin_pass='"+pass+"'",null);
                    curs.moveToFirst();
                    String pass1=curs.getString(0);*/
                    String adminID=myClass.AdminID(pass);


                    /*Cursor cursor;
                    cursor=db.rawQuery("select mh_branch_name from mh_branch where mh_setBranch =1 ",null);
                    cursor.moveToFirst();
                    String branchname=cursor.getString(0);*/
                    //String branchname=
                    Intent intent = new Intent(Admin_log.this,Choose.class);
                    intent.putExtra("name",myClass.getBranch());
                    intent.putExtra("count",adminID);
                    startActivity(intent);
                    ad_pass.setText("");


                }else{
                    Toast.makeText(getApplicationContext(),"Wrong Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void to_employee(View view) {
        Intent intent = new Intent(this,Emp_log.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finishAffinity();
                        System.exit(0);


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

}
