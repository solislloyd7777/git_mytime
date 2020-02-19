package com.example.mytime;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class admin_pass extends AppCompatActivity {

    EditText current,new_pass,c_pass;
    Button save;
    MyClass myClass;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pass);

        current=(EditText)findViewById(R.id.current_pass);
        new_pass=(EditText)findViewById(R.id.new_pass);
        c_pass=(EditText)findViewById(R.id.c_pass);
        save=(Button)findViewById(R.id.save);



        myClass=new MyClass(this);
        myClass.StartWork();
        db=myClass.getWritableDatabase();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cur=current.getText().toString();

                if(TextUtils.isEmpty(cur)){
                    current.setError("Please provide name");
                    return;
                }
                String newpass=new_pass.getText().toString();
                if(TextUtils.isEmpty(newpass)){
                    new_pass.setError("Please provide userCode");
                    return;
                }


                String cpass=c_pass.getText().toString();
                if(TextUtils.isEmpty(cpass)){
                    c_pass.setError("Please provide confirm password");
                    return;
                }

                if(newpass.equals(cpass)){

                    Boolean checkpass=myClass.verifyPassword(newpass);

                    if(checkpass==true){

                        Toast.makeText(admin_pass.this,"Password unavailable",Toast.LENGTH_SHORT).show();
                        new_pass.setText("");
                        new_pass.setError("Password unavailable");
                        c_pass.setText("");


                    }else{
                        String pass=getIntent().getStringExtra("num");
                        int pass1=Integer.parseInt(pass);
                        Boolean check1=myClass.AdminPass(current.getText().toString());
                        if(check1==true){
                            try{
                                myClass.updateAdminPass(newpass,pass1);
                                //db.execSQL("update mh_admin set admin_pass='"+newpass+"' where admin_ID='"+pass1+"'");
                                Toast.makeText(admin_pass.this,"Successfully changed",Toast.LENGTH_SHORT).show();
                                current.setText("");
                                new_pass.setText("");
                                c_pass.setText("");
                            }catch (Exception ex){
                                Toast.makeText(admin_pass.this,ex.getMessage().toString(),Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            current.setError("Wrong password");
                            current.setText("");
                        }
                    }





                }else{
                    c_pass.setError("Password didn't match");
                }
            }
        });


    }

    public void logout(View view) {

        Intent intent= new Intent(admin_pass.this,Admin_log.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        Intent in=new Intent(admin_pass.this,Admin_change.class);
        in.putExtra("name",getIntent().getStringExtra("name"));
        in.putExtra("count",getIntent().getStringExtra("count"));
        startActivity(in);


    }
}
