package com.example.mytime;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AddEmp extends AppCompatActivity {

    EditText name, usercode, password,cpass;
    Button save, show;
    Spinner spin;
    int id;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emp);
        MyClass myClass=new MyClass(this);
        spin=(Spinner)findViewById(R.id.spinner);
        name=(EditText)findViewById(R.id.name_txt);
        usercode=(EditText)findViewById(R.id.user_txt);
        password=(EditText)findViewById(R.id.pass_txt);
        cpass=(EditText)findViewById(R.id.cpass_txt);
        save=(Button)findViewById(R.id.update);
        show=(Button)findViewById(R.id.delete);

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

        ArrayList<String> list=myClass.getAllPosition();
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.txt,list);
        spin.setAdapter(adapter);

        myClass=new MyClass(this);
        myClass.StartWork();
        db=myClass.getWritableDatabase();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyClass myClass=new MyClass(AddEmp.this);



                String branch1=spin.getSelectedItem().toString();
                /*Cursor cursor=db.rawQuery("select mh_position_ID from mh_position where mh_description='"+branch1+"'",null);
                cursor.moveToFirst();
                int o=cursor.getInt(0);*/



                int posID=myClass.getPositionId(branch1);

                String name1=name.getText().toString();

                if(TextUtils.isEmpty(name1)){
                    name.setError("Please provide name");
                    return;
                }
                String usercode1=usercode.getText().toString();
                if(TextUtils.isEmpty(usercode1)){
                    usercode.setError("Please provide userCode");
                    return;
                }

                String password1=password.getText().toString();
                if(TextUtils.isEmpty(password1)){
                    password.setError("Please provide password");
                    return;
                }
                String c_pass1=cpass.getText().toString();
                if(TextUtils.isEmpty(c_pass1)){
                    cpass.setError("Please provide confirm password");
                    return;
                }

                if(usercode1.length()>6){
                    usercode.setError("maximum of 6 digits allowed");
                    usercode.setText("");
                }

                else{
                    if(password1.equals(c_pass1)){

                        Boolean checkmail= myClass.Checkuser(usercode1);
                        if(checkmail==true){

                            try{

                                myClass.insertInfo(posID,name1,usercode1,password1);
                                //db.execSQL("insert into mh_emp(mh_position_ID,mh_name,mh_userCode,mh_passCode) values('"+posID+"','"+name1+"','"+usercode1+"','"+password1+"')");
                                Toast.makeText(AddEmp.this,"succesful",Toast.LENGTH_SHORT).show();

                            }catch (Exception ex){
                                Toast.makeText(AddEmp.this,ex.getMessage().toString(),Toast.LENGTH_LONG).show();
                            }

                            name.setText("");
                            usercode.setText("");
                            password.setText("");
                            cpass.setText("");

                        }else{
                            usercode.setError("UserCode Already exists");
                            Toast.makeText(AddEmp.this,"UserCode already exist!!",Toast.LENGTH_LONG).show();
                        }


                    }else{

                        Toast.makeText(AddEmp.this,"Password didn't match",Toast.LENGTH_SHORT).show();

                        password.setText("");
                        cpass.setText("");

                    }

                }



            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent in=new Intent(AddEmp.this,Emp_list.class);
                in.putExtra("name",getIntent().getStringExtra("name"));
                in.putExtra("count",getIntent().getStringExtra("count"));
                startActivity(in);
            }
        });

    }


    public void toadmin(View view) {

        Intent in=new Intent(AddEmp.this,Admin_log.class);
        startActivity(in);
    }

    @Override
    public void onBackPressed() {
        Intent in=new Intent(AddEmp.this,Choose.class);
        in.putExtra("name",getIntent().getStringExtra("name"));
        in.putExtra("count",getIntent().getStringExtra("count"));
        startActivity(in);
    }
}
