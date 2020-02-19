package com.example.mytime;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Time_Log extends BaseApp{
    TextView mname,timer,timeee;
    MultiAutoCompleteTextView comment;
    Calendar calendar;
    Button timelog,savecomment;
    MyClass myClass;
    SQLiteDatabase db;
    SQLiteDatabase my;
    SQLiteDatabase mydb;
    private int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time__log);
        mname=(TextView)findViewById(R.id.myname);
        timer=(TextView)findViewById(R.id.timer);
        timeee=(TextView)findViewById(R.id.time_txt);
        comment=(MultiAutoCompleteTextView)findViewById(R.id.comment);
        savecomment=(Button)findViewById(R.id.sendcomment);

        myClass=new MyClass(this);
        myClass.StartWork();
        db=myClass.getWritableDatabase();
        my=myClass.getWritableDatabase();
        mydb=myClass.getWritableDatabase();

        timelog=findViewById(R.id.log_btn);



        savecomment.setEnabled(false);
        comment.setEnabled(false);
        savecomment.setTextColor(Color.parseColor("#FFF0BB6F"));


        final Thread t=new Thread() {
            @Override
            public void run() {
                try {

                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                long date = System.currentTimeMillis();
                                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                                String dateString= format.format(date);
                                timer.setText(dateString);
                            }
                        });


                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };t.start();

        final String id=getIntent().getStringExtra("id");
        final int ids=Integer.parseInt(id);

        final String initialID=getIntent().getStringExtra("timeTypeID");
        final int timetype =Integer.parseInt(initialID);
        final String bid=getIntent().getStringExtra("branch");
        final int bID=Integer.parseInt(bid);
        final String myname=getIntent().getStringExtra("myname");
        final String code=getIntent().getStringExtra("mycode");
        final String bname=getIntent().getStringExtra("branchname");



        mname.setText(myname);



        DateFormat dfDate = new SimpleDateFormat("yyyy/MM/dd");
        final String date1=dfDate.format(Calendar.getInstance().getTime());
        DateFormat dfTime = new SimpleDateFormat("HH:mm:ss");
        final String time1 = dfTime.format(Calendar.getInstance().getTime());


        int ident=0;

        ident=timetype%2;


        if(ident==0){
            timelog.setText("Time-Out ("+timetype+")");
        }
        else{
            timelog.setText("Time-In ("+timetype+")");
        }


        timelog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{

                    myClass.InserTransac(ids,bID,timetype,time1,date1);
                    String[] ar=myClass.getUsercodeName(ids);
                    String usercode=ar[0];
                    String commm="";
                    String timeT=myClass.getTimetype(timetype);
                    myClass.InsertToFinal(usercode,timeT,date1,time1,bname,commm);
                    timeee.setText(time1);
                    Toast.makeText(Time_Log.this,"succesful",Toast.LENGTH_SHORT).show();
                    timelog.setTextColor(Color.parseColor("#FFF0BB6F"));
                    timelog.setEnabled(false);
                    savecomment.setTextColor(Color.parseColor("#ffffff"));
                    savecomment.setEnabled(true);
                    comment.setEnabled(true);



                }catch (Exception ex){
                    Toast.makeText(Time_Log.this,ex.getMessage().toString(),Toast.LENGTH_LONG).show();
                }





            }
        });

        savecomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String com=comment.getText().toString();
                if(TextUtils.isEmpty(com)){
                    comment.setError("Empty field");
                }else{
                    try{
                        int count=myClass.getCount();
                        myClass.updateFinal(com,code,count);
                        Toast.makeText(Time_Log.this,"Successfully saved",Toast.LENGTH_SHORT).show();
                        comment.setText("");
                    }catch (Exception ex){
                        Toast.makeText(Time_Log.this,ex.getMessage().toString(),Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });
    }

    public void tohome(View view) {
        ((MyApp)getApplication()).cancel();
        onSessionLogout();

    }

    @Override
    public void onBackPressed() {
        Intent in=new Intent(Time_Log.this,Time_Log.class);
        in.putExtra("id",getIntent().getStringExtra("id"));
        in.putExtra("timeTypeID",getIntent().getStringExtra("timeTypeID"));
        in.putExtra("branchID",getIntent().getStringExtra("branchID"));
        in.putExtra("myname",getIntent().getStringExtra("myname"));
        in.putExtra("mycode",getIntent().getStringExtra("mycode"));
        in.putExtra("branchname",getIntent().getStringExtra("branchname"));
        startActivity(in);
    }


}
