package com.example.paulac.cis_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Drafts extends AppCompatActivity implements View.OnClickListener{

    TextView unametv;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drafts);

        unametv = (TextView)findViewById(R.id.unametv);
        Button blogout = (Button)findViewById(R.id.bLogout);

        blogout.setOnClickListener(this);

        userLocalStore = new UserLocalStore(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(authenticate() == true){
            displayUserDetails();
        }else{
            startActivity(new Intent(this, Login.class));
        }

    }


    private boolean authenticate(){
        return userLocalStore.getUserLoggedIn();
    }


    public void displayUserDetails(){
        User user = userLocalStore.getLoggedInUser();
        unametv.setText(user.username);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bLogout:

                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);

                startActivity(new Intent(this, Login.class));
                break;
        }
    }
}
