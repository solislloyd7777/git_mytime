package com.example.mytime;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;


public class compiler extends AppCompatActivity {
    Button fromdate,todate,comp,send;
    private DatePickerDialog.OnDateSetListener mydate,mydate1;
    private static final String TAG="Compile";

    MyClass myClass;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compiler);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        fromdate=findViewById(R.id.fromdate);
        todate=findViewById(R.id.todate);
        comp=findViewById(R.id.compile);
        send=findViewById(R.id.send);






        send.setEnabled(false);
        send.setTextColor(Color.parseColor("#FFF0BB6F"));




        myClass=new MyClass(this);
        myClass.StartWork();
        db=myClass.getWritableDatabase();

        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal= Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(compiler.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mydate,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mydate=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d(TAG,"onDateSet: date: "+year+"/"+(month+1)+"/"+dayOfMonth);

                if(month<10 && dayOfMonth<10){
                    String from_date=year+"/0"+(month+1)+"/0"+dayOfMonth;
                    fromdate.setText(from_date);
                }
                else if(month<10 && dayOfMonth>=10){
                    String from_date=year+"/0"+(month+1)+"/"+dayOfMonth;
                    fromdate.setText(from_date);
                }
                else if(month>=10 && dayOfMonth<10){
                    String from_date=year+"/"+(month+1)+"/0"+dayOfMonth;
                    fromdate.setText(from_date);
                }else if(month>=10 && dayOfMonth>=10){
                    String from_date=year+"/"+(month+1)+"/"+dayOfMonth;
                    fromdate.setText(from_date);
                }
            }

        };


        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal= Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(compiler.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mydate1,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mydate1=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d(TAG,"onDateSet: date: "+year+"/"+(month+1)+"/"+dayOfMonth);



                if(month<10 && dayOfMonth<10){
                    String to_date=year+"/0"+(month+1)+"/0"+dayOfMonth;
                    todate.setText(to_date);
                }
                else if(month<10 && dayOfMonth>=10){
                    String to_date=year+"/0"+(month+1)+"/"+dayOfMonth;
                    todate.setText(to_date);
                }
                else if(month>=10 && dayOfMonth<10){
                    String to_date=year+"/"+(month+1)+"/0"+dayOfMonth;
                    todate.setText(to_date);
                }else if(month>=10 && dayOfMonth>=10){
                    String to_date=year+"/"+(month+1)+"/"+dayOfMonth;
                    todate.setText(to_date);
                }

            }

        };





        comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                exportDatabase();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                try{

                    //String csv = (Environment.getExternalStorageDirectory().getAbsolutePath() ); // Here csv file name is MyCsvFile.csv

                    File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+ "/"+getIntent().getStringExtra("name")+".csv");
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    emailIntent.setType("text/csv");

                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{myClass.getRecipient()});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, getIntent().getStringExtra("name")+"("+fromdate.getText().toString()+"-"+todate.getText().toString()+")");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "");


                    Uri uri ;
                    if (Build.VERSION.SDK_INT < 24) {
                        uri = Uri.fromFile(file);
                    } else {
                        uri = FileProvider.getUriForFile(compiler.this, BuildConfig.APPLICATION_ID + ".provider",file);// My work-around for new SDKs, causes ActivityNotFoundException in API 10.
                    }
                    emailIntent.putExtra(Intent.EXTRA_STREAM, uri);

                    startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));
                }catch(Exception ex){
                    Toast.makeText(compiler.this,ex.getMessage().toString(),Toast.LENGTH_LONG).show();
                }

            }
        });



    }









    public boolean exportDatabase() {


        String[] zero={"A","+","z","A","+","z"};
        String[] one={"~","B","y","~","B","y"};
        String[] two={"C","!","x","C","!","x"};
        String[] three={"@","D","w","@","D","w"};
        String[] four={"E","$","v","E","$","v"};
        String[] five={"#","F","u","#","F","u"};
        String[] six={"%","G","t","%","G","t"};
        String[] seven={"H","^","s","H","^","s"};
        String[] eight={"&","I","r","&","I","r"};
        String[] nine={"J","*","q","J","*","q"};
        String[] i={"_","M",":","_","M",":"};
        String[] n={"N","-","]","N","-","]"};
        String[] o={"4","O","p","4","O","p"};
        String[] u={"P","<","o","P","<","o"};
        String[] t={">","Q","n",">","Q","n"};
        String[] colon={"K","(","/","K","(","/"};
        String[] slash={")","L","m",")","L","m"};



        MyClass myClass = new MyClass(this);

        if (fromdate.getText().toString().equals("From Date") || todate.getText().toString().equals("To Date")) {

            Toast.makeText(compiler.this, "Invalid Input for Date", Toast.LENGTH_SHORT).show();

        } else if ((fromdate.getText().toString()).compareTo(todate.getText().toString()) > 0) {

            Toast.makeText(compiler.this, "Invalid Input for Date", Toast.LENGTH_SHORT).show();

        }
        else{

        /**First of all we check if the external storage of the device is available for writing.
         * Remember that the external storage is not necessarily the sd card. Very often it is
         * the device storage.
         */
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            return false;
        } else {
            //We use the Download directory for saving our .csv file.
            File exportDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }

            File file;
            PrintWriter printWriter = null;
            try {
                file = new File(exportDir, myClass.getBranchName() + ".csv");
                file.createNewFile();
                printWriter = new PrintWriter(new FileWriter(file));

                /**This is our database connector class that reads the data from the database.
                 * The code of this class is omitted for brevity.
                 */
                SQLiteDatabase db = myClass.getReadableDatabase(); //open the database for reading

                /**Let's read the first table of the database.
                 * getFirstTable() is a method in our DBCOurDatabaseConnector class which retrieves a Cursor
                 * containing all records of the table (all fields).
                 * The code of this class is omitted for brevity.
                 *
                 *where date between '"+fromdate.getText().toString()+"' and '"+todate.getText().toString()+"'
                 *
                 * USERCODE,TIMETYPE,DATE,TIME
                 *<0936O59,>_79Q819,52n9,>M79
                 */
                printWriter.println("<0936O59,>_79Q819,52n9,>M79,BRANCH,CONCERNS");
                Cursor curCSV = db.rawQuery("select emp_userCode,time_type,date,time,branch,comment from mh_final " +
                        "where date between '" + fromdate.getText().toString() + "' and '" + todate.getText().toString()
                        + "' order by emp_userCode,date,time", null);
                //Write the name of the table and the name of the columns (comma separated values) in the .csv file.
                if(curCSV.getCount()>0){

                    Random r1=new Random();


                    while (curCSV.moveToNext()){


                        int r=r1.nextInt(5);
                        String usercode = curCSV.getString(0);
                        String timeT = curCSV.getString(1);
                        String date = curCSV.getString(2);
                        String time = curCSV.getString(3);
                        String branch=curCSV.getString(4);
                        String comment=curCSV.getString(5);


                        usercode=usercode.replace("0",zero[r]).replace("1",one[r]).replace("2",two[r])
                                .replace("3",three[r]).replace("4",four[r]).replace("5",five[r])
                                .replace("6",six[r]).replace("7",seven[r]).replace("8",eight[r])
                                .replace("9",nine[r]);
                        timeT=timeT.replace("I",i[r]).replace("N",n[r]).replace("O",o[r])
                                .replace("U",u[r]).replace("T",t[r]);

                        date=date.replace("0",zero[r]).replace("1",one[r]).replace("2",two[r])
                                .replace("3",three[r]).replace("4",four[r]).replace("5",five[r])
                                .replace("6",six[r]).replace("7",seven[r]).replace("8",eight[r])
                                .replace("9",nine[r]).replace("/",slash[r]);

                        time=time.replace("0",zero[r]).replace("1",one[r]).replace("2",two[r])
                                .replace("3",three[r]).replace("4",four[r]).replace("5",five[r])
                                .replace("6",six[r]).replace("7",seven[r]).replace("8",eight[r])
                                .replace("9",nine[r]).replace(":",colon[r]);





                        /**Create the line to write in the .csv file.
                         * We need a String where values are comma separated.
                         * The field date (Long) is formatted in a readable text. The amount field
                         * is converted into String.
                         */
                        String record = usercode + "," + timeT + "," + date + "," + time +"," + branch + "," + comment;
                        printWriter.println(record); //write the record in the .csv file

                }

                    Toast.makeText(compiler.this, "compiled success", Toast.LENGTH_SHORT).show();


                }else{
                    Toast.makeText(compiler.this, "Empty Record", Toast.LENGTH_SHORT).show();
                }


                curCSV.close();
                db.close();
            } catch (Exception exc) {

                return false;
            } finally {
                if (printWriter != null) printWriter.close();
            }

            //If there are no errors, return true.

            send.setEnabled(true);
            send.setTextColor(Color.parseColor("#ffffff"));

        }


    }

        return true;
    }

    public void logout(View view) {

        Intent in=new Intent(compiler.this,Admin_log.class);
        startActivity(in);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(compiler.this,Choose.class);
        intent.putExtra("name",getIntent().getStringExtra("name"));
        intent.putExtra("count",getIntent().getStringExtra("count"));
        startActivity(intent);


    }


    public void to_recepient(View view) {
        Intent in=new Intent(compiler.this,Recepient.class);
        in.putExtra("name",getIntent().getStringExtra("name"));
        in.putExtra("count",getIntent().getStringExtra("count"));
        startActivity(in);
    }
}


