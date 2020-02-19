package com.example.mytime;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class Updata_delete extends AppCompatActivity {

    EditText name, usercode, password, c_pass;
    TextView myposition;
    Button update, delete;
    MyClass myClass;
    Spinner spin;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_delete);

        myClass = new MyClass(this);
        myposition=(TextView)findViewById(R.id.position_txt);
        name = (EditText) findViewById(R.id.name_txt);
        usercode = (EditText) findViewById(R.id.user_txt);
        password = (EditText) findViewById(R.id.pass_txt);
        c_pass = (EditText) findViewById(R.id.cpass_txt);
        spin = (Spinner) findViewById(R.id.spinner);
        update = (Button) findViewById(R.id.update);
        delete = (Button) findViewById(R.id.delete);

        employee emp = (employee) getIntent().getExtras().getSerializable("EMPLOYEE");

        final int id = emp.getId();

        name.setText(emp.getName());
        usercode.setText(emp.getUsername());
        password.setText(emp.getPassword());
        c_pass.setText(emp.getPassword());




        ArrayList<String> list=myClass.getAllPosition();
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.txt,list);
        spin.setAdapter(adapter);

        String[] ar=myClass.getPositionID(usercode.getText().toString());

        //int posId=myClass.getPositionID(usercode.getText().toString());

        int posId=Integer.parseInt(ar[1]);


        String pos=myClass.getDescription(posId);


        myposition.setText(pos);
        myClass=new MyClass(this);
        myClass.StartWork();
        db=myClass.getWritableDatabase();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final MyClass myClass=new MyClass(Updata_delete.this);

                final int positionID=myClass.PositionID(spin.getSelectedItem().toString());



                //Cursor cursor=db.rawQuery("select mh_position_ID from mh_position where mh_description='"+pos+"'",null);
                //cursor.moveToFirst();
                //final int posId=;



                final String name1=name.getText().toString();
                if(TextUtils.isEmpty(name1)){
                    name.setError("Please provide name");
                    return;
                }
                final String usercode1=usercode.getText().toString();
                if(TextUtils.isEmpty(usercode1)){
                    usercode.setError("Please provide username");
                    return;
                }
                final String password1=password.getText().toString();
                if(TextUtils.isEmpty(password1)){
                    password.setError("Please provide password");
                    return;
                }
                String c_pass1=c_pass.getText().toString();
                if(TextUtils.isEmpty(c_pass1)){
                    c_pass.setError("Please provide confirm password");
                    return;
                }
                if(usercode1.length()>6){
                    usercode.setError("maximum of 6 digits allowed");
                    usercode.setText("");
                }
                else{
                    if (password1.equals(c_pass1)) {
                        AlertDialog dialog= new AlertDialog.Builder(Updata_delete.this)
                                .setMessage("Are you sure you want to update?")
                                .setPositiveButton("Yes",null)
                                .setNegativeButton("Cancel",null)
                                .show();
                        Button positiveButton=dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                try{

                                    myClass.updateInfo(positionID,name1,usercode1,password1,id);
                                    Toast.makeText(getApplicationContext(),"successfully updated",Toast.LENGTH_LONG).show();
                                    Intent in=new Intent(Updata_delete.this,Emp_list.class);
                                    in.putExtra("name",getIntent().getStringExtra("name"));
                                    in.putExtra("count",getIntent().getStringExtra("count"));
                                    startActivity(in);

                                }catch (Exception ex){
                                    Toast.makeText(Updata_delete.this,ex.getMessage().toString(),Toast.LENGTH_LONG).show();
                                }

                            }
                        });


                    }else{
                        Toast.makeText(Updata_delete.this,"Password didn't match",Toast.LENGTH_SHORT).show();

                        password.setText("");
                        c_pass.setText("");
                    }
                }


            }
        });




        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog dialog= new AlertDialog.Builder(Updata_delete.this)
                        .setMessage("Are you sure you want to delete?")
                        .setPositiveButton("Yes",null)
                        .setNegativeButton("Cancel",null)
                        .show();
                Button positiveButton=dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try{

                            myClass.deleteInfo(id);
                            //db.execSQL("delete from mh_emp where mh_emp_ID='"+id+"'");
                            Toast.makeText(Updata_delete.this,"Successfully deleted",Toast.LENGTH_SHORT).show();
                            name.setText("");
                            usercode.setText("");
                            password.setText("");
                            c_pass.setText("");

                            Intent in=new Intent(Updata_delete.this,Emp_list.class);
                            in.putExtra("name",getIntent().getStringExtra("name"));
                            in.putExtra("count",getIntent().getStringExtra("count"));
                            startActivity(in);



                        }catch (Exception ex){
                            Toast.makeText(Updata_delete.this,ex.getMessage().toString(),Toast.LENGTH_LONG).show();

                        }

                    }
                });





            }
        });
    }


    public void toadmin(View view) {
        Intent in=new Intent(Updata_delete.this,Admin_log.class);
        startActivity(in);
    }
    @Override
    public void onBackPressed() {

        Intent in=new Intent(Updata_delete.this,Emp_list.class);
        in.putExtra("name",getIntent().getStringExtra("name"));
        in.putExtra("count",getIntent().getStringExtra("count"));
        startActivity(in);
    }
}
