package com.example.mytime;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class BaseApp extends AppCompatActivity implements LogoutListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyApp)getApplication()).registerSessionListener(this);
        ((MyApp)getApplication()).startUserSession();
    }


    @Override
    public void onUserInteraction() {
        super.onUserInteraction();

        ((MyApp)getApplication()).onUserInteraction();
    }

    @Override
    public void onSessionLogout() {
        finish();
        startActivity(new Intent(this,Emp_log.class));

    }


}
