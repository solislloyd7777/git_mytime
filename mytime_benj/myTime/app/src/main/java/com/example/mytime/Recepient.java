package com.example.mytime;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class Recepient extends AppCompatActivity {

    Button save;
    TextView current;
    EditText email;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepient);

        save=(Button)findViewById(R.id.save);
        current=(TextView)findViewById(R.id.email_txt);
        email=(EditText)findViewById(R.id.email);


        final MyClass myClass=new MyClass(this);
        myClass.StartWork();
        db=myClass.getWritableDatabase();

        current.setText(myClass.getRecipient());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email_txt=email.getText().toString();
                if(TextUtils.isEmpty(email_txt)){
                    email.setText("Please provide recipient");
                }else{
                    myClass.insertRecepient(email_txt);
                    Toast.makeText(Recepient.this,"Changed successfully",Toast.LENGTH_SHORT).show();
                    Intent in=new Intent(Recepient.this,Recepient.class);
                    in.putExtra("name",getIntent().getStringExtra("name"));
                    in.putExtra("count",getIntent().getStringExtra("count"));
                    startActivity(in);
                }

            }
        });





    }
    @Override
    public void onBackPressed() {
        Intent in=new Intent(Recepient.this,compiler.class);
        in.putExtra("name",getIntent().getStringExtra("name"));
        in.putExtra("count",getIntent().getStringExtra("count"));
        startActivity(in);
    }
}
