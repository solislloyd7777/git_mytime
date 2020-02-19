package com.example.mytime;

import android.content.Intent;
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

public class Addbranch extends AppCompatActivity {

    Button save,adds,update,delete,edit;
    TextView branch;
    EditText add_branch;
    Spinner spin;

    MyClass myClass;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbranch);

        MyClass myClass=new MyClass(this);
        spin=(Spinner)findViewById(R.id.spinner);

        branch=(TextView)findViewById(R.id.branch_text);
        add_branch=(EditText) findViewById(R.id.addbranch);


        adds=(Button)findViewById(R.id.sAdd);
        update=(Button)findViewById(R.id.update);
        delete=(Button)findViewById(R.id.delete);
        edit=(Button)findViewById(R.id.edit);






        update.setTextColor(Color.parseColor("#FFF0BB6F"));
        update.setEnabled(false);


        ArrayList<String> list=myClass.getAll();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.txt,list);
        spin.setAdapter(adapter);

        myClass=new MyClass(this);
        myClass.StartWork();
        db=myClass.getWritableDatabase();

        //String branch1=spin.getSelectedItem().toString();
        //add_branch.setText(branch1);


        final MyClass finalMyClass = myClass;

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String branch1=spin.getSelectedItem().toString();
                add_branch.setText(branch1);
                update.setTextColor(Color.parseColor("#ffffff"));
                update.setEnabled(true);

            }
        });
        final MyClass finalMyClass1 = myClass;
        adds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Boolean checkbranch= finalMyClass.Checkbranch(add_branch.getText().toString());
                if(checkbranch==true){

                    AlertDialog dialog= new AlertDialog.Builder(Addbranch.this)
                            .setMessage("Are you sure you want to Add this Branch?")
                            .setPositiveButton("Yes",null)
                            .setNegativeButton("Cancel",null)
                            .show();
                    Button positiveButton=dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String branchname1=add_branch.getText().toString();
                            if(TextUtils.isEmpty(branchname1)){
                                add_branch.setError("Please provide Branch Name");
                                return;
                            }else{
                                try{
                                    finalMyClass1.AddBranch(branchname1);
                                    Toast.makeText(Addbranch.this,"Successfully Added",Toast.LENGTH_LONG).show();
                                    add_branch.setText("");
                                    Intent intent=new Intent(Addbranch.this,Branch.class);
                                    intent.putExtra("name",getIntent().getStringExtra("name"));
                                    intent.putExtra("count",getIntent().getStringExtra("count"));
                                    startActivity(intent);
                                    startActivity(intent);
                                }catch (Exception ex){
                                    Toast.makeText(Addbranch.this,ex.getMessage().toString(),Toast.LENGTH_LONG).show();
                                }

                            }

                        }
                    });



                }else{
                    add_branch.setError("Branch Name already Exist");
                    Toast.makeText(Addbranch.this,"Branch Name already Exist",Toast.LENGTH_LONG).show();
                }






            }
        });


        final MyClass finalMyClass2 = myClass;
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog dialog= new AlertDialog.Builder(Addbranch.this)
                        .setMessage("Are you sure you want to Update this Branch?")
                        .setPositiveButton("Yes",null)
                        .setNegativeButton("Cancel",null)
                        .show();
                Button positiveButton=dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(TextUtils.isEmpty(add_branch.getText().toString())){
                            add_branch.setError("Please provide Branch Name");
                            return;
                        }else{
                            try{
                                finalMyClass2.updateBranch(add_branch.getText().toString(),spin.getSelectedItem().toString());
                                Toast.makeText(Addbranch.this,"Successfully Updated",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(Addbranch.this,Branch.class);
                                intent.putExtra("name",getIntent().getStringExtra("name"));
                                intent.putExtra("count",getIntent().getStringExtra("count"));
                                startActivity(intent);

                            }catch (Exception ex){
                                Toast.makeText(Addbranch.this,ex.getMessage().toString(),Toast.LENGTH_SHORT).show();
                            }

                        }

                    }
                });





            }
        });

        final MyClass finalMyClass3 = myClass;
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog dialog= new AlertDialog.Builder(Addbranch.this)
                        .setMessage("Are you sure you want to Delete this Branch?")
                        .setPositiveButton("Yes",null)
                        .setNegativeButton("Cancel",null)
                        .show();
                Button positiveButton=dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if(TextUtils.isEmpty(add_branch.getText().toString())){
                            add_branch.setError("Please provide Branch Name");
                            return;
                        }else{


                            try{

                                finalMyClass3.deleteBranch(spin.getSelectedItem().toString());
                                Toast.makeText(Addbranch.this,"Successfully Deleted",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(Addbranch.this,Branch.class);
                                intent.putExtra("name",getIntent().getStringExtra("name"));
                                intent.putExtra("count",getIntent().getStringExtra("count"));
                                startActivity(intent);


                            }catch (Exception ex){
                                Toast.makeText(Addbranch.this,ex.getMessage().toString(),Toast.LENGTH_SHORT).show();
                            }

                        }

                    }
                });


            }
        });


    }

    public void toadmin(View view) {

        Intent intent=new Intent(Addbranch.this,Branch.class);

        startActivity(intent);
    }

    @Override
    public void onBackPressed() {


        Intent in=new Intent(Addbranch.this,Branch.class);
        in.putExtra("name",getIntent().getStringExtra("name"));
        in.putExtra("count",getIntent().getStringExtra("count"));
        startActivity(in);
    }
}
