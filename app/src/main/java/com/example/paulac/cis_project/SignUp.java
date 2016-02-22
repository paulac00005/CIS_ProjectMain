package com.example.paulac.cis_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SignUp extends AppCompatActivity implements View.OnClickListener {


    EditText etUsername, etPassword, etReenter,  etAnswer;
    Button signup, cam, gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Spinner security = (Spinner)findViewById(R.id.spinner);
        String[] questions = new String[]{"--","What is the name of your first pet?",
                "What is your favorite movie?",
                "What is your favorite food?",
                "What is your favorite drink?",
                "What is your favorite color?"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, questions);
        security.setAdapter(adapter);

        etUsername = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText)findViewById(R.id.etPassword);
        etReenter = (EditText)findViewById(R.id.etReenter);
        etAnswer = (EditText)findViewById(R.id.etAnswer);
        cam = (Button)findViewById(R.id.camera);
        gallery = (Button)findViewById(R.id.gallery);
        signup = (Button)findViewById(R.id.signup);


        cam.setOnClickListener(this);
        gallery.setOnClickListener(this);
        signup.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.signup:

                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String key = "70930f27";

                User registeredData = new User(username, password, key);


                break;
        }
    }
}
