package com.example.mytime;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;

public class Emp_log extends AppCompatActivity {

    EditText usercode, password;
    TextView branch1;
    Button sign_in;

    MyClass myClass;
    SQLiteDatabase db;
    Time_Log timelog;
    Admin_log ad;
    Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_log);



        usercode=(EditText)findViewById(R.id.user_txt);
        branch1=(TextView)findViewById(R.id.branch_text);
        password=(EditText)findViewById(R.id.password);
        sign_in=(Button)findViewById(R.id.login_btn);



        myClass=new MyClass(this);
        myClass.StartWork();
        db=myClass.getWritableDatabase();

        String[] ar=myClass.branchNameID();

       /* Cursor cursor;
        cursor=db.rawQuery("select mh_branch_ID,mh_branch_name from mh_branch where mh_setBranch =1 ",null);
        cursor.moveToFirst();
        final int branchID=cursor.getInt(0);
        final String branchname=cursor.getString(1);*/

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

        final int branchID=Integer.parseInt(ar[0]);
        final String branchname=ar[1];
        branch1.setText(branchname);

        DateFormat dfDate = new SimpleDateFormat("yyyy/MM/dd");
        final String date1=dfDate.format(Calendar.getInstance().getTime());

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String user=usercode.getText().toString();
                String pass=password.getText().toString();
                Boolean check1=myClass.userPass(user,pass);
                int bID=branchID;


                if(check1==true){
                    try{

                        String[] ar=myClass.getPositionID(user);

                        /*Cursor cursor;
                        cursor=db.rawQuery("select mh_emp_ID, mh_position_ID,mh_name,mh_userCode from mh_emp where mh_userCode='"+user+"'",null);
                        cursor.moveToFirst();
                        int name_id =cursor.getInt(0);
                        int pos=cursor.getInt(1);
                        String maname=cursor.getString(2);
                        String code=cursor.getString(3);*/

                        int name_id=Integer.parseInt(ar[0]);
                        int pos=Integer.parseInt(ar[1]);
                        String maname=ar[2];
                        String code=ar[3];
                        String nameID=String.valueOf(name_id);

                        /*Cursor curs;
                        curs=db.rawQuery(
                                "select mt.mh_timeType_ID\n" +
                                        " from mh_timeType mt\n" +
                                        " where mt.sequence = (\n" +
                                        "select mt1.sequence\n" +
                                        "from mh_transac mh \n" +
                                        "join mh_timeType mt1 on mh.mh_timeType_id = mt1.mh_timeType_id\n" +
                                        "where mh.mh_transac_ID = (select max(mh_transac_ID) from mh_transac where mh_emp_id = '"+name_id+"')\n" +

                                        " and mh.date ='"+date1+"'"+
                                        ") + 1",null);
                                        /* include filter on dates



                        if(!curs.moveToFirst()) {
                            curs = db.rawQuery("select min(mh_timeType_ID) from mh_timeType", null);
                            curs.moveToFirst();
                        }
                        int timetypeID=curs.getInt(0);

                        curs=db.rawQuery("select mh_max_allow from mh_position where mh_position_ID='"+pos+"'",null);
                        curs.moveToFirst();
                        int max=curs.getInt(0);*/

                        int[] array=myClass.getTimeType(name_id,date1,pos);

                        int timetypeID=array[0];
                        int max=array[1];

                        if(timetypeID > max){
                            Intent intent=new Intent(Emp_log.this,Emp_log.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(),"You've reach the maximum allowable Time-Log!!",Toast.LENGTH_SHORT).show();
                        }else{
                            openWindow(nameID, timetypeID,bID,maname,code,branchname);
                        }




                    }catch (Exception ex){
                        Toast.makeText(Emp_log.this,ex.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }


                }else{
                    Toast.makeText(getApplicationContext(),"Wrong usercode or password",Toast.LENGTH_SHORT).show();


                }




            }
        });


    }


    private void openWindow (String nameID, int timetypeID,int bID,String maname,String code,String bname)
    {

        Intent intent = new Intent(Emp_log.this,Time_Log.class);
        intent.putExtra("id",nameID);
        intent.putExtra("timeTypeID", String.valueOf(timetypeID));
        intent.putExtra("branch",String.valueOf(bID));
        intent.putExtra("myname",maname);
        intent.putExtra("mycode",code);
        intent.putExtra("branchname",bname);
        startActivity(intent);
        usercode.setText("");
        password.setText("");
    }



    public void to_admin(View view) {
        Intent intent=new Intent(Emp_log.this,Admin_log.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {



        //moveTaskToBack(true);
        //android.os.Process.killProcess(android.os.Process.myPid());
        //System.exit(1);



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




        //Intent in=new Intent(Emp_log.this,Emp_log.class);
        //startActivity(in);
    }
}
